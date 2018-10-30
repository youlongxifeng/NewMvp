package com.company.project.android.mvp.rx;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

/**
 * @Author: Administrator
 * @Time: 2018 2018/10/30 15:20
 * @description: （添加一句描述）
 */
public class ErrorObservableSource<T> implements Function<Throwable, ObservableSource<? extends T>> {
    @Override
    public ObservableSource<? extends T> apply(Throwable throwable) throws Exception {
        return Observable.error(ApiException.handleException(throwable));
    }
    /*@Override
    public ObservableSource apply(Throwable throwable) throws Exception {

    }*/
}

