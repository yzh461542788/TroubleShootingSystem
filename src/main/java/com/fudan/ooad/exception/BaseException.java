package com.fudan.ooad.exception;

/**
 * Created by zihao on 2017/6/19.
 */
public abstract class BaseException extends Exception {
    protected String serviceName;
    protected String message;

    public BaseException(String serviceName, String message) {
        super();
        this.message = message;
        this.serviceName = serviceName;
    }

    public String getServiceName() {
        return serviceName;
    }
}
