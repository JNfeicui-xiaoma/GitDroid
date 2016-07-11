package com.feicui.gitdroid.repo;

import android.os.AsyncTask;
import com.feicui.gitdroid.commonUtil.PtrPageView;
import com.feicui.gitdroid.netWork.GitHubClient;
import com.feicui.gitdroid.repo.modle.Language;
import com.hannesdorfmann.mosby.mvp.MvpNullObjectBasePresenter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import retrofit2.Callback;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Administrator on 2016/7/4.
 */
public class ReopListPresenter extends MvpNullObjectBasePresenter<PtrPageView>{
    private  int count = 0;
    private Call<RepoResult> resultCall;
    private Language mLanguage;
    public ReopListPresenter(Language language){
        this.mLanguage=language;
    }
    //这是上拉加载更多的视图层的业务逻辑
    public void loadMore() {
        getView().showLoadMoreLoading();
        resultCall=GitHubClient.getInstance().searchRepo("language:"+"java",count);
        resultCall.enqueue(loadMoreCallback);
    }


    //下拉刷新视图层业务逻辑
    public void loadData() {
        getView().hideLoadMore(); // 隐藏“加载更多”视图
        getView().showContentView(); // 显示内容(显示出列表)
        count = 1; // 刷新永远是第1页
        resultCall = GitHubClient.getInstance().searchRepo("language:"+"java", count);
        resultCall.enqueue(reposCallback);
    }
    private Callback<RepoResult> reposCallback = new Callback<RepoResult>() {
        @Override
        public void onResponse(Call<RepoResult> call, Response<RepoResult> response) {
            getView().stopRefersh();// 视图停止刷新
            RepoResult repoResult = response.body();
            if(repoResult == null){
                getView().showErroView("result is null");
                return;
            }
            // 当前搜索的语言下，没有任何仓库
            if(repoResult.getTotalCount() <= 0){
                getView().refreshData(null);
                getView().showEmptyView();
                return;
            }
            // 取出当前搜索的语言下，所有仓库
            List<Repo> repoList = repoResult.getRepoList();
            getView().refreshData(repoList); // 视图数据刷新
            count = 2; // 下拉刷新成功，当前为第1页,下一页则为第2页
        }

        @Override public void onFailure(Call<RepoResult> call, Throwable t) {
            getView().stopRefersh(); // 视图停止刷新
            getView().showErroView(t.getMessage());
        }
    };

    private Callback<RepoResult> loadMoreCallback=new Callback<RepoResult>() {
        @Override
        public void onResponse(Call<RepoResult> call, Response<RepoResult> response) {
            getView().hideLoadMore();
            RepoResult repoResult=response.body();
            if (repoResult==null){
                getView().showLoadMoreErro("result is null");
                return;
            }
            //没有更多数据了
            if (repoResult.getTotalCount()<=0){
                getView().showLoadMoreEnd();
                return;
            }
            List<Repo> repoList=repoResult.getRepoList();
            getView().addMoreData(repoList);

            count ++;
        }

        @Override
        public void onFailure(Call<RepoResult> call, Throwable t) {
            getView().hideLoadMore();
            getView().showLoadMoreErro(t.getMessage());
        }
    };
}
