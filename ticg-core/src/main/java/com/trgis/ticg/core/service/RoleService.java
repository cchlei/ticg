package com.trgis.ticg.core.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.trgis.common.jpa.specfication.ConditionGroup;
import com.trgis.common.jpa.specfication.OrderBy;
import com.trgis.ticg.core.exception.RoleException;
import com.trgis.ticg.core.model.Role;

public interface RoleService {
	/**
	 * @Description: 分页查询角色资源列表
	 * @author yanpeng
	 * @date 2016年5月18日 下午4:48:23
	 * @param conditionGroup  过滤条件
	 * @param pageNum
	 * @param pageSize
	 * @param order 排序
	 * @return
	 * @throws RoleException
	 */
	public Page<Role> findByConditions(ConditionGroup conditionGroup, int pageNum, int pageSize,
			OrderBy... order) throws  RoleException;
	/**
	 * @Description: 增加角色
	 * @author yanpeng
	 * @date 2016年5月17日 下午1:41:42
	 * @param role 角色详细信息
	 * @return
	 * @throws RoleException
	 */
	Long addRole(Role role) throws RoleException;
	/**
	 * @Description: 修改角色
	 * @author yanpeng
	 * @date 2016年5月17日 下午1:42:53
	 * @param role 修改后的角色信息
	 * @return
	 * @throws RoleException
	 */
	void editRole(Role role) throws RoleException;
	
	/**
	 * @Description: delete
	 * @author yanpeng
	 * @date 2016年5月23日 下午6:52:37
	 * @param id
	 */
	public void del(Long id) throws RoleException;
	
	/**
	 * @Description: 根据组织机构id查询所有的角色
	 * @author yanpeng
	 * @date 2016年5月24日 下午2:34:04
	 * @param oid
	 * @return
	 */
	public List<Role> findByOrg(Long oid) throws RoleException ;
	
	/**
	 * @Description: 批量删除
	 * @author yanpeng
	 * @date 2016年5月25日 上午11:43:23
	 * @param ids
	 */
	public void del(Long[] ids) throws RoleException;
	
	/**
	 * @Description: 根据角色id查询
	 * @author yanpeng
	 * @date 2016年5月25日 下午2:40:27
	 * @param id
	 * @return
	 */
	public Role findOne(Long id) throws RoleException;
	
	public void addMenu(Long rid, String ids, Long subid,Long oid, String rolname)  throws RoleException;
	
	/**
	 * @Description: 修改
	 * @author yanpeng
	 * @date 2016年5月28日 上午9:09:51
	 * @param temp
	 */
	public void saveAndFlush(Role temp) throws RoleException;
}
