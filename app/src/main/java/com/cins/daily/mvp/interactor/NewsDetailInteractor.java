package com.cins.daily.mvp.interactor;

import com.cins.daily.listener.RequestCallBack;
import rx.Subscription;

/**
 * Created by Eric on 2017/1/18.
 */

public interface NewsDetailInteractor<T> {
    Subscription loadNewsDetail(RequestCallBack<T> callBack, String postId);
}
