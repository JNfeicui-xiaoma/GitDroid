package com.feicui.gitdroid.login.model;

/**
 * Created by Administrator on 2016/7/6.
 * 此类是用来缓存当前用户信息的极简单的实现 没有使用SharedPreferences来持久化这些数据
 */
public class CurrentUser {
    //此类不可实例化
    private CurrentUser() {}
    private static User user;

    private static String accessToken;

    public static User getUser(){
        return user;
    }

    public static void setUser(User user) {
        CurrentUser.user = user;
    }

    public static boolean isEmpty(){
        return accessToken==null||user==null;
    }
    public static void  setAccessToken(String accessToken){
        CurrentUser.accessToken=accessToken;
    }
    //清楚缓存的信息
    public static void clear(){
        accessToken=null;
        user=null;
    }
    public static String getAccessToken(){
        return accessToken;
    }
    //当前是否有访问令牌
    public static boolean hasAccessToken(){
        return accessToken!=null;
    }
}
