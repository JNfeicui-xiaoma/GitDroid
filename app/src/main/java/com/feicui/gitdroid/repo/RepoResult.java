package com.feicui.gitdroid.repo;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Administrator on 2016/7/6.
 * 热门仓库API 结果
 */
public class RepoResult {

    @SerializedName("total_count")
    private int totalCount;

    @SerializedName("items")
    private List<Repo> repoList;

    @SerializedName("incomplete_results")
    private boolean incompleteResults;

    public int getTotalCount(){
        return totalCount;
    }

    public boolean isIncompleteResults(){
        return incompleteResults;
    }
    public List<Repo> getRepoList(){
        return repoList;
    }
}
