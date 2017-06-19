package com.fudan.ooad.repository;

import com.fudan.ooad.entity.Company;
import com.fudan.ooad.entity.CompanyState;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by zihao on 2017/6/18.
 */
public interface CompanyRepository extends JpaRepository<Company, Integer> {
    Company findByName(String name);
    Company findByCompanyCode(String companyCode);
    Company findByOrganizationCode(String organizationCode);
    Company findByCompanyState(CompanyState companyState);
    List<Company> findByBusinessGroup(String businessGroup);
    List<Company> findByBusinessCategory(String businessCategory);
    List<Company> findByBusinessScope(String businessScope);
}
