package com.fudan.ooad.service;

import com.fudan.ooad.entity.CheckTask;
import com.fudan.ooad.entity.Company;
import com.fudan.ooad.entity.TaskProcess;
import com.fudan.ooad.entity.Template;

import java.util.Set;

/**
 * Created by Jindiwei on 2017/6/19.
 */
public interface ICheckTaskService {

    /**
     *
     * @param checkTask 检查事务
     * @return 下发的企业列表
     */
    Set<Company> getCompanies(CheckTask checkTask);

    /**
     *
     * @param checkTask 检查事务
     * @return 检查事务对应使用的模板
     */
    Template getCheckItemsInCheckTask(CheckTask checkTask);

    /**
     *
     * @param checkTask 检查事务
     * @return 返回该检查事务对应公司的状态
     */
    Set<TaskProcess> getTaskProcess(CheckTask checkTask);

}
