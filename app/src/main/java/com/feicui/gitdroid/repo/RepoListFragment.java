package com.feicui.gitdroid.repo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.feicui.gitdroid.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * Created by Administrator on 2016/6/30.
 */
public class RepoListFragment extends Fragment{

    @Bind(R.id.lvRepos) ListView listView;
    @Bind(R.id.ptrClassicFrameLayout)
    PtrClassicFrameLayout mPtrClassicFrameLayout;


    private ArrayAdapter<String> adapter;
    private List<String> datas=new ArrayList<>();
    private static int count;

    public static RepoListFragment getInstance(String language){
        RepoListFragment repoListFragment=new RepoListFragment();
        Bundle bundle=new Bundle();
        bundle.putSerializable("key_language",language);
        repoListFragment.setArguments(bundle);
        return repoListFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_repo_list,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this,view);
        adapter=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1);
        listView.setAdapter(adapter);


        mPtrClassicFrameLayout.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                loadData(20);
            }
        });
    }

    private void loadData(final int size){

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    datas.clear();
                    for (int i = 1; i < size; i++) {
                        count++;
                        datas.add("我是第"+count+"条数据");
                    }
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                mPtrClassicFrameLayout.post(new Runnable() {
                    @Override
                    public void run() {
                        adapter.clear();
                        // 添加刷新数据
                        adapter.addAll(datas);
                        adapter.notifyDataSetChanged();
                        //下拉刷新完成
                        mPtrClassicFrameLayout.refreshComplete();
                    }
                });

            }
        }).start();

    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
