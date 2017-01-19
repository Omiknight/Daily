package com.cins.daily.mvp.view;

import com.cins.daily.mvp.view.base.BaseView;

/**
 * Created by Eric on 2017/1/19.
 */

public interface NewsChannelView extends BaseView {
    void initRecyclerViews(List<NewsChannelTable> newsChannelsMine, List<NewsChannelTable> newsChannelsMore);
}
