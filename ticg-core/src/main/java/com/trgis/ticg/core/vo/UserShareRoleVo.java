package com.trgis.ticg.core.vo;

import java.io.Serializable;

import com.trgis.ticg.core.model.ShareRole;
import com.trgis.ticg.core.model.User;

/**
 * 
 * @author majl
 * @Description 用户共享角色 VO
 * @data 2016年5月27日
 */
public class UserShareRoleVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
 
	private User user;
	
	private Boolean isCheck;
	
	private ShareRole shareRole;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Boolean getIsCheck() {
		return isCheck;
	}

	public void setIsCheck(Boolean isCheck) {
		this.isCheck = isCheck;
	}

	public ShareRole getShareRole() {
		return shareRole;
	}

	public void setShareRole(ShareRole shareRole) {
		this.shareRole = shareRole;
	}
	
	
}
