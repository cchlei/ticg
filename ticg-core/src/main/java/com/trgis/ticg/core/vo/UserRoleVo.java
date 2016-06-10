package com.trgis.ticg.core.vo;

import java.io.Serializable;

import com.trgis.ticg.core.model.Role;
import com.trgis.ticg.core.model.User;

/**
 * 
 * @author majl
 * @Description 用户角色VO
 * @data 2016年5月25日
 */
public class UserRoleVo implements Serializable {
    
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private User user;
	
	private Role role;
	
	private Boolean isCheck;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Boolean getIsCheck() {
		return isCheck;
	}

	public void setIsCheck(Boolean isCheck) {
		this.isCheck = isCheck;
	}
	
	
	
}
