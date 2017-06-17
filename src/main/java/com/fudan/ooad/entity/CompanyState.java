package com.fudan.ooad.entity;

/**
 * Created by zihao on 2017/6/17.
 */
public enum CompanyState {
    Normal("正常"),
    Incomplete("信息待完善");

    private String stateString;

    CompanyState(String stateString) {
        this.stateString = stateString;
    }

    @Override
    public String toString() {
        return stateString;
    }
}
