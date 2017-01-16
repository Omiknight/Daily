package com.cins.daily.mvp.interactor.impl;

import android.os.Handler;

import com.cins.daily.mvp.interactor.NewsInteractor;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Eric on 2017/1/15.
 */

public class NewsInteractorImpl implements NewsInteractor {
    @Override
    public void loadNews(final OnFinishedListener listener) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                listener.onFinished(createArrayList());
            }
        }, 2000);
    }

    private List<String> createArrayList() {
        return Arrays.asList(
                "Item 12",
                "Item 12",
                "Item 12",
                "Item 12",
                "Item 12"
        );
    }
}
