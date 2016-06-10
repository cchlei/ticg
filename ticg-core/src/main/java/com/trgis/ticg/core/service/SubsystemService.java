package com.trgis.ticg.core.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.trgis.common.jpa.specfication.ConditionGroup;
import com.trgis.common.jpa.specfication.OrderBy;
import com.trgis.ticg.core.model.Subsystem;

/**
 * 应用子系统service
 * @author Alice
 *
 * 2016年5月17日
 */
public interface SubsystemService {
	
/**
 * 查询全部
 * @return
 */
public List<Subsystem> findAll();

/**
 * 新增
 * @Author liuyan 
 * @param Subsystem
 * @return
 */
public	Subsystem add(Subsystem Subsystem);
/**
 * 修改
 * @Author liuyan 
 * @param Subsystem
 * @return
 */
public	Subsystem edit(Subsystem Subsystem);

/**
 * @Description:删除一个或者多个子系统
 * @Author liuyan 
 * @Date 2016年5月19日 上午11:00:24
 * @param list
 */
public	void del(Long list);
/**
 * @Description:根据id查询
 * @Author liuyan 
 * @Date 2016年5月19日 上午11:00:24
 * @param list
 */
public Subsystem findOne(Long id);
/**
 * 根据条件查询所有结果
 * 
 * @param conditionGroup
 * @return
 */
public Page<Subsystem> findByConditions(ConditionGroup conditionGroup, int pageNum, int pageSize, OrderBy... order);
/**
 * @Description:校验name和code
 * @Author liuyan 
 * @Date 2016年5月24日 下午5:38:09
 * @param sid
 * @param name
 * @param value
 * @return
 */
public Subsystem findSysByNameOrCode(String name, String value);

/**
 * @Description: 
 * @author yanpeng
 * @date 2016年5月25日 下午4:25:37
 * @param oid
 * @return
 */
public List<Subsystem> findByOrg(Long oid);

}

