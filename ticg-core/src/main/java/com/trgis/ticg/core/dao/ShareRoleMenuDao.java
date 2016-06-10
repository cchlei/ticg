package com.trgis.ticg.core.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.trgis.ticg.core.model.ShareRole;
import com.trgis.ticg.core.model.ShareRoleMenu;

/**
 * @ClassName: RoleDao
 * @Description: 角色资源关联关系表
 * @author yanpeng
 * @date 2016年5月17日 上午11:40:26
 */
@Repository
public interface ShareRoleMenuDao extends JpaRepository<ShareRoleMenu, Long>,JpaSpecificationExecutor<ShareRoleMenu>{

	/**
	 * @Description: 根据角色删除级联关系
	 * @author yanpeng
	 * @date 2016年5月25日 下午3:43:41
	 * @param role
	 */
	@Modifying
	@Query("delete from ShareRoleMenu where sharerole = ?1")
	void deleteByShareRole(ShareRole sharerole);

	/**
	 * @Description: 根据角色批量删除级联关系
	 * @author yanpeng
	 * @date 2016年5月25日 下午3:43:41
	 * @param ids
	 */
	@Modifying
	@Query(nativeQuery=true,value="delete from ticg_share_role_menu where sharerole in (?1)")
	void deleteByShareRoles(Long[] ids);
	
	List<ShareRoleMenu> findBySharerole(ShareRole sharerole);
	
}
