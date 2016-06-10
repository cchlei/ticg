package com.trgis.ticg.core.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import com.trgis.ticg.core.dao.ShareRoleAuthcDao;
import com.trgis.ticg.core.exception.AuthcException;
import com.trgis.ticg.core.model.Authc;
import com.trgis.ticg.core.model.Menu;
import com.trgis.ticg.core.model.Role;
import com.trgis.ticg.core.model.RoleAuthc;
import com.trgis.ticg.core.model.ShareRole;
import com.trgis.ticg.core.model.ShareRoleAuthc;
import com.trgis.ticg.core.service.AuthcService;
import com.trgis.ticg.core.util.BeanUtil;
/**
 * 操作Service
 * 
 * @author Alice
 *
 * 2016年5月19日
 */
@Transactional
@Service
public class AuthcServiceImpl implements AuthcService {

	@Autowired
	private AuthcDao authcDao;
	@Autowired
	private MenuDao muDao;
	@Autowired
	private RoleAuthcDao roleAuthcDao;
	@Autowired
	private ShareRoleAuthcDao shareRoleAuthcDao;
	
	private static final Logger logger = LoggerFactory.getLogger(AuthcServiceImpl.class);

	@Override
	public Authc add(Authc authc) throws AuthcException{
		try {
			logger.debug("添加操作start");
			authcDao.save(authc);
		} catch (Exception e) {
			logger.debug("添加操作失败"+e.getMessage());
			throw e;
		}
		return authc;
		
	}

	
		@Override
		public Authc edit(Authc authc) {
			try {
				logger.debug("修改操作start");
				authcDao.saveAndFlush(authc);
			} catch (Exception e) {
				logger.debug("修改操作失败"+e.getMessage());
				throw e;
			}
			return authc;
		}
	
	
		@Override
		public void del(Long id) {
			try {
				logger.debug("删除操作start");
				//1、级联删除角色操作表
			    Authc authc = 	authcDao.findOne(id);
			    try {
			    	if(BeanUtil.isNotEmpty(authc)&& authc!=null){
			    		roleAuthcDao.deleteByAuthc(authc);
			    	}
				} catch (Exception e) {
					logger.debug("级联删除角色操作表失败"+e.getMessage());
				}
			   //2、级删除操作表自己
				authcDao.delete(id);
				
				/*Menu menu =new Menu();
				menu.setId(id);
				authcDao.deleteByMenu(menu);*/
			} catch (Exception e) {
				logger.debug("操作删除失败"+e.getMessage());
				throw e;
			}
		}

	
	@Override
	public Page<Authc> findByConditions(ConditionGroup group, int pageNum, int pageSize, OrderBy... order)
			throws AuthcException {
		try {
			if (BeanUtil.isEmpty(group)) {
				throw new AuthcException("查询条件为空!");
			}
			Specification<Authc> specifications = DynamicSpecficationUtil.buildSpecfication(group);
			long count = authcDao.count(specifications);
			if (count == 0) {
				return null;
			}
			PageRequest page = PageAndSortUtil.buildPageAndSort(count, pageNum, pageSize, order);
			logger.debug("操作列表查询成功！");
			return authcDao.findAll(specifications, page);
		} catch (Exception e) {
			logger.debug("操作列表查询失败!"+e.getMessage());
			throw e;
		}
		
	}

	@Override
	public List<Map<String, Object>> findByMenu(Long mid,Long rid) throws AuthcException {
		try {
			Menu menu = new Menu();
			menu.setId(mid);
			Set<Long> set = new HashSet<Long>();
			if(rid!=null){
				Role role = new Role();
				role.setId(rid);
				List<RoleAuthc> rolAus = roleAuthcDao.findByRole(role);
				for (RoleAuthc roleAuthc : rolAus) {
					set.add(roleAuthc.getAuthc().getId());
				}
			}
			List<Authc> authcs = authcDao.findByMenu(menu);
			List<Map<String,Object>> rows = new ArrayList<Map<String,Object>>();
			Map<String,Object> map = null;
			for (Authc authc : authcs) {
				map =new HashMap<String,Object>();
				map.put("id", authc.getId());
				map.put("name", authc.getName());
				if (set.contains(authc.getId())) {
					map.put("checked", true);
				}else{
					map.put("checked", false);
				}
				rows.add(map);
			}
			return rows;
		} catch (Exception e) {
			logger.debug("根据菜单查询操作失败"+e.getMessage());
			throw e;
		}
	}

	@Override
	public List<Authc> findAll() throws AuthcException {
		try {
			return authcDao.findAll();
		} catch (Exception e) {
			logger.debug("查询操作列表失败"+e.getMessage());
			throw e;
		}
	}

	@Override
	public List<Authc> findByNameOrCode(String muid,String name, String value) {
		List<Authc> list = new ArrayList<Authc>();
		 Menu mu = muDao.findOne(Long.parseLong(muid));
		 if(!BeanUtil.isEmpty(name)&& name.equals("name")){
			   list= authcDao.findByNameAndMenu(value,mu);
	        }
	        if(!BeanUtil.isEmpty(name)&& name.equals("code")){
	        	list= authcDao.findByCodeAndMenu(value,mu);
	        }
			return list;
	}


	@Override
	public Authc findOne(Long id) throws AuthcException {
		
		return authcDao.findOne(id);
	}
	
	@Override
	public List<Map<String, Object>> findByShareMenu(Long mid,Long srid) throws AuthcException {
		try {
			Menu menu = new Menu();
			menu.setId(mid);
			List<Authc> authcs = authcDao.findByMenu(menu);
			Set<Long> set = new HashSet<Long>();
			if(BeanUtil.isNotEmpty(srid)){
				ShareRole sharerole = new ShareRole();
				sharerole.setId(srid);
				List<ShareRoleAuthc> srolAus = shareRoleAuthcDao.findBySharerole(sharerole);
				for (ShareRoleAuthc sroleAuthc : srolAus) {
					set.add(sroleAuthc.getAuthc().getId());
				}
			}
			List<Map<String,Object>> rows = new ArrayList<Map<String,Object>>();
			Map<String,Object> map = null;
			for (Authc authc : authcs) {
				map =new HashMap<String,Object>();
				map.put("id", authc.getId());
				map.put("name", authc.getName());
				if (set.contains(authc.getId())) {
					map.put("ck", true);
				}else{
					map.put("ck", false);
				}
				rows.add(map);
			}
			return rows;
		} catch (Exception e) {
			logger.debug("根据菜单查询操作失败"+e.getMessage());
			throw e;
		}
	}
}
