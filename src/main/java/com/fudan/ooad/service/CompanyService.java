package com.fudan.ooad.service;

import com.fudan.ooad.entity.CheckItemProcess;
import com.fudan.ooad.entity.CheckTask;
import com.fudan.ooad.entity.Company;
import com.fudan.ooad.entity.TaskProcess;

import java.util.Set;

/**
 * Created by Jindiwei on 2017/6/19.
 */
public class CompanyService implements ICompanyService {

    @Override
    public void setCheckItemState(Company company, CheckItemProcess checkItemProcess) {

    }

    @Override
    public Set<TaskProcess> getTaskProcesses(Company company) {
        return null;
    }

    @Override
    public TaskProcess getCheckItemsInTask(Company company, CheckTask checkTask) {
        return null;
    }
}
