package com.fudan.ooad.service;

import com.fudan.ooad.entity.Template;

import java.util.Set;

/**
 * Created by Jindiwei on 2017/6/19.
 */
public interface ITemplateService {

    void templateAddCheckItem();
    void templateDeleteCheckItem();
    void templateAddCompany();
    void templateDeleteCompany();

    void createTemplate();
    void modifyTemplate();
    void deleteTemplate();
    void deliverTemplate();
    Set<Template> searchTemplate();
}
