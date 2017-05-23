package com.kindofhttp.cc.entity;

/**
 * Created by cc on 17/5/23.
 */

public class Email {

    private String msg;
    private String email;
    private String returnValue;

    public String getReturnValue() {
        return returnValue;
    }

    public void setReturnValue(String returnValue) {
        this.returnValue = returnValue;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "Email{" +
                "msg='" + msg + '\'' +
                '}';
    }
}
