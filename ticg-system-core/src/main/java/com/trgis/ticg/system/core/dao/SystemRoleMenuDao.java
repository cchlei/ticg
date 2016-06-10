package com.trgis.ticg.system.core.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.trgis.ticg.system.core.model.SystemRoleMenu;
/**
 * @author Alice
 *
 * 2016年5月18日
 */
public interface SystemRoleMenuDao extends JpaRepository<SystemRoleMenu, Long>,JpaSpecificationExecutor<SystemRoleMenu> {
	
	@Query(nativeQuery=true,value="select * from ticg_sys_role_menu where sysrole = ?1")
	public List<SystemRoleMenu> getMenusByRoleid(Long roleid);
	
	
	@Modifying 
	@Query(nativeQuery=true,value="delete from ticg_sys_role_menu where sysrole = ?1")
	public void deleteByRoleid (Long roleid); 
}
