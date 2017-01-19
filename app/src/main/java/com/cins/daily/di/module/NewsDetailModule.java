package com.cins.daily.di.module;

import com.cins.daily.mvp.presenter.NewsDetailPresenter;
import com.cins.daily.mvp.presenter.impl.NewsDetailPresenterImpl;
import com.cins.daily.mvp.view.NewsDetailView;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Eric on 2017/1/18.
 */
@Module
public class NewsDetailModule{
    private String mPostId;

    public NewsDetailModule(NewsDetailView newsDetailView, String postId) {

    }

    @Provides
    public NewsDetailPresenter provideNewsDetailPresenter() {
        return new NewsDetailPresenterImpl();
    }
}
