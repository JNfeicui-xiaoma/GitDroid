package com.feicui.gitdroid.netWork;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 2016/7/5.
 */
public class GitHubClient {

    private static GitHubClient sClient;

    private static GitHubClient getInstance(){

        if (sClient==null){
            sClient=new GitHubClient();
        }
        return sClient;
    }

    private GitHubClient(){
        OkHttpClient okHttpClient=new OkHttpClient.Builder()
                .build();
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
    }
}
