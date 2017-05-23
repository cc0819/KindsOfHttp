package com.kindofhttp.cc.retrofitutlis.base;

/**
 * Created by cc on 17/5/22.
 * 获取json数据基类
 */

public class BaseEntity<T>{

    private static int SUCCESS_CODE=200;//成功的code
    public int code;
    public String msg;

    private int count;
    private int start;
    private int total;
    private T data;

    public boolean isSuccess(){
        return this.getCode() ==SUCCESS_CODE;
    }


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }


    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
