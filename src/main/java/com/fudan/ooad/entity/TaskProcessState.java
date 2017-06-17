package com.fudan.ooad.entity;

/**
 * Created by zihao on 2017/6/17.
 */
public enum TaskProcessState {
    Finished("已完成"),
    Checking("排查中");

    private String stateString;

    TaskProcessState(String state) {
        this.stateString = state;
    }

    @Override
    public String toString() {
        return this.stateString;
    }
}
