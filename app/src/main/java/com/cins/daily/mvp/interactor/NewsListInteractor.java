package com.cins.daily.mvp.interactor;

import com.cins.daily.listener.RequestCallBack;

import rx.Subscription;

/**
 * Created by Eric on 2017/1/17.
 */

public interface NewsListInteractor<T> {
    Subscription loadNews(RequestCallBack<T> listener, String type, String id, int startPage);
}
