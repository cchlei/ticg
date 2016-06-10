package com.trgis.ticg.core.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.trgis.ticg.core.model.ShareRole;
import com.trgis.ticg.core.model.ShareRoleAuthc;

/**
 * 共享角色操作关联关系表
 * 
 * @author Alice
 *
 * 2016年5月26日
 */
@Repository
public interface ShareRoleAuthcDao extends JpaRepository<ShareRoleAuthc, Long>,JpaSpecificationExecutor<ShareRoleAuthc>{

	List<ShareRoleAuthc> findBySharerole(ShareRole sharerole);

	/**
	 * @Description: 根据角色删除级联关系
	 * @author yanpeng
	 * @date 2016年5月25日 下午3:45:07
	 * @param role
	 */
	@Modifying
	@Query("delete from ShareRoleAuthc where sharerole = ?1")
	void deleteByShareRole(ShareRole sharerole);

	/**
	 * @Description: 根据批量角色删除级联关系
	 * @author yanpeng
	 * @date 2016年5月25日 下午3:45:07
	 * @param role
	 */
	@Modifying
	@Query(nativeQuery=true,value="delete from ticg_share_role_authc where sharerole in (?1)")
	void deleteByShareRoles(Long[] ids);
}
