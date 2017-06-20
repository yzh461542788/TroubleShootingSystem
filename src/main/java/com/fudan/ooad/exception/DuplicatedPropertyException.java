package com.fudan.ooad.exception;

/**
 * Created by zihao on 2017/6/20.
 */
public class DuplicatedPropertyException extends BaseException {
    public DuplicatedPropertyException(String serviceName, String message) {
        super(serviceName, message);
    }
}
