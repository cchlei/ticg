package com.trgis.ticg.core.dao;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.trgis.ticg.core.model.Authc;
import com.trgis.ticg.core.model.Menu;

/**
 * 操作DAO
 * 
 * @author Alice
 *
 * 2016年5月19日
 */
@Repository
public interface AuthcDao extends JpaRepository<Authc, Long>,JpaSpecificationExecutor<Authc>{

	List<Authc> findByMenu(Menu menu);
	/**
	 * 	@Description:
	 * @Author liuyan 根据name和code查找操作
	 * @Date 2016年5月24日 下午6:27:34
	 * @param name
	 * @param meid
	 * @return
	 */
	public List<Authc> findByNameAndMenu(String value, Menu mu);
	public List<Authc> findByCodeAndMenu(String value, Menu mu);
	
	/**
	 * @Description: 根据菜单删除操作表
	 * @author liuyan
	 * @date 2016年5月27日 下午2:43:41
	 * @param role
	 */
	@Modifying
	@Query("delete from Authc where menu = ?1")
	void deleteByMenu(Menu  menu);
}
