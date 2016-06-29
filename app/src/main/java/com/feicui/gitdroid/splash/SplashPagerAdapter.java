package com.feicui.gitdroid.splash;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.feicui.gitdroid.splash.pager.Pager0;
import com.feicui.gitdroid.splash.pager.Pager1;
import com.feicui.gitdroid.splash.pager.Pager2;

/**
 * Created by Administrator on 2016/6/28.
 */
public class SplashPagerAdapter extends PagerAdapter {
    private final View[] mViews;
    public SplashPagerAdapter(Context context){
        mViews=new View[]{
                new Pager0(context),
                new Pager1(context),
                new Pager2(context)
        };
    }
    @Override
    public int getCount() {
        return mViews.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {

        return view==object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view=mViews[position];
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        View view=mViews[position];
        container.removeView(view);
    }
    public View getView (int position){
        return mViews[position];
    }
}
