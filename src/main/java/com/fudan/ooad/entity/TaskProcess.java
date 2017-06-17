package com.fudan.ooad.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.Date;

/**
 * Created by zihao on 2017/6/17.
 */
@Entity(name = "task_process")
public class TaskProcess {
    private CheckTask checkTask;
    private Company company;
    private Date finishTime;
    private TaskProcessState taskProcessState;

    @Id
    @ManyToOne
    @JoinColumn(name = "check_task_id")
    public CheckTask getCheckTask() {
        return checkTask;
    }

    public void setCheckTask(CheckTask checkTask) {
        this.checkTask = checkTask;
    }

    @Id
    @ManyToOne
    @JoinColumn(name = "company_id")
    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Date getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }

    public TaskProcessState getTaskProcessState() {
        return taskProcessState;
    }

    public void setTaskProcessState(TaskProcessState taskProcessState) {
        this.taskProcessState = taskProcessState;
    }

}
