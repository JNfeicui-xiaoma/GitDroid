package com.feicui.gitdroid.repo;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.feicui.gitdroid.repo.modle.Language;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/6/30.
 */
public class HotRepoPagerAdapter extends FragmentPagerAdapter{
    private final List<Language> languages;
    public HotRepoPagerAdapter(FragmentManager fm, Context context){
        super(fm);
        languages=Language.getDefaultLanguage(context);
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
        return languages.get(position).getName();
    }
}

