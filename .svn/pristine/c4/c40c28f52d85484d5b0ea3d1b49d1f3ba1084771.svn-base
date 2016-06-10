package com.trgis.ticg.core.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.trgis.common.jpa.specfication.ConditionGroup;
import com.trgis.common.jpa.specfication.OrderBy;
import com.trgis.ticg.core.exception.MenuException;
import com.trgis.ticg.core.model.Menu;
/**
 * 菜单service
 * 
 * @author Alice
 *
 * 2016年5月19日
 */
public interface MenuService {
	/**
	 * 根据ID查找
	 * @param id
	 * @throws MenuException
	 */
	Menu findOne(Long id) throws MenuException;
	/**
	 * @Description: 增加菜单资源
	 * @author yanpeng
	 * @date 2016年5月18日 下午4:37:15
	 * @param menu 菜单资源信息
	 */
	void addMenu(Menu menu) throws MenuException;
	/**
	 * @Description: 删除菜单资源
	 * @author yanpeng
	 * @date 2016年5月18日 下午4:37:53
	 * @param ids 菜单资源id
	 */
	void delMenu(Long[] ids) throws MenuException;
	/**
	 * @Description: 修改菜单资源
	 * @author yanpeng
	 * @date 2016年5月18日 下午4:38:09
	 * @param resource 修改后的菜单资源信息
	 */
	void editMenu(Menu menu) throws MenuException;
	
	/**
	 * @Description: 校验菜单名称和code
	 * @author liuyan
	 * @date 2016年5月18日 下午4:38:09
	 * @param resource 修改后的菜单资源信息
	 */
	List<Menu> findMenuByNameOrCode(Long sysid,String name,String value) throws MenuException;
	

	/**
	 * @Description: 分页查询菜单资源列表
	 * @author yanpeng
	 * @date 2016年5月18日 下午4:48:23
	 * @param conditionGroup  过滤条件
	 * @param pageNum
	 * @param pageSize
	 * @param order 排序
	 * @return
	 */
	public Page<Menu> findByConditions(ConditionGroup conditionGroup, int pageNum, int pageSize,
			OrderBy... order) throws  MenuException;
	
	/**
	 * @Description: TODO
	 * @author yanpeng
	 * @date 2016年5月23日 下午7:09:11
	 * @return
	 * @throws MenuException
	 */
	List<Menu> findBySystem(Long sysid) throws  MenuException;
}
