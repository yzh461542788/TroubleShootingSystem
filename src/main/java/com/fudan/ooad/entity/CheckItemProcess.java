package com.fudan.ooad.entity;

/**
 * Created by Jindiwei on 2017/6/19.
 */
public class CheckItemProcess {
    private int id;
    private TaskProcess taskProcess;
    private CheckItem checkItem;
    private ItemState itemState;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public CheckItem getCheckItem() {
        return checkItem;
    }

    public void setCheckItem(CheckItem checkItem) {
        this.checkItem = checkItem;
    }

    public ItemState getItemState() {
        return itemState;
    }

    public void setItemState(ItemState itemState) {
        this.itemState = itemState;
    }

    public TaskProcess getTaskProcess() {
        return taskProcess;
    }

    public void setTaskProcess(TaskProcess taskProcess) {
        this.taskProcess = taskProcess;
    }
}
