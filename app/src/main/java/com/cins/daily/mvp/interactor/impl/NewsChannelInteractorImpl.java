package com.cins.daily.mvp.interactor.impl;

import com.cins.daily.App;
import com.cins.daily.R;
import com.cins.daily.common.Constants;
import com.cins.daily.greendao.NewsChannelTable;
import com.cins.daily.listener.RequestCallBack;
import com.cins.daily.mvp.interactor.NewsChannelInteractor;
import com.cins.daily.repository.db.NewsChannelTableManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Eric on 2017/1/20.
 */

public class NewsChannelInteractorImpl implements NewsChannelInteractor<Map<Integer,List<NewsChannelTable>>>{

    @Inject
    public NewsChannelInteractorImpl() {

    }

    @Override
    public Subscription lodeNewsChannels(final RequestCallBack<Map<Integer, List<NewsChannelTable>>> callback) {
        return rx.Observable.create(new Observable.OnSubscribe<Map<Integer, List<NewsChannelTable>>>() {

            @Override
            public void call(Subscriber<? super Map<Integer, List<NewsChannelTable>>> subscriber) {
                Map<Integer, List<NewsChannelTable>> newsChannelListMap = getNewsChannelData();
                subscriber.onNext(newsChannelListMap);
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.io())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Map<Integer, List<NewsChannelTable>>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError(App.getAppContext().getString(R.layout.));
                    }

                    @Override
                    public void onNext(Map<Integer, List<NewsChannelTable>> newsChannelListMap) {
                        callback.success(newsChannelListMap);
                    }
                });
    }

    private Map<Integer, List<NewsChannelTable>> getNewsChannelData() {
        Map<Integer, List<NewsChannelTable>> newsChannelListMap = new HashMap<>();
        List<NewsChannelTable> channelTableListMine = NewsChannelTableManager.loadNewsChannelsMine();
        List<NewsChannelTable> channelTableListMore = NewsChannelTableManager.loadNewsChannelsMore();
        newsChannelListMap.put(Constants.NEWS_CHANNEL_MINE, channelTableListMine);
        newsChannelListMap.put(Constants.NEWS_CHANNEL_MORE, channelTableListMore);
        return newsChannelListMap;
    }
}
