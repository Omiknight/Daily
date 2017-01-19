package com.cins.daily.mvp.ui.activities;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toolbar;

import com.cins.daily.R;
import com.cins.daily.greendao.NewsChannelTable;
import com.cins.daily.mvp.ui.activities.base.BaseActivity;
import com.cins.daily.mvp.ui.adapter.NewsChannelAdapter;
import com.cins.daily.mvp.view.NewsChannelView;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by Eric on 2017/1/19.
 */

public class NewsChannelActivity extends BaseActivity implements NewsChannelView {
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.news_channel_mine_rv)
    RecyclerView mNewsChannelMineRv;
    @BindView(R.id.news_channel_more_rv)
    RecyclerView mNewsChannelMoreRv;

    @Inject
    NewsChannelPresenterImpl mNewsChannelPresenter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_news_channel;
    }

    @Override
    public void initInjector() {
        mActivityComponent.inject(this);
    }

    @Override
    public void initViews() {

    }

    @Override
    public void initRecyclerViews(List<NewsChannelTable> newsChannelsMine, List<NewsChannelTable> newsChannelsMore) {
        initRecyclerViewMineAndMore(newsChannelsMine, newsChannelsMore);
    }

    @Override
    public void initSupportActionBar() {

    }

    @Override
    public void initRecyclerViewMineAndMore(RecyclerView recyclerView, List<NewsChannelTable> newsChannelsMore) {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 4, LinearLayoutManager.VERTICAL, false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        NewsChannelAdapter newsChannelMineAdapter = new NewsChannelAdapter(newsChannelsMore);
        recyclerView.setAdapter(newsChannelMineAdapter);
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showErrorMsg(String message) {

    }
}
