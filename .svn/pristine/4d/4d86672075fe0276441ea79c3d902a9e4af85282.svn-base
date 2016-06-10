package com.trgis.ticg.system.core.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.trgis.ticg.system.core.model.SystemMenu;
/**
 * 
 * @author Alice
 *
 * 2016年5月18日
 */
public interface SystemMenuDao extends JpaRepository<SystemMenu, Long>,JpaSpecificationExecutor<SystemMenu> {
	/**
	 * 根节点的权限列表
	 * @return
	 */
	@Query(nativeQuery=true,value="select * from ticg_sys_menu m where m.parent is null order by m.id")
	public List<SystemMenu> getRootMenus();
}
