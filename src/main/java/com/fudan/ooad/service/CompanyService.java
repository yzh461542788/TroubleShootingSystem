package com.fudan.ooad.service;

import com.fudan.ooad.entity.*;
import com.fudan.ooad.exception.BaseException;
import com.fudan.ooad.exception.InvalidOperationException;
import com.fudan.ooad.exception.NullEntityException;
import com.fudan.ooad.repository.*;
import com.fudan.ooad.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Set;

/**
 * Created by Jindiwei on 2017/6/19.
 */
@Service
public class CompanyService{

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private CheckItemRepository checkItemRepository;

    @Autowired
    private TaskProcessRepository taskProcessRepository;

    @Autowired
    private CheckItemProcessRepository checkItemProcessRepository;


    private final String SERVICE_NAME = "CompanyService";

    
    public Set<TaskProcess> getAllTaskProcesses(Company company) throws BaseException{
        /*
        company是否存在

         */
        if (company.getId() == null) {
            throw new NullEntityException(
                    SERVICE_NAME,
                    "The company is a new one, has not being saved to the database."
            );
        }

        if (!companyRepository.exists(company.getId())) {
            throw new NullEntityException(
                    SERVICE_NAME,
                    "checkItem does not exist in database."
            );
        }

        return company.getTaskProcesses();
    }

    public Set<CheckItemProcess> getAllCheckItemsProcessInTaskProcess(Company company, TaskProcess taskProcess) throws BaseException{
        if (company.getId() == null) {
            throw new NullEntityException(
                    SERVICE_NAME,
                    "The company is a new one, has not being saved to the database."
            );
        }

        if (!companyRepository.exists(company.getId())) {
            throw new NullEntityException(
                    SERVICE_NAME,
                    "checkItem does not exist in database."
            );
        }

        if (taskProcess.getId() == null) {
            throw new NullEntityException(
                    SERVICE_NAME,
                    "The taskProcess is a new one, has not being saved to the database."
            );
        }

        if (!taskProcessRepository.exists(taskProcess.getId())) {
            throw new NullEntityException(
                    SERVICE_NAME,
                    "taskProcess does not exist in database."
            );
        }

        if(taskProcess.getCompany().getId() != company.getId()){
            throw new InvalidOperationException(
                    SERVICE_NAME,
                    "The company is not in the taskProcess."
            );
        }

        return checkItemProcessRepository.findByTaskProcess(taskProcess);



    }
    
    public void setCheckItemState(TaskProcess taskProcess, CheckItem checkItem, ItemState itemState) throws BaseException{
        if (taskProcess.getId() == null) {
            throw new NullEntityException(
                    SERVICE_NAME,
                    "The taskProcess is a new one, has not being saved to the database."
            );
        }

        if(!taskProcessRepository.exists(taskProcess.getId())){
            throw new NullEntityException(
                    SERVICE_NAME,
                    "The taskProcess does not exist in the system."
            );
        }

        if (checkItem.getId() == null) {
            throw new NullEntityException(
                    SERVICE_NAME,
                    "checkItem is a new checkItem, you can use CheckItemService.createCheckItem() to add this item."
            );
        }

        if (!checkItemRepository.exists(checkItem.getId())) {
            throw new NullEntityException(
                    SERVICE_NAME,
                    "The checkItem does not exist in database."
            );
        }
        Date cur = DateUtil.getCurrentDate();
        if(cur.after(taskProcess.getCheckTask().getDeadline())){
            throw new InvalidOperationException(
                    SERVICE_NAME,
                    "Current data is after deadline, can not set the state."
            );
        }
        //设置结束时间
        taskProcess.setFinishTime(cur);

        if(itemState.toString().equals("已检查")){
            CheckItemProcess checkItemProcess = checkItemProcessRepository.findByTaskProcessAndCheckItem(taskProcess, checkItem);
            checkItemProcess.setItemState(itemState);
        }
        else{
            throw new InvalidOperationException(
                    SERVICE_NAME,
                    "You can only set the state to finished."
            );
        }

    }

}
