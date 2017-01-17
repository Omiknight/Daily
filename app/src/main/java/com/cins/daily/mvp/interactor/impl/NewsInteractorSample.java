package com.cins.daily.mvp.interactor.impl;

import android.os.Handler;

import com.cins.daily.common.ApiConstants;
import com.cins.daily.common.HostType;
import com.cins.daily.listener.RequestCallBack;
import com.cins.daily.mvp.entity.NewsSummary;
import com.cins.daily.mvp.interactor.NewsInteractor;
import com.cins.daily.repository.network.RetrofitManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Eric on 2017/1/17.
 */

public class NewsInteractorSample implements NewsInteractor{


    @Override
    public void loadNews(RequestCallBack listener) { {
        /*
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                listener.onFinished(createArrayList());
            }
        });
        */
        final List<String> list = new ArrayList<>();

        Observable.from(createArrayList())
                .filter(new Func1<String, Boolean>() {
                    @Override
                    public Boolean call(String s) {
                        return !s.contains("12");
                    }
                })
                .map(new Func1<String, String>() {
                    @Override
                    public String call(String s) {
                        return s + "_rxjava";
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {
                        listener.(list);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(String s) {
                        list.add(s);
                    }
                });
    }

    private List<String> createArrayList() {
        return Arrays.asList(
                "Item 12",
                "Item 12",
                "Item 12",
                "Item 12",
                "Item 12",
                "Item 12",
                "Item 12",
                "Item 12",
                "Item 12",
                "Item 12",
                "Item 13",
                "Item 13",
                "Item 13",
                "Item 13",
                "Item 13",
                "Item 13",
                "Item 13",
                "Item 13",
                "Item 13",
                "Item 13",
                "Item 13",
                "Item 13"
        );
    }
}

