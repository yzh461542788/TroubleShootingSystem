package com.fudan.ooad.exception;

/**
 * Created by zihao on 2017/6/20.
 */
public class InvalidPropertyException extends BaseException {
    public InvalidPropertyException(String serviceName, String message) {
        super(serviceName, message);
    }
}
