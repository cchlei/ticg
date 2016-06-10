package com.trgis.ticg.core.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.trgis.ticg.core.model.Organization;
import com.trgis.ticg.core.model.ShareRole;

/**
 * 共享角色Dao
 * 
 * @author Alice
 *
 * 2016年5月19日
 */
@Repository
public interface ShareRoleDao extends JpaRepository<ShareRole, Long>,JpaSpecificationExecutor<ShareRole>{


	List<ShareRole> findByOrganization(Organization org);

	List<ShareRole> findByRolename(String rolename);
	
	/**
	 * @Description 批量删除
	 * @author yanpeng
	 * @date 2016年5月25日 下午1:01:36
	 * @param ids
	 */
	@Modifying
	@Query("delete from ShareRole where id in (?1)")
	void delete(Long[] ids);

}
