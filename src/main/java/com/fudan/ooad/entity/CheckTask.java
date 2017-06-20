package com.fudan.ooad.entity;

import javax.persistence.*;
import java.io.Serializable;
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
    private Template template;
    private Set<TaskProcess> taskProcesses = new HashSet<>();
    private Date postDate;
    private Date deadline;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "title", unique = true)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "template_id")
    public Template getTemplate() {
        return template;
    }

    public void setTemplate(Template template) {
        this.template = template;
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
                + ", templateId=" + template
                + ", postDate" + postDate
                + ", deadline" + deadline + "]";
    }
}
