package com.feicui.gitdroid.main;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.feicui.gitdroid.R;
import com.feicui.gitdroid.commonUtil.ActivityUtil;
import com.feicui.gitdroid.favorite.FavoriteFragment;
import com.feicui.gitdroid.login.LoginActivity;
import com.feicui.gitdroid.login.model.CurrentUser;
import com.feicui.gitdroid.repo.HotRepoFragment;
import com.feicui.gitdroid.repo.repoInfo.RepoInfoActivity;
import com.nostra13.universalimageloader.core.ImageLoader;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @Bind(R.id.navigationView) NavigationView mNavigationView;
    @Bind(R.id.drawerLayout) DrawerLayout drawerLayout;
    @Bind(R.id.toolbar) Toolbar toolbar;
    private MenuItem mMenuItem;
    private HotRepoFragment mHotRepoFragment;
    private FavoriteFragment mFavoriteFragment;
    private Button btnLogin;
    private ImageView ivIcon;//yonghu头像

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        ButterKnife.bind(this);
        //ActionBar的处理
        setSupportActionBar(toolbar);
        mNavigationView.setNavigationItemSelectedListener(this);
        //设置Toolbar 左上角切换侧滑菜单的按钮
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        );
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        //默认第一个menu项为最热门
        mMenuItem = mNavigationView.getMenu().findItem(R.id.github_hot_repo);
        mMenuItem.setChecked(true);


        mHotRepoFragment = new HotRepoFragment();
        replaceFragment(mHotRepoFragment);

        btnLogin= (Button)ButterKnife.findById(mNavigationView.getHeaderView(0),R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                mActivityUtil.startActivity(LoginActivity.class);
                Intent intent=new Intent(MainActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });
        ivIcon = (ImageView) ButterKnife.findById(mNavigationView.getHeaderView(0), R.id.ivIcon);
    }

    @Override protected void onStart() {
        super.onStart();
        // 还没有授权登陆
        if(CurrentUser.isEmpty()){
            btnLogin.setText(R.string.login_github);
//            Toast.makeText(MainActivity.this, CurrentUser.getAccessToken()+CurrentUser.getUser().getName(), Toast.LENGTH_SHORT).show();
            return;
        }
        // 已经授权登陆
        btnLogin.setText(R.string.switch_account);
        getSupportActionBar().setTitle(CurrentUser.getUser().getName());
        // 设置用户头像
        String photoUrl = CurrentUser.getUser().getAvatar();
        ImageLoader.getInstance().displayImage(photoUrl,ivIcon);
    }
    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.commit();
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        if (mMenuItem.isChecked()) {
            mMenuItem.setChecked(false);
        }
        switch (item.getItemId()) {
            case R.id.github_hot_repo:
                if (!mHotRepoFragment.isAdded()){
                    replaceFragment(mHotRepoFragment);
                }
                break;
            case R.id.arsenal_my_repo:
                if (mFavoriteFragment==null) mFavoriteFragment=new FavoriteFragment();
                if (!mFavoriteFragment.isAdded()){
                    replaceFragment(mFavoriteFragment);
                }
                break;
            case R.id.tips_daily:
                Toast.makeText(MainActivity.this, "每日干货", Toast.LENGTH_SHORT).show();
                break;
        }
        drawerLayout.post(new Runnable() {
            @Override
            public void run() {
                drawerLayout.closeDrawer(GravityCompat.START);
            }
        });
        //返回true  代表将该菜单项变为checked状态
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //如果drawerLayout是开的则关闭
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        //如果drawerLayout是关闭的则推出当前Activity
        else {
            super.onBackPressed();
        }
    }
}
