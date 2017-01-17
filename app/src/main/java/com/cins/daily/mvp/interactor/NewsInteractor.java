package com.cins.daily.mvp.interactor;

import com.cins.daily.listener.RequestCallBack;

import java.util.List;

/**
 * Created by Eric on 2017/1/15.
 */

public interface NewsInteractor<T> {

    void loadNews(RequestCallBack<T> listener);
}
