package com.fudan.ooad.repository;

import com.fudan.ooad.entity.CheckItem;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by zihao on 2017/6/17.
 */
public interface CheckItemRepository extends JpaRepository<CheckItem, Integer> {
    CheckItem findByTitle(String title);
}
