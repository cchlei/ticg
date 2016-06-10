package com.trgis.ticg.core.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.trgis.ticg.core.model.Organization;
import com.trgis.ticg.core.model.Role;

/**
 * 子系统角色DAO
 * 
 * @author Alice
 *
 * 2016年5月19日
 */
@Repository
public interface RoleDao extends JpaRepository<Role, Long>,JpaSpecificationExecutor<Role>{


	List<Role> findByOrganization(Organization org);

	
	/**
	 * @Description 批量删除
	 * @author yanpeng
	 * @date 2016年5月25日 下午1:01:36
	 * @param ids
	 */
	@Modifying
	@Query("delete from Role where id in (?1)")
	void delete(Long[] ids);

}
