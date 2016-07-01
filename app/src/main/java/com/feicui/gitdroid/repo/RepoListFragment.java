package com.feicui.gitdroid.repo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.feicui.gitdroid.R;
import com.feicui.gitdroid.commonUtil.LoadMoreView;
import com.feicui.gitdroid.commonUtil.PtrView;
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

/**
 * Created by Administrator on 2016/6/30.
 */
public class RepoListFragment extends Fragment implements PtrView<List<String>>, LoadMoreView<List<String>> {

    @Bind(R.id.lvRepos)
    ListView listView;
    @Bind(R.id.ptrClassicFrameLayout)
    PtrClassicFrameLayout mPtrClassicFrameLayout;
    @Bind(R.id.errorView)
    TextView errorView;
    @Bind(R.id.emptyView)
    TextView emptyView;
    private ArrayAdapter<String> adapter;
    private List<String> datas = new ArrayList<>();
    private static int count;
    private FooterView footerView;//上拉加载更多视图

    public static RepoListFragment getInstance(String language) {
        RepoListFragment repoListFragment = new RepoListFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("key_language", language);
        repoListFragment.setArguments(bundle);
        return repoListFragment;
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

        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1);
        listView.setAdapter(adapter);
        //下拉刷新
        mPtrClassicFrameLayout.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                loadData();
            }
        });
        footerView = new FooterView(getContext());
        //上拉加载更多 （当listView滑动到最后位置就可以loadMore）
        Mugen.with(listView, new MugenCallbacks() {
            @Override
            public void onLoadMore() {
                Toast.makeText(getContext(), "loadMore...", Toast.LENGTH_SHORT).show();
//                loadMore();
            }

            @Override
            public boolean isLoading() {
                return listView.getFooterViewsCount() > 0 && footerView.isLoading();
            }

            @Override
            public boolean hasLoadedAllItems() {
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

    //视图层业务逻辑
    @OnClick({R.id.emptyView, R.id.errorView})
    public void loadData() {
        final int size = new Random().nextInt(5);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    return;
                }
                datas.clear();
                for (int i = 1; i < size; i++) {
                    count++;
                    datas.add("我是第" + count + "条数据");
                }
                asyncLoadData(size);
            }
        }).start();
    }

    private void asyncLoadData(final int size) {
        mPtrClassicFrameLayout.post(new Runnable() {
            @Override
            public void run() {
                //模拟空数据时的情况
                if (size == 0) {
                    showEmptyView();//listView不可见 空的TextView可见
                } else if (size == 1) {//模拟错误数据时的情况
                    showErroView("unknow erro");
                } else {//模拟正常获取数据
                    showContentView();
                    //视图进行刷新
                    refreshData(datas);
                }
                //停止结束这次下拉刷新
                stopRefersh();
            }
        });
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
    public void refreshData(List<String> strings) {
        adapter.clear();
        adapter.addAll(datas);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void stopRefersh() {
        mPtrClassicFrameLayout.refreshComplete();//下拉刷新完成
    }

    //这是上拉加载更多的视图层的业务逻辑
    private void loadMore() {
        //显示加载中...
        showLoadMoreLoading();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                final ArrayList<String> loadDatas = new ArrayList<String>();
                for (int i = 0; i < 10; i++) {
                    loadDatas.add("我是loadMore的第" + i + "条数据");
                }
                mPtrClassicFrameLayout.post(new Runnable() {
                    @Override
                    public void run() {
//                      将加载到数据添加到视图上
                        addMoreData(loadDatas);
                        //隐藏加载中...
                        hideLoadMore();
                    }
                });
            }
        }).start();
    }

    @Override
    public void showMessage(String mag) {
        Toast.makeText(getContext(), mag, Toast.LENGTH_SHORT).show();
    }

    //这是上拉加载更多视图层实现
    @Override
    public void addMoreData(List<String> datas) {
        adapter.addAll(datas);
    }

    @Override
    public void hideLoadMore() {
        listView.removeView(footerView);
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
