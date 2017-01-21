package com.cins.daily.mvp.interactor.impl;

import com.cins.daily.App;
import com.cins.daily.common.HostType;
import com.cins.daily.listener.RequestCallBack;
import com.cins.daily.mvp.entity.NewsDetail;
import com.cins.daily.mvp.interactor.NewsDetailInteractor;
import com.cins.daily.repository.network.RetrofitManager;
import com.cins.daily.utils.MyUtils;
import com.cins.daily.utils.TransformUtils;
import com.socks.library.KLog;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import rx.Observer;
import rx.Subscription;
import rx.functions.Func1;

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
                .map(new Func1<Map<String, NewsDetail>, NewsDetail>() {
                    @Override
                    public NewsDetail call(Map<String, NewsDetail> map) {
                        KLog.d(Thread.currentThread().getName());

                        NewsDetail newsDetail = map.get(postId);
                        changeNewsDetail(newsDetail);
                        return newsDetail;
                    }
                })
                .compose(TransformUtils.<NewsDetail>defaultSchedulers())
                .subscribe(new Observer<NewsDetail>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        KLog.e(e.toString());
                        callBack.onError(MyUtils.analyzeNetworkError(e));
                    }

                    @Override
                    public void onNext(NewsDetail newsDetail) {
                        callBack.success(newsDetail);
                    }
                });
    }

    private void changeNewsDetail(NewsDetail newsDetail) {
        List<NewsDetail.ImgBean> imgSrcs = newsDetail.getImg();
        if (isChange(imgSrcs)) {
            String newsBody = newsDetail.getBody();
            newsBody = changeNewsBody(imgSrcs, newsBody);
            newsDetail.setBody(newsBody);
        }
    }
    private boolean isChange(List<NewsDetail.ImgBean> imgSrcs) {
        return imgSrcs != null && imgSrcs.size() >= 2 && App.isHavePhoto();
    }

    private String changeNewsBody(List<NewsDetail.ImgBean> imgSrcs, String newsBody) {
        for (int i = 0; i < imgSrcs.size(); i++) {
            String oldChars = "<!--IMG#" + i + "-->";
            String newChars;
            if (i == 0) {
                newChars = "";
            } else {
                newChars = "<img src=\"" + imgSrcs.get(i).getSrc() + "\" />";
            }
            newsBody = newsBody.replace(oldChars, newChars);
        }
        return newsBody;
    }
}
