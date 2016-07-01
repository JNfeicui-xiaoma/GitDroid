package com.feicui.gitdroid.commonUtil;

/**
 * Created by Administrator on 2016/7/1.
 * 下拉刷新视图抽象接口
 */
public interface PtrView <T>{

    /**显示内容视图*/
    void showContentView();
    /**显示错误视图*/
    void showErroView(String msg);
    /**显示空视图*/
    void showEmptyView();
    /**刷新数据*/
    void refreshData(T t);
    /**停止刷新*/
    void stopRefersh();

    void showMessage(String mag);
}
