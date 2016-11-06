package com.rishabh.github.finclusionhack.utils;

import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class RxUtils {
    public static <T> Observable.Transformer<T, T> applyDefaultSchedulers(final RxAppCompatActivity activity) {
        return new Observable.Transformer<T, T>() {
            @Override
            public Observable<T> call(Observable<T> tObservable) {
                return tObservable
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .unsubscribeOn(Schedulers.computation())
                        .compose(activity.<T>bindToLifecycle());
            }
        };
    }
}