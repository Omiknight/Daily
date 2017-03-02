package com.cins.daily.mvp.interactor;

import com.cins.daily.listener.RequestCallBack;

import rx.Subscription;

/**
 * Created by Eric on 2017/1/15.
 */

public interface NewsInteractor<T> {

    Subscription lodeNewsChannels(RequestCallBack<T> callBack);
}
