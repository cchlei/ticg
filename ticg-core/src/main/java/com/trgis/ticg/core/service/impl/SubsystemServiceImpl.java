package com.trgis.ticg.core.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.trgis.common.jpa.specfication.ConditionGroup;
import com.trgis.common.jpa.specfication.DynamicSpecficationUtil;
import com.trgis.common.jpa.specfication.OrderBy;
import com.trgis.common.jpa.specfication.PageAndSortUtil;
import com.trgis.ticg.core.dao.AuthcDao;
import com.trgis.ticg.core.dao.MenuDao;
import com.trgis.ticg.core.dao.RoleAuthcDao;
import com.trgis.ticg.core.dao.RoleMenuDao;
import com.trgis.ticg.core.dao.RoleSubsystemDao;
import com.trgis.ticg.core.dao.SubsystemDao;
import com.trgis.ticg.core.dao.SubsystemRegisterDao;
import com.trgis.ticg.core.model.Authc;
import com.trgis.ticg.core.model.Menu;
import com.trgis.ticg.core.model.SubSystemRegister;
import com.trgis.ticg.core.model.Subsystem;
import com.trgis.ticg.core.service.SubsystemService;
import com.trgis.ticg.core.util.BeanUtil;

/**
 * 应用子系统service
 * @author Alice
 *
 * 2016年5月17日
 */
@Transactional
@Service
public class SubsystemServiceImpl implements SubsystemService {

	private static final Logger logger = LoggerFactory.getLogger(SubsystemServiceImpl.class);
	
	@Autowired
	private SubsystemDao subsystemDao;
	@Autowired
	private SubsystemRegisterDao  subsystemRegisterDao;
    @Autowired
	private RoleSubsystemDao roleSubsystemDao;
    @Autowired
    private MenuDao menuDao;
    @Autowired
    private RoleMenuDao roleMenuDao;
    @Autowired
    private RoleAuthcDao roleAuthcDao;
    @Autowired
    private AuthcDao authcDao;
    
	
	/**
	 * 查询全部
	 */
	@Override
	public List<Subsystem> findAll() {
		return subsystemDao.findAll();
	}
	
	/**
	 * 新增
	 */
	@Override
	public Subsystem add(Subsystem Subsystem) {
		try {
			logger.debug("添加子系统start");
			return subsystemDao.save(Subsystem);
		} catch (Exception e) {
			logger.debug("添加子系统失败"+e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 根据条件查询所有结果
	 * 
	 * @param conditionGroup
	 * @return
	 */
	@Override
	public Page<Subsystem> findByConditions(ConditionGroup conditionGroup, int pageNum, int pageSize, OrderBy... order) {
		Specification<Subsystem> specifications = DynamicSpecficationUtil.buildSpecfication(conditionGroup);
		long count = subsystemDao.count(specifications);
		if (count == 0) {
			return null;
		}
		PageRequest page = PageAndSortUtil.buildPageAndSort(count, pageNum, pageSize, order);
		return subsystemDao.findAll(specifications, page);
	}

	@Override
	public Subsystem edit(Subsystem subsystem) {
		try {
			logger.debug("修改子系统开始");
			return subsystemDao.saveAndFlush(subsystem);
		} catch (Exception e) {
			logger.debug("修改子系统失败"+e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
	@Override
	public void del(Long id) {
		try {
			Subsystem subsystem =subsystemDao.findOne(id);
			logger.debug("删除子系统开始");
			if(BeanUtil.isNotEmpty(subsystem)&& subsystem!=null){
				//1.根据子系统查找注册子系统表    ****单独测试通过  测试地址 RoleSubsystemServiceTest.java
			    List<SubSystemRegister> sysReg = subsystemRegisterDao.findBySubsystem(subsystem);
				//2.根据注册子系统删除角色注册子系统表
				roleSubsystemDao.deleteBySsRegister(sysReg);
			    //3、根据子系统删除注册子系统的表 //单独测试通过
				subsystemRegisterDao.deleteBySubsystem(subsystem);
				//4.删除菜单关联的表
			    List<Menu> menus = menuDao.findBySubsystem(subsystem);
			    for(int i=0;i<menus.size();i++){
			    	Menu mu= menus.get(i);
			    	if(BeanUtil.isNotEmpty(mu) && mu!=null){
			    		//删除角色菜单
						roleMenuDao.deleteByMenu(mu);
						//删除角色操作
						List<Authc> authcs = authcDao.findByMenu(mu);
						if(authcs.size()>0){
							roleAuthcDao.deleteByAuthcs(authcs);
						}
						//删除操作
						authcDao.deleteByMenu(mu);
			    	}
			    	
			    }
				//5.根据子系统删除菜单的表 
				menuDao.deleteBySubsystem(subsystem);//单独测试通过
				logger.debug("根据子系统删除菜单的表 成功！");
			 }
			//5.根据子系统删除子系统自己的表    **单独测试通过
			subsystemDao.delete(id);
		} catch (Exception e) {
			logger.debug("删除子系统失败"+e.getMessage());
			e.printStackTrace();
		}
		
	}

	@Override
	public Subsystem findOne(Long id) {
		try {
			logger.debug("查找子系统开始");
			return	subsystemDao.findOne(id);
		} catch (Exception e) {
			logger.debug("查找子系统失败"+e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Subsystem findSysByNameOrCode(String name, String value) {
		   Subsystem sub =null;
		   if(!BeanUtil.isEmpty(name)&& name.equals("name")){
			    sub = subsystemDao.findByName(value);
	        }
	        if(!BeanUtil.isEmpty(name)&& name.equals("code")){
	        	sub =  subsystemDao.findByCode(value);
	        }
		   return sub;
	}

	@Override
	public List<Subsystem> findByOrg(Long oid) {
		//TODO 子系统注册表dao没有
		return null;
	}
}
