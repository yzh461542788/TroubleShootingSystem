package com.fudan.ooad.entity;

import javax.persistence.*;

/**
 * Created by Jindiwei on 2017/6/19.
 */
@Entity
public class CheckItemProcess {
    private int id;
    private TaskProcess taskProcess;
    private CheckItem checkItem;
    private ItemState itemState;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @ManyToOne
    @JoinColumn(name = "checkitem_id")
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

    @ManyToOne
    @JoinColumn(name = "task_process_id")
    public TaskProcess getTaskProcess() {
        return taskProcess;
    }

    public void setTaskProcess(TaskProcess taskProcess) {
        this.taskProcess = taskProcess;
    }
}
