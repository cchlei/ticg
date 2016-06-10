package com.trgis.ticg.core.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.trgis.ticg.core.dao.RoleMenuDao;
import com.trgis.ticg.core.model.Role;
import com.trgis.ticg.core.model.RoleMenu;
import com.trgis.ticg.core.service.RoleMenuService;
/**
 * 操作Service
 * 
 * @author Alice
 *
 * 2016年5月19日
 */
@Transactional
@Service
public class RoleMenuServiceImpl implements RoleMenuService {

	@Autowired
	private RoleMenuDao roleMenuDao;
	
	private static final Logger logger = LoggerFactory.getLogger(RoleMenuServiceImpl.class);

	@Override
	public void add(RoleMenu roleMenu) {
		try {
			roleMenuDao.save(roleMenu);
		} catch (Exception e) {
			logger.debug("删除角色菜单关系失败"+e.getMessage());
			throw e;
		}
	}

	@Override
	public void del(RoleMenu roleMenu) {
		try {
			roleMenuDao.delete(roleMenu);
			logger.debug("删除角色菜单关系成功");
		} catch (Exception e) {
			logger.debug("删除角色菜单"+e.getMessage());
			throw e;
		}
	}

	@Override
	public Set<Long> findByRole(Role role) {
		List<RoleMenu> lists =  roleMenuDao.findByRole(role);
		Set<Long> set = new HashSet<Long>();
		for (RoleMenu roleMenu : lists) {
			set.add(roleMenu.getMenu().getId());
		}
		return set;
	}

}
