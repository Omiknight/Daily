package com.cins.daily.mvp.presenter.impl;

import com.cins.daily.listener.RequestCallBack;
import com.cins.daily.mvp.presenter.base.BasePresenter;
import com.cins.daily.mvp.view.base.BaseView;

import rx.Subscription;

/**
 * Created by Eric on 2017/1/17.
 */

public class BasePresenterImpl<T extends BaseView, E> implements BasePresenter ,RequestCallBack<E> {

    protected T mView;
    protected Subscription mSubscription;

    @Override
    public void onCreate() {

    }

    @Override
    public void onDestroy() {
        if (mSubscription != null && !mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
        }
        mView = null;
    }

    @Override
    public void success(E data) {
        mView.showProgress();
    }

    @Override
    public void onError(String errorMsg) {
        mView.showProgress();
        mView.showErrorMsg(errorMsg);
    }
}
