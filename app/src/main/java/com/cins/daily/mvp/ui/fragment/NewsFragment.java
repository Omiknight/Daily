package com.cins.daily.mvp.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.cins.daily.R;
import com.cins.daily.component.DaggerNewsComponent;
import com.cins.daily.module.NewsModule;
import com.cins.daily.mvp.entity.NewsSummary;
import com.cins.daily.mvp.presenter.NewsPresenter;
import com.cins.daily.mvp.ui.adapter.NewsRecyclerViewAdapter;
import com.cins.daily.mvp.ui.fragment.base.BaseFragment;
import com.cins.daily.mvp.view.NewsView;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Eric on 2017/1/16.
 */

public class NewsFragment extends BaseFragment implements NewsView {

    @BindView(R.id.news_rv)
    RecyclerView mNewsRv;
    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;

    @Inject
    NewsRecyclerViewAdapter mNewsRecyclerViewAdapter;
    @Inject
    NewsPresenter mNewsPresenter;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, container, false);
        ButterKnife.bind(this, view);

        mNewsRv.setHasFixedSize(true);
        //setting the LayoutManager
        mNewsRv.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

        DaggerNewsComponent.builder()
                .newsModule(new NewsModule(this))
                .build()
                .inject(this);
        mNewsPresenter.onCreateView();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void setItems(List<NewsSummary> items) {
        mNewsRecyclerViewAdapter.setItems(items);
        mNewsRv.setAdapter(mNewsRecyclerViewAdapter);
    }

    @Override
    public void showProgress() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void showMessage(String message) {

    }

    @Override
    public void onDestroyView() {
        mNewsPresenter.onDestroy();
        super.onDestroyView();
    }
}
