package com.feicui.gitdroid.repo;

import com.feicui.gitdroid.login.model.User;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/7/6.
 */
public class Repo implements Serializable {
    @SerializedName("id")
    private int id;

    //仓库名称
    @SerializedName("name")
    private String name;

    //仓库全名
    @SerializedName("full_name")
    private String fullName;

    //仓库描述
    @SerializedName("description")
    private String description;

    @SerializedName("stargazers_count")
    private int stargazersCount;

    @SerializedName("forks_count")
    private int  forksCount;

    @SerializedName("owner")
    private User owner;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getFullName() {
        return String.valueOf(fullName);
    }

    public String getDescription() {
        return description;
    }

    public int  getStargazersCount() {
        return stargazersCount;
    }

    public int  getForksCount() {
        return forksCount;
    }

    public User getOwner() {
        return owner;
    }
}
