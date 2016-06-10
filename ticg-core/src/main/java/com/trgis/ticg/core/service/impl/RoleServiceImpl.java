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
import com.trgis.ticg.core.dao.RoleAuthcDao;
import com.trgis.ticg.core.dao.RoleDao;
import com.trgis.ticg.core.dao.RoleMenuDao;
import com.trgis.ticg.core.dao.RoleSubsystemDao;
import com.trgis.ticg.core.dao.SubsystemRegisterDao;
import com.trgis.ticg.core.dao.UserRoleDao;
import com.trgis.ticg.core.exception.RoleException;
import com.trgis.ticg.core.model.Authc;
import com.trgis.ticg.core.model.Menu;
import com.trgis.ticg.core.model.Organization;
import com.trgis.ticg.core.model.Role;
import com.trgis.ticg.core.model.RoleAuthc;
import com.trgis.ticg.core.model.RoleMenu;
import com.trgis.ticg.core.model.RoleSubsystem;
import com.trgis.ticg.core.model.SubSystemRegister;
import com.trgis.ticg.core.model.Subsystem;
import com.trgis.ticg.core.service.RoleService;
import com.trgis.ticg.core.util.BeanUtil;

@Transactional
@Service
public class RoleServiceImpl implements RoleService {

	private static final Logger log = LoggerFactory.getLogger(RoleServiceImpl.class);

	@Autowired
	private RoleDao roleDao;
	@Autowired
	private RoleMenuDao roleMenuDao;
	@Autowired
	private RoleAuthcDao roleAuthcDao;
	@Autowired
	private UserRoleDao userRoleDao;
	
	@Override
	public Long addRole(Role role) throws RoleException {
		try {
			if (BeanUtil.isEmpty(role)) {
				throw new RoleException("角色信息为空!");
			}
			Role save = roleDao.save(role);
			log.debug("角色信息保存成功！");
			return save.getId();
		} catch (Exception e) {
			log.debug("角色信息保存失败!");
			throw e;
		}
	}


	@Override
	public void editRole(Role role) throws RoleException {
		try {
			if (BeanUtil.isEmpty(role)) {
				throw new RoleException("角色为空!");
			}
			roleDao.saveAndFlush(role);
			log.debug("角色修改成功！");
		} catch (Exception e) {
			log.debug("角色修改失败!");
			throw e;
		}
	}


	@Override
	public Page<Role> findByConditions(ConditionGroup group, int pageNum, int pageSize, OrderBy... order)
			throws RoleException {
		try {
			if (BeanUtil.isEmpty(group)) {
				throw new RoleException("查询条件为空!");
			}
			Specification<Role> specifications = DynamicSpecficationUtil.buildSpecfication(group);
			long count = roleDao.count(specifications);
			if (count == 0) {
				return null;
			}
			PageRequest page = PageAndSortUtil.buildPageAndSort(count, pageNum, pageSize, order);
			log.debug("我的好友列表查询成功！");
			return roleDao.findAll(specifications, page);
		} catch (Exception e) {
			log.debug("我的好友列表查询失败!"+e.getMessage());
			throw e;
		}
	}


	@Override
	public void del(Long id) throws RoleException {
		try {
			if (id==null) {
				throw new RoleException("角色id为空");
			}
			Role role = new Role();
			role.setId(id);
			roleMenuDao.deleteByRole(role);
			log.debug("删除角色菜单级联关系成功");
			roleAuthcDao.deleteByRole(role);
			log.debug("删除角色操作级联关系成功");
			roleSubsystemDao.deleteByRole(role);
			log.debug("删除角色子系统级联关系成功");
			//级联删除userrole关系
			userRoleDao.deleteByRole(role);
			log.debug("删除角色用户级联关系成功");
			roleDao.delete(id);
			log.debug("角色删除成功！");
		} catch (Exception e) {
			log.debug("角色删除失败!"+e.getMessage());
			throw e;
		}
	}


	@Override
	public List<Role> findByOrg(Long oid) throws RoleException {
		try {
			if (oid==null) {
				throw new RoleException("组织机构id为空");
			}
			Organization org =new Organization();
			org.setId(oid);
			log.debug("根据组织机构"+oid+"查询成功！");
			return roleDao.findByOrganization(org);
		} catch (Exception e) {
			log.debug("角色删除失败!"+e.getMessage());
			throw e;
		}
	}


	@Override
	public void del(Long[] ids)  throws RoleException {
		try {
			if (ids==null) {
				throw new RoleException("角色id为空");
			}
			roleMenuDao.deleteByRoles(ids);
			log.debug("删除角色菜单级联关系成功");
			roleAuthcDao.deleteByRoles(ids);
			log.debug("删除角色操作级联关系成功");
			roleSubsystemDao.deleteByRoles(ids);
			log.debug("删除角色子系统级联关系成功");
			//级联删除userrole关系
			userRoleDao.deleteByRoles(ids);
			log.debug("删除角色用户级联关系成功");
			roleDao.delete(ids);
			log.debug("批量删除成功！");
		} catch (Exception e) {
			log.debug("角色删除失败!"+e.getMessage());
			throw e;
		}
	}


	@Override
	public Role findOne(Long id) throws RoleException{
		try {
			if (id==null) {
				throw new RoleException("角色id为空");
			}
			log.debug(id+"角色查询成功！");
			return roleDao.findOne(id);
		} catch (Exception e) {
			log.debug("角色删除失败!"+e.getMessage());
			throw e;
		}
	}


	@Autowired
	private RoleSubsystemDao roleSubsystemDao;
	@Autowired
	private SubsystemRegisterDao subsystemRegisterDao;
	
	@Override
	public void addMenu(Long rid, String ids, Long subid,Long oid, String rolname) throws RoleException {
		try {
			//*********************维护角色
			Role role =null;
			Organization organization = new Organization(oid);
			if(rid==null){
				Role roles = new Role();
				roles.setRolename(rolname);
				roles.setOrganization(organization);
				role = roleDao.save(roles);
			}else{
				role = roleDao.findOne(rid);
				if (!role.getRolename().equals(rolname)) {
					role.setRolename(rolname);
					roleDao.saveAndFlush(role);
				}
			}
			roleSubsystemDao.deleteByRole(role);//先删除然后保存
			roleMenuDao.deleteByRole(role);
			roleAuthcDao.deleteByRole(role);
			//********************维护应用子系统彪
			if(subid!=null){
				Subsystem subsystem = new Subsystem(subid);//当前子系统
				SubSystemRegister ssRegister = subsystemRegisterDao.findBySubsystemAndOrganization(subsystem,organization);//获得关系表数据
				RoleSubsystem roleSubsystem = new RoleSubsystem(role,ssRegister);
				roleSubsystemDao.save(roleSubsystem);
			}
			//********************维护角色菜单
			if (ids.trim().length()==0||ids==null) {
				return;
			}
			String[] split = ids.split("-");
			for (String string : split) {
				String[] split2 = string.split(",");
				Menu menu = new Menu();
				menu.setId(Long.valueOf(split2[0]));
				RoleMenu roleMenu = new RoleMenu();
				roleMenu.setMenu(menu);
				roleMenu.setRole(role);
				roleMenuDao.save(roleMenu);
				//********************维护角色操作
				if(split2.length>1){
					for (int i = 1; i < split2.length; i++) {
						Authc authc = new Authc();
						authc.setId(Long.valueOf(split2[i]));
						RoleAuthc roleAuthc = new RoleAuthc();
						roleAuthc.setRole(role);
						roleAuthc.setAuthc(authc);
						roleAuthcDao.save(roleAuthc);
					}
				}
			}
			log.debug("分配菜单成功！");
		} catch (Exception e) {
			log.debug("分配菜单失败!"+e.getMessage());
			throw e;
		}
	}


	@Override
	public void saveAndFlush(Role temp) throws RoleException{
		try {
			roleDao.saveAndFlush(temp);
		} catch (Exception e) {
			throw e;
		}
	}
}
