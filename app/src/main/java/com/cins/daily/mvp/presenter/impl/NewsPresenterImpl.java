package com.cins.daily.mvp.presenter.impl;

import com.cins.daily.mvp.interactor.NewsInteractor;
import com.cins.daily.mvp.interactor.impl.NewsInteractorImpl;
import com.cins.daily.mvp.presenter.NewsPresenter;
import com.cins.daily.mvp.view.NewsView;

import java.util.List;

/**
 * Created by Eric on 2017/1/16.
 */

public class NewsPresenterImpl implements NewsPresenter, NewsInteractor.OnFinishedListener{

    private NewsView mNewsView;
    private NewsInteractor mNewsInteractor;

    public NewsPresenterImpl(NewsView newsView) {
        mNewsView = newsView;
        mNewsInteractor = new NewsInteractorImpl();
    }

    @Override
    public void onCreateView() {
        if (mNewsView != null) {
            mNewsView.showProgress();
        }
        mNewsInteractor.loadNews(this);
    }

    @Override
    public void onFabClicked() {

    }

    @Override
    public void onItemClicked(int position) {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onDestroy() {
        mNewsView = null;
    }

    @Override
    public void onFinished(List<String> items) {
        if (mNewsView != null) {
            mNewsView.setItems(items);
            mNewsView.hideProgress();
        }
    }
}
