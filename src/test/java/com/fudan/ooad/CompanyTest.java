package com.fudan.ooad;

/**
 * Created by Jindiwei on 2017/6/17.
 */

import com.fudan.ooad.entity.CheckItemProcess;
import com.fudan.ooad.entity.Company;
import com.fudan.ooad.service.CompanyService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest

public class CompanyTest {

    CompanyService companyService;
    @Test
    public void setCheckItemStateTest(Company company, CheckItemProcess checkItemProcess) {

    }

    @Test
    public void getCheckItemsInTaskTest(){ //获取某个检查事务的检查项目列表

    }

    @Test
    public void setCheckItemStateTest(){

    }
}
