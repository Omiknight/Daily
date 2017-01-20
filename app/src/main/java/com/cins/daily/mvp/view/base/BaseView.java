package com.cins.daily.mvp.view.base;

/**
 * Created by Eric on 2017/1/16.
 */

public interface BaseView {
    void showProgress();

    void hideProgress();

    void showMsg(String message);
}
