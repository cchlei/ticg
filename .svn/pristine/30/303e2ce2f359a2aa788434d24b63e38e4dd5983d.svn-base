package com.trgis.ticg.system.core.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.trgis.ticg.system.core.dao.SystemMenuDao;
import com.trgis.ticg.system.core.dao.SystemRoleMenuDao;
import com.trgis.ticg.system.core.model.SystemMenu;
import com.trgis.ticg.system.core.model.SystemRoleMenu;
import com.trgis.ticg.system.core.service.SystemMenuService;

/**
 * 
 * @author Alice
 *
 * 2016年5月19日
 */
@Service
@Transactional
public class SystemMenuServiceImpl implements SystemMenuService {

	@Autowired
	private SystemMenuDao menuDao;
	@Autowired
	private SystemRoleMenuDao roleMenuDao;
	
	/**
	 * 新增权限，用于实例化权限列表
	 * @param permission .
	 */
	@Override
	public void save(SystemMenu menu) {
		menuDao.save(menu);
	}
	
	/**
	 * 查询所有权限
	 * @return .
	 */
//	@Override
//	public List<SystemMenu> getAllPermissions() {
//		return permissionDao.findAll();
//	}
	
	/**
	 * 根节点的权限列表
	 * @return
	 */
	@Override
	public List<SystemMenu> getRootMenus() {
		List<SystemMenu> menus = menuDao.getRootMenus();
		Hibernate.initialize(menus);//强制加载这样就相当于动态改变为lazy=false
		for (Iterator<SystemMenu> iterator = menus.iterator(); iterator.hasNext();) {
			SystemMenu menu = (SystemMenu) iterator.next();
			initlize(menu);
		}
		return menus;
	}

	/**
	 * 递归 完成所有子对象的初始化 .
	 * @param permission .
	 */
	private static void initlize(SystemMenu menu) {
		Hibernate.initialize(menu.getMenus());
		List<SystemMenu> menus = menu.getMenus();
		Assert.notNull(menus);
		for (Iterator<SystemMenu> iterator = menus.iterator(); iterator.hasNext();) {
			SystemMenu m = (SystemMenu) iterator.next();
			initlize(m);
		}
	}
	
	/**
	 * 根据角色查找权限列表 .
	 * @param role .
	 * @return List
	 */
	@Override
	public List<SystemMenu> getPermissionsByRoleid(Long roleid) {
		List<SystemRoleMenu> rpList = roleMenuDao.getMenusByRoleid(roleid);
		List<SystemMenu> permissions = new ArrayList<SystemMenu>();
		
		for (SystemRoleMenu rolePermission : rpList) {
			SystemMenu permission = rolePermission.getSysmenu();
			permissions.add(permission);
		}
		return permissions;
	}

}
