package com.trgis.ticg.core.realm;

import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cas.CasRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.trgis.ticg.core.model.User;
import com.trgis.ticg.core.service.UserService;

/**
 * 用户登录交给cas单点登录系统授权
 * 
 * @author yanpeng
 *
 */
public class UserCASRealm extends CasRealm{
	
	private static final Logger logger = LoggerFactory.getLogger(UserCASRealm.class);

	@Autowired
	private UserService userService;
	
	/**
	 * 这里只需要进行用户权限设置即可，登录验证部分由cas实现
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		logger.debug("已登录，开始授权");
        SimplePrincipalCollection principalCollection = (SimplePrincipalCollection) principals;
        String username = (String) principalCollection.getPrimaryPrincipal();
        User user = userService.findUserByUsername(username);
        logger.debug("查询用户在系统内的角色和权限信息");
        if(user!=null){
            //权限信息对象info,用来存放查出的用户的所有的角色（role）及权限（permission）
            SimpleAuthorizationInfo info=new SimpleAuthorizationInfo();
            //用户的角色集合
            //info.setRoles(user.getRolesName());
            //用户的角色对应的所有权限，如果只使用角色定义访问权限，下面的四行可以不要
//            List<Role> roleList=user.getRoleList();
//            for (Role role : roleList) {
//                info.addStringPermissions(role.getResourceName());
//            }
            
//          @Transient
//          public List<String> getResourceName() {
//              List<String> list = new ArrayList<String>();
//              List<Resource> perlist = getResources();
//              for (Resource per : perlist) {
//                  list.add(per.getResourceName());
//              }
//              return list;
//          }
            
            
            return info;
        }
        // 返回null的话，就会导致任何用户访问被拦截的请求时，都会自动跳转到unauthorizedUrl指定的地址
        return null;
	}

}
