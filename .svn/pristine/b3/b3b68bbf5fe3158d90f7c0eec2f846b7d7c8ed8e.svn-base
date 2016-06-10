package com.trgis.ticg.core.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.trgis.ticg.core.model.Menu;
import com.trgis.ticg.core.model.Subsystem;

/**
 * 菜单DAO
 * 
 * @author Alice
 *
 * 2016年5月19日
 */
@Repository
public interface MenuDao extends JpaRepository<Menu, Long>,JpaSpecificationExecutor<Menu>{

	/**
	 * @Description: 删除
	 * @author liuyan
	 * @date 2016年5月20日
	 * @param cids
	 */
	@Modifying
	@Query(nativeQuery=true , value="delete  from ticg_menu  where id in (?1)")
	public void delete(Long[] id);

	/**
	 * @Description: 根据子系统获取菜单列表
	 * @author yanpeng
	 * @date 2016年5月24日 下午3:13:12
	 * @param subsystem
	 * @return
	 */
	public List<Menu> findBySubsystem(Subsystem subsystem);
	 /**
	  * @Description:根据子系统和name查找菜单
	  * @Author liuyan 
	  * @Date 2016年5月24日 下午7:00:16
	  * @param name
	  * @param sb
	  * @return
	  */
	public List<Menu> findByNameAndSubsystem(String name, Subsystem sb);
	 /**
	  * @Description:根据子系统和name查找菜单
	  * @Author liuyan 
	  * @Date 2016年5月24日 下午7:00:16
	  * @param code
	  * @param sb
	  * @return
	  */
	public List<Menu> findByCodeAndSubsystem(String name, Subsystem sb);
	/**
	 * @Description:根据子系统删除菜单表
	 * @Author liuyan 
	 * @Date 2016年5月27日 下午3:46:12
	 * @param subsystem
	 */
	public void deleteBySubsystem(Subsystem subsystem);
	
}
