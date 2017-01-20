package com.cins.daily.mvp.presenter;

import com.cins.daily.mvp.presenter.base.BasePresenter;

/**
 * Created by Eric on 2017/1/16.
 */

public interface NewsPresenter extends BasePresenter {
    void onChannelDbChanged();
}
