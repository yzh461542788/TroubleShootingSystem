package com.fudan.ooad.repository;

import com.fudan.ooad.entity.CheckItem;
import com.fudan.ooad.entity.CheckItemProcess;
import com.fudan.ooad.entity.TaskProcess;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

/**
 * Created by zihao on 2017/6/20.
 */
public interface CheckItemProcessRepository extends JpaRepository<CheckItemProcess, Integer> {

    Set<CheckItemProcess> findByTaskProcess(TaskProcess taskProcess);

    CheckItemProcess findByTaskProcessAndCheckItem(TaskProcess taskProcess, CheckItem checkItem);

}
