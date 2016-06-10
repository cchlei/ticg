package com.trgis.ticg.system.core.realm;

import java.util.List;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.trgis.ticg.system.core.model.SystemMenu;
import com.trgis.ticg.system.core.model.SystemUser;
import com.trgis.ticg.system.core.service.SystemMenuService;
import com.trgis.ticg.system.core.service.SystemUserService;
import com.trgis.ticg.system.core.util.QTManager;

/**
 * 实现系统超级管理员认证的realm
 * 
 * @author Alice
 *
 */
@Component
public class SystemUserRealm extends AuthorizingRealm {

	@Autowired
	private SystemUserService systemUserService;
	
	@Autowired
	private SystemMenuService systemMenuService;
	
	/**
	 * 为当前登录用户授权
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		QTManager manager = (QTManager) super.getAvailablePrincipal(principals);// 获取用户
		SystemUser systemuser = systemUserService.findSystemUserByUsername(manager.getUsername());
		SimpleAuthorizationInfo simpleAuthorInfo = new SimpleAuthorizationInfo();
		simpleAuthorInfo.addRole("SYSTEM_MANAGER"); //系统管理员
		//添加权限
		List<SystemMenu> mList = systemMenuService.getPermissionsByRoleid(systemuser.getSysrole().getId());
        for (SystemMenu menu : mList) {
        	simpleAuthorInfo.addStringPermission(menu.getMenuname());
        }
		return simpleAuthorInfo;
	}

	/**
	 * 验证当前登陆的subject
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken)
			throws AuthenticationException {
		UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
		SystemUser systemUser = systemUserService.findSystemUserByUsername(token.getUsername());
		if (systemUser != null) {
			QTManager shiroUser = new QTManager(systemUser.getId(),systemUser.getUsername(),systemUser.getSalt());
			return new SimpleAuthenticationInfo(shiroUser,systemUser.getPassword(),systemUser.getUsername());
		} else {
			return null;
		}
	}

}
