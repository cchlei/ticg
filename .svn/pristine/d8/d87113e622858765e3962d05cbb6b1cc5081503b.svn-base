package com.trgis.ticg.core.service.impl;

import java.util.ArrayList;
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
import com.trgis.ticg.core.dao.SubsystemDao;
import com.trgis.ticg.core.exception.MenuException;
import com.trgis.ticg.core.model.Authc;
import com.trgis.ticg.core.model.Menu;
import com.trgis.ticg.core.model.Subsystem;
import com.trgis.ticg.core.service.MenuService;
import com.trgis.ticg.core.util.BeanUtil;
/**
 * 菜单Service
 * 
 * @author Alice
 *
 * 2016年5月19日
 */
@Transactional
@Service
public class MenuServiceImpl implements MenuService {

	@Autowired
	private MenuDao menuDao;
	@Autowired
	private SubsystemDao subDao;
	@Autowired
	private RoleMenuDao roleMenuDao;
	@Autowired
	private AuthcDao authcDao;
	@Autowired
	private RoleAuthcDao roleAuthcDao;
	
	
	private static final Logger log = LoggerFactory.getLogger(MenuServiceImpl.class);

	@Override
	public void addMenu(Menu menu) throws MenuException {
		try {
			if (BeanUtil.isEmpty(menu)) {
				throw new MenuException("资源菜单信息为空!");
			}
			menuDao.save(menu);
			log.debug("资源菜单成功！");
		} catch (Exception e) {
			log.debug("资源菜单失败!"+e.getMessage());
			throw e;
		}
	}

	@Override
	public void delMenu(Long[] id) throws MenuException {
		try {
			if (BeanUtil.isEmpty(id)) {
				throw new MenuException("删除资源菜单id为空!");
			}
			//1.删除角色菜单的关联表
			Menu menu = menuDao.findOne(id[0]);
			//删除角色菜单
			roleMenuDao.deleteByMenu(menu);
			//删除角色操作
			List<Authc> authcs = authcDao.findByMenu(menu);
			roleAuthcDao.deleteByAuthcs(authcs);
			//删除操作
			authcDao.deleteByMenu(menu);
			//删除菜单
			menuDao.delete(id);
		} catch (Exception e) {
			e.printStackTrace();
			log.debug("删除资源菜单出错！"+e.getMessage());
			throw e;
		}
	}


	@Override
	public void editMenu(Menu menu) throws MenuException {
		try {
			if (BeanUtil.isEmpty(menu.getId())) {
				throw new MenuException("资源菜单信息为空!");
			}
			menuDao.saveAndFlush(menu);
			log.debug("修改资源菜单成功！"+menu.getId());
		} catch (Exception e) {
			log.debug("资源菜单失败!"+e.getMessage());
			throw e;
		}
	}

	@Override
	public Page<Menu> findByConditions(ConditionGroup group, int pageNum, int pageSize, OrderBy... order)
			throws MenuException {
		try {
			if (BeanUtil.isEmpty(group)) {
				throw new MenuException("菜单查询条件为空!");
			}
			Specification<Menu> specifications = DynamicSpecficationUtil.buildSpecfication(group);
			long count = menuDao.count(specifications);
			if (count == 0) {
				return null;
			}
			PageRequest page = PageAndSortUtil.buildPageAndSort(count, pageNum, pageSize, order);
			log.debug("菜单查询成功！");
			return menuDao.findAll(specifications, page);
		} catch (Exception e) {
			log.debug("菜单查询失败!"+e.getMessage());
			throw e;
		}
	}
	
	@Override
	public Menu findOne(Long id) throws MenuException {
		try {
			return menuDao.findOne(id);
		} catch (Exception e) {
			log.debug(id+"id菜单查询失败!"+e.getMessage());
			throw e;
		}
	}

	@Override
	public List<Menu> findMenuByNameOrCode(Long sysid, String name, String value) throws MenuException {
		List<Menu> list = new ArrayList<Menu>();
		 Subsystem sb =subDao.findOne(sysid);
		if(!BeanUtil.isEmpty(name)&& name.equals("name")){
			list  =menuDao.findByNameAndSubsystem(value,sb);
        }
        if(!BeanUtil.isEmpty(name)&& name.equals("code")){
        	list=  menuDao.findByCodeAndSubsystem(value,sb);
        }
		return list;
	}
	
	@Override
	public List<Menu> findBySystem(Long sysid) throws MenuException {
		try {
			Subsystem subsystem = new Subsystem();
			subsystem.setId(sysid);
			return menuDao.findBySubsystem(subsystem);
		} catch (Exception e) {
			log.debug("菜单查询失败!"+e.getMessage());
			throw e;
		}
	}
}
