package com.kindofhttp.cc.entity;

import java.util.List;

/**
 * Created by cc on 17/5/21.
 */

public class WeekDayEntiy {

    private int lastWeekCount;
    /**
     * success : true
     * message : ok!
     * returnValue : ["17W20"]
     * times :
     * secretKey : null
     */

    private boolean success;
    private String message;
    private String times;
    private Object secretKey;
    private List<String> returnValue;

    public int getLastWeekCount() {
        return lastWeekCount;
    }

    public void setLastWeekCount(int lastWeekCount) {
        this.lastWeekCount = lastWeekCount;
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

    public String getTimes() {
        return times;
    }

    public void setTimes(String times) {
        this.times = times;
    }

    public Object getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(Object secretKey) {
        this.secretKey = secretKey;
    }

    public List<String> getReturnValue() {
        return returnValue;
    }

    public void setReturnValue(List<String> returnValue) {
        this.returnValue = returnValue;
    }
}
