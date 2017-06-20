package com.fudan.ooad.service;

import com.fudan.ooad.entity.*;
import com.fudan.ooad.exception.BaseException;
import com.fudan.ooad.exception.DuplicatedPropertyException;
import com.fudan.ooad.exception.NullEntityException;
import com.fudan.ooad.exception.SystemException;
import com.fudan.ooad.repository.CheckTaskRepository;
import com.fudan.ooad.repository.CompanyRepository;
import com.fudan.ooad.repository.TaskProcessRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by zihao on 2017/6/20.
 */
@Service
public class CheckTaskService {
    @Autowired
    private CheckTaskRepository checkTaskRepository;
    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private TaskProcessRepository taskProcessRepository;

    private final String SERVICE_NAME = "CheckTaskService";

    public Set<Company> getDeliveredCompanies(CheckTask checkTask) throws BaseException {
        if (checkTask == null || checkTask.getId() == null || !checkTaskRepository.exists(checkTask.getId())) {
            throw new NullEntityException(SERVICE_NAME, "CheckTask does not exist in database");
        }
        return checkTask.getTaskProcesses().stream().map(TaskProcess::getCompany).collect(Collectors.toSet());
    }

    public boolean taskAlreadyDeliveredToCompany(CheckTask checkTask, Company company) throws BaseException {
        return getDeliveredCompanies(checkTask).contains(company);
    }

    public TaskProcess deliverTaskToCompany(CheckTask checkTask, Company company) throws BaseException {
        if (checkTask == null || checkTask.getId() == null || !checkTaskRepository.exists(checkTask.getId())) {
            throw new NullEntityException(
                    SERVICE_NAME,
                    "Cannot deliver a checkTask that does not exist in database."
            );
        }
        if (company == null || company.getId() == null || !companyRepository.exists(company.getId())) {
            throw new NullEntityException(
                    SERVICE_NAME,
                    "Cannot deliver a checkTask to a company that does not exist in database"
            );
        }
        if (taskAlreadyDeliveredToCompany(checkTask, company)) {
            throw new DuplicatedPropertyException(
                    SERVICE_NAME,
                    "CheckTask already delivered to this company"
            );
        }

        TaskProcess taskProcess = new TaskProcess();
        taskProcess.setCheckTask(checkTask);
        taskProcess.setCompany(company);
        taskProcess.setTaskProcessState(TaskProcessState.Checking);
        try {
            taskProcessRepository.save(taskProcess);
        } catch (Exception e) {
            throw new SystemException(SERVICE_NAME, e.getMessage());
        }
        return taskProcess;
    }

    public List<Company> getAllCompanyList() {
        return companyRepository.findAll();
    }

    public List<Company> getDeliverableCompanies(CheckTask checkTask) throws BaseException {
        List<Company> result = getAllCompanyList();
        result.removeAll(getDeliveredCompanies(checkTask));
        return result;
    }

    public Set<CheckItem> getCheckItemsInCheckTask(CheckTask checkTask) throws BaseException {
        if (checkTask.getId() == null || !checkTaskRepository.exists(checkTask.getId())) {
            return checkTask.getCheckItems();
        }
        throw new NullEntityException(SERVICE_NAME, "CheckTask does not exist in database");
    }

    public Set<TaskProcess> getTaskProcesses(CheckTask checkTask) throws BaseException {
        if (checkTask.getId() == null || !checkTaskRepository.exists(checkTask.getId())) {
            return checkTask.getTaskProcesses();
        }
        throw new NullEntityException(SERVICE_NAME, "CheckTask does not exist in database");
    }

    public TaskProcess getTaskProcess(CheckTask checkTask, Company company) throws BaseException {
        if (checkTask.getId() == null || !checkTaskRepository.exists(checkTask.getId())) {
            throw new NullEntityException(
                    SERVICE_NAME,
                    "CheckTask does not exist in database"
            );
        }
        if (company.getId() == null || !companyRepository.exists(company.getId())) {
            throw new NullEntityException(
                    SERVICE_NAME,
                    "Company does not exist in database"
            );
        }
        return checkTask.getTaskProcesses().stream()
                .filter(taskProcess -> taskProcess.getCompany().equals(company))
                .findFirst().orElse(null);
    }

    public Set<CheckItemProcess> checkItemProcesses(CheckTask checkTask, Company company) throws BaseException {
        TaskProcess taskProcess = getTaskProcess(checkTask, company);
        return taskProcess != null ? taskProcess.getCheckItemProcessSet() : new HashSet<>(0);
    }
}
