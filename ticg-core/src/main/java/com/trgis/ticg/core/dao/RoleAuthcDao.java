package com.trgis.ticg.core.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.trgis.ticg.core.model.Authc;
import com.trgis.ticg.core.model.Role;
import com.trgis.ticg.core.model.RoleAuthc;

/**
 * @ClassName: RoleAuthcDao
 * @Description: 角色操作关联关系表
 * @author yanpeng
 * @date 2016年5月18日 下午5:11:31
 */
@Repository
public interface RoleAuthcDao extends JpaRepository<RoleAuthc, Long>,JpaSpecificationExecutor<RoleAuthc>{

	List<RoleAuthc> findByRole(Role role);

	/**
	 * @Description: 根据角色删除级联关系
	 * @author yanpeng
	 * @date 2016年5月25日 下午3:45:07
	 * @param role
	 */
	@Modifying
	@Query("delete from RoleAuthc where role = ?1")
	void deleteByRole(Role role);

	/**
	 * @Description: 根据批量角色删除级联关系
	 * @author yanpeng
	 * @date 2016年5月25日 下午3:45:07
	 * @param role
	 */
	@Modifying
	@Query(nativeQuery=true,value="delete from ticg_role_authc where role in (?1)")
	void deleteByRoles(Long[] ids);
	
	

	/**
	 * @Description: 根据操作删除级联关系
	 * @author liuyan
	 * @date 2016年5月27日 
	 * @param authc
	 */
	void deleteByAuthc(Authc authc);
	/**
	 * @Description:根据操作删除级联关系
	 * @Author liuyan 
	 * @Date 2016年5月27日 下午5:37:45
	 * @param authc
	 */
	/*@Modifying
	@Query("delete from RoleAuthc where authc in ?1")
	void deleteByAuthc(List<Authc> authc);*/
	@Modifying
	@Query("delete from RoleAuthc where authc in (?1)")
	void deleteByAuthcs(List<Authc> authcs);
	
}
