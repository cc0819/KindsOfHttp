package com.kindofhttp.cc.retrofitutlis.base;

import java.io.Serializable;

/**
 * Created by cc on 17/5/22.
 * 获取json数据基类
 */

public class BaseEntity<T> implements Serializable {
//
//    private static int SUCCESS_CODE=200;//成功的code
//    public int code;
//    public String msg;
    public T data;

    private int start;
    private int count;
    private String title;

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    //
//    public boolean isSuccess(){
//        return this.getCode() ==SUCCESS_CODE;
//    }
//
//
//    public int getCode() {
//        return code;
//    }
//
//    public void setCode(int code) {
//        this.code = code;
//    }
//
//    public String getMsg() {
//        return msg;
//    }
//
//    public void setMsg(String msg) {
//        this.msg = msg;
//    }
//
    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }





}
