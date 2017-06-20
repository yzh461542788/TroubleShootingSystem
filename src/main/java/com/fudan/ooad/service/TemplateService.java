package com.fudan.ooad.service;

import com.fudan.ooad.entity.CheckItem;
import com.fudan.ooad.entity.CheckTask;
import com.fudan.ooad.entity.Template;
import com.fudan.ooad.exception.*;
import com.fudan.ooad.repository.CheckItemRepository;
import com.fudan.ooad.repository.CheckTaskRepository;
import com.fudan.ooad.repository.TemplateRepository;
import com.fudan.ooad.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Created by zihao on 2017/6/20.
 */
@Service
public class TemplateService {
    @Autowired
    private TemplateRepository templateRepository;
    @Autowired
    private CheckItemRepository checkItemRepository;
    @Autowired
    private CheckTaskRepository checkTaskRepository;

    private final String SERVICE_NAME = "TemplateService";

    public CheckTask postToCheckTask(Template template, String checkTaskTitle, Date deadline) throws BaseException {
        if (!templateRepository.exists(template.getId())) {
            throw new NullEntityException(
                    SERVICE_NAME,
                    "Cannot post a template that does not exist in database to a checkTask."
            );
        }
        CheckTask checkTask = new CheckTask();
        checkTask.addCheckItems(template.getCheckItems());
        checkTask.setTitle(checkTaskTitle);
        if (checkTaskRepository.findByTitle(checkTaskTitle) != null) {
            throw new InvalidPropertyException(
                    SERVICE_NAME,
                    "Unique property CheckTask.checkTaskTitle already exist."
            );
        }
        Date cur = DateUtil.getCurrentDate();
        checkTask.setPostDate(cur);
        if (deadline.before(cur)) {
            throw new InvalidPropertyException(
                    SERVICE_NAME,
                    "Deadline is earlier than current date."
            );
        }
        checkTask.setDeadline(deadline);
        try {
            checkTaskRepository.save(checkTask);
        } catch (Exception e) {
            throw new SystemException(
                    SERVICE_NAME,
                    e.getMessage()
            );
        }
        return checkTask;
    }

    public Template createTemplate(String title, String description, Set<CheckItem> checkItems) throws BaseException {
        if (templateRepository.findByTitle(title) != null) {
            throw new InvalidPropertyException(
                    SERVICE_NAME,
                    "Unique property Template.title already exist."
            );
        }
        Template template = new Template();
        template.setTitle(title);
        template.setDescription(description);
        template.addCheckItems(checkItems);
        try {
            templateRepository.save(template);
        } catch (Exception e) {
            throw new SystemException(SERVICE_NAME, e.getMessage());
        }
        return template;
    }

    public Template addCheckItem(Template template, CheckItem checkItem) throws BaseException {
        if (template == null || !templateRepository.exists(template.getId())) {
            throw new NullEntityException(
                    SERVICE_NAME,
                    "Template does not exist in database. Try to use templateService.createTemplate instead"
            );
        }
        if (checkItem == null || !checkItemRepository.exists(checkItem.getId())) {
            throw new NullEntityException(
                    SERVICE_NAME,
                    "Cannot add a checkItem that does not exist in database to a template."
            );
        }
        if (template.getCheckItems().contains(checkItem)) {
            throw new DuplicatedPropertyException(
                    SERVICE_NAME,
                    "This template already contains this check item"
            );
        }
        template.addCheckItem(checkItem);
        try {
            templateRepository.save(template);
            checkItemRepository.save(checkItem);
        } catch (Exception e) {
            throw new SystemException(SERVICE_NAME, e.getMessage());
        }
        return template;
    }

    public Template deleteCheckItem(Template template, CheckItem checkItem) throws BaseException {
        if (!templateRepository.exists(template.getId())) {
            throw new NullEntityException(
                    SERVICE_NAME,
                    "Template does not exist in database. Try to use templateService.createTemplate instead"
            );
        }
        if (!checkItemRepository.exists(checkItem.getId())) {
            throw new NullEntityException(
                    SERVICE_NAME,
                    "Cannot add a checkItem that does not exist in database to a template."
            );
        }
        if (!template.getCheckItems().contains(checkItem)) {
            throw new InvalidPropertyException(
                    SERVICE_NAME,
                    "This template does not contain this check item"
            );
        }
        template.removeCheckItem(checkItem);
        try {
            templateRepository.save(template);
            checkItemRepository.save(checkItem);
        } catch (Exception e) {
            throw new SystemException(SERVICE_NAME, e.getMessage());
        }
        return template;
    }

    public Template editTemplateMessage(Template template, String title, String description) throws BaseException {
        if (!templateRepository.exists(template.getId())) {
            throw new NullEntityException(
                    SERVICE_NAME,
                    "Template does not exist in database."
            );
        }
        if (!template.getTitle().equals(title) && templateRepository.findByTitle(title) != null) {
            throw new DuplicatedPropertyException(
                    SERVICE_NAME,
                    "Duplicated title"
            );
        }
        template.setTitle(title);
        template.setDescription(description);
        try {
            templateRepository.save(template);
        } catch (Exception e) {
            throw new SystemException(SERVICE_NAME, e.getMessage());
        }
        return template;
    }

    public void deleteTemplate(Template template) throws BaseException {
        if (!templateRepository.exists(template.getId())) {
            throw new NullEntityException(
                    SERVICE_NAME,
                    "Template does not exist in database."
            );
        }
        try {
            templateRepository.save(template);
        } catch (Exception e) {
            throw new SystemException(SERVICE_NAME, e.getMessage());
        }
    }

    // TODO
    public Template findTemplateByName(String name) {
        return null;
    }

    public List<Template> getTemplateList() {
        return templateRepository.findAll();
    }

}