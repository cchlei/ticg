package com.trgis.ticg.core.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
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
import com.trgis.ticg.core.dao.ShareRoleAuthcDao;
import com.trgis.ticg.core.dao.ShareRoleDao;
import com.trgis.ticg.core.dao.ShareRoleMenuDao;
import com.trgis.ticg.core.dao.ShareRoleOrgDao;
import com.trgis.ticg.core.dao.ShareRoleSubsystemDao;
import com.trgis.ticg.core.dao.SubsystemRegisterDao;
import com.trgis.ticg.core.dao.UserShareRoleDao;
import com.trgis.ticg.core.exception.ShareRoleException;
import com.trgis.ticg.core.model.Authc;
import com.trgis.ticg.core.model.Menu;
import com.trgis.ticg.core.model.Organization;
import com.trgis.ticg.core.model.ShareRole;
import com.trgis.ticg.core.model.ShareRoleAuthc;
import com.trgis.ticg.core.model.ShareRoleMenu;
import com.trgis.ticg.core.model.ShareRoleOrg;
import com.trgis.ticg.core.model.ShareRoleSubsystem;
import com.trgis.ticg.core.model.SubSystemRegister;
import com.trgis.ticg.core.model.Subsystem;
import com.trgis.ticg.core.service.ShareRoleService;
import com.trgis.ticg.core.util.BeanUtil;
/**
 * 共享共享角色
 * 
 * @author Alice
 *
 * 2016年5月26日
 */
@Service
@Transactional
public class ShareRoleServiceImpl implements ShareRoleService {

	private static final Logger log = LoggerFactory.getLogger(ShareRoleServiceImpl.class);

	@Autowired
	private ShareRoleDao shareRoleDao;
	@Autowired
	private ShareRoleMenuDao shareRoleMenuDao;
	@Autowired
	private ShareRoleAuthcDao shareRoleAuthcDao;
	@Autowired
	private ShareRoleOrgDao shareRoleOrgDao;
	@Autowired
	private SubsystemRegisterDao subsystemRegisterDao;
	@Autowired
	private ShareRoleSubsystemDao shareRoleSubsystemDao;
	@Autowired
	private UserShareRoleDao userShareRoleDao;
	
	@Override
	public void edit(ShareRole ShareRole) throws ShareRoleException {
		try {
			if (BeanUtil.isEmpty(ShareRole)) {
				throw new ShareRoleException("共享角色为空!");
			}
			shareRoleDao.saveAndFlush(ShareRole);
			log.debug("共享角色修改成功！");
		} catch (Exception e) {
			log.debug("共享角色修改失败!");
			throw e;
		}
	}


	@Override
	public Page<ShareRole> findByConditions(ConditionGroup group, int pageNum, int pageSize, OrderBy... order)
			throws ShareRoleException {
		try {
			if (BeanUtil.isEmpty(group)) {
				throw new ShareRoleException("查询条件为空!");
			}
			Specification<ShareRole> specifications = DynamicSpecficationUtil.buildSpecfication(group);
			long count = shareRoleDao.count(specifications);
			if (count == 0) {
				return null;
			}
			PageRequest page = PageAndSortUtil.buildPageAndSort(count, pageNum, pageSize, order);
			log.debug("共享角色列表查询成功！");
			return shareRoleDao.findAll(specifications, page);
		} catch (Exception e) {
			log.debug("共享角色列表查询失败!"+e.getMessage());
			throw e;
		}
	}


	@Override
	public void del(Long id) throws ShareRoleException {
		try {
			if (id==null) {
				throw new ShareRoleException("共享角色id为空");
			}
			ShareRole shareRole = new ShareRole();
			shareRole.setId(id);
			shareRoleMenuDao.deleteByShareRole(shareRole);
			log.debug("删除共享角色菜单级联关系成功");
			shareRoleAuthcDao.deleteByShareRole(shareRole);
			log.debug("删除共享角色操作级联关系成功");
			shareRoleSubsystemDao.deleteByShareRole(shareRole);
			log.debug("删除共享角色子系统级联关系成功");
			List<ShareRoleOrg> srolist = shareRoleOrgDao.findBySharerole(shareRole);
			for (ShareRoleOrg sro : srolist) {
				userShareRoleDao.deleteByShareRoleOrg(sro);
			}
			log.debug("删除用户共享角色组织机构级联关系成功");
			shareRoleOrgDao.deleteByShareRole(shareRole);
			log.debug("删除共享角色组织机构级联关系成功");
			shareRoleDao.delete(id);
			log.debug("共享角色删除成功！");
		} catch (Exception e) {
			log.debug("共享角色删除失败!"+e.getMessage());
			throw e;
		}
	}


	@Override
	public List<ShareRole> findByOrg(Long oid) throws ShareRoleException {
		try {
			if (oid==null) {
				throw new ShareRoleException("组织机构id为空");
			}
			Organization org =new Organization();
			org.setId(oid);
			log.debug("根据组织机构"+oid+"查询成功！");
			return shareRoleDao.findByOrganization(org);
		} catch (Exception e) {
			log.debug("共享角色删除失败!"+e.getMessage());
			throw e;
		}
	}


	@Override
	public void del(Long[] ids)  throws ShareRoleException {
		try {
			if (ids==null) {
				throw new ShareRoleException("共享角色id为空");
			}
			shareRoleMenuDao.deleteByShareRoles(ids);
			log.debug("删除共享角色菜单级联关系成功");
			shareRoleAuthcDao.deleteByShareRoles(ids);
			log.debug("删除共享角色操作级联关系成功");
			shareRoleSubsystemDao.deleteByShareRoles(ids);
			log.debug("删除共享角色子系统级联关系成功");
			List<ShareRoleOrg> srolist = shareRoleOrgDao.findByShareroleIds(ids);
			for (ShareRoleOrg sro : srolist) {
				userShareRoleDao.deleteByShareRoleOrg(sro);
			}
			log.debug("删除用户共享角色组织机构级联关系成功");
			shareRoleOrgDao.deleteByShareRoles(ids);
			log.debug("删除共享角色组织机构级联关系成功");
			shareRoleDao.delete(ids);
			log.debug("批量删除成功！");
		} catch (Exception e) {
			log.debug("共享角色删除失败!"+e.getMessage());
			throw e;
		}
	}


	@Override
	public ShareRole findOne(Long id) throws ShareRoleException{
		try {
			if (id==null) {
				throw new ShareRoleException("共享角色id为空");
			}
			log.debug(id+"共享角色查询成功！");
			return shareRoleDao.findOne(id);
		} catch (Exception e) {
			log.debug("共享角色删除失败!"+e.getMessage());
			throw e;
		}
	}
	
	@Override
	public void saveShareRole(ShareRole sharerole,String menus,String authcs, String subids, Long oid, String oids) throws ShareRoleException {
		try {
			//1保存角色
			shareRoleDao.save(sharerole);
			//2维护共享组织机构
			List<String> orgids = Arrays.asList(oids.split(","));//新共享组织机构id集合
			List<ShareRoleOrg> srolist = shareRoleOrgDao.findBySharerole(sharerole);//原共享组织机构id集合
			List<String> oldorgids = new ArrayList<String>();
			//处理移除对象
			for (int i = 0; i < srolist.size(); i++) {
				oldorgids.add(srolist.get(i).getOrganization().getId().toString());
				if(!orgids.contains(srolist.get(i).getOrganization().getId().toString())){//需要移除
					List<ShareRoleOrg> oldsrolist = shareRoleOrgDao.findByShareroleAndOrganization(sharerole, srolist.get(i).getOrganization());
					for (ShareRoleOrg sro : oldsrolist) {
						userShareRoleDao.deleteByShareRoleOrg(sro);
						shareRoleOrgDao.delete(sro);
					}
				}
			}
			//处理新增对象
			for (int i = 0; i < orgids.size(); i++) {
				if(!oldorgids.contains(orgids.get(i))){//不包含
					ShareRoleOrg sro = new ShareRoleOrg();
					Organization organization = new Organization();
					organization.setId(Long.parseLong(orgids.get(i)));
					sro.setOrganization(organization);
					sro.setSharerole(sharerole);
					shareRoleOrgDao.save(sro);
				}
			}
			//3维护应用子系统
			shareRoleSubsystemDao.deleteByShareRole(sharerole);//先删除然后保存
			String[] subsystemids = subids.split(",");
			Organization organization = new Organization(oid);
			for (String s : subsystemids) {
				Subsystem subsystem = new Subsystem(Long.parseLong(s));//当前子系统
				SubSystemRegister ssRegister = subsystemRegisterDao.findBySubsystemAndOrganization(subsystem,organization);//获得关系表数据
				ShareRoleSubsystem shareRoleSubsystem = new ShareRoleSubsystem(sharerole,ssRegister);
				shareRoleSubsystemDao.save(shareRoleSubsystem);
			}
			//4维护菜单
			shareRoleMenuDao.deleteByShareRole(sharerole);
			String[] menuids = menus.split(",");
			if(menuids.length > 0 && !"".equals(menuids[0])){
				for (int i = 0; i < menuids.length; i++) {
					Menu menu = new Menu();
					menu.setId(Long.parseLong(menuids[i]));
					ShareRoleMenu shareRoleMenu = new ShareRoleMenu();
					shareRoleMenu.setMenu(menu);
					shareRoleMenu.setSharerole(sharerole);
					shareRoleMenuDao.save(shareRoleMenu);
				}
			}
			//5维护操作
			shareRoleAuthcDao.deleteByShareRole(sharerole);
			String[] authcids = authcs.split(",");
			if(authcids.length > 0 && !"".equals(authcids[0])){
				for (int i = 0; i < authcids.length; i++) {
					Authc authc = new Authc();
					authc.setId(Long.parseLong(authcids[i]));
					ShareRoleAuthc shareRoleAuthc = new ShareRoleAuthc();
					shareRoleAuthc.setSharerole(sharerole);
					shareRoleAuthc.setAuthc(authc);
					shareRoleAuthcDao.save(shareRoleAuthc);
				}
			}
			log.debug("共享角色创建成功！");
		} catch (Exception e) {
			log.debug("共享角色创建失败!"+e.getMessage());
			throw e;
		}
	}


	@Override
	public ShareRole findByRolename(String rolename) {
		List<ShareRole> rolelist = shareRoleDao.findByRolename(rolename); 
		if(rolelist != null & rolelist.size() > 0) {
			return rolelist.get(0);
		}else{
			return null;
		}
	}
}
