package com.trgis.ticg.core.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.trgis.ticg.core.model.Menu;
import com.trgis.ticg.core.model.Role;
import com.trgis.ticg.core.model.RoleMenu;

/**
 * @ClassName: RoleDao
 * @Description: 角色资源关联关系表
 * @author yanpeng
 * @date 2016年5月17日 上午11:40:26
 */
@Repository
public interface RoleMenuDao extends JpaRepository<RoleMenu, Long>,JpaSpecificationExecutor<RoleMenu>{

	/**
	 * @Description: 根据角色删除级联关系
	 * @author yanpeng
	 * @date 2016年5月25日 下午3:43:41
	 * @param role
	 */
	@Modifying
	@Query("delete from RoleMenu where role = ?1")
	void deleteByRole(Role role);

	/**
	 * @Description: 根据角色批量删除级联关系
	 * @author yanpeng
	 * @date 2016年5月25日 下午3:43:41
	 * @param ids
	 */
	@Modifying
	@Query(nativeQuery=true,value="delete from ticg_role_menu where role in (?1)")
	void deleteByRoles(Long[] ids);

	List<RoleMenu> findByRole(Role role);
	
	/**
	 * @Description: 根据操作删除级联关系
	 * @author liuyan
	 * @date 2016年5月27日 下午2:43:41
	 * @param role
	 */
	@Modifying
	@Query("delete from RoleMenu where menu = ?1")
	void deleteByMenu(Menu menu);
	/**
	 * @Description: 根据操作删除级联关系
	 * @Author liuyan 
	 * @Date 2016年5月28日 下午12:21:40
	 * @param menus
	 */
/*	@Modifying
	@Query("delete from RoleMenu where menu in (?1)")
	void deleteByMenu(List<Menu> menus);
	*/
}
