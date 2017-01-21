package com.cins.daily.mvp.interactor.impl;

import com.cins.daily.common.ApiConstants;
import com.cins.daily.common.HostType;
import com.cins.daily.listener.RequestCallBack;
import com.cins.daily.mvp.entity.NewsSummary;
import com.cins.daily.mvp.interactor.NewsListInteractor;
import com.cins.daily.repository.network.RetrofitManager;
import com.cins.daily.utils.MyUtils;
import com.cins.daily.utils.TransformUtils;
import com.socks.library.KLog;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Func1;
import rx.functions.Func2;

/**
 * Created by Eric on 2017/1/17.
 */

public class NewsListInteractorImpl implements NewsListInteractor<List<NewsSummary>> {

    //    private boolean mIsNetError;
    @Inject
    public NewsListInteractorImpl() {
    }

    @Override
    public Subscription loadNews(final RequestCallBack<List<NewsSummary>> listener, String type,
                                 final String id, int startPage) {
//        mIsNetError = false;
        // 对API调用了observeOn(MainThread)之后，线程会跑在主线程上，包括onComplete也是，
        // unsubscribe也在主线程，然后如果这时候调用call.cancel会导致NetworkOnMainThreadException
        // 加一句unsubscribeOn(io)
        return RetrofitManager.getInstance(HostType.NETEASE_NEWS_VIDEO).getNewsListObservable(type, id, startPage)
                .flatMap(new Func1<Map<String, List<NewsSummary>>, Observable<NewsSummary>>() {
                    @Override
                    public Observable<NewsSummary> call(Map<String, List<NewsSummary>> map) {
                        if (id.endsWith(ApiConstants.HOUSE_ID)) {
                            // 房产实际上针对地区的它的id与返回key不同
                            return Observable.from(map.get("北京"));
                        }
                        return Observable.from(map.get(id));
                    }
                })
                .map(new Func1<NewsSummary, NewsSummary>() {
                    @Override
                    public NewsSummary call(NewsSummary newsSummary) {
                        String ptime = MyUtils.formatDate(newsSummary.getPtime());
                        newsSummary.setPtime(ptime);
                        return newsSummary;
                    }
                })
//                .toList()
                .distinct()
                .toSortedList(new Func2<NewsSummary, NewsSummary, Integer>() {
                    @Override
                    public Integer call(NewsSummary newsSummary, NewsSummary newsSummary2) {
                        return newsSummary2.getPtime().compareTo(newsSummary.getPtime());
                    }
                })
                .compose(TransformUtils.<List<NewsSummary>>defaultSchedulers())
                .subscribe(new Subscriber<List<NewsSummary>>() {
                    @Override
                    public void onCompleted() {
                        KLog.d();
//                        checkNetState(listener);
                    }

                    @Override
                    public void onError(Throwable e) {
                        KLog.e(e.toString());
//                        checkNetState(listener);
//                        if (!NetUtil.isNetworkAvailable(App.getAppContext())) {
                        listener.onError(MyUtils.analyzeNetworkError(e));
//                        }
                    }

                    @Override
                    public void onNext(List<NewsSummary> newsSummaries) {
                        KLog.d();
                        listener.success(newsSummaries);
                    }
                });

    }

//    private void checkNetState(RequestCallBack<List<NewsSummary>> listener) {
//        if (!NetUtil.isNetworkAvailable(App.getAppContext())) {
//            mIsNetError = true;
//            listener.onError(App.getAppContext().getString(R.string.internet_error));
//        } else {
//            mIsNetError = false;
//        }
//    }
}
