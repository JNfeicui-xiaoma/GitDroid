package com.feicui.gitdroid.netWork;

/**
 * Created by Administrator on 2016/7/5.
 */
public interface GitHubApi {

    //gitHub开发者 申请就行
    String CLIENT_ID="c360b01e5ef05890248b";

    //授权事申请的可访问愈

    String INITIAL_SCOPE = "user,public_repo,repo";
    //WebView来加载此URl  用来做】显示GItHub的登录页面

    String AUTH_URL="https://github.com/login/oauth/authorize?client_id="+ CLIENT_ID + "&"+"scope="+INITIAL_SCOPE;
}
