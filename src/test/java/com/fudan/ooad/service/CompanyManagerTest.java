package com.fudan.ooad.service;

import com.fudan.ooad.entity.Company;
import com.fudan.ooad.entity.CompanyState;
import com.fudan.ooad.exception.BaseException;
import com.fudan.ooad.util.DateUtil;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

/**
 * Created by zihao on 2017/6/21.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CompanyManagerTest {
    @Autowired
    private CompanyManagerService companyManagerService;
    private Date curDate;
    private String cur;
    private Company company;

    @Before
    public void init() throws BaseException {
        curDate = DateUtil.getCurrentDate();
        cur = curDate.toString();
        company = companyManagerService.createCompany(
                cur, cur, cur, cur, cur, cur, cur, null
        );
    }

    @After
    public void clean() throws BaseException {
        companyManagerService.deleteCompany(company);
    }

    @Test
    public void testCreateAndDeleteCompanyAndGetCompanyList() throws BaseException {
        // create company with null field
        Company company1 = companyManagerService.createCompany(
                cur + 1, cur + 1, cur + 1, cur + 1, cur + 1, cur + 1, cur + 1, null
        );
        Assert.assertNotNull(company1);
        Assert.assertEquals(CompanyState.Incomplete, company1.getCompanyState());

        // create company without null field
        Company company2 = companyManagerService.createCompany(
                cur + 2, cur + 2, cur + 2, cur + 2, cur + 2, cur + 2, cur + 2, cur + 2
        );
        Assert.assertNotNull(company2);
        Assert.assertEquals(CompanyState.Normal, company2.getCompanyState());

        List<Company> companyList = companyManagerService.getAllCompanyList();
        Assert.assertTrue(companyList.contains(company1));
        Assert.assertTrue(companyList.contains(company2));

        // delete records
        companyManagerService.deleteCompany(company1);
        companyManagerService.deleteCompany(company2);
        companyList = companyManagerService.getAllCompanyList();
        Assert.assertFalse(companyList.contains(company1));
        Assert.assertFalse(companyList.contains(company2));
    }

    @Test
    public void testEditCompany() throws BaseException {
        Assert.assertEquals(cur, company.getContact());
        Assert.assertEquals(cur, company.getContactPhoneNumber());
        Assert.assertEquals(cur, company.getBusinessCategory());
        Assert.assertEquals(cur, company.getBusinessGroup());
        Assert.assertNull(company.getBusinessScope());
        Assert.assertEquals(CompanyState.Incomplete, company.getCompanyState());

        companyManagerService.editCompany(company, cur + 3, cur + 3, cur + 3, cur + 3, cur + 3);

        Assert.assertEquals(cur+3, company.getContact());
        Assert.assertEquals(cur+3, company.getContactPhoneNumber());
        Assert.assertEquals(cur+3, company.getBusinessCategory());
        Assert.assertEquals(cur+3, company.getBusinessGroup());
        Assert.assertEquals(cur+3, company.getBusinessScope());
        Assert.assertEquals(CompanyState.Normal, company.getCompanyState());
    }

}
