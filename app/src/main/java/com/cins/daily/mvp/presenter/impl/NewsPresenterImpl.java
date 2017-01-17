package com.cins.daily.mvp.presenter.impl;

import com.cins.daily.greendao.NewsChannelTable;
import com.cins.daily.mvp.entity.NewsSummary;
import com.cins.daily.mvp.interactor.NewsInteractor;
import com.cins.daily.mvp.interactor.impl.NewsInteractorImpl;
import com.cins.daily.mvp.presenter.NewsPresenter;
import com.cins.daily.mvp.view.NewsView;

import java.util.List;

/**
 * Created by Eric on 2017/1/16.
 */

public class NewsPresenterImpl extends BasePresenterImpl<NewsView, List<NewsChannelTable>>
       implements NewsPresenter {

    private NewsInteractor<List<NewsChannelTable>> mNewsInteractor;

    public NewsPresenterImpl(NewsView newsView) {
        mView = newsView;
        mNewsInteractor = new NewsInteractorImpl();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mSubscription = mNewsInteractor.lodeNewsChannels(this);
    }

    @Override
    public void success(List<NewsChannelTable> data) {
        super.success(data);
        mView.initViewPager(data);
    }
}
