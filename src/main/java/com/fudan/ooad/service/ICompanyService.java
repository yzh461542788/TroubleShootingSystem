package com.fudan.ooad.service;

import com.fudan.ooad.entity.CheckItemProcess;
import com.fudan.ooad.entity.CheckTask;
import com.fudan.ooad.entity.Company;
import com.fudan.ooad.entity.TaskProcess;

import java.util.Set;

/**
 * Created by Jindiwei on 2017/6/19.
 */
public interface ICompanyService {

    //TODO
    //需要确认状态是整个检查事务的状态还是每个检查项目的状态，如果是检查项目的状态的话，那么检查事务的状态就不能手动修改，而是根据检查项目的完成情况自动设置
    void setCheckItemState(Company company, CheckItemProcess checkItemProcess);

    /**
     *
     * @param company 公司对象
     * @return 该公司需要检查的事务列表
     */
    Set<TaskProcess> getTaskProcesses(Company company);//获取每个公司对应的检查事务的List

    /**
     *
     * @param company 公司对象
     * @param checkTask 检查事务
     * @return 某项事务内相关项目的检查情况
     */
    TaskProcess getCheckItemsInTask(Company company, CheckTask checkTask); //获取某个检查事务的检查项目列表

}
