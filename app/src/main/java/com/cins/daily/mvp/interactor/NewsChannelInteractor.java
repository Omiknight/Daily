package com.cins.daily.mvp.interactor;

import com.cins.daily.listener.RequestCallBack;
import com.cins.daily.mvp.entity.NewsChannelTable;

import rx.Subscription;

/**
 * Created by Eric on 2017/1/20.
 */

public interface NewsChannelInteractor<T> {
    Subscription lodeNewsChannels(RequestCallBack<T> callback);

    void swapDb(int fromPosition,int toPosition);

    void updateDb(NewsChannelTable newsChannel, boolean isChannelMine);
}
