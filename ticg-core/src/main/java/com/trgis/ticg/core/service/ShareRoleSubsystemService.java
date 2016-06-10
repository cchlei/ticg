package com.trgis.ticg.core.service;

import java.util.List;

import com.trgis.ticg.core.model.ShareRole;
import com.trgis.ticg.core.model.ShareRoleSubsystem;
import com.trgis.ticg.core.model.SubSystemRegister;
/**
 * 共享角色子系统关系表
 * 
 * @author Alice
 *
 * 2016年5月19日
 */
public interface ShareRoleSubsystemService {
	/**
	 * 新增
	 * @param authc
	 */
	public void add(ShareRoleSubsystem srs) ;

	/**
	 * @Description: delete
	 * @param RoleMenu
	 */
	public void del(ShareRoleSubsystem srs);
	/**
	 * @Description: 根据共享角色查询
	 * @param sysid
	 * @return
	 */
	List<ShareRoleSubsystem> findByShareRole(ShareRole sharerole);
	public List<ShareRoleSubsystem> findBySubSystemRegister(SubSystemRegister subSystemRegister);
}
