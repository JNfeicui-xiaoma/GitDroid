package com.feicui.gitdroid.login;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.feicui.gitdroid.R;
import com.feicui.gitdroid.netWork.GitHubApi;
import com.hannesdorfmann.mosby.mvp.MvpActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import pl.droidsonroids.gif.GifImageView;

/**
 * Created by Administrator on 2016/7/5.
 */
public class LoginActivity extends MvpActivity<LoginView, LoginPresenter> {

    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.webView)
    WebView mWebView;
    @Bind(R.id.gifImageView)
    GifImageView mGifImageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initWebView();
    }

    public void initWebView() {
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.removeAllCookie();

        mWebView.loadUrl(GitHubApi.AUTH_URL);
        mWebView.setFocusable(true);
        mWebView.setFocusableInTouchMode(true);
        mWebView.setWebChromeClient(webChromeClient);
        mWebView.setWebViewClient(webViewClinet);
    }

    private WebViewClient webViewClinet = new WebViewClient() {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            // 登陆成功后github将重定向一个url路径
            // 我们从中对比，取出code(临时授权值, 在真正授权时一定要的值)
            // 进行授权接口操作,且获取到你的基本信息
            return super.shouldOverrideUrlLoading(view, url);
        }
    };
    private WebChromeClient webChromeClient=new WebChromeClient(){
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if (newProgress==100){
                mGifImageView.setVisibility(View.GONE);
                mWebView.setVisibility(View.VISIBLE);
            }
        }
    };

    @NonNull
    @Override
    public LoginPresenter createPresenter() {
        return new LoginPresenter();
    }
}
