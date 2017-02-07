package com.cins.daily.mvp.interactor.impl;

import com.cins.daily.App;
import com.cins.daily.R;

import com.cins.daily.listener.RequestCallBack;
import com.cins.daily.mvp.entity.NewsChannelTable;
import com.cins.daily.mvp.interactor.NewsInteractor;
import com.cins.daily.repository.db.NewsChannelTableManager;
import com.cins.daily.utils.TransformUtils;

import java.util.List;
import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;

/**
 * Created by Eric on 2017/1/15.
 */

public class NewsInteractorImpl implements NewsInteractor<List<NewsChannelTable>> {

    @Inject
    public NewsInteractorImpl() {

    }
    @Override
    public Subscription lodeNewsChannels(final RequestCallBack<List<NewsChannelTable>> callBack) {
        return Observable.create(new Observable.OnSubscribe<List<NewsChannelTable>>() {
            @Override
            public void call(Subscriber<? super List<NewsChannelTable>> subscriber) {
                NewsChannelTableManager.initDB();
                subscriber.onNext(NewsChannelTableManager.loadNewsChannelsMine());
                subscriber.onCompleted();
            }
        })
                .compose(TransformUtils.<List<NewsChannelTable>>defaultSchedulers())
                .subscribe(new Subscriber<List<NewsChannelTable>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        callBack.onError(App.getAppContext().getString(R.string.db_error));
                    }

                    @Override
                    public void onNext(List<NewsChannelTable> newsChannelTables) {
                        callBack.success(newsChannelTables);
                    }
                });
    }
}
