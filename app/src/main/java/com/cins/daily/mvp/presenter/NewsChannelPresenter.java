package com.cins.daily.mvp.presenter;


import com.cins.daily.mvp.entity.NewsChannelTable;
import com.cins.daily.mvp.presenter.base.BasePresenter;

/**
 * Created by Eric on 2017/1/19.
 */

public interface NewsChannelPresenter extends BasePresenter {
    void onItemSwap(int fromPosition, int toPosition);

    void onItemAddOrRemove(NewsChannelTable newsChannel, boolean isChannelMine);
}
