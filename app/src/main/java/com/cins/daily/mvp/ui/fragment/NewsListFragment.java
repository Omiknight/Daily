package com.cins.daily.mvp.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.cins.daily.App;
import com.cins.daily.R;
import com.cins.daily.common.Constants;
import com.cins.daily.listener.OnItemClickListener;
import com.cins.daily.module.NewsListModule;
import com.cins.daily.mvp.entity.NewsSummary;
import com.cins.daily.mvp.presenter.NewsListPresenter;
import com.cins.daily.mvp.ui.adapter.NewsRecyclerViewAdapter;
import com.cins.daily.mvp.ui.fragment.base.BaseFragment;
import com.cins.daily.mvp.view.NewsListView;
import com.cins.daily.utils.NetUtil;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Eric on 2017/1/16.
 */

public class NewsListFragment extends BaseFragment implements NewsListView,OnItemClickListener {

    @BindView(R.id.news_rv)
    RecyclerView mNewsRv;
    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;

    @Inject
    NewsRecyclerViewAdapter mNewsRecyclerViewAdapter;
    @Inject
    NewsListPresenter mNewsListPresenter;

    private String mNewsId;
    private String mNewsType;
    private int mStartPage;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mNewsId = getArguments().getString(Constants.NEWS_ID);
            mNewsType = getArguments().getString(Constants.NEWS_TYPE);
            mStartPage = getArguments().getInt(Constants.CHANNEL_POSITION);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, container, false);
        ButterKnife.bind(this, view);

        mNewsRv.setHasFixedSize(true);
        //setting the LayoutManager
        mNewsRv.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

        DaggerNewsListComponent.builder()
                .newsListModule(new NewsListModule(this, mNewsType, mNewsId))
                .build()
                .inject(this);
        mNewsListPresenter.onCreate();
        mNewsRecyclerViewAdapter.set
        checkNetState();
        return view;
    }

    private void checkNetState() {
        if (!NetUtil.isNetworkAvailable(App.getAppContext())) {
            //TODO: 刚启动app Snackbar不起作用，延迟显示也不好使，这是why？
            Toast.makeText(getActivity(), getActivity().getString(R.string.internet_error), Toast.LENGTH_SHORT).show();
            /*            new Handler().postDelayed(new Runnable() {
                 public void run() {
                     Snackbar.make(mNewsRV, App.getAppContext().getString(R.string.internet_error), Snackbar.LENGTH_LONG);
                 }
            }, 1000);*/

        }
    }

    @Override
    public void onResume() {
        super.onResume();
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
    public void showErrorMsg(String message) {
        mProgressBar.setVisibility(View.GONE);
        if (NetUtil.isNetworkAvailable(App.getAppContext())) {
            Snackbar.make(mNewsRv, message, Snackbar.LENGTH_LONG).show();
        }
    }


    @Override
    public void onDestroyView() {
        mNewsListPresenter.onDestroy();
        super.onDestroyView();
    }

    @Override
    public void onItemClick(View view, int position) {
        List<NewsSummary> newsSummaryList = mNewsRecyclerViewAdapter.getNewsSummaryList();
        mNewsListPresenter.onItemClicked(getActivity(),newsSummaryList.get(position).getPostid(),
                newsSummaryList.get(position).getImgsrc());
    }

    @Override
    public void setNewsList(List<NewsSummary> newsSummary) {
        mNewsRecyclerViewAdapter.setItems(newsSummary);
        mNewsRv.setAdapter(mNewsRecyclerViewAdapter);
    }
}
