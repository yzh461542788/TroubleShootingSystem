package com.fudan.ooad.entity;

import javax.persistence.*;

/**
 * Created by Jindiwei on 2017/6/19.
 */
@Entity
public class CheckItemProcess {
    private Integer id;
    private TaskProcess taskProcess;
    private CheckItem checkItem;
    private ItemState itemState;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Integer getId() {
        return id;
    }

    private void setId(Integer id) {
        this.id = id;
    }

    @ManyToOne
    @JoinColumn(name = "check_item_id")
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

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;
        if ((obj == null) || !(obj instanceof CheckItemProcess))
            return false;
        CheckItemProcess checkItemProcess = (CheckItemProcess) obj;
        if (id != null && checkItemProcess.getId() != null)
            return id.equals(checkItemProcess.getId());
        return false;
    }

    @Override
    public int hashCode() {
        return id == null ? 0 : id.hashCode();
    }
}
