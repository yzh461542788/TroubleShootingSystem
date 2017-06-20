package com.fudan.ooad.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by zihao on 2017/6/7.
 */
@Entity(name = "check_item")
public class CheckItem {
    private Integer id;
    private String title;
    private String content;
    private Set<Template> templates = new HashSet<>();
    private Set<CheckTask> checkTasks = new HashSet<>();

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Integer getId() {
        return id;
    }

    private void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Column(name = "content")
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @ManyToMany(mappedBy = "checkItems", fetch = FetchType.EAGER)
    public Set<Template> getTemplates() {
        // return a copy of set so that the Set cannot be modified outside
        return new HashSet<>(templates);
    }

    // make it private so that this setter can only be invoke by Hibernate reflection
    private void setTemplates(Set<Template> templates) {
        this.templates = templates;
    }

    void addTemplate(Template template) {
        if (templates.contains(template))
            return;
        templates.add(template);
        template.addCheckItem(this);
    }

    void removeTemplate(Template template) {
        if (!templates.contains(template))
            return;
        templates.remove(template);
        template.removeCheckItem(this);
    }

    @ManyToMany(mappedBy = "checkItems", fetch = FetchType.EAGER)
    public Set<CheckTask> getCheckTasks() {
        return checkTasks;
    }

    private void setCheckTasks(Set<CheckTask> checkTasks) {
        this.checkTasks = checkTasks;
    }

    void addCheckTask(CheckTask checkTask) {
        if (checkTasks.contains(checkTask))
            return;
        checkTasks.add(checkTask);
        checkTask.addCheckItem(this);
    }

    void removeCheckTask(CheckTask checkTask) {
        if (!checkTasks.contains(checkTask))
            return;
        checkTasks.remove(checkTask);
        checkTask.removeCheckItem(this);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;
        if ((obj == null) || !(obj instanceof CheckItem))
            return false;
        CheckItem checkItem = (CheckItem) obj;
        if (id != null && checkItem.getId() != null)
            return id.equals(checkItem.getId());
        return false;
    }

    @Override
    public int hashCode() {
        return id == null ? 0 : id.hashCode();
    }

    @Override
    public String toString() {
        return "CheckItem [id=" + id
                + ", title=" + title
                + ", content=" + content
                + ", templates.size=" + templates.size() + "]";
    }
}
