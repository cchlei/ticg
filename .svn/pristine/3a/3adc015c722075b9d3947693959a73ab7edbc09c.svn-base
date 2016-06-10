package com.trgis.ticg.core.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.trgis.ticg.core.dao.ShareRoleSubsystemDao;
import com.trgis.ticg.core.model.ShareRole;
import com.trgis.ticg.core.model.ShareRoleSubsystem;
import com.trgis.ticg.core.model.SubSystemRegister;
import com.trgis.ticg.core.service.ShareRoleSubsystemService;
/**
 * 共享角色子系统关系Service
 * 
 * @author Alice
 *
 * 2016年5月19日
 */
@Transactional
@Service
public class ShareRoleSubsystemServiceImpl implements ShareRoleSubsystemService {

	@Autowired
	private ShareRoleSubsystemDao srsDao;
	
	private static final Logger logger = LoggerFactory.getLogger(ShareRoleSubsystemServiceImpl.class);

	@Override
	public void add(ShareRoleSubsystem srs) {
		try {
			srsDao.save(srs);
		} catch (Exception e) {
			logger.debug(""+e.getMessage());
			throw e;
		}
	}

	@Override
	public void del(ShareRoleSubsystem srs) {
		try {
			srsDao.delete(srs);
		} catch (Exception e) {
			logger.debug(""+e.getMessage());
			throw e;
		}
	}

	@Override
	public List<ShareRoleSubsystem> findByShareRole(ShareRole sharerole) {
		try {
			return srsDao.findBySharerole(sharerole);
		} catch (Exception e) {
			logger.debug(""+e.getMessage());
			throw e;
		}
	}

	@Override
	public List<ShareRoleSubsystem> findBySubSystemRegister(SubSystemRegister subSystemRegister) {
		return srsDao.findBySubSystemRegister(subSystemRegister);
	}

}
