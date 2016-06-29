package com.feicui.gitdroid;

import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @Bind(R.id.navigationView)
    NavigationView mNavigationView;
    @Bind(R.id.drawerLayout)
    DrawerLayout drawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        ButterKnife.bind(this);
        mNavigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.github_hot_repo:
                Toast.makeText(MainActivity.this, "最热门", Toast.LENGTH_SHORT).show();
                break;
            case R.id.arsenal_my_repo:
                Toast.makeText(MainActivity.this, "我的收藏", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tips_daily:
                Toast.makeText(MainActivity.this, "每日干货", Toast.LENGTH_SHORT).show();
                break;
        }
        //返回true  代表将该菜单项变为checked状态
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //如果drawerLayout是开的则关闭
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        //如果drawerLayout是关闭的则推出当前Activity
        else{
            super.onBackPressed();
        }
    }
}
