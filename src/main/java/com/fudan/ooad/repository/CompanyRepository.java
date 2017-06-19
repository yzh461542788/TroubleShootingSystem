package com.fudan.ooad.repository;

import com.fudan.ooad.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Jindiwei on 2017/6/19.
 */
public interface CompanyRepository extends JpaRepository<Company, Integer> {
}
