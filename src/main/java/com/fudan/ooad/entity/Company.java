package com.fudan.ooad.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.Set;

/**
 * Created by zihao on 2017/6/17.
 */
@Entity(name = "company")
public class Company {
    private int id;
    private String name;
    private String companyCode;
    private String organizationCode; // 组织机构代码
    private String contact;
    private String contactPhoneNumber;
    private String businessGroup; // 行业大类
    private String businessCategory; // 所属行业
    private String businessScope; // 主要经营类项
    private CompanyState companyState;
    private Set<TaskProcess> taskProcesses;

    @Id
    @GeneratedValue
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public String getOrganizationCode() {
        return organizationCode;
    }

    public void setOrganizationCode(String organizationCode) {
        this.organizationCode = organizationCode;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getContactPhoneNumber() {
        return contactPhoneNumber;
    }

    public void setContactPhoneNumber(String contactPhoneNumber) {
        this.contactPhoneNumber = contactPhoneNumber;
    }

    public String getBusinessGroup() {
        return businessGroup;
    }

    public void setBusinessGroup(String businessGroup) {
        this.businessGroup = businessGroup;
    }

    public String getBusinessCategory() {
        return businessCategory;
    }

    public void setBusinessCategory(String businessCategory) {
        this.businessCategory = businessCategory;
    }

    public String getBusinessScope() {
        return businessScope;
    }

    public void setBusinessScope(String businessScope) {
        this.businessScope = businessScope;
    }

    public CompanyState getCompanyState() {
        return companyState;
    }

    public void setCompanyState(CompanyState companyState) {
        this.companyState = companyState;
    }

    @OneToMany(mappedBy = "company")
    public Set<TaskProcess> getTaskProcesses() {
        return taskProcesses;
    }

    public void setTaskProcesses(Set<TaskProcess> taskProcesses) {
        this.taskProcesses = taskProcesses;
    }
}
