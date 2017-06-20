package com.fudan.ooad.exception;

/**
 * Created by zihao on 2017/6/20.
 */
public class NullEntityException extends BaseException {
    private String serviceName;

    public NullEntityException(String serviceName, String message) {
        super(serviceName, message);
        this.serviceName = serviceName;
    }

    @Override
    public String getServiceName() {
        return serviceName;
    }
}
