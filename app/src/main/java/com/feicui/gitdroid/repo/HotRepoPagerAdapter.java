package com.feicui.gitdroid.repo;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/6/30.
 */
public class HotRepoPagerAdapter extends FragmentPagerAdapter{
    private final List<String> languages=new ArrayList<>();
    public HotRepoPagerAdapter(FragmentManager fm, Context context){
        super(fm);
        for (int i = 1; i < 10; i++) {
            languages.add("Java"+i);
        }
    }

    @Override
    public Fragment getItem(int position) {
        return RepoListFragment.getInstance(languages.get(position));
    }

    @Override
    public int getCount() {
        return languages.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return languages.get(position);
    }
}

