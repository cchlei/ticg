package com.trgis.ticg.system.core.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.trgis.common.jpa.specfication.ConditionGroup;
import com.trgis.common.jpa.specfication.OrderBy;
import com.trgis.ticg.core.exception.AuthcException;
import com.trgis.ticg.system.core.model.SystemRole;

/**
 * 系统超级管理员角色
 * 
 * @author Alice
 *
 * 2016年5月18日
 */
public interface SystemRoleService {

	/**
	 * 新增编辑
	 * 
	 * @param role
	 */
	void save(SystemRole role);
	
	/**
	 * 给角色分配权限
	 * @param role
	 * @param selectedPermissionIds
	 */
	void doAssignPermission(SystemRole role, String[] selectedPermissionIds);
	
	/**
	 * 删除
	 * @param role
	 */
	void delete(Long id);
	
	/**
	 * 根据角色名查找角色
	 * @param role
	 * @return
	 */
	public SystemRole findRoleByRolename(String rolename, Integer status);
	
	/**
	 * 按ID查找
	 * @return
	 */
	public SystemRole getRoleById(Long id);

	/**
	 * 查询全部
	 * @return
	 */
	public List<SystemRole> findAll();
	
	/**
	 * 分页查询
	 * @param conditionGroup
	 * @param pageNum
	 * @param pageSize
	 * @param order
	 * @return
	 * @throws AuthcException
	 */
	public Page<SystemRole> findByConditions(ConditionGroup conditionGroup, int pageNum, int pageSize,
			OrderBy... order);
}
