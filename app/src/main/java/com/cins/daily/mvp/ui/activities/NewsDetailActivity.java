package com.cins.daily.mvp.ui.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.cins.daily.App;
import com.cins.daily.R;
import com.cins.daily.common.Constants;
import com.cins.daily.component.DaggerNewsComponent;
import com.cins.daily.module.NewsDetailModule;
import com.cins.daily.mvp.entity.NewsDetail;
import com.cins.daily.mvp.presenter.NewsDetailPresenter;
import com.cins.daily.mvp.ui.activities.base.BaseActivity;
import com.cins.daily.mvp.view.NewsDetailView;
import com.cins.daily.utils.MyUtils;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Eric on 2017/1/16.
 */

public class NewsDetailActivity extends BaseActivity implements NewsDetailView {

    @BindView(R.id.news_detail_photo_iv)
    ImageView mNewsDetailPhotoIv;
    @BindView(R.id.mask_view)
    View mMaskView;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.toolbar_layout)
    CollapsingToolbarLayout mToolbarLayout;
    @BindView(R.id.app_bar)
    AppBarLayout mAppBar;
    @BindView(R.id.news_detail_from_tv)
    TextView mNewsDetailFromTv;
    @BindView(R.id.news_detail_body_tv)
    TextView mNewsDetailBodyTv;
    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;
    @BindView(R.id.fab)
    FloatingActionButton mFab;

    @Inject
    NewsDetailPresenter mNewsDetailPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        init();
    }

    private void init() {
        ButterKnife.bind(this);
        String postId = getIntent().getStringExtra(Constants.NEWS_POST_ID);
        DaggerNewsComponent.builder()
                .newsModule(new NewsDetailModule(this, postId))
                .build().inject(this);
        mNewsDetailPresenter.onCreate();
    }

    @Override
    public void setNewsDetail(NewsDetail newsDetail) {
        String newsTitle = newsDetail.getTitle();
        String newsSource = newsDetail.getSource();

        List<NewsDetail.ImgBean> imgSrcs = newsDetail.getImg();
        String imgSrc;
        if (imgSrcs.size() > 0) {
            imgSrc = imgSrcs.get(0).getSrc();
        } else {
            imgSrc = getIntent().getStringExtra(Constants.NEWS_IMG_RES);
        }

        String newsTime = MyUtils.formatDate(newsDetail.getPtime());
        String newsBody = newsDetail.getBody();

        mNewsDetailTitleTv.setText(newsTitle);
        mNewsDetailFromTv.setText(getString(R.string.news_from, newsSource, newsTime));

        Glide.with(App.getAppContext()).load(imgSrc).asBitmap()/*.animate(R.anim.image_load)*/
                .diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.drawable.ic_menu_gallery)
                .error(R.drawable.ic_menu_camera)
                .into(mNewsDetailPhotoIv);

        RichText.from(newsBody).into(mNewsDetailBodyTv);

        mToolbarLayout.setTitle(newsTitle);
        mToolbarLayout.setExpandedTitleColor(ContextCompat.getColor(this, R.color.colorPrimary));
        mToolbarLayout
                .setCollapsedTitleTextColor(Color.WHITE);
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
        Snackbar.make((mAppBar, message, Snackbar.LENGTH_LONG).show();
    }
}
