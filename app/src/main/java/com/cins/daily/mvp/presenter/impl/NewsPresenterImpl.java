package com.cins.daily.mvp.presenter.impl;


import com.cins.daily.mvp.entity.NewsChannelTable;
import com.cins.daily.mvp.interactor.NewsInteractor;
import com.cins.daily.mvp.interactor.impl.NewsInteractorImpl;
import com.cins.daily.mvp.presenter.NewsPresenter;
import com.cins.daily.mvp.presenter.base.BasePresenterImpl;
import com.cins.daily.mvp.view.NewsView;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by Eric on 2017/1/16.
 */
public class NewsPresenterImpl extends BasePresenterImpl<NewsView, List<NewsChannelTable>>
        implements NewsPresenter {

    private NewsInteractor<List<NewsChannelTable>> mNewsInteractor;

    @Inject
    public NewsPresenterImpl(NewsInteractorImpl newsInteractor) {
        mNewsInteractor = newsInteractor;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        loadNewsChannels();
    }

    private void loadNewsChannels() {
        mSubscription = mNewsInteractor.lodeNewsChannels(this);
    }

    @Override
    public void success(List<NewsChannelTable> data) {
        super.success(data);
        mView.initViewPager(data);
    }

    @Override
    public void onChannelDbChanged() {
        loadNewsChannels();
    }
}
