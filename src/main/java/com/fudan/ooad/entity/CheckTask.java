package com.fudan.ooad.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

/**
 * Created by zihao on 2017/6/17.
 */
@Entity
public class CheckTask {
    private int id;
    private String title;
    private Template template;
    private Set<TaskProcess> taskProcess;
    private Date postDate;
    private Date deadLine;

    @Id
    @GeneratedValue
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @ManyToOne
    @JoinColumn(name = "template_id")
    public Template getTemplate() {
        return template;
    }

    public void setTemplate(Template template) {
        this.template = template;
    }

    @OneToMany(mappedBy = "checkTask")
    public Set<TaskProcess> getTaskProcess() {
        return taskProcess;
    }

    public void setTaskProcess(Set<TaskProcess> taskProcess) {
        this.taskProcess = taskProcess;
    }

    public Date getPostDate() {
        return postDate;
    }

    public void setPostDate(Date postDate) {
        this.postDate = postDate;
    }

    public Date getDeadLine() {
        return deadLine;
    }

    public void setDeadLine(Date deadLine) {
        this.deadLine = deadLine;
    }
}
