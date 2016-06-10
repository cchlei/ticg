package com.trgis.ticg.core.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.trgis.common.jpa.specfication.ConditionGroup;
import com.trgis.common.jpa.specfication.DynamicSpecficationUtil;
import com.trgis.common.jpa.specfication.OrderBy;
import com.trgis.common.jpa.specfication.PageAndSortUtil;
import com.trgis.ticg.core.dao.SubsystemDao;
import com.trgis.ticg.core.dao.SubsystemRegisterDao;
import com.trgis.ticg.core.model.Organization;
import com.trgis.ticg.core.model.SubSystemRegister;
import com.trgis.ticg.core.model.Subsystem;
import com.trgis.ticg.core.service.SubsystemRegisterService;

/**
 * 应用子系统service
 * @author Alice
 *
 * 2016年5月17日
 */
@Transactional
@Service
public class SubsystemRegisterServiceImpl implements SubsystemRegisterService {

	@Autowired
	private SubsystemRegisterDao subsystemRegisterDao;
	@Autowired
	private SubsystemDao subsystemDao;
	@Override
	public List<SubSystemRegister> findByOrganization(Organization organization) {
		return subsystemRegisterDao.findByOrganization(organization);
	}
	@Override
	public Page<SubSystemRegister> findByConditions(ConditionGroup conditionGroup, int pageNum, int pageSize,
			OrderBy... order) {
		Specification<SubSystemRegister> specifications = DynamicSpecficationUtil.buildSpecfication(conditionGroup);
		long count = subsystemRegisterDao.count(specifications);
		if (count == 0) {
			return null;
		}
		PageRequest page = PageAndSortUtil.buildPageAndSort(count, pageNum, pageSize, order);
		return subsystemRegisterDao.findAll(specifications, page);
	}
	@Override
	public SubSystemRegister findOne(Long id) {
		return subsystemRegisterDao.findOne(id);
	}
	@Override
	public List<SubSystemRegister> addSubSystemRegister(List<SubSystemRegister> list) {
		return subsystemRegisterDao.save(list);
	}
	@Override
	public void deleteSubSystemRegister(Long id) {
		subsystemRegisterDao.delete(id);
	}
	@Override
	public List<Subsystem> findSystemRegister(Organization organization) {
		return subsystemDao.findUnRegistedSubsystems(organization.getId());
	}
	@Override
	public SubSystemRegister addSubSystemRegister(SubSystemRegister ssr) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List<SubSystemRegister> editSystemRegister(List<SubSystemRegister> list) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
}
