package com.cins.daily.mvp.presenter;

import android.content.Context;

import com.cins.daily.mvp.presenter.base.BasePresenter;

/**
 * Created by Eric on 2017/1/17.
 */

public interface NewsListPresenter extends BasePresenter{
    void onItemClicked(Context context, String postId, String imgSrc);
}
