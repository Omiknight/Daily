package com.cins.daily.mvp.interactor.impl;

import android.os.Handler;

import com.cins.daily.App;
import com.cins.daily.R;
import com.cins.daily.common.ApiConstants;
import com.cins.daily.common.HostType;
import com.cins.daily.greendao.NewsChannelTable;
import com.cins.daily.listener.RequestCallBack;
import com.cins.daily.mvp.entity.NewsSummary;
import com.cins.daily.mvp.interactor.NewsInteractor;
import com.cins.daily.repository.db.NewsChannelTableManager;
import com.cins.daily.repository.network.RetrofitManager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import retrofit2.Retrofit;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.schedulers.Schedulers;

import static android.R.attr.id;

/**
 * Created by Eric on 2017/1/15.
 */

public class NewsInteractorImpl implements NewsInteractor<List<NewsChannelTable>> {

    @Override
    public Subscription lodeNewsChannels(final RequestCallBack<List<NewsChannelTable>> callBack) {
        return Observable.create(new Observable.OnSubscribe<List<NewsChannelTable>>() {
            @Override
            public void call(Subscriber<? super List<NewsChannelTable>> subscriber) {
                NewsChannelTableManager.initDB();
                subscriber.onNext(NewsChannelTableManager.loadNewsChannels());
                subscriber.onCompleted();
            }
        })
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
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
