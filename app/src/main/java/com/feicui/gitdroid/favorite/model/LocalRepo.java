package com.feicui.gitdroid.favorite.model;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Created by Administrator on 2016/7/8.
 */
@DatabaseTable(tableName = "repositories")
public class LocalRepo {
    //主键
     @DatabaseField(id=true)
    private long id;
    //cangku 名称
    @DatabaseField
    private String name;
    //仓库全名
    @DatabaseField(columnName = "full_name")
    @SerializedName("full_name")
    private String fullName;

    //仓库描述
    @DatabaseField
    private String description;

    //本地仓库start数量
    @SerializedName("stargazers_count")
    @DatabaseField(columnName = "stargazers_count")
    private int stargazersCount;

    //本地仓库在forks_count上的数量
    @SerializedName("forks_count")
    @DatabaseField(columnName = "forks_count")
    private int forksCount;

    //用户头像路径
    @SerializedName("avatar_url")
    @DatabaseField(columnName = "avatar_url")
    private String avatar;

    // 是一个外键
    @DatabaseField(canBeNull = true, foreign = true, columnName = COLUMN_GROUP_ID)
    @SerializedName("group")
    private RepoGroup repoGroup;

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStargazersCount(int stargazersCount) {
        this.stargazersCount = stargazersCount;
    }

    public void setForksCount(int forksCount) {
        this.forksCount = forksCount;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public void setRepoGroup(RepoGroup repoGroup) {
        this.repoGroup = repoGroup;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getFullName() {
        return fullName;
    }

    public String getDescription() {
        return description;
    }

    public int getStargazersCount() {
        return stargazersCount;
    }

    public int getForksCount() {
        return forksCount;
    }

    public String getAvatar() {
        return avatar;
    }

    public RepoGroup getRepoGroup() {
        return repoGroup;
    }

    public static final String COLUMN_GROUP_ID = "group_id";

    @Override public boolean equals(Object o) {
        return o != null && o instanceof LocalRepo && this.id == ((LocalRepo)o).getId();
    }

    public static List<LocalRepo> getDefaultLocalRepos(Context context) {

        try {
            InputStream inputStream = context.getAssets().open("defaultrepos.json");
            // 将流转换为字符串
            String content = IOUtils.toString(inputStream);
            // 将字符串转换为对象数组
            Gson gson = new Gson();
            return gson.fromJson(content, new TypeToken<List<LocalRepo>>() {
            }.getType());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
