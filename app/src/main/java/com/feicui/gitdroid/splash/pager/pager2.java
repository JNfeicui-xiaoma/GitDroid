package com.feicui.gitdroid.splash.pager;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import com.feicui.gitdroid.R;

/**
 * Created by Administrator on 2016/6/28.
 */
public class pager2 extends FrameLayout {
    public pager2(Context context) {
        super(context);
        init();
    }

    public pager2(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public pager2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.content_pager_2, this, true);
    }
}
