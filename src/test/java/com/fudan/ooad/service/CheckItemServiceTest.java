package com.fudan.ooad.service;

import com.fudan.ooad.entity.CheckItem;
import com.fudan.ooad.exception.BaseException;
import com.fudan.ooad.repository.CheckItemRepository;
import com.fudan.ooad.util.DateUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Set;

/**
 * Created by Jindiwei on 2017/6/20.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CheckItemServiceTest {

    @Autowired
    CheckItemService checkItemService;

    @Autowired
    CheckItemRepository checkItemRepository;

    /**
     * 测试添加和删除检查项目
     */
    @Test
    public void createAndDeleteCheckItemTest(){
        String cur = DateUtil.getCurrentDate().toString();
        CheckItem checkItem = null;
        try {
            //测试添加检查项目的用例为：title:cur content:cur + 1
            checkItem = checkItemService.createCheckItem(cur, cur + 1);
            Assert.assertEquals(cur, checkItem.getTitle());
            Assert.assertEquals(cur + 1, checkItem.getContent());
        } catch (BaseException e) {
            e.printStackTrace();
        }

        try {
            //测试删除检查项目的用例为：上文添加的检查项目checkItem.
            checkItemService.deleteCheckItem(checkItem);
        } catch (BaseException e) {
            e.printStackTrace();
        }

    }

    /**
     * 测试修改检查项目
     */
    @Test
    public void modifyCheckItemTest() {
        String cur = DateUtil.getCurrentDate().toString();
        CheckItem checkItem = null;
        CheckItem checkItem1 = null;

        try {

            //往数据库中添加检查项目
            checkItem = checkItemService.createCheckItem(cur, cur + 1);
            Assert.assertEquals(cur, checkItem.getTitle());
            Assert.assertEquals(cur + 1, checkItem.getContent());
        } catch (BaseException e) {
            e.printStackTrace();
        }

        try {

            //测试修改检查项目的用例为：CheckItem：checkItem title:cur + 2 content:cur + 3
            checkItem1 = checkItemService.modifyCheckItem(checkItem, cur + 2, cur +3);
            Assert.assertEquals(cur + 2, checkItem1.getTitle());
            Assert.assertEquals(cur + 3, checkItem1.getContent());
        } catch (BaseException e) {
            e.printStackTrace();
        }

        try {
            //删除数据库中的检查项目数据
            checkItemService.deleteCheckItem(checkItem1);
        } catch (BaseException e) {
            e.printStackTrace();
        }
    }

    /**
     * 测试搜索已有的检查项目
     */
    @Test
    public void searchCheckItem() {
        String test = "abc";
        CheckItem checkItem1 = null;
        CheckItem checkItem2 = null;
        try {
            //往数据库中添加两个检查项目条目
            checkItem1 = checkItemService.createCheckItem(test, test + 1);
            checkItem2 = checkItemService.createCheckItem(test + 2, test + 3);
            Assert.assertEquals(test , checkItem1.getTitle());
            Assert.assertEquals(test + 2, checkItem2.getTitle());
        } catch (BaseException e) {
            e.printStackTrace();
        }


        try {
            //测试搜索检查项目的用例为 keyword = "abc"
            Set<CheckItem> checkItemSet = checkItemService.searchCheckItem("abc");
            Assert.assertEquals(2, checkItemSet.size());
        } catch (BaseException e) {
            e.printStackTrace();
        }


        try {
            //删除数据库中的检查项目
            checkItemService.deleteCheckItem(checkItem1);
            checkItemService.deleteCheckItem(checkItem2);
        } catch (BaseException e) {
            e.printStackTrace();
        }
    }

}