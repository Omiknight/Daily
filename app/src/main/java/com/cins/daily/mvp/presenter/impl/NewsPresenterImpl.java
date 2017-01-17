package com.cins.daily.mvp.presenter.impl;

import com.cins.daily.listener.RequestCallBack;
import com.cins.daily.mvp.entity.NewsSummary;
import com.cins.daily.mvp.interactor.NewsInteractor;
import com.cins.daily.mvp.interactor.impl.NewsInteractorImpl;
import com.cins.daily.mvp.presenter.NewsPresenter;
import com.cins.daily.mvp.view.NewsView;

import java.util.List;

/**
 * Created by Eric on 2017/1/16.
 */

public class NewsPresenterImpl implements NewsPresenter, RequestCallBack<List<NewsSummary>>{

    private NewsView mNewsView;
    private NewsInteractor<List<NewsSummary>> mNewsInteractor;

    public NewsPresenterImpl(NewsView newsView) {
        mNewsView = newsView;
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
    public void success(List<NewsSummary> items) {
        if (mNewsView != null) {
            mNewsView.setItems(items);
            mNewsView.hideProgress();
        }
    }

    @Override
    public void onError(String errorMsg) {
        mNewsView.showMessage(errorMsg);
    }
}
