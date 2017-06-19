package com.fudan.ooad.repository;

import com.fudan.ooad.entity.CheckTask;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by zihao on 2017/6/18.
 */
public interface CheckTaskRepository extends JpaRepository<CheckTask, Integer> {
    CheckTask findByTitle(String title);
}
