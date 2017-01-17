package com.cins.daily.mvp.interactor.impl;

/**
 * Created by Eric on 2017/1/17.
 */

public class NewsListInteractorSample /*implements NewsListInteractor*/ {
/*    @Override
    public void loadNews(final RequestCallBack listener, String type,
                         final String id, int startPag) {
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                listener.onFinished(createArrayList());
//            }
//        }, 2000);

        final List<String> list = new ArrayList<>();

        Observable.from(createArrayList())
*//*                .flatMap(new Func1<String, Observable<String>>() {
                    @Override
                    public Observable<String> call(String s) {
                        String[] str = new String[]{s.split(" ")[0], s.split(" ")[1]};
                        return Observable.from(str);
                    }
                })*//*
                .filter(new Func1<String, Boolean>() {
                    @Override
                    public Boolean call(String s) {
                        return !s.contains("12");
                    }
                })
                .map(new Func1<String, String>() {
                    @Override
                    public String call(String s) {
                        return s + "_rxjava";
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {
//                        listener.onFinished(list);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(String s) {
                        list.add(s);
                    }
                });

    }

    private List<String> createArrayList() {
        return Arrays.asList(
                "Item 1",
                "Item 2",
                "Item 3",
                "Item 4",
                "Item 5",
                "Item 6",
                "Item 7",
                "Item 8",
                "Item 9",
                "Item 10",
                "Item 10",
                "Item 10",
                "Item 10",
                "Item 10",
                "Item 10",
                "Item 10",
                "Item 11",
                "Item 11",
                "Item 11",
                "Item 11",
                "Item 11",
                "Item 11",
                "Item 11",
                "Item 11",
                "Item 11",
                "Item 11",
                "Item 11",
                "Item 11",
                "Item 11",
                "Item 11",
                "Item 11",
                "Item 11",
                "Item 11",
                "Item 11",
                "Item 11",
                "Item 11",
                "Item 11",
                "Item 11",
                "Item 11",
                "Item 11",
                "Item 11",
                "Item 11",
                "Item 11",
                "Item 11",
                "Item 11",
                "Item 11",
                "Item 11",
                "Item 11",
                "Item 12",
                "Item 12",
                "Item 12",
                "Item 12",
                "Item 12",
                "Item 12",
                "Item 12",
                "Item 12",
                "Item 12",
                "Item 12",
                "Item 13",
                "Item 13",
                "Item 13",
                "Item 13",
                "Item 13",
                "Item 13",
                "Item 13",
                "Item 13",
                "Item 13",
                "Item 13",
                "Item 13",
                "Item 13",
                "Item 13",
                "Item 13",
                "Item 13",
                "Item 13",
                "Item 13",
                "Item 13",
                "Item 13",
                "Item 13"
        );
    }*/
}
