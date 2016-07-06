package com.feicui.gitdroid.login;

import com.hannesdorfmann.mosby.mvp.MvpView;

/**
 * Created by Administrator on 2016/7/5.
 */
public interface LoginView extends MvpView{

    //显示进度条
    void showProgress();
    //重置WebView
    void resetWeb();

    void showMessage(String msg);
    //导航至主页面
    void navigateToMain();
}
