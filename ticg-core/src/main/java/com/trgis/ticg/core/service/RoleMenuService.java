package com.trgis.ticg.core.service;

import java.util.Set;

import com.trgis.ticg.core.model.Role;
import com.trgis.ticg.core.model.RoleMenu;
/**
 * 操作Service
 * 
 * @author Alice
 *
 * 2016年5月19日
 */
public interface RoleMenuService {
	/**
	 * 新增
	 * @param authc
	 */
	public void add(RoleMenu roleMenu) ;

	/**
	 * @Description: delete
	 * @author yanpeng
	 * @date 2016年5月23日 上午11:49:46
	 * @param RoleMenu
	 */
	public void del(RoleMenu roleMenu);

	/**
	 * @Description: 查询当前角色关联的菜单
	 * @author yanpeng
	 * @date 2016年5月26日 上午9:18:50
	 * @param role
	 * @return
	 */
	public Set<Long> findByRole(Role role);
}
