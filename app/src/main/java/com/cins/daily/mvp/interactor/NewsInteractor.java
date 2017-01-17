package com.cins.daily.mvp.interactor;

import java.util.List;

/**
 * Created by Eric on 2017/1/15.
 */

public interface NewsInteractor<T> {

    interface OnFinishedListener<T> {
        void onFinished(T items);
    }

    void loadNews(OnFinishedListener<T> listener);
}
