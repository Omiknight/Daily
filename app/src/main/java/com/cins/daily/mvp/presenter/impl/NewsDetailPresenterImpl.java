package com.cins.daily.mvp.presenter.impl;

import com.cins.daily.mvp.entity.NewsDetail;
import com.cins.daily.mvp.interactor.NewsDetailInteractor;
import com.cins.daily.mvp.interactor.impl.NewsDetailInteractorImpl;
import com.cins.daily.mvp.presenter.NewsDetailPresenter;
import com.cins.daily.mvp.view.NewsDetailView;

/**
 * Created by Eric on 2017/1/18.
 */

public class NewsDetailPresenterImpl extends BasePresenterImpl<NewsDetailView, NewsDetail>
        implements NewsDetailPresenter{
    private NewsDetailInteractor<NewsDetail> mNewsDetailInteractor;
    private String mPostId;

    public NewsDetailPresenterImpl(NewsDetailView newsDetailView, String postId) {
        mView = newsDetailView;
        mPostId = postId;
        mNewsDetailInteractor = new NewsDetailInteractorImpl();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mSubscription = mNewsDetailInteractor.loadNewsDetail(this, mPostId);
    }

    @Override
    public void success(NewsDetail data) {
        super.success(data);
        mView.setNewsDetail(data);
    }

    @Override
    public void setPosId(String postId) {

    }
}
