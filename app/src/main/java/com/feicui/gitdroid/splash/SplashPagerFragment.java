package com.feicui.gitdroid.splash;

import android.animation.ArgbEvaluator;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.feicui.gitdroid.R;

import butterknife.Bind;
import butterknife.BindColor;
import butterknife.ButterKnife;
import me.relex.circleindicator.CircleIndicator;

/**
 * Created by Administrator on 2016/6/28.
 */
public class SplashPagerFragment extends Fragment {

    private SplashPagerAdapter mAdapter;
    @Bind(R.id.viewPager)
    ViewPager mViewPager;
    @Bind(R.id.indicator)
    CircleIndicator mIndicator;//指示器（下方的小圆点）

    @BindColor(R.color.colorBlue)
    int colorBlue;
    @BindColor(R.color.colorGreen)
    int colorGreen;
    @BindColor(R.color.colorRed)
    int colorRed;
    @Bind(R.id.content)
    FrameLayout mFrameLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_splash_pager, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        mAdapter = new SplashPagerAdapter(getContext());

        mViewPager.setAdapter(mAdapter);
        mViewPager.addOnPageChangeListener(onPageChangeListener);
        mIndicator.setViewPager(mViewPager);
    }

    private ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
        //ARGB取值器
        final ArgbEvaluator mEvaluator = new ArgbEvaluator();

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            if (position == 0) {
                //颜色取蓝色和绿色的中间值  偏移量position [0-1]
                int color = (int) mEvaluator.evaluate(positionOffset, colorBlue, colorGreen);
                mFrameLayout.setBackgroundColor(color);
                return;
            }
            //从第二个页面到第三个页面
            if (position==1){
                int color = (int) mEvaluator.evaluate(positionOffset, colorGreen, colorRed);
                mFrameLayout.setBackgroundColor(color);
                return;
            }

        }

        @Override
        public void onPageSelected(int position) {

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };


}
