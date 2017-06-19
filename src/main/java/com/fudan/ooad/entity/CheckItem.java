package com.fudan.ooad.entity;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by zihao on 2017/6/7.
 */
@Entity(name = "checkitem")
public class CheckItem {
    private int id;
    private String title;
    private String content;
    private Set<Template> templates;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    @ManyToMany(mappedBy = "checkItems")
    public Set<Template> getTemplates() {
        return templates;
    }

    public void setTemplates(Set<Template> templates) {
        this.templates = templates;
    }
}
