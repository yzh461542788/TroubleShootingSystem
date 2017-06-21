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

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Jindiwei on 2017/6/21.
 */

@RunWith(SpringRunner.class)
@SpringBootTest

public class CompanyServiceTest {

    @Autowired
    CompanyRepository companyRepository;
    @Autowired
    CheckItemRepository checkItemRepository;
    @Autowired
    CheckTaskRepository checkTaskRepository;
    @Autowired
    TaskProcessRepository taskProcessRepository;
    @Autowired
    TemplateRepository templateRepository;
    @Autowired
    TemplateService templateService;
    @Autowired
    CheckItemService checkItemService;
    @Autowired
    CheckTaskService checkTaskService;
    @Autowired
    CompanyManagerService companyManagerService;
    @Autowired
    CompanyService companyService;

    CheckItem checkItem1 = new CheckItem();
    CheckItem checkItem2 = new CheckItem();
    Template template = new Template();
    Company company = new Company();
    CheckTask checkTask = new CheckTask();
    TaskProcess taskProcess = new TaskProcess();

    @Before
    public void setUp() {
        String cur = DateUtil.getCurrentDate().toString();
        try {
            checkItem1 = checkItemService.createCheckItem(cur, cur + 1);
            checkItem2 = checkItemService.createCheckItem(cur + 2, cur + 3);
            Assert.assertEquals(cur, checkItem1.getTitle());
            Assert.assertEquals(cur + 1, checkItem1.getContent());
            Assert.assertEquals(cur + 2, checkItem2.getTitle());
            Assert.assertEquals(cur + 3, checkItem2.getContent());

        } catch (BaseException e) {
            e.printStackTrace();
        }

        Set<CheckItem> checkItemSet = new HashSet<>();

        try {
            template = templateService.createTemplate("templatetitle", "templatecontent", checkItemSet);
            Assert.assertEquals("templatetitle", template.getTitle());
            Assert.assertEquals("templatecontent", template.getDescription());
            templateService.addCheckItem(template, checkItem1);
            templateService.addCheckItem(template, checkItem2);

            Assert.assertEquals(2,template.getCheckItems().size());

        } catch (BaseException e) {
            e.printStackTrace();
        }

        try {
            template = templateRepository.findByTitle(template.getTitle());
            checkTask = templateService.postToCheckTask(template, "CheckTaskTitle", DateUtil.getDateFromCurrentDate(1,1,1,1,1,1));
            Assert.assertEquals("CheckTaskTitle", checkTask.getTitle());
        } catch (BaseException e) {
            e.printStackTrace();
        }
        try {
            company = companyManagerService.createCompany("companyName","companyCode", "code", "contact", "phoneNumber", "businessGroup", "Category", "Scope");
            Assert.assertEquals("companyName", company.getName());
            Assert.assertEquals("companyCode", company.getCompanyCode());
            Assert.assertEquals("code", company.getOrganizationCode());
            Assert.assertEquals("contact", company.getContact());
            Assert.assertEquals("phoneNumber", company.getContactPhoneNumber());
            Assert.assertEquals("businessGroup", company.getBusinessGroup());
            Assert.assertEquals("Category", company.getBusinessCategory());
            Assert.assertEquals("Scope", company.getBusinessScope());
        } catch (BaseException e) {
            e.printStackTrace();
        }


        try {
            taskProcess = checkTaskService.deliverTaskToCompany(checkTask, company);
        } catch (BaseException e) {
            e.printStackTrace();
        }
    }

    @After
    public void tearDown() {
        taskProcessRepository.delete(taskProcess.getId());
        checkTaskRepository.delete(checkTask.getId());
        templateRepository.delete(template.getId());
        checkItemRepository.delete(checkItem1.getId());
        checkItemRepository.delete(checkItem2.getId());
        companyRepository.delete(company.getId());
    }

    /**
     * 公司进入该系统后获取与该公司相关的检查事务的列表
     */
    @Test
    public void getAllTaskProcessesTest() {
        try {
            company = companyRepository.findByName(company.getName());

            //获取检查事务列表
            Set<TaskProcess> taskProcessSet = companyService.getAllTaskProcesses(company);
            //用例在数据库中导入了1个检查事务，正确的返回结果是大小为1的检查事务列表
            Assert.assertEquals(1, taskProcessSet.size());
        } catch (BaseException e) {
            e.printStackTrace();
        }
    }

    /**
     * 当公司选中某一个检查事务后显示该检查事务下的检查项目列表
     */
    @Test
    public void getAllCheckItemsProcessInTaskProcessTest() {
        Set<TaskProcess> taskProcessSet = null;
        try {
            company = companyRepository.findByName(company.getName());
            taskProcessSet = companyService.getAllTaskProcesses(company);
            Assert.assertEquals(1, taskProcessSet.size());
        } catch (BaseException e) {
            e.printStackTrace();
        }

        try {
            taskProcess = taskProcessSet.iterator().next();

            //获取检查项目列表
            Set<CheckItemProcess> checkItemProcessSet = companyService.getAllCheckItemsProcessInTaskProcess(company, taskProcess);
            //用例在该检查项目列表中导入了两个检查项目，正确返回结果是大小为2的检查项目集合
            Assert.assertEquals(2, checkItemProcessSet.size());
        } catch (BaseException e) {
            e.printStackTrace();
        }
    }

    /**
     * 公司对检查列表中的检查事项进行排查后可以修改该检查事务的状态
     */
    @Test
    public void setCheckItemStateTest() {
        Set<TaskProcess> taskProcessSet = null;
        Set<CheckItemProcess> checkItemProcessSet = null;
        TaskProcess taskProcess = null;
        try {
            company = companyRepository.findByName(company.getName());
            taskProcessSet = companyService.getAllTaskProcesses(company);
            Assert.assertEquals(1, taskProcessSet.size());
        } catch (BaseException e) {
            e.printStackTrace();
        }
        taskProcess = taskProcessSet.iterator().next();
        try {
            checkItemProcessSet = companyService.getAllCheckItemsProcessInTaskProcess(company, taskProcess);
            Assert.assertEquals(2, checkItemProcessSet.size());
        } catch (BaseException e) {
            e.printStackTrace();
        }

        try {
            checkItem1 = checkItemRepository.findOne(checkItem1.getId());
            //设置检查项目的状态
            CheckItemProcess checkItemProcess = companyService.setCheckItemState(taskProcess, checkItem1, ItemState.Finished);
            //用例的初始状态是未检查，经过该操作后正确的返回结果应该是"已检查"
            Assert.assertEquals(ItemState.Finished, checkItemProcess.getItemState());
        } catch (BaseException e) {
            e.printStackTrace();
        }
    }

}