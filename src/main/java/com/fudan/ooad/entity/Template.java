package com.fudan.ooad.entity;


import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by zihao on 2017/6/7.
 */
@Entity(name = "template")
public class Template {
    private Integer id;
    private String title;
    private String description;
    private Set<CheckItem> checkItems = new HashSet<>();

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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

    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "template_check_item",
            joinColumns = @JoinColumn(name = "template_id", referencedColumnName = "id"),
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
        checkItem.addTemplate(this);
    }

    public void addCheckItems(Collection<CheckItem> checkItems) {
        checkItems.forEach(this::addCheckItem);
    }

    public void removeCheckItem(CheckItem checkItem) {
        if (!checkItems.contains(checkItem))
            return;
        checkItems.remove(checkItem);
        checkItem.removeTemplate(this);
    }

    public void clearCheckItems() {
        checkItems.forEach(checkItem -> checkItem.removeTemplate(this));
    }

    public void removeCheckItems(Collection<CheckItem> checkItems) {
        checkItems.forEach(this::removeCheckItem);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if ((o == null) || !(o instanceof Template))
            return false;
        Template template = (Template) o;
        if (id != null && template.getId() != null)
            return id.equals(template.getId());
        return false;
    }

    @Override
    public int hashCode() {
        if (id == null)
            return 0;
        return id % 100;
    }

    @Override
    public String toString() {
        return "Template [id=" + id +
                ", title=" + title +
                ", description=" + description +
                ", checkItems.size=" + checkItems.size() + "]";
    }
}
