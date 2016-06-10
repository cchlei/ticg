package com.trgis.ticg.core.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.trgis.ticg.core.model.Organization;
import com.trgis.ticg.core.model.ShareRole;
import com.trgis.ticg.core.model.ShareRoleOrg;

/**
 * 组织机构共享角色Dao
 * 
 * @author Alice
 *
 * 2016年5月19日
 */
@Repository
public interface ShareRoleOrgDao extends JpaRepository<ShareRoleOrg, Long>,JpaSpecificationExecutor<ShareRoleOrg>{

	List<ShareRoleOrg> findByOrganization(Organization org);
	
	List<ShareRoleOrg> findBySharerole(ShareRole sharerole);
	
	List<ShareRoleOrg> findByShareroleAndOrganization(ShareRole sharerole, Organization org);
	
	/**
	 * @Description: 根据角色删除级联关系
	 * @param role
	 */
	@Modifying
	@Query("delete from ShareRoleOrg where sharerole = ?1")
	void deleteByShareRole(ShareRole sharerole);

	/**
	 * @Description: 根据角色批量删除级联关系
	 * @param ids
	 */
	@Modifying
	@Query(nativeQuery=true,value="delete from ticg_share_role_org where sharerole in (?1)")
	void deleteByShareRoles(Long[] ids);
	
	/**
	 * 按shareroleids查找集合
	 * @param ids
	 */
	@Query(nativeQuery=true,value="select * from ticg_share_role_org where sharerole in (?1)")
	List<ShareRoleOrg> findByShareroleIds(Long[] ids);

}
