package com.fudan.ooad.service;

import com.fudan.ooad.entity.CheckItem;
import com.fudan.ooad.entity.CheckTask;
import com.fudan.ooad.entity.Template;
import com.fudan.ooad.exception.BaseException;
import com.fudan.ooad.repository.CheckItemRepository;
import com.fudan.ooad.repository.CheckTaskRepository;
import com.fudan.ooad.repository.TemplateRepository;
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
import java.util.HashSet;
import java.util.Set;

/**
 * Created by zihao on 2017/6/20.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TemplateServiceTest {
    @Autowired
    private TemplateService templateService;
    @Autowired
    private CheckItemService checkItemService;

    // repositories are used to delete records in database after tests
    @Autowired
    private CheckTaskRepository checkTaskRepository;
    @Autowired
    private TemplateRepository templateRepository;
    @Autowired
    private CheckItemRepository checkItemRepository;

    private String cur;
    private Date curDate;
    private Set<CheckItem> checkItems;
    private CheckItem checkItem;

    @Before
    public void init() {
        curDate = DateUtil.getCurrentDate();
        cur = curDate.toString();
        checkItems = new HashSet<>();

        for (int i = 0; i < 10; i++) {
            try {
                CheckItem c = checkItemService.createCheckItem(cur + i, cur + i);
                checkItems.add(c);
            } catch (BaseException e) {
                e.printStackTrace();
            }
        }
        try {
            checkItem = checkItemService.createCheckItem(cur, cur);
        } catch (BaseException e) {
            e.printStackTrace();
        }
    }
    @After
    public void clean() {
        checkItems.forEach(checkItem -> checkItemRepository.delete(checkItem.getId()));
        checkItemRepository.delete(this.checkItem.getId());
    }


    @Test
    public void testCreateAndDeleteTemplate() {
        // 测试模版的增加和删除
        try {
            int templateNum = templateService.getTemplateList().size();

            // 新建一个模版后，模版非空，属性值与设置相同
            Template template = templateService.createTemplate(cur, cur, checkItems);
            Assert.assertNotNull(template);
            Assert.assertEquals(cur, template.getTitle());
            Assert.assertEquals(cur, template.getDescription());
            Assert.assertEquals(checkItems.size(), template.getCheckItems().size());
            Assert.assertEquals(templateNum + 1, templateService.getTemplateList().size());

            // 删除后再次搜索模版，模版为空
            templateService.deleteTemplate(template);
            Assert.assertNull(templateService.findTemplateByName(cur));
        } catch (BaseException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testEditTemplate() {
        // 测试模版的修改
        try {
            // 修改模版属性后，与预期相同
            Template template = templateService.createTemplate(cur, cur, checkItems);
            Assert.assertEquals(cur, template.getTitle());
            Assert.assertEquals(cur, template.getDescription());

            templateService.editTemplateMessage(template, cur+1, cur+1);
            Assert.assertEquals(cur+1, template.getTitle());
            Assert.assertEquals(cur+1, template.getDescription());

            templateService.deleteTemplate(template);
            Assert.assertNull(templateService.findTemplateByName(cur));
        } catch (BaseException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testAddCheckItem() {
        try {
            // 向模版添加检查项目，添加后模版的项目列表包含该项目
            Template template = templateService.createTemplate(cur, cur, checkItems);
            Assert.assertNotNull(template);
            Assert.assertEquals(checkItems.size(), template.getCheckItems().size());

            templateService.addCheckItem(template, checkItem);
            Assert.assertEquals(checkItems.size() + 1, template.getCheckItems().size());
            Assert.assertTrue(template.getCheckItems().contains(checkItem));

            templateService.deleteTemplate(template);
            Assert.assertNull(templateService.findTemplateByName(cur));
        } catch (BaseException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testDeleteCheckItem() {
        try {
            // 删除模版中的项目，删除后模版的项目列表不再包含该项目
            Template template = templateService.createTemplate(cur, cur, checkItems);
            Assert.assertEquals(checkItems.size(), template.getCheckItems().size());

            CheckItem toBeDeleted = checkItems.iterator().next();
            templateService.deleteCheckItem(template, toBeDeleted);
            Assert.assertEquals(checkItems.size() - 1, template.getCheckItems().size());
            Assert.assertFalse(template.getCheckItems().contains(toBeDeleted));

            templateService.deleteTemplate(template);
        } catch (BaseException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testPostToCheckTask() {
        try {
            // 将模版发放为任务
            Template template = templateService.createTemplate(cur, cur, checkItems);
            String checkTaskTitle = cur;
            Date deadline = Date.from(curDate.toInstant().plusSeconds(3600 * 24 * 10));

            CheckTask checkTask = templateService.postToCheckTask(template, checkTaskTitle, deadline);

            // 任务发放成功后非空，且属性与预期相同，检查项目列表与模版中的相同
            Assert.assertNotNull(checkTask);
            Assert.assertEquals(checkTaskTitle, checkTask.getTitle());
            Assert.assertEquals(deadline, checkTask.getDeadline());
            Assert.assertArrayEquals(checkItems.toArray(), checkTask.getCheckItems().toArray());

            // delete record in database after this test
            checkTaskRepository.delete(checkTask.getId());
            templateRepository.delete(template.getId());
        } catch (BaseException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testFindTemplateByTitle() {
        // 根据模版名称搜索模版
        try {
            // 添加模版后搜索，二者应该相等
            Template template = templateService.createTemplate(cur, cur, checkItems);
            Template found = templateService.findTemplateByName(cur);
            Assert.assertEquals(template, found);

            templateService.deleteTemplate(template);
            found = templateService.findTemplateByName(cur);
            Assert.assertNull(found);
        } catch (BaseException e) {
            e.printStackTrace();
        }
    }


}
