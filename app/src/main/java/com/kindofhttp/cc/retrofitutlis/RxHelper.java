package com.kindofhttp.cc.retrofitutlis;

import android.util.Log;

import com.kindofhttp.cc.retrofitutlis.base.BaseEntity;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by cc on 17/5/23.
 * 处理返回的数据
 */

public class RxHelper {
    /**
     * 用来统一处理Http的resultCode,并将返回的Data部分剥离出来返回给subscriber
     *
     * @param <T> Subscriber真正需要的数据类型，也就是Data部分的数据类型
     */
    public static <T> Observable.Transformer<BaseEntity<T>, T> handleResult() {
        return new Observable.Transformer<BaseEntity<T>, T>() {
            @Override
            public Observable<T> call(Observable<BaseEntity<T>> tObservable) {
                return tObservable.flatMap(new Func1<BaseEntity<T>, Observable<T>>() {
                    @Override
                    public Observable<T> call(BaseEntity<T> result) {
                        if (result.isSuccess()  && result.getReturnValue()!= null) {
                            Log.e("data","---请求成功了Observable-------"+result.isSuccess());
                            Log.e("data","---请求成功了Observable-------"+result.getReturnValue());
                            return createData(result.getReturnValue());
                        } else {
                            Log.e("data","---请求失败了Observable-------"+result.getMessage());
                            return Observable.error(new Exception(result.getMessage()));
                        }
                    }
                })
                        .subscribeOn(Schedulers.io())
                        .unsubscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };

    }


    /**
     * 将数据存入subscriber
     * @param data
     * @param <T>
     * @return
     */
    private static <T> Observable<T> createData(final T data) {
        return Observable.create(new Observable.OnSubscribe<T>() {
            @Override
            public void call(Subscriber<? super T> subscriber) {
                try {
                    subscriber.onNext(data);
                    subscriber.onCompleted();
                } catch (Exception e) {
                    subscriber.onError(e);
                }
            }
        });

    }






}
