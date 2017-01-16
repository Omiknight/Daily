package com.cins.daily.mvp.interactor;

import java.util.List;

/**
 * Created by Eric on 2017/1/15.
 */

public interface NewsInteractor {

    interface OnFinishedListener {
        void onFinished(List<String> items);
    }

    void loadNews(OnFinishedListener listener);
}
