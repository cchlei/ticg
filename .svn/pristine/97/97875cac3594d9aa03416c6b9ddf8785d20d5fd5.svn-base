package com.trgis.ticg.system.core.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.trgis.common.jpa.specfication.ConditionGroup;
import com.trgis.common.jpa.specfication.OrderBy;
import com.trgis.ticg.core.exception.AuthcException;
import com.trgis.ticg.core.model.Organization;
import com.trgis.ticg.system.core.model.SystemRole;
import com.trgis.ticg.system.core.model.SystemUser;

/**
 * 系统管理员服务接口
 * @author zhangqian
 *
 */
public interface SystemUserService {
	
	void create(SystemUser systemUser);
	
	void edit(SystemUser systemUser);
	
	void delete(Long id);
	
	void resetPwd(SystemUser systemUser);
	
	void updateRolename(String rolename, SystemRole systemrole);
	
	void updateOrgname(String orgname, Organization org); 
	
	SystemUser find(Long id);

	SystemUser findSystemUserByUsername(String username);
	
	List<SystemUser> findListByRole(Long roleid);
	/**
	 * 分页查询
	 * @param conditionGroup
	 * @param pageNum
	 * @param pageSize
	 * @param order
	 * @return
	 * @throws AuthcException
	 */
	public Page<SystemUser> findByConditions(ConditionGroup conditionGroup, int pageNum, int pageSize,
			OrderBy... order);

	void changePwd(SystemUser systemUser, String newPwd);
}
