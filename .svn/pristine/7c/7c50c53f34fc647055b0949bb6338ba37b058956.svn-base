package com.trgis.ticg.core.service;

import java.util.Set;

import com.trgis.ticg.core.model.ShareRole;
import com.trgis.ticg.core.model.ShareRoleMenu;
/**
 * 共享角色菜单Service
 * 
 * @author Alice
 *
 * 2016年5月19日
 */
public interface ShareRoleMenuService {
	/**
	 * 新增
	 * @param authc
	 */
	public void add(ShareRoleMenu shareRoleMenu) ;

	/**
	 * @Description: delete
	 * @author yanpeng
	 * @date 2016年5月23日 上午11:49:46
	 * @param RoleMenu
	 */
	public void del(ShareRoleMenu shareRoleMenu);

	/**
	 * @Description: 查询当前角色关联的菜单
	 * @author yanpeng
	 * @date 2016年5月26日 上午9:18:50
	 * @param role
	 * @return
	 */
	public Set<Long> findByShareRole(ShareRole sharerole);
}
