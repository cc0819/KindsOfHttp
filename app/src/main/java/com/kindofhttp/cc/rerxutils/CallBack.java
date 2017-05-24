package com.kindofhttp.cc.rerxutils;

/**
 * Created by cc on 2017-03-11.
 */
public abstract class CallBack {
    public void onStart(){}

    public void onCompleted(){}

    abstract public void onFailure(Throwable e);

    public void onProgress(long fileSizeDownloaded){}

    abstract public void onSucess(String path, String name, long fileSize);
}
