package com.fudan.ooad.entity;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by zihao on 2017/6/7.
 */
@Entity(name = "template")
public class Template {

    private int id;
    private String title;
    private Set<CheckItem> checkItems;

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

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "template_checkitem",
            joinColumns = @JoinColumn(name = "template_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "checkitem_id", referencedColumnName = "id"))
    public Set<CheckItem> getCheckItems() {
        return checkItems;
    }

    public void setCheckItems(Set<CheckItem> checkItems) {

        this.checkItems = checkItems;
    }
}
