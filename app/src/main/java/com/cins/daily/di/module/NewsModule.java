package com.cins.daily.di.module;

import com.cins.daily.mvp.presenter.NewsPresenter;
import com.cins.daily.mvp.presenter.impl.NewsPresenterImpl;
import com.cins.daily.mvp.ui.adapter.NewsRecyclerViewAdapter;
import com.cins.daily.mvp.view.NewsView;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Eric on 2017/1/16.
 */
@Module
public class NewsModule {
    private NewsView mNewsView;

    public NewsModule(NewsView newsView) {
        mNewsView = newsView;
    }

    @Provides
    public NewsPresenter provideNewsPresenter() {
        return new NewsPresenterImpl(mNewsView);
    }

    @Provides
    public NewsRecyclerViewAdapter provideNewsRecyclerViewAdapter() {
        return new NewsRecyclerViewAdapter();
    }
}
