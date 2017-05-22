package com.kindofhttp.cc.retrofitutlis.base;

import android.accounts.NetworkErrorException;
import android.content.Context;
import android.util.Log;

import com.kindofhttp.cc.retrofitutlis.view.ProgressDialog;

import java.net.ConnectException;
import java.net.UnknownHostException;
import java.util.concurrent.TimeoutException;

import rx.Subscriber;

/**
 * Created by cc on 17/5/22.
 *
 */
public abstract class BaseSubscriber<T> extends Subscriber<BaseEntity<T>> {

    private Context mContext;

    public BaseSubscriber(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void onStart() {
        super.onStart();
        showProgressDialog();
        Log.e("cc","----请求开始了---");
    }



    @Override
    public void onCompleted() {
        Log.e("cc","----请求结束了---");
    }

    @Override
    public void onError(Throwable e) {
        Log.w("info", "onError: "+e.toString() );//这里可以打印错误信息
        onRequestEnd();
        try {
            if (e instanceof ConnectException
                    || e instanceof TimeoutException
                    || e instanceof NetworkErrorException
                    || e instanceof UnknownHostException) {
                onFailure(e, true);
            } else {
                onFailure(e, false);
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

        @Override
    public void onNext(BaseEntity<T> tBaseEntity) {
        Log.w("info", "onNext: "+"成功了"+tBaseEntity.getData());//这里可以打印错误信息
        onRequestEnd();
//        if (tBaseEntity.isSuccess()) {
            try {
                onSuccees(tBaseEntity);
            } catch (Exception e) {
                e.printStackTrace();
            }
//        } else {
//            try {
//                onCodeError(tBaseEntity);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
    }




    /**
     * 返回成功
     *
     * @param t
     * @throws Exception
     */
    protected abstract void onSuccees(BaseEntity<T> t) throws Exception;

    /**
     * 返回成功了,但是code错误
     *
     * @param t
     * @throws Exception
     */
    protected void onCodeError(BaseEntity<T> t) throws Exception {


    }

    /**
     * 返回失败
     *
     * @param e
     * @param isNetWorkError 是否是网络错误
     * @throws Exception
     */
    protected abstract void onFailure(Throwable e, boolean isNetWorkError) throws Exception;





    protected void onRequestEnd() {
        closeProgressDialog();
    }

    public void showProgressDialog() {
        ProgressDialog.show(mContext, false, "请稍后");
    }

    public void closeProgressDialog() {
        ProgressDialog.cancle();
    }






}
