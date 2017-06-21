package com.fudan.ooad.service;

import com.fudan.ooad.entity.Company;
import com.fudan.ooad.entity.CompanyState;
import com.fudan.ooad.exception.*;
import com.fudan.ooad.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zihao on 2017/6/20.
 */
@Service
public class CompanyManagerService {
    @Autowired
    private CompanyRepository companyRepository;

    private final String SERVICE_NAME = "CompanyManagerService";

    public List<Company> getAllCompanyList() {
        return companyRepository.findAll();
    }

    public Company createCompany(String name,
                                 String companyCode,
                                 String organizationCode,
                                 String contact,
                                 String contactPhoneNumber,
                                 String businessGroup,
                                 String businessCategory,
                                 String businessScope)
            throws BaseException {
        Company company = new Company();
        company.setName(name);
        company.setCompanyCode(companyCode);
        company.setOrganizationCode(organizationCode);

        if (name == null || organizationCode == null || companyCode == null) {
            throw new InvalidPropertyException(
                    SERVICE_NAME,
                    "Name, organizationCode and companyCode cannot be null"
            );
        }
        Example<Company> example = Example.of(company);
        if (companyRepository.exists(example)) {
            throw new DuplicatedPropertyException(
                    SERVICE_NAME,
                    "Name, organizationCode or companyCode are duplicated"
            );
        }

        company.setContact(contact);
        company.setContactPhoneNumber(contactPhoneNumber);
        company.setBusinessGroup(businessGroup);
        company.setBusinessCategory(businessCategory);
        company.setBusinessScope(businessScope);

        if (contact == null || contactPhoneNumber == null || businessGroup == null
                || businessCategory == null || businessScope == null) {
            company.setCompanyState(CompanyState.Incomplete);
        } else {
            company.setCompanyState(CompanyState.Normal);
        }
        try {
            companyRepository.save(company);
        } catch (Exception e) {
            throw new SystemException(SERVICE_NAME, e.getMessage());
        }
        return company;
    }

    public Company editCompany(Company company,
                               String contact,
                               String contactPhoneNumber,
                               String businessGroup,
                               String businessCategory,
                               String businessScope)
            throws BaseException {
        if (company != null && company.getId() != null) {
            company.setContact(contact);
            company.setContactPhoneNumber(contactPhoneNumber);
            company.setBusinessGroup(businessGroup);
            company.setBusinessCategory(businessCategory);
            company.setBusinessScope(businessScope);

            if (contact == null || contactPhoneNumber == null || businessGroup == null
                    || businessCategory == null || businessScope == null) {
                company.setCompanyState(CompanyState.Incomplete);
            } else {
                company.setCompanyState(CompanyState.Normal);
            }
            try {
                return companyRepository.save(company);
            } catch (Exception e) {
                throw new SystemException(SERVICE_NAME, e.getMessage());
            }
        }
        throw new NullEntityException(
                SERVICE_NAME,
                "Company does not exist in database"
        );
    }

    public void deleteCompany(Company company) throws BaseException {
        if (company != null && company.getId() != null) {
            companyRepository.delete(company.getId());
            return;
        }
        throw new NullEntityException(
                SERVICE_NAME,
                "Company does not exist in database"
        );
    }
}
