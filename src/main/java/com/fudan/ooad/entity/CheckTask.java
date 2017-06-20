package com.fudan.ooad.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by zihao on 2017/6/17.
 */
@Entity
public class CheckTask implements Serializable {
    private Integer id;
    private String title;
    private Set<CheckItem> checkItems = new HashSet<>();
    private Set<TaskProcess> taskProcesses = new HashSet<>();
    private Date postDate;
    private Date deadline;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    private void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "title", unique = true)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "check_task_check_item",
            joinColumns = @JoinColumn(name = "check_task_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "check_item_id", referencedColumnName = "id"))
    public Set<CheckItem> getCheckItems() {
        return new HashSet<>(checkItems);
    }

    private void setCheckItems(Set<CheckItem> checkItems) {
        this.checkItems = checkItems;
    }

    public void addCheckItem(CheckItem checkItem) {
        if (checkItems.contains(checkItem))
            return;
        checkItems.add(checkItem);
        checkItem.addCheckTask(this);
    }

    public void addCheckItems(Collection<CheckItem> checkItems) {
        checkItems.forEach(this::addCheckItem);
    }

    public void removeCheckItem(CheckItem checkItem) {
        if (!checkItems.contains(checkItem))
            return;
        checkItems.remove(checkItem);
        checkItem.removeCheckTask(this);
    }

    public void clearCheckItems() {
        checkItems.forEach(checkItem -> checkItem.removeCheckTask(this));
    }

    public void removeCheckItems(Collection<CheckItem> checkItems) {
        checkItems.forEach(this::removeCheckItem);
    }


    @OneToMany(mappedBy = "checkTask", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    public Set<TaskProcess> getTaskProcesses() {
        return taskProcesses;
    }

    private void setTaskProcesses(Set<TaskProcess> taskProcesses) {
        this.taskProcesses = taskProcesses;
    }

    public void addTaskProcess(TaskProcess taskProcess) {
        taskProcesses.add(taskProcess);
    }

    public void removeTaskProcess(TaskProcess taskProcess) {
        taskProcesses.remove(taskProcess);
    }

    public Date getPostDate() {
        return postDate;
    }

    public void setPostDate(Date postDate) {
        this.postDate = postDate;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if ((o == null) || !(o instanceof CheckTask))
            return false;
        CheckTask checkTask = (CheckTask) o;
        if (id != null && checkTask.getId() != null)
            return id.equals(checkTask.getId());
        return false;
    }

    @Override
    public int hashCode() {
        return id == null ? 0 : id.hashCode();
    }

    @Override
    public String toString() {
        return "CheckTask [id=" + id
                + ", title=" + title
                + ", postDate" + postDate
                + ", deadline" + deadline
                + ", checkItem.size]";
    }
}