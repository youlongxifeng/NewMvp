package com.company.project.android.ui.main;

import android.util.Log;

import com.company.project.android.api.ApiEngine;
import com.company.project.android.bean.Gank;
import com.company.project.android.mvp.rx.RxSchedulers;
import com.company.project.android.utils.LogUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

/**
 * @author Administrator
 * @name MyMvp
 * @class name：com.company.project.android.ui.main
 * @class describe
 * @time 2018/2/8 16:57
 * @change
 * @class describe
 */


public class MainPresenter extends MainContract.Presenter {


    public MainPresenter(/*MainContract.View view*/) {
        //this.mView = view;
        this.mModel = new MainModel();
    }

    @Override
    public String login(Map maps) {
        /*  String name = mModel.loginSuccess();
         */
        Log.i("TAG", "=====active=" + Thread.currentThread().getName());
        String name = mModel.loginSuccess();
        Log.i("TAG", "=====active=" + name + "  =" + (mView != null));
        if (mView != null) {
            mView.setLogin(name);
        }
        return "";
    }

    private Observable<Boolean> getObservable() {
        return Observable.just(true);
    }


    private CompositeDisposable mDisposables = new CompositeDisposable();

    @Override
    public void getGank() {
        DisposableObserver<Gank> mObserver = getDisposableObserver();
        mModel.getGank()
                .compose(RxSchedulers.<Gank>switchObservableThread())
                .subscribe(mObserver);
        addSubscribe(mObserver);
        ApiEngine.getInstance().getApiService().download("http://192.168.0.42:8080/download/fxhb_multiple_device/release/1.0.0.1/app-device-release.apk")
                .subscribeOn(Schedulers.io())
                .map(new Function<ResponseBody, Object>() {
                    @Override
                    public Object apply(ResponseBody responseBody) throws Exception {

                        InputStream inputStream = null;
                        //初始化
                        inputStream = responseBody.byteStream();
                        //总长度
                        long allLength =responseBody.contentLength();
                        LogUtils.i("TAG","=========================allLength="+allLength);
                        return null;
                    }
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Object>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Object o) {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    @Override
    public void accessToken(String app_id, String secret_key) {
        DisposableObserver<JSONObject> mObserver = getDisposableObserver2();

        try {
            JSONObject requestData = new JSONObject();
            requestData.put("appid", app_id);
            requestData.put("secret", secret_key);
            RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), requestData.toString());
            mModel.accessToken(requestBody)
                    .compose(RxSchedulers.<JSONObject>switchObservableThread())
                    .subscribe(mObserver);
            addSubscribe(mObserver);
        } catch (JSONException e) {
            e.printStackTrace();
            LogUtils.i("TAG", "e======0=" + e);
        }

    }

    private DisposableObserver<Gank> getDisposableObserver() {
        return new DisposableObserver<Gank>() {
            @Override
            public void onNext(Gank aBoolean) {
                LogUtils.i("TAG", "aBoolean===" + aBoolean);

            }

            @Override
            public void onError(Throwable e) {
                LogUtils.i("TAG", "aBoolean==e=" + e);
            }

            @Override
            public void onComplete() {
                LogUtils.i("TAG", "aBoolean==onComplete=");
            }
        };
    }

    public DisposableObserver<JSONObject> getDisposableObserver2() {
        return new DisposableObserver<JSONObject>() {
            @Override
            public void onNext(JSONObject aBoolean) {
                LogUtils.i("TAG", "aBoolean===" + aBoolean);

            }

            @Override
            public void onError(Throwable e) {
                LogUtils.i("TAG", "aBoolean==e=" + e);
            }

            @Override
            public void onComplete() {
                LogUtils.i("TAG", "onComplete=");
            }
        };
    }
}
