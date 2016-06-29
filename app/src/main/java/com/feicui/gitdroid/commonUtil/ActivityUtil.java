package com.feicui.gitdroid.commonUtil;


import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;

import java.lang.ref.WeakReference;

/**
 * Created by Administrator on 2016/6/29.
 */
public class ActivityUtil {

    private WeakReference<Activity> mActivityWeakReference;
    private WeakReference<Fragment> mFragmentWeakReference;

    public ActivityUtil(Activity activity) {
        mActivityWeakReference = new WeakReference<Activity>(activity);
    }
    public ActivityUtil(Fragment fragment){
        mFragmentWeakReference=new WeakReference<Fragment>(fragment);
    }
    private Activity getActivity(){
        if (mActivityWeakReference!=null){
            return mActivityWeakReference.get();
        }
        if(mFragmentWeakReference!=null){
            Fragment fragment=mFragmentWeakReference.get();
            return fragment==null?null:fragment.getActivity();
        }
        return null;
    }
    public void startActivity(Class<? extends Activity> clazz){
        Activity activity=getActivity();
        if (activity==null)return;
        Intent intent=new Intent(activity,clazz);
        activity.startActivity(intent);
    }
}
