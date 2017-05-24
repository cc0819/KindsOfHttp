package com.kindofhttp.cc.rerxutils;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import rx.Subscriber;

/**
 * Created by cc on 2017-05-11.
 * BaseSubscriber
 */
public abstract class BaseSubscriber<T> extends Subscriber<T> {

    private Context context;
    private boolean isShowProgress;
    private boolean isNeedCahe;

    public BaseSubscriber(Context context) {
        this.context = context;
    }


    @Override
    public void onStart() {
        super.onStart();
        Toast.makeText(context, "http is start", Toast.LENGTH_SHORT).show();

        // todo some common as show loadding  and check netWork is NetworkAvailable
        // if  NetworkAvailable no !   must to call onCompleted
//        if (!NetworkUtil.isNetworkAvailable(context)) {
//            Toast.makeText(context, "无网络，读取缓存数据", Toast.LENGTH_SHORT).show();
//            onCompleted();
//        }

    }



    @Override
    public void onNext(T t) {
        onSuccess(t);

    }

    @Override
    public void onError(Throwable e) {
        Log.e("Tamic", e.getMessage());
        // todo error somthing
        Toast.makeText(context, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
        if(e instanceof ExceptionHandle.ResponeThrowable){
            onFailure((ExceptionHandle.ResponeThrowable)e);
        } else {
            onFailure(new ExceptionHandle.ResponeThrowable(e, ExceptionHandle.ERROR.UNKNOWN));
        }
    }



    @Override
    public void onCompleted() {
        Toast.makeText(context, "http is Complete", Toast.LENGTH_SHORT).show();
        // todo some common as  dismiss loadding
    }


    public abstract void onFailure(ExceptionHandle.ResponeThrowable e);


    public abstract void onSuccess(T t);





}
