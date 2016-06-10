package com.trgis.ticg.core.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.trgis.ticg.core.dao.RoleAuthcDao;
import com.trgis.ticg.core.model.Role;
import com.trgis.ticg.core.model.RoleAuthc;
import com.trgis.ticg.core.service.RoleAuthcService;
/**
 * 操作Service
 * 
 * @author Alice
 *
 * 2016年5月19日
 */
@Transactional
@Service
public class RoleAuthcServiceImpl implements RoleAuthcService {

	@Autowired
	private RoleAuthcDao roleAuthcDao;
	
	private static final Logger logger = LoggerFactory.getLogger(RoleAuthcServiceImpl.class);

	@Override
	public void add(RoleAuthc operation){
		try {
			logger.debug("添加角色操作关系");
			roleAuthcDao.save(operation);
		} catch (Exception e) {
			logger.debug("添加角色操作关系失败"+e.getMessage());
			e.printStackTrace();
		}
		
	}

	@Override
	public void del(RoleAuthc roleAuthc) {
		try {
			logger.debug("删除角色角色操作关联关系");
			roleAuthcDao.delete(roleAuthc);
		} catch (Exception e) {
			logger.debug("添加操作失败"+e.getMessage());
			e.printStackTrace();
		}
	}

	@Override
	public List<RoleAuthc> findByRole(Long rid) {
		try {
			Role role= new Role();
			role.setId(rid);
			logger.debug("删除角色角色操作关联关系");
			return roleAuthcDao.findByRole(role);
		} catch (Exception e) {
			logger.debug("添加操作失败"+e.getMessage());
			throw e;
		}
	}
}
