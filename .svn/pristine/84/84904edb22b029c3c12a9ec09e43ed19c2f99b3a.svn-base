package com.trgis.ticg.core.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.trgis.ticg.core.dao.RoleSubsystemDao;
import com.trgis.ticg.core.model.Role;
import com.trgis.ticg.core.model.RoleSubsystem;
import com.trgis.ticg.core.model.SubSystemRegister;
import com.trgis.ticg.core.service.RoleSubsystemService;
/**
 * 操作Service
 * 
 * @author Alice
 *
 * 2016年5月19日
 */
@Transactional
@Service
public class RoleSubsystemServiceImpl implements RoleSubsystemService {

	@Autowired
	private RoleSubsystemDao roleSubsystemDao;
	
	private static final Logger logger = LoggerFactory.getLogger(RoleSubsystemServiceImpl.class);

	@Override
	public void add(RoleSubsystem roleSubsystem) {
		try {
			roleSubsystemDao.save(roleSubsystem);
		} catch (Exception e) {
			logger.debug(""+e.getMessage());
			throw e;
		}
	}

	@Override
	public void del(RoleSubsystem roleSubsystem) {
		try {
			roleSubsystemDao.delete(roleSubsystem);
		} catch (Exception e) {
			logger.debug(""+e.getMessage());
			throw e;
		}
	}


	@Override
	public List<RoleSubsystem> findByRole(Role role) {
		try {
			return roleSubsystemDao.findByRole(role);
		} catch (Exception e) {
			logger.debug(""+e.getMessage());
			throw e;
		}
	}

	@Override
	public void delBySsRegister(List<SubSystemRegister> sysReg) {
		    roleSubsystemDao.deleteBySsRegister(sysReg);
	}

	@Override
	public List<RoleSubsystem> findBySubSystemRegister(SubSystemRegister subSystemRegister) {
		return  roleSubsystemDao.findBySubSystemRegister(subSystemRegister);
	}

	


}
