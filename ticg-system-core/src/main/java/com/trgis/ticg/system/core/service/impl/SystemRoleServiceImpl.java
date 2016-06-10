package com.trgis.ticg.system.core.service.impl;

import java.util.ArrayList;
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
import com.trgis.ticg.system.core.dao.SystemMenuDao;
import com.trgis.ticg.system.core.dao.SystemRoleDao;
import com.trgis.ticg.system.core.dao.SystemRoleMenuDao;
import com.trgis.ticg.system.core.model.SystemMenu;
import com.trgis.ticg.system.core.model.SystemRole;
import com.trgis.ticg.system.core.model.SystemRoleMenu;
import com.trgis.ticg.system.core.service.SystemRoleService;

@Service
@Transactional
public class SystemRoleServiceImpl implements SystemRoleService {

	@Autowired
	private SystemRoleDao roleDao;
	
	@Autowired
	private SystemMenuDao menuDao;
	
	@Autowired
	private SystemRoleMenuDao roleMenuDao;
	
	@Override
	public SystemRole getRoleById(Long id) {
		return roleDao.findOne(id);
	}
	
	/**
	 * 查询正常状态下的role集合
	 */
	@Override
	public List<SystemRole> findAll() {
		return  roleDao.findAll();
	}

	@Override
	public void save(SystemRole role) {
		roleDao.save(role);
	}
	
	/**
	 * 给角色分配权限
	 */
	@SuppressWarnings("unused")
	@Override
	public void doAssignPermission(SystemRole role, String[] selectedPermissionIds) {
		// 跟之前的作比较，删除取消的，如果已选中则不添加，未选中则添加(也可全清，再附加新的，但会占用数据库)
		try {
			List<SystemRoleMenu> rpList = new ArrayList<SystemRoleMenu>(roleMenuDao.getMenusByRoleid(role.getId()));
			// 1.删除取消的
			outer: for (SystemRoleMenu rp : rpList) {
				inner: for (int i = 0; i < selectedPermissionIds.length; i++) {
					if (rp.getSysmenu().getId().equals(Long.parseLong(selectedPermissionIds[i])))
						continue outer;
				}
				roleMenuDao.delete(rp);
			}
			// 2.如果已选中则不添加，未选中则添加
			List<Long> pids = new ArrayList<Long>();
			for (SystemRoleMenu rolePermission : rpList) {
				Long i = rolePermission.getSysmenu().getId();
				pids.add(i);
			}
			for (int i = 0; i < selectedPermissionIds.length; i++) {
				SystemMenu p = menuDao.findOne(Long.parseLong(selectedPermissionIds[i]));
				SystemRoleMenu rp2 = new SystemRoleMenu(role, p);
				if(pids.contains(Integer.parseInt(selectedPermissionIds[i])))
						continue;
				roleMenuDao.save(rp2);
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 根据角色名查找角色
	 */
	@Override
	public SystemRole findRoleByRolename(String rolename, Integer status) {
		List<SystemRole> rolelist = roleDao.getRoleByRolename(rolename, status); 
		if(rolelist != null & rolelist.size() > 0) {
			return rolelist.get(0);
		}else{
			return null;
		}
	}
	
	/**
	 * 分页查询
	 */
	@Override
	public Page<SystemRole> findByConditions(ConditionGroup conditionGroup, int pageNum, int pageSize, OrderBy... order){
		Specification<SystemRole> specifications = DynamicSpecficationUtil.buildSpecfication(conditionGroup);
		long count = roleDao.count(specifications);
		if (count == 0) {
			return null;
		}
		PageRequest page = PageAndSortUtil.buildPageAndSort(count, pageNum, pageSize, order);
		return roleDao.findAll(specifications, page);
	}

	@Override
	public void delete(Long id) {
		roleMenuDao.deleteByRoleid(id);
		roleDao.delete(id);
	}

}
