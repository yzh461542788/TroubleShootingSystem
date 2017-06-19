package com.fudan.ooad.service;

import com.fudan.ooad.entity.CheckItem;
import com.fudan.ooad.repository.CheckItemRepository;

import java.util.Set;

/**
 * Created by Jindiwei on 2017/6/19.
 */
public class CheckItemService implements ICheckItemService {
    CheckItemRepository checkItemRepository;
    @Override
    public void addCheckItem(CheckItem checkItem) {
        checkItemRepository.save(checkItem);
    }

    @Override
    public void modifyCheckItem(CheckItem checkItem) {

    }

    @Override
    public void deleteCheckItem(CheckItem checkItem) {
        checkItemRepository.delete(checkItem);
    }

    @Override
    public Set<CheckItem> searchCheckItem(String keyword) {

        return null;
    }
}
