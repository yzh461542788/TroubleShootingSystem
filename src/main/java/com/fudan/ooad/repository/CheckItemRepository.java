package com.fudan.ooad.repository;

import com.fudan.ooad.entity.CheckItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

/**
 * Created by zihao on 2017/6/17.
 */
public interface CheckItemRepository extends JpaRepository<CheckItem, Integer> {
    CheckItem findByTitle(String title);
    CheckItem findByContent(String content);
    Set<CheckItem> findByTitleContainingOrContentContaining(String keyword, String keyword_copy);
}