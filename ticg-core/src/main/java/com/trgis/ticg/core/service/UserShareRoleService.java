package com.trgis.ticg.core.service;

import java.util.List;

import com.trgis.ticg.core.model.User;
import com.trgis.ticg.core.model.UserShareRole;

/**
 * 
 * @author majl
 * @Description 用户共享角色关系 
 * @data 2016年5月27日
 */
public interface UserShareRoleService {

	List<UserShareRole> findAllUserShareRoleByUser(User user);
	
	void registUserRole(User user,List<UserShareRole> userShareRoles);
	
	void removeUserRole(UserShareRole userShareRole);
	
}
