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
    // 在测试开始之前插入10个检查项目，一个模版，一个检查任务，10个公司
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
    // 在测试结束后清除之前的插入
    public void clean() {
        checkTaskRepository.delete(checkTask.getId());
        templateRepository.delete(template.getId());
        checkItemSet.forEach(checkItem -> checkItemRepository.delete(checkItem.getId()));
        companyList.forEach(company -> companyRepository.delete(company.getId()));
    }

    @Test
    public void testGetAllCompanyList() {
        // 在测试前插入了10个公司，插入后数据库公司总数应该与插入前的公司总数加上插入的公司数量相等
        Assert.assertEquals(companyList.size() + companyNumberBeforeTest, checkTaskService.getAllCompanyList().size());
    }

    @Test
    public void testDeliverToCompany() throws BaseException {
        // 将任务发放给公司，
        Company toBeDelivered = companyList.get(1);
        TaskProcess taskProcess = checkTaskService.deliverTaskToCompany(checkTask, toBeDelivered);

        // 若发放成功，则任务对应的公司与上列公司相等
        Assert.assertEquals(toBeDelivered, taskProcess.getCompany());
        Assert.assertEquals(checkTask, taskProcess.getCheckTask());

        // delete database record
        taskProcessRepository.delete(taskProcess.getId());
    }

    @Test
    public void testGetDeliveredCompanies() throws BaseException {
        // 获取所有已发放公司列表
        Company toBeDelivered = companyList.get(1);
        TaskProcess taskProcess = checkTaskService.deliverTaskToCompany(checkTask, toBeDelivered);

        // 已发放公司列表应该包含刚刚发放的公司
        Assert.assertTrue(checkTaskService.getDeliveredCompanies(checkTask).contains(toBeDelivered));

        // delete database record
        taskProcessRepository.delete(taskProcess.getId());
    }

    @Test
    public void testTaskAlreadyDeliveredToCompany() throws BaseException {
        // 测试任务是否已发放给某公司
        Company toBeDelivered = companyList.get(1);
        TaskProcess taskProcess = checkTaskService.deliverTaskToCompany(checkTask, toBeDelivered);

        // 成功发放后该方法返回True
        Assert.assertTrue(checkTaskService.taskAlreadyDeliveredToCompany(checkTask, toBeDelivered));

        // delete database record
        taskProcessRepository.delete(taskProcess.getId());
    }

    @Test
    public void testGetDeliverableCompanies() throws BaseException {
        // 测试可发放的公司
        List<Company> deliverableCompanies = checkTaskService.getDeliverableCompanies(checkTask);

        // 可发放公司列表不应包含刚刚发放的公司
        Company toBeDelivered = deliverableCompanies.get(1);
        TaskProcess taskProcess = checkTaskService.deliverTaskToCompany(checkTask, toBeDelivered);
        List<Company> newDeliverableCompanies = checkTaskService.getDeliverableCompanies(checkTask);

        // 刚刚发放一个公司后，可发放公司列表大小应该-1
        Assert.assertEquals(deliverableCompanies.size() - 1, newDeliverableCompanies.size());
        Assert.assertFalse(newDeliverableCompanies.contains(toBeDelivered));

        // delete database record
        taskProcessRepository.delete(taskProcess.getId());
    }

    @Test
    public void testGetCheckItemsInCheckTask() throws BaseException {
        // 获取任务中的检查列表，应该与@Before 中初始化的相同
        Set<CheckItem> checkItemsInCheckTask = checkTaskService.getCheckItemsInCheckTask(checkTask);
        Assert.assertArrayEquals(checkItemSet.toArray(), checkItemsInCheckTask.toArray());
    }

    @Test
    public void testGetTaskProcesses() throws BaseException {
        // 获取任务中发放至公司的检查过程列表
        Assert.assertEquals(0, checkTaskService.getTaskProcesses(checkTask).size());

        // 应该与插入的公司相对应
        Company toBeDelivered = companyList.get(1);
        TaskProcess taskProcess = checkTaskService.deliverTaskToCompany(checkTask, toBeDelivered);
        Assert.assertEquals(taskProcess, checkTaskService.getTaskProcess(checkTask, toBeDelivered));

        // delete database record
        taskProcessRepository.delete(taskProcess.getId());
    }

    @Test
    public void testGetCheckItemProcesses() throws BaseException {
        // 获取任务中每个检查项目的状态
        Company toBeDelivered = companyList.get(1);
        TaskProcess taskProcess = checkTaskService.deliverTaskToCompany(checkTask, toBeDelivered);
        Set<CheckItemProcess> checkItemProcesses = checkTaskService.getCheckItemProcesses(checkTask, toBeDelivered);
        checkItemProcesses.forEach(checkItemProcess -> {
            // 新建的任务中检查项目的状态应该都是"检查中"，并且项目与任务对应
            Assert.assertEquals(ItemState.Checking, checkItemProcess.getItemState());
            Assert.assertEquals(taskProcess, checkItemProcess.getTaskProcess());
        });

        // delete database record
        taskProcessRepository.delete(taskProcess.getId());
    }
}
