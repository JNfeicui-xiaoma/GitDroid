package com.feicui.gitdroid.splash;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.feicui.gitdroid.login.LoginActivity;
import com.feicui.gitdroid.main.MainActivity;
import com.feicui.gitdroid.R;
import com.feicui.gitdroid.commonUtil.ActivityUtil;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SplashActivity extends AppCompatActivity {

    @Bind(R.id.btnLogin) Button btnLogin;
    @Bind(R.id.btnEnter) Button btnEnter;

    private ActivityUtil mActivityUtil;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        mActivityUtil=new ActivityUtil(this);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btnLogin,R.id.btnEnter})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.btnLogin:
                Toast.makeText(SplashActivity.this, "login", Toast.LENGTH_SHORT).show();
                mActivityUtil.startActivity(LoginActivity.class);
                break;
            case R.id.btnEnter:
                mActivityUtil.startActivity(MainActivity.class);
//                Toast.makeText(SplashActivity.this, "enter", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
