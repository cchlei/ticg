package com.trgis.ticg.core.service;

import java.util.List;

import com.trgis.ticg.core.model.Role;
import com.trgis.ticg.core.model.RoleSubsystem;
import com.trgis.ticg.core.model.SubSystemRegister;
/**
 * 操作Service
 * 
 * @author Alice
 *
 * 2016年5月19日
 */
public interface RoleSubsystemService {
	/**
	 * 新增
	 * @param authc
	 */
	public void add(RoleSubsystem roleSubsystem) ;

	/**
	 * @Description: delete
	 * @author yanpeng
	 * @date 2016年5月23日 上午11:49:46
	 * @param RoleMenu
	 */
	public void del(RoleSubsystem roleSubsystem);
	/**
	 * @Description: 根据注册子系统删除角色注册子系统表
	 * @author liuyan
	 * @date 2016年5月27日 下午16:49:46
	 * @param sysReg
	 */
	public void delBySsRegister(List<SubSystemRegister> sysReg);

	/**
	 * @Description: 
	 * @author yanpeng
	 * @date 2016年5月27日 下午3:14:36
	 * @param role
	 * @return
	 */
	public List<RoleSubsystem> findByRole(Role role);
	/**
	 * 
	 * @param subSystemRegister
	 * @return
	 */
	public List<RoleSubsystem> findBySubSystemRegister(SubSystemRegister subSystemRegister);
}
