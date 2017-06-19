package com.fudan.ooad.service;

import com.fudan.ooad.repository.CheckItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by zihao on 2017/6/17.
 */
@Service
public class CheckItemService {
    @Autowired
    CheckItemRepository checkItemRepository;


}
