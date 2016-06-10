package com.trgis.ticg.core.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.trgis.ticg.core.model.ShareRole;
import com.trgis.ticg.core.model.ShareRoleSubsystem;
import com.trgis.ticg.core.model.SubSystemRegister;

/**
 * 共享角色与注册子系统关系DAO
 * 
 * @author Alice
 *
 * 2016年5月19日
 */
@Repository
public interface ShareRoleSubsystemDao extends JpaRepository<ShareRoleSubsystem, Long>,JpaSpecificationExecutor<ShareRoleSubsystem>{

	List<ShareRoleSubsystem> findBySharerole(ShareRole sharerole);
	
	@Modifying
	@Query("delete from ShareRoleSubsystem where sharerole=?1")
	void deleteByShareRole(ShareRole sharerole);
	
	@Modifying
	@Query(nativeQuery=true,value="delete from ticg_share_role_subsystem where sharerole in (?1)")
	void deleteByShareRoles(Long[] ids);
	
	@Modifying
	@Query("from ShareRoleSubsystem where ssRegister= ?1")
	List<ShareRoleSubsystem> findBySubSystemRegister(SubSystemRegister subSystemRegister);
}
