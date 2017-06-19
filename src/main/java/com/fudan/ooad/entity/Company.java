package com.fudan.ooad.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by zihao on 2017/6/17.
 */
@Entity(name = "company")
public class Company {
    private Integer id;
    private String name;
    private String companyCode;
    private String organizationCode; // 组织机构代码
    private String contact;
    private String contactPhoneNumber;
    private String businessGroup; // 行业大类
    private String businessCategory; // 所属行业
    private String businessScope; // 主要经营类项
    private CompanyState companyState;
    private Set<TaskProcess> taskProcesses = new HashSet<>();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "name", unique = true)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "company_code", unique = true)
    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    @Column(name = "organization_code", unique = true)
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

    @OneToMany(mappedBy = "company", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    public Set<TaskProcess> getTaskProcesses() {
        return new HashSet<>(taskProcesses);
    }

    private void setTaskProcesses(Set<TaskProcess> taskProcesses) {
        this.taskProcesses = taskProcesses;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;
        if ((obj == null) || !(obj instanceof Company))
            return false;
        Company company = (Company) obj;
        if (id != null && company.getId() != null)
            return id.equals(company.getId());
        return false;
    }

    @Override
    public int hashCode() {
        return id == null ? 0 : id.hashCode();
    }

    @Override
    public String toString() {
        return "Company [id=" + id +
                ", name=" + name +
                ", companyCode=" + companyCode +
                ", organizationCode=" + organizationCode +
                ", contact=" + contact +
                ", contactPhoneNumber" + contactPhoneNumber +
                ", businessGroup" + businessGroup +
                ", businessCategory" + businessCategory +
                ", businessScope" + businessScope +
                ", companyState" + companyState + "]";
    }
}
