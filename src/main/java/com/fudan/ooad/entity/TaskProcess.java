package com.fudan.ooad.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

/**
 * Created by zihao on 2017/6/17.
 */
@Entity
@Table(name = "task_process")
public class TaskProcess {
    private CheckTask checkTask;
    private Company company;
    private Date finishTime;
    private TaskProcessState taskProcessState;
    private Integer id;
    private Set<CheckItemProcess> checkItemProcessSet;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Integer getId() {
        return id;
    }

    private void setId(Integer id) {
        this.id = id;
    }

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "check_task_id", referencedColumnName = "id", nullable = false)
    public CheckTask getCheckTask() {
        return checkTask;
    }

    public void setCheckTask(CheckTask checkTask) {
        this.checkTask = checkTask;
    }

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "company_id", referencedColumnName = "id", nullable = false)
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

    @OneToMany(mappedBy = "taskProcess", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    public Set<CheckItemProcess> getCheckItemProcessSet() {
        return checkItemProcessSet;
    }

    private void setCheckItemProcessSet(Set<CheckItemProcess> checkItemProcessSet) {
        this.checkItemProcessSet = checkItemProcessSet;
    }

    public void addCheckItemProcess(CheckItemProcess checkItemProcess) {
        this.checkItemProcessSet.add(checkItemProcess);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if ((obj == null) || !(obj instanceof TaskProcess))
            return false;
        TaskProcess that = (TaskProcess) obj;
        return !(this.getCheckTask() == null || this.getCompany() == null) && this.getCheckTask().equals(that.getCheckTask()) && this.getCompany().equals(that.getCompany());
    }

    @Override
    public int hashCode() {
        if (this.getCheckTask() == null || this.getCompany() == null)
            return 0;
        return this.getCheckTask().hashCode() * 31 + this.getCompany().hashCode();
    }
}
