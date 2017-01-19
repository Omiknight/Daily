package com.cins.daily.mvp.presenter.impl;

import com.cins.daily.common.LoadNewsType;
import com.cins.daily.listener.RequestCallBack;
import com.cins.daily.mvp.entity.NewsSummary;
import com.cins.daily.mvp.interactor.NewsListInteractor;
import com.cins.daily.mvp.interactor.impl.NewsListInteractorImpl;
import com.cins.daily.mvp.presenter.NewsListPresenter;
import com.cins.daily.mvp.presenter.base.BasePresenterImpl;
import com.cins.daily.mvp.view.NewsListView;

import java.util.List;

/**
 * Created by Eric on 2017/1/17.
 */

public class NewsListPresenterImpl extends BasePresenterImpl<NewsListView,List<NewsSummary>>
        implements NewsListPresenter,RequestCallBack<List<NewsSummary>>{

    private NewsListInteractor<List<NewsSummary>> mNewsListInteractor;
    private String mNewsType;
    private String mNewsId;
    private int mStartPage;

    private boolean misFirstLoad;
    private boolean mIsRefresh = true;

    /**
     * 新闻页首次加载完毕
     */
    private boolean mIsLoaded;

    public NewsListPresenterImpl(NewsListInteractorImpl newsListInteractor) {
        mNewsListInteractor = newsListInteractor;
    }

    @Override
    public void onCreate() {
        if (mView != null) {
            loadNewsData();
        }
    }

    private void loadNewsData() {
        mSubscription = mNewsListInteractor.loadNews(this, mNewsType, mNewsId, mStartPage);
    }

    @Override
    public void success(List<NewsSummary> items) {
        misFirstLoad = true;
        if (mView != null) {
            mView.setNewsList(items);
            mView.hideProgress();
        }
    }

    @Override
    public void onError(String errorMsg) {
        super.onError(errorMsg);
        if (mView != null) {
            int loadType = mIsRefresh ? LoadNewsType.TYPE_REFRESH_ERROR : LoadNewsType.TYPE_LOAD_MORE_ERROR;
            mView.setNewsList(null, loadType);
        }
    }


    @Override
    public void onDestroy() {
        mView = null;
    }

    @Override
    public void setNewsTypeAndId(String newsType, String newsId) {
        mNewsType = newsType;
        mNewsId = newsId;
    }

    @Override
    public void refreshData() {
        mStartPage = 0;
        mIsRefresh = true;
        loadNewsData();
    }

    @Override
    public void loadMore() {
        mIsRefresh = false;
        loadNewsData();
    }
}
