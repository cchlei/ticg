package com.trgis.ticg.core.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.trgis.common.jpa.specfication.ConditionGroup;
import com.trgis.common.jpa.specfication.OrderBy;
import com.trgis.ticg.core.exception.ShareRoleException;
import com.trgis.ticg.core.model.ShareRole;
/**
 * 共享角色service
 * 
 * @author Alice
 *
 * 2016年5月26日
 */
public interface ShareRoleService {
	/**
	 * @Description: 分页查询角色资源列表
	 * @param conditionGroup  过滤条件
	 * @param pageNum
	 * @param pageSize
	 * @param order 排序
	 * @return
	 * @throws ShareRoleException
	 */
	public Page<ShareRole> findByConditions(ConditionGroup conditionGroup, int pageNum, int pageSize,
			OrderBy... order) throws  ShareRoleException;
	/**
	 * @Description: 修改角色
	 * @author yanpeng
	 * @date 2016年5月17日 下午1:42:53
	 * @param role 修改后的角色信息
	 * @return
	 * @throws ShareRoleException
	 */
	void edit(ShareRole sharerole) throws ShareRoleException;
	
	/**
	 * @Description: delete
	 * @author yanpeng
	 * @date 2016年5月23日 下午6:52:37
	 * @param id
	 */
	void del(Long id) throws ShareRoleException;
	
	/**
	 * @Description: 根据组织机构id查询所有的角色
	 * @author yanpeng
	 * @date 2016年5月24日 下午2:34:04
	 * @param oid
	 * @return
	 */
	public List<ShareRole> findByOrg(Long oid) throws ShareRoleException ;
	
	/**
	 * @Description: 批量删除
	 * @author yanpeng
	 * @date 2016年5月25日 上午11:43:23
	 * @param ids
	 */
	public void del(Long[] ids) throws ShareRoleException;
	
	/**
	 * @Description: 根据角色id查询
	 * @author yanpeng
	 * @date 2016年5月25日 下午2:40:27
	 * @param id
	 * @return
	 */
	public ShareRole findOne(Long id) throws ShareRoleException;
	
	/**
	 * 根据名称查找
	 * @param rolename
	 * @return
	 */
	public ShareRole findByRolename(String rolename);
	
	/**
	 * 分配菜单
	 * @param rid		共享角色id
	 * @param menus		菜单id集合
	 * @param authcs	操作id集合
	 * @param subids	子系统id集合
	 * @param oid		创建组织机构id	
	 * @param oids		共享组织机构id集合
	 */
	public void saveShareRole(ShareRole sharerole,String menus, String authcs, String subids, Long oid, String oids) throws ShareRoleException;
}
