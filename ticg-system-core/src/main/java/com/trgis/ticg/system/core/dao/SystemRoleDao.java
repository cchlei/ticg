package com.trgis.ticg.system.core.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.trgis.ticg.system.core.model.SystemRole;

/**
 * 角色DAO
 * 
 * @author Alice
 *
 * 2016年5月19日
 */
public interface SystemRoleDao extends JpaRepository<SystemRole, Long>,JpaSpecificationExecutor<SystemRole> {

	/**
	 * 按名称超找角色
	 * @param rolename
	 * @return
	 */
	@Query(nativeQuery=true,value="select * from ticg_sys_role where rolename = ?1 and status = ?2")
	public List<SystemRole> getRoleByRolename(String rolename, Integer status);

}
