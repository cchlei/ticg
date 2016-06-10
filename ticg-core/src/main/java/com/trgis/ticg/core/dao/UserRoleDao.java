package com.trgis.ticg.core.dao;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.trgis.ticg.core.model.Role;
import com.trgis.ticg.core.model.User;
import com.trgis.ticg.core.model.UserRole;

/**
 * 
 * @author majl
 * @Description 用户角色表dao
 * @data 2016年5月25日
 */
@Repository
public interface UserRoleDao extends JpaRepository<UserRole,Long>,JpaSpecificationExecutor<UserRole>{
	//这谁写的？？？？？？
	@Modifying
	@Query("delete  from UserRole where user.id = ?1")
		void deleteAllUserRoleByUser(Long userid);
	
	List<UserRole> findAllByUser(User user);
	
	/**
	 * 删除单个
	 * @author Alice
	 * @param role
	 * @return
	 */
	@Modifying
	@Query("delete from UserRole where role = ?1")
	void deleteByRole(Role role);
	/**
	 * 批量删除
	 * @author Alice
	 * @param role
	 * @return
	 */
	@Modifying
	@Query(nativeQuery=true,value="delete from ticg_user_role where role in (?1)")
	void deleteByRoles(Long[] ids);
	
}
