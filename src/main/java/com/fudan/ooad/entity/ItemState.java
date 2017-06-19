package com.fudan.ooad.entity;

/**
 * Created by Jindiwei on 2017/6/19.
 */
public enum ItemState {
    Finished("已检查"),
    Checking("未检查");

    private String stateString;

    ItemState(String state) {
        this.stateString = state;
    }

    @Override
    public String toString() {
        return this.stateString;
    }
}
