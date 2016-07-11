package com.feicui.gitdroid.favorite.model;

import android.support.annotation.NonNull;

import com.feicui.gitdroid.repo.Repo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/7/9.
 */
public class RepoConverter {
    private RepoConverter(){}

    public static @NonNull
    LocalRepo convert(@NonNull Repo repo) {
        LocalRepo localRepo = new LocalRepo();
        localRepo.setAvatar(repo.getOwner().getAvatar());
        localRepo.setDescription(repo.getDescription());
        localRepo.setFullName(repo.getFullName());
        localRepo.setId(repo.getId());
        localRepo.setName(repo.getName());
        localRepo.setStargazersCount(repo.getStargazersCount());
        localRepo.setForksCount(repo.getForksCount());
        return localRepo;
    }

    public static @NonNull
    List<LocalRepo> convertAll(@NonNull List<Repo> repos) {
        ArrayList<LocalRepo> localRepos = new ArrayList<>();
        for (Repo repo : repos) {
            localRepos.add(convert(repo));
        }
        return localRepos;
    }
}
