package com.trgis.ticg.core.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.trgis.common.jpa.specfication.ConditionGroup;
import com.trgis.common.jpa.specfication.OrderBy;
import com.trgis.ticg.core.model.Organization;
import com.trgis.ticg.core.model.SubSystemRegister;
import com.trgis.ticg.core.model.Subsystem;

public interface SubsystemRegisterService {
/**
 * @author Alice
 * 根据组织机构查询已注册的子系统
 * @param organization
 * @return
 */
public List<SubSystemRegister> findByOrganization(Organization organization);

public Page<SubSystemRegister> findByConditions(ConditionGroup conditionGroup, int pageNum, int pageSize,
		OrderBy... order);
/**
 * 根据ID查找
 * @param id
 * @return
 */
public SubSystemRegister findOne(Long id);
/**
 * 
 * @param org
 */
public SubSystemRegister addSubSystemRegister(SubSystemRegister ssr);
/**
 * 
 * @param org
 */
public void deleteSubSystemRegister(Long id);
/**
 * 
 * @param org
 * @return
 */
public List<Subsystem> findSystemRegister(Organization organization);

public List<SubSystemRegister> editSystemRegister(List<SubSystemRegister> list);

public List<SubSystemRegister> addSubSystemRegister(List<SubSystemRegister> list);



}
