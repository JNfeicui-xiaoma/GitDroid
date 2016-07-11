package com.feicui.gitdroid.repo.repoInfo;

import com.hannesdorfmann.mosby.mvp.MvpView;

/**
 * Created by Administrator on 2016/7/7.
 */
public interface RepoInfoView extends MvpView{

    void showProgress();

    void hideProgress();

    void setData(String data);

    void showMessage(String msg);
}
