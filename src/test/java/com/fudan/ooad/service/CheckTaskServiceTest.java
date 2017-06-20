package com.fudan.ooad.service;

import com.fudan.ooad.entity.*;
import com.fudan.ooad.exception.BaseException;
import com.fudan.ooad.repository.*;
import com.fudan.ooad.util.DateUtil;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

/**
 * Created by zihao on 2017/6/21.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CheckTaskServiceTest {
    @Autowired
    private CheckTaskService checkTaskService;
    @Autowired
    private CompanyService companyService;
    @Autowired
    private CheckItemService checkItemService;
    @Autowired
    private TemplateService templateService;
    @Autowired
    private CompanyManagerService companyManagerService;

    // use repository to clean up database after tests
    @Autowired
    private CheckItemRepository checkItemRepository;
    @Autowired
    private CheckTaskRepository checkTaskRepository;
    @Autowired
    private TemplateRepository templateRepository;
    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private TaskProcessRepository taskProcessRepository;

    private Set<CheckItem> checkItemSet;
    private Template template;
    private CheckTask checkTask;
    private List<Company> companyList;
    private Date curDate;
    private String cur;
    private int companyNumberBeforeTest;

    @Before
    public void init() throws BaseException {
        curDate = DateUtil.getCurrentDate();
        cur = curDate.toString();

        checkItemSet = new HashSet<>();
        for (int i = 0; i < 10; i++) {
            CheckItem checkItem = checkItemService.createCheckItem(cur + i, cur + i);
            checkItemSet.add(checkItem);
        }
        template = templateService.createTemplate(cur, cur, checkItemSet);
        checkTask = templateService.postToCheckTask(template, cur, Date.from(DateUtil.getCurrentDate().toInstant().plusSeconds(3600 * 24 * 10)));

        companyNumberBeforeTest = companyRepository.findAll().size();

        companyList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Company company = companyManagerService.createCompany(cur+i, cur+i, cur+i, cur, cur, cur, cur, cur);
            companyList.add(company);
        }
    }

    @After
    public void clean() {
        checkTaskRepository.delete(checkTask.getId());
        templateRepository.delete(template.getId());
        checkItemSet.forEach(checkItem -> checkItemRepository.delete(checkItem.getId()));
        companyList.forEach(company -> companyRepository.delete(company.getId()));
    }

    @Test
    public void testGetAllCompanyList() {
        Assert.assertEquals(companyList.size() + companyNumberBeforeTest, checkTaskService.getAllCompanyList().size());
    }

    @Test
    public void testDeliverToCompany() throws BaseException {
        Company toBeDelivered = companyList.get(1);
        TaskProcess taskProcess = checkTaskService.deliverTaskToCompany(checkTask, toBeDelivered);
        Assert.assertEquals(toBeDelivered, taskProcess.getCompany());
        Assert.assertEquals(checkTask, taskProcess.getCheckTask());

        // delete database record
        taskProcessRepository.delete(taskProcess.getId());
    }

    @Test
    public void testGetDeliveredCompanies() throws BaseException {
        Company toBeDelivered = companyList.get(1);
        TaskProcess taskProcess = checkTaskService.deliverTaskToCompany(checkTask, toBeDelivered);

        Assert.assertTrue(checkTaskService.getDeliveredCompanies(checkTask).contains(toBeDelivered));

        // delete database record
        taskProcessRepository.delete(taskProcess.getId());
    }

    @Test
    public void testTaskAlreadyDeliveredToCompany() throws BaseException {
        Company toBeDelivered = companyList.get(1);
        TaskProcess taskProcess = checkTaskService.deliverTaskToCompany(checkTask, toBeDelivered);

        Assert.assertTrue(checkTaskService.taskAlreadyDeliveredToCompany(checkTask, toBeDelivered));

        // delete database record
        taskProcessRepository.delete(taskProcess.getId());
    }

    @Test
    public void testGetDeliverableCompanies() throws BaseException {
        List<Company> deliverableCompanies = checkTaskService.getDeliverableCompanies(checkTask);

        Company toBeDelivered = deliverableCompanies.get(1);
        TaskProcess taskProcess = checkTaskService.deliverTaskToCompany(checkTask, toBeDelivered);
        List<Company> newDeliverableCompanies = checkTaskService.getDeliverableCompanies(checkTask);

        Assert.assertEquals(deliverableCompanies.size() - 1, newDeliverableCompanies.size());
        Assert.assertFalse(newDeliverableCompanies.contains(toBeDelivered));

        // delete database record
        taskProcessRepository.delete(taskProcess.getId());
    }

    @Test
    public void testGetCheckItemsInCheckTask() throws BaseException {
        Set<CheckItem> checkItemsInCheckTask = checkTaskService.getCheckItemsInCheckTask(checkTask);
        Assert.assertArrayEquals(checkItemSet.toArray(), checkItemsInCheckTask.toArray());
    }

    @Test
    public void testGetTaskProcesses() throws BaseException {
        Assert.assertEquals(0, checkTaskService.getTaskProcesses(checkTask).size());

        Company toBeDelivered = companyList.get(1);
        TaskProcess taskProcess = checkTaskService.deliverTaskToCompany(checkTask, toBeDelivered);
        Assert.assertEquals(taskProcess, checkTaskService.getTaskProcess(checkTask, toBeDelivered));

        // delete database record
        taskProcessRepository.delete(taskProcess.getId());
    }

    @Test
    public void testGetCheckItemProcesses() throws BaseException {
        Company toBeDelivered = companyList.get(1);
        TaskProcess taskProcess = checkTaskService.deliverTaskToCompany(checkTask, toBeDelivered);
        Set<CheckItemProcess> checkItemProcesses = checkTaskService.getCheckItemProcesses(checkTask, toBeDelivered);
        checkItemProcesses.forEach(checkItemProcess -> {
            Assert.assertEquals(ItemState.Checking, checkItemProcess.getItemState());
            Assert.assertEquals(taskProcess, checkItemProcess.getTaskProcess());
        });

        // delete database record
        taskProcessRepository.delete(taskProcess.getId());
    }
}
