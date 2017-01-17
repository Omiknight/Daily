package com.cins.daily.mvp.presenter.impl;

import com.cins.daily.listener.RequestCallBack;
import com.cins.daily.mvp.entity.NewsSummary;
import com.cins.daily.mvp.presenter.NewsListPresenter;

import java.util.List;

/**
 * Created by Eric on 2017/1/17.
 */

public class NewsListPresenterImpl implements NewsListPresenter,RequestCallBack<List<NewsSummary>>{


    @Override
    public void success(List<NewsSummary> data) {

    }

    @Override
    public void onError(String errorMsg) {

    }

    @Override
    public void onItemClicked(int position) {

    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDestroy() {

    }
}
