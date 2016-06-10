package com.trgis.ticg.system.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.trgis.ticg.core.model.ShareRole;
import com.trgis.ticg.core.model.ShareRoleOrg;
import com.trgis.ticg.core.model.User;
import com.trgis.ticg.core.model.UserShareRole;
import com.trgis.ticg.core.service.ShareRoleOrgService;
import com.trgis.ticg.core.service.UserService;
import com.trgis.ticg.core.service.UserShareRoleService;
import com.trgis.ticg.core.util.Result;
import com.trgis.ticg.core.vo.UserShareRoleVo;

/**
 * 
 * @author majl
 * @Description 用户共享角色
 * @data 2016年5月25日
 */
@Controller
@RequestMapping(value="/userShareRole")
public class UserShareRoleController {

	Logger logger = LoggerFactory.getLogger(UserShareRoleController.class);
		
	@Autowired
	UserShareRoleService userShareRoleService;
	
	@Autowired
	ShareRoleOrgService shareRoleOrgService;
	
	@Autowired
	UserService userService;
	
	@ResponseBody
	@RequestMapping(value="/find",method=RequestMethod.POST)
	public List<UserShareRoleVo> findAll(String userid,String orgid){
		logger.debug("findUserShareRole Begin!");
		List<ShareRoleOrg> shareRoleOrgs = new ArrayList<ShareRoleOrg>();
		List<UserShareRole> userShareRoles = new ArrayList<UserShareRole>();
		List<UserShareRoleVo> vos = null;
		User user = new User();
		try{
			if(StringUtils.isNotBlank(orgid)){
				shareRoleOrgs = shareRoleOrgService.findByOrg(Long.parseLong(orgid));
			}
			if(StringUtils.isNotBlank(userid)){
				user.setId(Long.parseLong(userid));
				userShareRoles = userShareRoleService.findAllUserShareRoleByUser(user);
			}
			vos = initUserShareRoleVos(shareRoleOrgs,userShareRoles,user);
		}catch(Exception e){
			logger.debug("findUserShareRole have error! error:"+e.getMessage());
		}
		logger.debug("findUserShareRole End!");
		return vos;
	}
	
	private List<UserShareRoleVo> initUserShareRoleVos(List<ShareRoleOrg> shareRoleOrgs,List<UserShareRole> userShareRoles,User user){
		List<UserShareRoleVo> userShareRoleVos = new ArrayList<UserShareRoleVo>();
		if(shareRoleOrgs!=null){
			for(ShareRoleOrg shareRoleOrg : shareRoleOrgs){
				UserShareRoleVo vo = new UserShareRoleVo();
				vo.setIsCheck(false);
				if(userShareRoles!=null){
					for(UserShareRole userShareRole : userShareRoles){
						long shareRoleOrgid = shareRoleOrg.getId();
						long uShareRoleOrgid = userShareRole.getShareRoleOrg().getId();
						if(shareRoleOrgid==uShareRoleOrgid){
							vo.setIsCheck(true);
							vo.setUser(user);
						}
					}
				}
				vo.setShareRole(shareRoleOrg.getSharerole());
				userShareRoleVos.add(vo);
			}
		}
		return userShareRoleVos;
	}
	
	@ResponseBody
	@RequestMapping(value="/sync",method=RequestMethod.POST)
	public Result sync(@RequestParam(value="roles[]")Long[]  roles,Long userid){
		Result result = new Result();
		try{
			List<UserShareRole> userRoles = null;
			User user = new User();
			user.setId(userid);
			user = userService.findUserByUser(user);
			if(roles!=null && roles.length>0){
				userRoles = new ArrayList<UserShareRole>();
				for(Long roleid : roles){
					ShareRole shareRole = new ShareRole();
					shareRole.setId(roleid);
					ShareRoleOrg shareRoleOrg = new ShareRoleOrg();
					shareRoleOrg.setSharerole(shareRole);
					UserShareRole userRole = new UserShareRole();
					userRole.setShareRoleOrg(shareRoleOrg);
					userRole.setUser(user);
					userRoles.add(userRole);
				}
				userShareRoleService.registUserRole(user, userRoles);
				result.setMsg("分配角色成功");
				result.setStatus(Result.STATUS_OK);
			}
		}catch(Exception e){
			result.setMsg("分配角色失败");
			result.setStatus(Result.STATUS_FAILED);
		}
		return result;
	}
	
}
