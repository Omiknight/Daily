package com.cins.daily.mvp.interactor.impl;

import com.cins.daily.App;
import com.cins.daily.R;
import com.cins.daily.common.HostType;
import com.cins.daily.listener.RequestCallBack;
import com.cins.daily.mvp.entity.NewsDetail;
import com.cins.daily.mvp.interactor.NewsDetailInteractor;
import com.cins.daily.repository.network.RetrofitManager;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Eric on 2017/1/18.
 */

public class NewsDetailInteractorImpl implements NewsDetailInteractor<NewsDetail> {
    @Inject
    public NewsDetailInteractorImpl() {

    }

    @Override
    public Subscription loadNewsDetail(final RequestCallBack<NewsDetail> callBack, final String postId) {
        return RetrofitManager.getInstance(HostType.NETEASE_NEWS_VIDEO).getNewsDetailObservable(postId)
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(Schedulers.io())
                .map(new Func1<Map<String, NewsDetail>, NewsDetail>() {
                    @Override
                    public NewsDetail call(Map<String, NewsDetail> map) {
                        NewsDetail newsDetail = map.get(postId);
                        List<NewsDetail.ImgBean> imgSrcs = newsDetail.getImg();
                        if (imgSrcs != null && imgSrcs.size() >= 2 && App.isHavePhoto()) {
                            String newsBody = newsDetail.getBody();
                            for (int i = 0; i < imgSrcs.size(); i++) {
                                String oldChars = "<!--IMG#" + i + "-->";
                                String newChars = "<img src=\"" + imgSrcs.get(i).getSrc() + "\" />";
                                newsBody = newsBody.replace(oldChars, newChars);
                            }
                            newsDetail.setBody(newsBody);
                        }
                        return newsDetail;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<NewsDetail>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        callBack.onError(App.getAppContext().getString(R.string.load_error));
                    }

                    @Override
                    public void onNext(NewsDetail newsDetail) {
                        callBack.success(newsDetail);
                    }
                });
    }
}
