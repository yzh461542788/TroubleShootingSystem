package com.fudan.ooad.repository;

import com.fudan.ooad.entity.*;
import com.fudan.ooad.util.DateUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by zihao on 2017/6/18.
 * <p>
 * CAUTION: If entity should be created and persisted in database, delete it after
 * testing to keep database clean
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RepositoryTest {
    private static final Logger logger = LoggerFactory.getLogger(RepositoryTest.class);
    @Autowired
    private TaskProcessRepository taskProcessRepository;
    @Autowired
    private CheckTaskRepository checkTaskRepository;
    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private TemplateRepository templateRepository;
    @Autowired
    private CheckItemRepository checkItemRepository;

    @Test
    public void testCheckItemCRUD() {
        String cur = DateUtil.getCurrentDate().toString();
        String title = "title_" + cur;
        String content = "content_" + cur;
        String newContent = "new";

        CheckItem checkItem = new CheckItem();
        checkItem.setTitle(title);
        checkItem.setContent(content);
        checkItemRepository.save(checkItem);

        CheckItem found = checkItemRepository.findByTitle(title);
        Assert.assertEquals(checkItem, found);

        checkItem.setContent(newContent);
        checkItemRepository.save(checkItem);
        found = checkItemRepository.findByTitle(title);
        Assert.assertEquals(newContent, found.getContent());

        checkItemRepository.delete(checkItem);
        found = checkItemRepository.findByTitle(title);
        Assert.assertNull(found);
    }

    @Test
    public void testTemplateCRUD() {
        String cur = DateUtil.getCurrentDate().toString();
        String title = "title_" + cur;
        String description = "description_" + cur;
        String newDescription = "new";

        Template template = new Template();
        template.setTitle(title);
        template.setDescription(description);
        templateRepository.save(template);

        Template found = templateRepository.findByTitle(title);
        Assert.assertEquals(template, found);

        template.setDescription(newDescription);
        templateRepository.save(template);
        found = templateRepository.findByTitle(title);
        Assert.assertEquals(newDescription, found.getDescription());

        templateRepository.delete(template);
        found = templateRepository.findByTitle(title);
        Assert.assertNull(found);
    }

    @Test
    public void testTemplateCheckItemAssociation() {
        String cur = DateUtil.getCurrentDate().toString();
        String title = "title_" + cur;
        String content = "content_" + cur;
        String description = "description_" + cur;

        CheckItem checkItem = new CheckItem();
        checkItem.setTitle(title);
        checkItem.setContent(content);

        Template template = new Template();
        template.setTitle(title);
        template.setDescription(description);

        template.addCheckItem(checkItem);
        Assert.assertTrue(template.getCheckItems().contains(checkItem));
        Assert.assertTrue(checkItem.getTemplates().contains(template));

        // add checkItem from template
        template.addCheckItem(checkItem);
        Assert.assertTrue(template.getCheckItems().contains(checkItem));
        Assert.assertTrue(checkItem.getTemplates().contains(template));

        templateRepository.save(template);

        // find checkItem
        CheckItem foundCheckItem = checkItemRepository.findByTitle(title);
        Assert.assertEquals(checkItem, foundCheckItem);
        Assert.assertTrue(template.getCheckItems().contains(foundCheckItem));
        Assert.assertTrue(foundCheckItem.getTemplates().contains(template));

        // delete association
        template.removeCheckItem(checkItem);
        templateRepository.save(template);
        Assert.assertFalse(checkItemRepository.findByTitle(title).getTemplates().contains(template));

        // add association and delete template
        template.addCheckItem(checkItem);
        templateRepository.save(template);
        templateRepository.delete(template);

        foundCheckItem = checkItemRepository.findByTitle(title);
        Assert.assertNotNull(foundCheckItem);
        Assert.assertFalse(foundCheckItem.getTemplates().contains(template));

        // delete check item
        checkItemRepository.delete(foundCheckItem);
        Assert.assertNull(checkItemRepository.findByTitle(title));
    }

    @Test
    public void testCompanyCRUD() {
        String s = DateUtil.getCurrentDate().toString();
        Company company = new Company();
        company.setName(s);
        company.setOrganizationCode(s);
        company.setCompanyCode(s);
        companyRepository.save(company);

        Company found = companyRepository.findByCompanyCode(s);
        Assert.assertEquals(company, found);

        company.setContact(s);
        companyRepository.save(company);
        found = companyRepository.findByCompanyCode(s);
        Assert.assertEquals(s, found.getContact());

        companyRepository.delete(company);
        found = companyRepository.findByCompanyCode(s);
        Assert.assertNull(found);


    }

    @Test
    public void testCheckTaskCRUD() {
        String s = DateUtil.getCurrentDate().toString();

        CheckTask checkTask = new CheckTask();
        checkTask.setTitle(s);
        checkTaskRepository.save(checkTask);
        Assert.assertNotNull(checkTaskRepository.findByTitle(s));

        checkTaskRepository.delete(checkTask);
        Assert.assertNull(checkTaskRepository.findByTitle(s));
    }

    @Test
    public void testCheckTaskTemplateAssociation() {
        String s = DateUtil.getCurrentDate().toString();

        Template template = new Template();
        template.setTitle(s);
        template.setDescription(s);

        CheckTask checkTask = new CheckTask();
        checkTask.setTitle(s);
        checkTask.setTemplate(template);

        // add checkTask that contains a template
        checkTaskRepository.save(checkTask);

        // template should be added to database
        CheckTask foundCheckTask = checkTaskRepository.findByTitle(s);
        Template foundTemplate = templateRepository.findByTitle(s);

        Assert.assertNotNull(foundCheckTask);
        Assert.assertNotNull(foundTemplate);

        Assert.assertEquals(template, foundCheckTask.getTemplate());
        Assert.assertTrue(foundTemplate.getCheckTasks().contains(checkTask));

        // delete checkTask should not delete template,
        // but the association should be removed
        checkTaskRepository.delete(checkTask);
        foundTemplate = templateRepository.findByTitle(s);
        Assert.assertNotNull(foundTemplate);
        Assert.assertFalse(foundTemplate.getCheckTasks().contains(checkTask));

        // delete template
        templateRepository.delete(template);
        Assert.assertNull(templateRepository.findByTitle(s));
    }

    @Test
    public void testTaskProcessCRUD() {
        String s = DateUtil.getCurrentDate().toString();

        Company company1 = new Company();
        company1.setName(s + 1);
        Company company2 = new Company();
        company2.setName(s + 2);

        companyRepository.save(company1);

        CheckTask checkTask = new CheckTask();
        checkTask.setTitle(s);

        checkTaskRepository.save(checkTask);

        TaskProcess taskProcess1 = new TaskProcess();
        taskProcess1.setCheckTask(checkTask);
        taskProcess1.setCompany(company1);

        TaskProcess taskProcess2 = new TaskProcess();
        taskProcess2.setCheckTask(checkTask);
        taskProcess2.setCompany(company2);

        taskProcessRepository.save(taskProcess1);

        checkTask.removeTaskProcess(taskProcess1);
        checkTaskRepository.save(checkTask);


        // add task process from check task
        checkTask.addTaskProcess(taskProcess2);
        checkTaskRepository.save(checkTask);
//        Assert.assertTrue(taskProcessRepository.findAll().contains(taskProcess1));
//
//        CheckTask found = checkTaskRepository.findByTitle(s);
        Assert.assertEquals(2, checkTaskRepository.findByTitle(s).getTaskProcesses().size());

        checkTask.removeTaskProcess(taskProcess2);
        checkTaskRepository.save(checkTask);
        Assert.assertEquals(0, checkTask.getTaskProcesses().size());
        Assert.assertEquals(0, checkTaskRepository.findOne(checkTask.getId()).getTaskProcesses().size());

    }
}
