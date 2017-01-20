package com.cins.daily.mvp.view;

import com.cins.daily.common.LoadNewsType;
import com.cins.daily.mvp.entity.NewsSummary;
import com.cins.daily.mvp.view.base.BaseView;

import java.util.List;

/**
 * Created by Eric on 2017/1/17.
 */

public interface NewsListView extends BaseView {

    void setNewsList(List<NewsSummary> newsSummary, @LoadNewsType.checker int loadType);

}
