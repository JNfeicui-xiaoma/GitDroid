package com.feicui.gitdroid.login;

import com.hannesdorfmann.mosby.mvp.MvpNullObjectBasePresenter;

/**
 * Created by Administrator on 2016/7/5.
 * + * 此类是处理登陆用例的, 并且在登陆的过程中，将触发调用LoginView
 + *
 + * 登录过程遵循标准的OAuth2.0协议。
 + *
 + * 用户通过WebView登录GitHub网站，如果登录成功，且用户给我们授权，GitHub会访问我们的回调地址，给我们一个授权码。
 + *
 + * 我们就能过授权码去获得访问令牌, 最终就有权利访问信息了
 */
public class LoginPresenter extends MvpNullObjectBasePresenter<LoginView>{

    public void login(){

    }
}
