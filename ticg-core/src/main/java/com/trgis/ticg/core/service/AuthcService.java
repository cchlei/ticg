package com.trgis.ticg.core.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;

import com.trgis.common.jpa.specfication.ConditionGroup;
import com.trgis.common.jpa.specfication.OrderBy;
import com.trgis.ticg.core.exception.AuthcException;
import com.trgis.ticg.core.exception.MenuException;
import com.trgis.ticg.core.model.Authc;
/**
 * 操作Service
 * 
 * @author Alice
 *
 * 2016年5月19日
 */
public interface AuthcService {

/**
 * 新增
 * @Author liuyan 
 * @param Authc
 * @return
 * @throws AuthcException 
 */
public	Authc add(Authc Authc) throws AuthcException;
/**
 * 修改
 * @Author liuyan 
 * @param Authc
 * @return
 */
public	Authc edit(Authc Authc);

/**
 * @Description:删除一个或者多个操作
 * @Author liuyan 
 * @Date 2016年5月19日 上午11:00:24
 * @param list
 */
public	void del(Long id);
	/**
	 * 分页
	 * @param conditionGroup
	 * @param pageNum
	 * @param pageSize
	 * @param order
	 * @return
	 * @throws MenuException
	 */
	public Page<Authc> findByConditions(ConditionGroup conditionGroup, int pageNum, int pageSize,
			OrderBy... order) throws AuthcException;
	/**
	 * @Description: 根据菜单查询操作列表
	 * @author yanpeng
	 * @date 2016年5月24日 上午9:38:07
	 * @param mid
	 * @return
	 */
	public List<Map<String, Object>> findByMenu(Long mid,Long rid) throws AuthcException;
	/**
	 * @author Alice
	 * @param mid
	 * @param srid
	 * @return
	 * @throws AuthcException
	 */
	public List<Map<String, Object>> findByShareMenu(Long mid,Long srid) throws AuthcException;
	/**
	 * @Description: 不分页查询所有的操作
	 * @author yanpeng
	 * @date 2016年5月24日 上午10:49:57
	 * @return
	 */
	public List<Authc> findAll() throws AuthcException;
	/**
	 * @Description:根据id查询操作
	 * @Author liuyan 
	 * @Date 2016年5月27日 上午10:31:15
	 * @return
	 * @throws AuthcException
	 */
	public Authc findOne(Long id) throws AuthcException;
	/**
	 * @Description:校验name和code
	 * @Author liuyan 
	 * @Date 2016年5月24日 下午5:38:09
	 * @param name
	 * @param value
	 * @return
	 */
	public List<Authc> findByNameOrCode(String muid,String name, String value);
}
