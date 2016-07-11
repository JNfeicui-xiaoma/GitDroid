package com.feicui.gitdroid.commonUtil;

import com.feicui.gitdroid.repo.Repo;
import com.hannesdorfmann.mosby.mvp.MvpView;
import java.util.List;

/**
 * Created by Administrator on 2016/7/4.
 */
public interface PtrPageView extends MvpView,PtrView<List<Repo>>,LoadMoreView<List<Repo>>{


}
