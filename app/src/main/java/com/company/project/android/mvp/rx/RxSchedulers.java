package com.company.project.android.mvp.rx;

import com.company.project.android.enitiy.ResponseBean;

import org.reactivestreams.Publisher;

import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * @author Administrator
 * @name MyMvp
 * @class name：com.company.project.android.mvp.rx
 * @class describe
 * @time 2018/2/8 17:50
 * @change
 * @class describe
 */

public class RxSchedulers {
    public static <T> FlowableTransformer<T, T> switchFlowableThread() {
        return new FlowableTransformer<T, T>() {
            @Override
            public Publisher<T> apply(Flowable<T> upstream) {
                return upstream
                        .subscribeOn(Schedulers.io())
                        .unsubscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    /**
     * 统一线程处理 ompose简化线程
     *
     * @param <T>
     * @return
     */
    public static <T> ObservableTransformer<T, T> switchObservableThread() {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
                /**
                 * subscribeOn()主要改变的是订阅的线程，即call()执行的线程;
                 *observeOn()主要改变的是发送的线程，即onNext()执行的线程。
                 *
                 */
                return upstream
                        .subscribeOn(Schedulers.io())//指定的就是发射事件的线程
                        .unsubscribeOn(Schedulers.io())
                        .onErrorResumeNext(new ErrorObservableSource<T>())//需要传入泛型
                        .observeOn(AndroidSchedulers.mainThread());//指定的就是订阅者接收事件的线程。

            }
        };
    }

    public static <T> ObservableTransformer<ResponseBean<T>, T> switchObservableThread2() {
        return new ObservableTransformer<ResponseBean<T>, T>() {
            @Override
            public ObservableSource<T> apply(Observable<ResponseBean<T>> upstream) {
                /**
                 * subscribeOn()主要改变的是订阅的线程，即call()执行的线程;
                 *observeOn()主要改变的是发送的线程，即onNext()执行的线程。
                 *
                 */
                return upstream
                        .subscribeOn(Schedulers.io())//指定的就是发射事件的线程
                        .unsubscribeOn(Schedulers.io())
                        .map(new MapFunction<T>())
                        .onErrorResumeNext(new ErrorObservableSource<T>())//需要传入泛型
                        .observeOn(AndroidSchedulers.mainThread());//指定的就是订阅者接收事件的线程。

            }
        };
    }
}
