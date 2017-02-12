package com.cins.daily.mvp.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.cins.daily.App;
import com.cins.daily.R;
import com.cins.daily.common.Constants;
import com.cins.daily.event.PhotoDetailOnClickEvent;
import com.cins.daily.mvp.ui.fragment.base.BaseFragment;

import com.cins.daily.utils.RxBus;
import com.cins.daily.utils.TransformUtils;
import com.socks.library.KLog;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscriber;
import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by Eric on 2017/1/20.
 */

public class PhotoDetailFragment extends BaseFragment {

    @BindView(R.id.photo_view)
    PhotoView mPhotoView;
    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;

    private String mImgSrc;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mImgSrc = getArguments().getString(Constants.PHOTO_DETAIL_IMGSRC);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), container, false);
        ButterKnife.bind(this, view);
        initViews(view);
        return view;
    }

    @Override
    public void initInjector() {

    }

    @Override
    public void initViews(View view) {
        mProgressBar.setVisibility(View.VISIBLE);
        initPhotoView();
        setPhotoViewClickEvent();
    }

    private void initPhotoView() {
        mSubscription = Observable.timer(100, TimeUnit.MILLISECONDS) // 直接使用glide加载的话，activity切换动画时背景短暂为默认背景色
                .compose(TransformUtils.<Long>defaultSchedulers())
                .subscribe(new Subscriber<Long>() {
                    @Override
                    public void onCompleted() {
                        mProgressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mProgressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onNext(Long aLong) {
                        Glide.with(App.getAppContext()).load(mImgSrc).asBitmap()
                                .format(DecodeFormat.PREFER_ARGB_8888)
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .error(R.drawable.ic_load_fail)
                                .into(mPhotoView);

                    }
                });
    }

    private void setPhotoViewClickEvent() {
        mPhotoView.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
            @Override
            public void onPhotoTap(View view, float v, float v1) {
                KLog.d();
                handleOnTabEvent();
            }

            @Override
            public void onOutsidePhotoTap() {
                KLog.d();
                handleOnTabEvent();
            }
        });
    }

    private void handleOnTabEvent() {
        RxBus.getInstance().post(new PhotoDetailOnClickEvent());
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_news_photo_detail;
    }
}
