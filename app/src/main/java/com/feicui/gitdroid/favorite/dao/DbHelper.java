package com.feicui.gitdroid.favorite.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.feicui.gitdroid.favorite.model.LocalRepo;
import com.feicui.gitdroid.favorite.model.RepoGroup;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

/**
 * Created by Administrator on 2016/7/8.
 */
public class DbHelper extends OrmLiteSqliteOpenHelper{

    private static final String TABLE_NAME="repo_favorites.db";

    private static final int VERSION=2;

    private static DbHelper sInstance;
    public static synchronized DbHelper getInstance(Context context){
            if ( sInstance==null){
                sInstance=new DbHelper(context.getApplicationContext());
            }
        return sInstance;
    }

    private final Context mContext;

    public DbHelper(Context context){
        super(context, TABLE_NAME, null, VERSION);
        this.mContext=context;
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            //创建表
            TableUtils.createTableIfNotExists(connectionSource, RepoGroup.class);
            //添加本地默认类别数据到表里去
            TableUtils.createTableIfNotExists(connectionSource, LocalRepo.class);
            new RepoGroupDao(this).createOrUpdate(RepoGroup.getDefaultGroups(mContext));
            new LocalRepoDao(this).createOrUpdate(LocalRepo.getDefaultLocalRepos(mContext));
        } catch (SQLException e) {
            throw  new RuntimeException(e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {

        try {
            TableUtils.dropTable(connectionSource,RepoGroup.class,true);
            TableUtils.dropTable(connectionSource, LocalRepo.class, true);
            onCreate(database,connectionSource);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
