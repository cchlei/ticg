package com.trgis.ticg.core.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.trgis.ticg.core.model.ShareRoleOrg;
import com.trgis.ticg.core.model.User;
import com.trgis.ticg.core.model.UserShareRole;

/**
 * 
 * @author majl
 * @Description 用户角色共享关系DAO
 * @data 2016年5月25日
 */
@Repository
public interface UserShareRoleDao extends JpaRepository<UserShareRole,Long>,JpaSpecificationExecutor<UserShareRole>{

	@Modifying
	@Query("delete  from UserShareRole where user.id = ?1")
		void deleteAllUserShareRoleByUser(Long userid);
	
	List<UserShareRole> findAllByUser(User user);
	
	/**
	 * 按关系级联删除
	 * @param sro
	 * @return
	 */
	@Modifying
	@Query("delete from UserShareRole where shareRoleOrg = ?1")
	void deleteByShareRoleOrg(ShareRoleOrg sro);
}
