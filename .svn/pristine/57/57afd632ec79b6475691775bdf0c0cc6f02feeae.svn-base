package com.trgis.ticg.system.core.service.impl;

import java.util.List;

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
import com.trgis.ticg.core.model.Organization;
import com.trgis.ticg.system.core.dao.SystemUserDao;
import com.trgis.ticg.system.core.model.SystemRole;
import com.trgis.ticg.system.core.model.SystemUser;
import com.trgis.ticg.system.core.service.SystemUserService;
import com.trgis.ticg.system.core.util.UserEncrypt;

/**
 * 系统管理员服务接口
 * 
 * @author Alice
 *
 */
@Service
@Transactional
public class SystemUserServiceImpl implements SystemUserService {

	@Autowired
	private SystemUserDao systemUserDao;

	@Override
	public SystemUser find(Long id) {
		return systemUserDao.findOne(id);
	}

	@Override
	public SystemUser findSystemUserByUsername(String username) {
		return systemUserDao.findByUsername(username);
	}

	@Override
	public void create(SystemUser systemUser) {
		//如果修改role 需要级联修改systemUser.rolename属性
		if(systemUser.getSysrole()!=null){
			systemUser.setRolename(systemUser.getSysrole().getRolename());
		}
		//如果修改organization 需要级联修改systemUser.organizationname属性
		if(systemUser.getOrganization()!=null){
			systemUser.setOrganizationname(systemUser.getOrganization().getOrgnazationName());
		}
		resetPwd(systemUser);
	}
	
	@Override
	public void edit(SystemUser systemUser) {
		//如果修改role 需要级联修改systemUser.rolename属性
		if(systemUser.getSysrole()!=null){
			systemUser.setRolename(systemUser.getSysrole().getRolename());
		}
		//如果修改organization 需要级联修改systemUser.organizationname属性
		if(systemUser.getOrganization()!=null){
			systemUser.setOrganizationname(systemUser.getOrganization().getOrgnazationName());
		}
		systemUserDao.save(systemUser);
	}
	/**
	 * 分页查询
	 */
	@Override
	public Page<SystemUser> findByConditions(ConditionGroup conditionGroup, int pageNum, int pageSize, OrderBy... order){
		Specification<SystemUser> specifications = DynamicSpecficationUtil.buildSpecfication(conditionGroup);
		long count = systemUserDao.count(specifications);
		if (count == 0) {
			return null;
		}
		PageRequest page = PageAndSortUtil.buildPageAndSort(count, pageNum, pageSize, order);
		return systemUserDao.findAll(specifications, page);
	}
	
	/**
	 * 重置密码
	 */
	@Override
	public void resetPwd(SystemUser systemUser) {
		systemUser.setSalt(UserEncrypt.generateSalt()); // 重置密码生成新salt
		systemUser.setPassword(UserEncrypt.md5hash("123456", systemUser.getSalt()));
		systemUserDao.saveAndFlush(systemUser);
	}
	
	/**
	 * 修改密码
	 */
	@Override
	public void changePwd(SystemUser systemUser, String newPwd) {
		systemUser.setSalt(UserEncrypt.generateSalt()); // 重置密码生成新salt
		systemUser.setPassword(UserEncrypt.md5hash(newPwd, systemUser.getSalt()));
		systemUserDao.saveAndFlush(systemUser);
	}
	
	/**
	 * 删除
	 */
	@Override
	public void delete(Long id) {
		systemUserDao.delete(id);
	}
	
	/**
	 * 更新角色名
	 */
	@Override
	public void updateRolename(String rolename, SystemRole sysrole) {
		systemUserDao.updateRolename(rolename, sysrole);
	}
	
	/**
	 * 更新组织机构名
	 */
	@Override
	public void updateOrgname(String orgname, Organization org) {
		systemUserDao.updateOrgname(orgname, org);
	}
	
	@Override
	public List<SystemUser> findListByRole(Long roleid) {
		return systemUserDao.findListByRole(roleid);
	}

}
