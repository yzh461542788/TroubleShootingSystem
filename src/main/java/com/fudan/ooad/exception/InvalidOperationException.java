package com.fudan.ooad.exception;

/**
 * Created by Jindiwei on 2017/6/20.
 */
public class InvalidOperationException extends BaseException {
    public InvalidOperationException(String serviceName, String message) {
        super(serviceName, message);
    }
}
