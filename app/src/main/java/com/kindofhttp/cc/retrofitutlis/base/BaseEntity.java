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
    /**
     * success : true
     * message :
     * returnValue : success
     * times :
     */

    private boolean success;
    private String message;
    private T returnValue;
    private String times;


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

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }


    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getReturnValue() {
        return returnValue;
    }

    public void setReturnValue(T returnValue) {
        this.returnValue = returnValue;
    }

    public String getTimes() {
        return times;
    }

    public void setTimes(String times) {
        this.times = times;
    }

    @Override
    public String toString() {
        return "BaseEntity{" +
                "success=" + success +
                ", message='" + message + '\'' +
                ", returnValue=" + returnValue +
                ", times='" + times + '\'' +
                '}';
    }
}
