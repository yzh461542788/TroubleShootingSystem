package com.fudan.ooad.service;

import com.fudan.ooad.entity.CheckTask;
import com.fudan.ooad.entity.Company;
import com.fudan.ooad.entity.TaskProcess;
import com.fudan.ooad.entity.Template;

import java.util.Set;

/**
 * Created by Jindiwei on 2017/6/19.
 */
public class CheckTaskService implements ICheckTaskService {
    @Override
    public Set<Company> getCompanies(CheckTask checkTask) {
        return null;
    }

    @Override
    public Template getCheckItemsInCheckTask(CheckTask checkTask) {
        return null;
    }

    @Override
    public Set<TaskProcess> getTaskProcess(CheckTask checkTask) {
        return null;
    }
}
