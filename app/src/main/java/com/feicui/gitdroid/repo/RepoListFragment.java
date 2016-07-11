package com.feicui.gitdroid.repo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.feicui.gitdroid.R;
import com.feicui.gitdroid.commonUtil.ActivityUtil;
import com.feicui.gitdroid.commonUtil.LoadMoreView;
import com.feicui.gitdroid.commonUtil.PtrPageView;
import com.feicui.gitdroid.commonUtil.PtrView;
import com.feicui.gitdroid.favorite.dao.DbHelper;
import com.feicui.gitdroid.favorite.dao.LocalRepoDao;
import com.feicui.gitdroid.favorite.model.LocalRepo;
import com.feicui.gitdroid.favorite.model.RepoConverter;
import com.feicui.gitdroid.repo.modle.Language;
import com.feicui.gitdroid.repo.repoInfo.RepoInfoActivity;
import com.hannesdorfmann.mosby.mvp.MvpFragment;
import com.mugen.Mugen;
import com.mugen.MugenCallbacks;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.header.StoreHouseHeader;

/**
 * Created by Administrator on 2016/6/30.
 */
public class RepoListFragment extends MvpFragment<PtrPageView,ReopListPresenter> implements PtrPageView {
    private static final String KEY_LANGUAGE = "key_language";
    //本Fragment的主要内容
    @Bind(R.id.lvRepos)ListView listView;
    //用于下拉刷新
    @Bind(R.id.ptrClassicFrameLayout) PtrClassicFrameLayout mPtrClassicFrameLayout;
    @Bind(R.id.errorView) TextView errorView;
    //下拉刷新如果得倒的是空的 显示此视图
    @Bind(R.id.emptyView) TextView emptyView;
    //下拉刷新发生错误  显示此视图
    private RepoAdapter adapter;
    private FooterView footerView;//上拉加载更多视图
    private ActivityUtil mActivityUtil;


    public static RepoListFragment getInstance(Language language) {
        RepoListFragment repoListFragment = new RepoListFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("KEY_LANGUAGE",language);
        repoListFragment.setArguments(bundle);
        return repoListFragment;
    }

    @Override
    public ReopListPresenter createPresenter() {
        return new ReopListPresenter(getLanguage());
    }
    private Language getLanguage() {
        return (Language) getArguments().getSerializable(KEY_LANGUAGE);
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_repo_list, container, false);
    }



    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        adapter =new RepoAdapter();
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // 获取当前click的仓库
                Repo repo = adapter.getItem(position);
                RepoInfoActivity.open(getContext(), repo);
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Repo repo = adapter.getItem(position);
                LocalRepo localRepo = RepoConverter.convert(repo);
                // 将当前长按的Repo(已转换为LocalRepo)添加到本地仓库表
                new LocalRepoDao(DbHelper.getInstance(getContext())).createOrUpdate((List<LocalRepo>) localRepo);
                mActivityUtil.showToast(R.string.set_favorite_success);
                return true;
            }
        });
        // 初始下拉刷新
        initPullToRefresh();
        // 初始上拉加载
        initLoadMoreScroll();
        // 如果当前页面没有数据，开始自动刷新
        if (adapter.getCount() == 0) {
            mPtrClassicFrameLayout.postDelayed(new Runnable() {
                @Override public void run() {
                    mPtrClassicFrameLayout.autoRefresh();
                }
            }, 200);
        }
    }

    private void initPullToRefresh() {
        // 使用本对象作为key，来记录上一次刷新时间，如果两次下拉间隔太近，不会触发刷新方法
        mPtrClassicFrameLayout.setLastUpdateTimeRelateObject(this);
        mPtrClassicFrameLayout.setBackgroundResource(R.color.colorRefresh);
        // 关闭Header所耗时长
        mPtrClassicFrameLayout.setDurationToCloseHeader(1500);

        // 以下的代码只是一个好玩的Header效果，非什么重要内容
        StoreHouseHeader header = new StoreHouseHeader(getContext());
        header.setPadding(0, 60, 0, 60);
        header.initWithString("I like " + getLanguage());
        mPtrClassicFrameLayout.setHeaderView(header);
        mPtrClassicFrameLayout.addPtrUIHandler(header);
        // 下拉刷新处理
        mPtrClassicFrameLayout.setPtrHandler(new PtrDefaultHandler() {
            @Override public void onRefreshBegin(PtrFrameLayout frame) {
                // 执行下拉刷新数据业务
                getPresenter().loadData();
            }
        });
    }
    private void initLoadMoreScroll() {
        footerView = new FooterView(getContext());
        // 当加载更多失败时，用户点击FooterView，会再次触发加载
        footerView.setErrorClickLister(new View.OnClickListener() {
            @Override public void onClick(View v) {
                presenter.loadMore();
            }
        });
        // 使用了一个微型库Mugen来处理滚动监听
        Mugen.with(listView, new MugenCallbacks() {
            // ListView滚动到底部，触发加载更多，此处要执行加载更多的业务逻辑
            @Override public void onLoadMore() {
                // 执行上拉加载数据业务
                getPresenter().loadMore();
            }

            // 是否正在加载，此方法用来避免重复加载
            @Override public boolean isLoading() {
                return listView.getFooterViewsCount() > 0 && footerView.isLoading();
            }

            // 是否所有数据都已加载
            @Override public boolean hasLoadedAllItems() {
                return listView.getFooterViewsCount() > 0 && footerView.isComplete();
            }
        }).start();
    }
    @OnClick({R.id.emptyView, R.id.errorView})
    public void autoRefresh() {
        mPtrClassicFrameLayout.autoRefresh();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


    //这是视图的实现
    @Override
    public void showContentView() {
        mPtrClassicFrameLayout.setVisibility(View.VISIBLE);
        emptyView.setVisibility(View.GONE);
        errorView.setVisibility(View.GONE);
    }

    @Override
    public void showErroView(String msg) {
        mPtrClassicFrameLayout.setVisibility(View.GONE);
        emptyView.setVisibility(View.GONE);
        errorView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showEmptyView() {
        mPtrClassicFrameLayout.setVisibility(View.GONE);
        emptyView.setVisibility(View.VISIBLE);
        errorView.setVisibility(View.GONE);
    }

    @Override
    public void refreshData(List<Repo> strings) {
        adapter.clear();
        if (strings == null) return;
        adapter.addAll(strings);
    }

    @Override
    public void stopRefersh() {
        mPtrClassicFrameLayout.refreshComplete();//下拉刷新完成
    }

    @Override
    public void showMessage(String mag) {
        Toast.makeText(getContext(), mag, Toast.LENGTH_SHORT).show();
    }

    //这是上拉加载更多视图层实现
    @Override
    public void addMoreData(List<Repo> datas) {
        if (datas == null) return;
        adapter.addAll(datas);
    }

    @Override
    public void hideLoadMore() {
        listView.removeFooterView(footerView);
    }
    @Override
    public void showLoadMoreLoading() {
        if (listView.getFooterViewsCount() == 0) {
            listView.addFooterView(footerView);
        }
        footerView.showLoading();
    }

    @Override
    public void showLoadMoreErro(String msg) {
        if (listView.getFooterViewsCount() == 0) {
            listView.addFooterView(footerView);
        }
        footerView.showError(msg);
    }

    @Override
    public void showLoadMoreEnd() {
        if (listView.getFooterViewsCount() == 0) {
            listView.addFooterView(footerView);
        }
        footerView.showComplete();
    }
}
