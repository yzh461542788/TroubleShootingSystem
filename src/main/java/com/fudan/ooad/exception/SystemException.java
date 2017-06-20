package com.fudan.ooad.exception;

/**
 * Created by zihao on 2017/6/20.
 */
public class SystemException extends BaseException {
    public SystemException(String serviceName, String message) {
        super(serviceName, message);
    }
}
