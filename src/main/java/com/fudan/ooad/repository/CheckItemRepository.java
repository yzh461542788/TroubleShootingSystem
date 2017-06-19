package com.fudan.ooad.repository;

import com.fudan.ooad.entity.CheckItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

/**
 * Created by zihao on 2017/6/17.
 */
public interface CheckItemRepository extends JpaRepository<CheckItem, Integer> {
    @Query("select checkItems from CheckItem where CheckItem.title like ?1 or CheckItem.content like ?1")
    Set<CheckItem> searchCheckItems(String keyword);
}
