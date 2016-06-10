package com.trgis.ticg.core.service.impl;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trgis.ticg.core.dao.UserShareRoleDao;
import com.trgis.ticg.core.model.ShareRoleOrg;
import com.trgis.ticg.core.model.User;
import com.trgis.ticg.core.model.UserShareRole;
import com.trgis.ticg.core.service.ShareRoleOrgService;
import com.trgis.ticg.core.service.UserShareRoleService;

/**
 * 
 * @author majl
 * @Description 
 * @data 2016年5月27日
 */
@Service
public class UserShareRoleServiceImpl implements UserShareRoleService {
  
	@Autowired
	UserShareRoleDao userShareRoleDao;
	
	@Autowired
	ShareRoleOrgService shareRoleOrgService;

	private Logger logger = LoggerFactory.getLogger(UserShareRoleServiceImpl.class);
	
	@Override
	public List<UserShareRole> findAllUserShareRoleByUser(User user) {
		return	userShareRoleDao.findAllByUser(user);
	}

	@Override
	public void registUserRole(User user, List<UserShareRole> userShareRoles) {
			try{
				logger.debug("start UserShareRole find");
				List<UserShareRole> old = userShareRoleDao.findAllByUser(user);
				if(old!=null && old.size()>0){				
					userShareRoleDao.delete(old);
				}
				if(userShareRoles.get(0).getShareRoleOrg().getSharerole().getId()==-1){
					return;
				}
				List<ShareRoleOrg> shareRoleOrgs =shareRoleOrgService.findByOrg(user.getOrganization().getId());
				initUserShareRole(userShareRoles,shareRoleOrgs);
				userShareRoleDao.save(userShareRoles);
			}catch(Exception e){
				logger.debug("分配共享角色失败!"+e.getMessage());
				e.printStackTrace();
			}
	}
	
	/**
	 * 初始化用户共享角色关系表
	 * @param userShareRoles
	 * @param shareRoleOrgs
	 * @return
	 */
	private List<UserShareRole> initUserShareRole(List<UserShareRole> userShareRoles,List<ShareRoleOrg> shareRoleOrgs){
		 for(ShareRoleOrg shareRoleorg : shareRoleOrgs){
			 for(UserShareRole userShareRole : userShareRoles){
				 long shareroleid=shareRoleorg.getSharerole().getId();
				 long uShareRoleId= userShareRole.getShareRoleOrg().getSharerole().getId();
				 boolean flag = (shareroleid==uShareRoleId);
				 if(flag){
					 userShareRole.setShareRoleOrg(shareRoleorg);
				 }
			 }
		 }	
		return userShareRoles;
	}
	
	
	@Override
	public void removeUserRole(UserShareRole userShareRole) {
		userShareRoleDao.delete(userShareRole);
	}

}
