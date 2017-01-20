package com.cins.daily.utils;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Eric on 2017/1/20.
 */

public class TransformUtils {
    public static <T> Observable.Transformer<T, T> defaultSchedulers() {
        return new Observable.Transformer<T, T>() {

            @Override
            public Observable<T> call(Observable<T> tObservable) {
                return tObservable
                        .unsubscribeOn(Schedulers.io())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }
}
