package com.cins.daily.mvp.interactor.impl;

import com.cins.daily.App;
import com.cins.daily.R;
import com.cins.daily.common.Constants;

import com.cins.daily.listener.RequestCallBack;
import com.cins.daily.mvp.entity.NewsChannelTable;
import com.cins.daily.mvp.interactor.NewsChannelInteractor;
import com.cins.daily.repository.db.NewsChannelTableManager;
import com.cins.daily.utils.TransformUtils;
import com.socks.library.KLog;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;

/**
 * Created by Eric on 2017/1/20.
 */

public class NewsChannelInteractorImpl implements NewsChannelInteractor<Map<Integer, List<NewsChannelTable>>> {

    private ExecutorService mSingleThreadPool;

    @Inject
    public NewsChannelInteractorImpl() {

    }

    @Override
    public Subscription lodeNewsChannels(final RequestCallBack<Map<Integer, List<NewsChannelTable>>> callback) {
        return Observable.create(new Observable.OnSubscribe<Map<Integer, List<NewsChannelTable>>>() {
            @Override
            public void call(Subscriber<? super Map<Integer, List<NewsChannelTable>>> subscriber) {
                Map<Integer, List<NewsChannelTable>> newsChannelListMap = getNewsChannelData();
                subscriber.onNext(newsChannelListMap);
                subscriber.onCompleted();
            }

        }).compose(TransformUtils.<Map<Integer, List<NewsChannelTable>>>defaultSchedulers())
                .subscribe(new Subscriber<Map<Integer, List<NewsChannelTable>>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError(App.getAppContext().getString(R.string.db_error));
                    }

                    @Override
                    public void onNext(Map<Integer, List<NewsChannelTable>> newsChannelListMap) {
                        callback.success(newsChannelListMap);
                    }
                });
    }

    private Map<Integer, List<NewsChannelTable>> getNewsChannelData() {
        Map<Integer, List<NewsChannelTable>> map = new HashMap<>();
        List<NewsChannelTable> channelListMine = NewsChannelTableManager.loadNewsChannelsMine();
        List<NewsChannelTable> channelListMore = NewsChannelTableManager.loadNewsChannelsMore();
        map.put(Constants.NEWS_CHANNEL_MINE, channelListMine);
        map.put(Constants.NEWS_CHANNEL_MORE, channelListMore);
        return map;
    }

    @Override
    public void swapDb(final int fromPosition, final int toPosition) {
        createThreadPool();
        mSingleThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                KLog.d(Thread.currentThread().getName());
                KLog.d("fromPosition: " + fromPosition + "； toPosition: " + toPosition);

                NewsChannelTable fromNewsChannel = NewsChannelTableManager.loadNewsChannel(fromPosition);
                NewsChannelTable toNewsChannel = NewsChannelTableManager.loadNewsChannel(toPosition);

                if (isAdjacent(fromPosition, toPosition)) {
                    swapAdjacentIndexAndUpdate(fromNewsChannel, toNewsChannel);
                } else if (fromPosition - toPosition > 0) {
                    List<NewsChannelTable> newsChannels = NewsChannelTableManager
                            .loadNewsChannelsWithin(toPosition, fromPosition - 1);

                    increaseOrReduceIndexAndUpdate(newsChannels, true);
                    changeFromChannelIndexAndUpdate(fromNewsChannel, toPosition);
                } else if (fromPosition - toPosition < 0) {
                    List<NewsChannelTable> newsChannels = NewsChannelTableManager
                            .loadNewsChannelsWithin(fromPosition + 1, toPosition);

                    increaseOrReduceIndexAndUpdate(newsChannels, false);
                    changeFromChannelIndexAndUpdate(fromNewsChannel, toPosition);
                }
            }

            private boolean isAdjacent(int fromChannelIndex, int toChannelIndex) {
                return Math.abs(fromChannelIndex - toChannelIndex) == 1;
            }

            private void swapAdjacentIndexAndUpdate(NewsChannelTable fromNewsChannel,
                                                    NewsChannelTable toNewsChannel) {
                fromNewsChannel.setNewsChannelIndex(toPosition);
                toNewsChannel.setNewsChannelIndex(fromPosition);

                NewsChannelTableManager.update(fromNewsChannel);
                NewsChannelTableManager.update(toNewsChannel);
            }
        });
    }

    private void increaseOrReduceIndexAndUpdate(List<NewsChannelTable> newsChannels, boolean isIncrease) {
        for (NewsChannelTable newsChannel : newsChannels) {
            increaseOrReduceIndex(isIncrease, newsChannel);
            NewsChannelTableManager.update(newsChannel);
        }
    }

    private void increaseOrReduceIndex(boolean isIncrease, NewsChannelTable newsChannel) {
        int targetIndex;
        if (isIncrease) {
            targetIndex = newsChannel.getNewsChannelIndex() + 1;
        } else {
            targetIndex = newsChannel.getNewsChannelIndex() - 1;
        }
        newsChannel.setNewsChannelIndex(targetIndex);
    }

    private void changeFromChannelIndexAndUpdate(NewsChannelTable fromNewsChannel, int toPosition) {
        fromNewsChannel.setNewsChannelIndex(toPosition);
        NewsChannelTableManager.update(fromNewsChannel);
    }

    @Override
    public void updateDb(final NewsChannelTable newsChannel, final boolean isChannelMine) {
        createThreadPool();
        mSingleThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                KLog.d(Thread.currentThread().getName());

                int channelIndex = newsChannel.getNewsChannelIndex();
                if (isChannelMine) {
                    List<NewsChannelTable> newsChannels = NewsChannelTableManager.loadNewsChannelsIndexGt(channelIndex);
                    increaseOrReduceIndexAndUpdate(newsChannels, false);

                    int targetIndex = NewsChannelTableManager.getAllSize();
                    ChangeIsSelectAndIndex(targetIndex, false);
                } else {
                    List<NewsChannelTable> newsChannels = NewsChannelTableManager.loadNewsChannelsIndexLtAndIsUnselect(channelIndex);
                    increaseOrReduceIndexAndUpdate(newsChannels, true);

                    int targetIndex = NewsChannelTableManager.getNewsChannelSelectSize();
                    ChangeIsSelectAndIndex(targetIndex, true);
                }

            }

            private void ChangeIsSelectAndIndex(int targetIndex, boolean isSelect) {
                newsChannel.setNewsChannelSelect(isSelect);
                changeFromChannelIndexAndUpdate(newsChannel, targetIndex);
            }
        });
    }

    private void createThreadPool() {
        if (mSingleThreadPool == null) {
            mSingleThreadPool = Executors.newSingleThreadExecutor();
        }
    }
}
