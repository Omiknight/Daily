package com.cins.daily.di.module;

import com.cins.daily.mvp.presenter.NewsListPresenter;
import com.cins.daily.mvp.presenter.impl.NewsListPresenterImpl;
import com.cins.daily.mvp.ui.adapter.NewsRecyclerViewAdapter;
import com.cins.daily.mvp.view.NewsListView;

import dagger.Provides;

/**
 * Created by Eric on 2017/1/17.
 */

public class NewsListModule {
    private NewsListView mNewsListView;
    private String mNewsType;
    private String mNewsId;

    public NewsListModule(NewsListView newsListView, String newsType, String newsId) {
        mNewsListView = newsListView;
        mNewsType = newsType;
        mNewsId = newsId;
    }

    @Provides
    public NewsListPresenter provideNewsListPresenter() {
        return new NewsListPresenterImpl(mNewsListView, mNewsType, mNewsId);
    }

    @Provides
    public NewsRecyclerViewAdapter provideNewsRecyclerViewAdapter() {
        return new NewsRecyclerViewAdapter();
    }

}
