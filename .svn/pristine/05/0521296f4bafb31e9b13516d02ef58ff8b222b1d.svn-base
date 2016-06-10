package com.trgis.ticg.system.core.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.trgis.ticg.core.model.Organization;
import com.trgis.ticg.system.core.model.SystemRole;
import com.trgis.ticg.system.core.model.SystemUser;

/**
 * 系统用户
 * 
 * @author Alice
 *
 * 2016年5月25日
 */
@Repository
public interface SystemUserDao extends JpaRepository<SystemUser, Long>,JpaSpecificationExecutor<SystemUser> {

	SystemUser findByUsername(String username);
	
	/**
	 * 更新用户的角色名
	 * @param rolename
	 * @param id
	 */
	@Modifying 
	@Query("update SystemUser set rolename = ?1 where sysrole = ?2") 
	public void updateRolename (String rolename, SystemRole sysrole); 
	
	/**
	 * 更新用户的角色名
	 * @param rolename
	 * @param id
	 */
	@Modifying 
	@Query("update SystemUser set organizationname = ?1 where organization = ?2") 
	public void updateOrgname (String orgname, Organization org); 
	
	/**
	 * 查找拥有该角色的用户
	 * @param roleid
	 * @return
	 */
	@Query(nativeQuery=true,value="select * from ticg_sys_user where sysrole =?1")
	public List<SystemUser>findListByRole(Long roleid);
}
 