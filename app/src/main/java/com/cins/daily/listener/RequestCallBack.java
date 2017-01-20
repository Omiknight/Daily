package com.cins.daily.listener;

/**
 * Created by Eric on 2017/1/17.
 */

public interface RequestCallBack<T> {

    void beforeRequest();

    void success(T data);

    void onError(String errorMsg);
}
