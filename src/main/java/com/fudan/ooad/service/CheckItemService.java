package com.fudan.ooad.service;

import com.fudan.ooad.entity.CheckItem;
import com.fudan.ooad.exception.*;
import com.fudan.ooad.repository.CheckItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * Created by Jindiwei on 2017/6/20.
 */
@Service
public class CheckItemService{

    @Autowired
    private CheckItemRepository checkItemRepository;

    private final String SERVICE_NAME = "CheckItemService";


    public CheckItem createCheckItem(String title, String content) throws BaseException {
        //添加： 属性：title content
        /*
        检查title和content是否存在
         */
        if (checkItemRepository.findByTitle(title) != null) {
            throw new DuplicatedPropertyException(
                    SERVICE_NAME,
                    "CheckItem with same tile is exist in the database."
            );
        }
//        if (checkItemRepository.findByContent(content) != null) {
//            throw new DuplicatedPropertyException(
//                    SERVICE_NAME,
//                    "CheckItem with same content is exist in the database."
//            );
//        }
        CheckItem checkItem = new CheckItem();
        checkItem.setTitle(title);
        checkItem.setContent(content);
        try {
            checkItemRepository.save(checkItem);
            return checkItem;
        } catch (Exception e) {
            throw new SystemException(SERVICE_NAME, e.getMessage());
        }

    }


    public void modifyCheckItem(CheckItem checkItem, String title, String content) throws BaseException {
        if (checkItem.getId() == null) {
            throw new NullEntityException(
                    SERVICE_NAME,
                    "checkItem is a new checkItem, you can use CheckItemService.createCheckItem() to add this item."
            );
        }

        if (!checkItemRepository.exists(checkItem.getId())) {
            throw new NullEntityException(
                    SERVICE_NAME,
                    "checkItem does not exist in database, Try to use checkItemService.createCheckItem instead"
            );
        }

        //判断是否有关联的已发布的事务
        int size = checkItem.getCheckTasks().size();
        if (size == 0) {
            throw new InvalidOperationException(
                    SERVICE_NAME,
                    "CheckItem has already binded with a least one CheckTask."
            );
        }

        if (title == "" || content == "") {
            throw new InvalidPropertyException(
                    SERVICE_NAME,
                    "title or content can not be null."
            );
        }
        //title need to modify
        if (!checkItem.getTitle().equals(title)) {
            if (checkItemRepository.findByTitle(title) == null) {
                checkItem.setTitle(title);
            } else {
                throw new DuplicatedPropertyException(
                        SERVICE_NAME,
                        "There already exist a checkItem with the same title."
                );
            }
        }

        //content need to modify
//        if (!checkItem.getContent().equals(content)) {
//            if (checkItemRepository.findByContent(content) == null) {
//                checkItem.setContent(content);
//            } else {
//                throw new DuplicatedPropertyException(
//                        SERVICE_NAME,
//                        "There already exist a checkItem with the same content."
//                );
//            }
//        }

        try {
            //保存
            checkItemRepository.save(checkItem);
        } catch (Exception e) {
            throw new SystemException(SERVICE_NAME, e.getMessage());
        }
    }


    public void deleteCheckItem(CheckItem checkItem) throws BaseException {
        if (checkItem.getId() == null) {
            throw new NullEntityException(
                    SERVICE_NAME,
                    "checkItem is a new checkItem, you can use CheckItemService.createCheckItem() to add this item."
            );
        }

        if (!checkItemRepository.exists(checkItem.getId())) {
            throw new NullEntityException(
                    SERVICE_NAME,
                    "checkItem does not exist in database, Try to use checkItemService.createCheckItem instead"
            );
        }

        //判断是否有关联的已发布的事务
        int size = checkItem.getCheckTasks().size();
        if (size == 0) {
            throw new InvalidOperationException(
                    SERVICE_NAME,
                    "CheckItem has already binded with a least one CheckTask."
            );
        }

        try {
            //删除操作
            checkItemRepository.delete(checkItem.getId());
        } catch (Exception e) {
            throw new SystemException(SERVICE_NAME, e.getMessage());
        }
    }


    public Set<CheckItem> searchCheckItem(String keyword) throws BaseException{
        Set<CheckItem> checkItems;
        try {
//            checkItems = checkItemRepository.findByTitleContainsOrContentContains(keyword);
        }catch(Exception e){
            throw new SystemException(SERVICE_NAME, e.getMessage());
        }
//        return checkItems;
        return null;
    }

}
