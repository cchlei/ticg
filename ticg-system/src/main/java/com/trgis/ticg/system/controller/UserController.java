package com.trgis.ticg.system.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.trgis.ticg.core.model.Organization;
import com.trgis.ticg.core.model.User;
import com.trgis.ticg.core.service.UserService;
import com.trgis.ticg.core.util.BeanUtil;
import com.trgis.ticg.core.util.JSONPage;
import com.trgis.ticg.core.util.Result;
import com.trgis.ticg.system.core.model.SystemUser;
import com.trgis.ticg.system.core.service.SystemUserService;
import com.trgis.ticg.system.core.util.QTManager;

/**
 * 
 * @author majl
 * @Description 用户控制器
 * @data 2016年5月20日
 */
@Controller
@RequestMapping(value="/user")
public class UserController {
	
	private Logger logger = LoggerFactory.getLogger(UserController.class);
	

	@Autowired
	SystemUserService sysuserService;
	
	@Autowired
	UserService userService;
	
	/**
	 * 转发到用户管理页面 
	 * @return
	 */
	@RequestMapping(value="/usermain")
	public ModelAndView UserMain(ModelAndView mv){
		//ModelAndView mv = new ModelAndView();
		QTManager sysuser = (QTManager)SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
		SystemUser user = sysuserService.find(sysuser.getId());
		if(BeanUtil.isNotEmpty(user)){
			if(BeanUtil.isEmpty(user.getOrganization())){
				//超管
				mv.addObject("oid", 0);
				mv.setViewName("user/sysusermain");
			}else{
				//组织机构运维
					mv.addObject("oid", user.getOrganization().getId());
					mv.setViewName("user/subsystemmain");
			}
		}
				return mv;
	}
	/**
	 * 加载用户
	 * @param organizationid
	 * @param roleid
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/userlist",method=RequestMethod.POST)
	public JSONPage userList(int rows,int page,String organizationid,String searchVal){
		User user = new User();
		JSONPage  jsonPage = new JSONPage();
		if(organizationid!=null && organizationid.trim().length()>0){
			Organization org = new Organization();
			org.setId(Long.parseLong(organizationid));
			user.setOrganization(org);
		}

		Page<User> users  = userService.findByConditions(page,rows,user,searchVal);
		if(users!=null && users.getContent()!=null && users.getContent().size()>0){
			jsonPage.setRows(users==null?new ArrayList<User>():users.getContent());
			}else{
				List<User> tagList = new ArrayList<User>();
				jsonPage.setRows(tagList);
			}
		jsonPage.setTotal(users==null?0:(int)users.getTotalElements());
		return jsonPage;
	}
	
	/**
	 * 添加用户
	 */
	@ResponseBody
	@RequestMapping(value="/registUser",method=RequestMethod.POST)
	public Result registerUser(User user,String orgid){
		Result result = new Result();
		try{
			logger.debug("registUser Begin");
		if(orgid!=null && orgid.trim().length()>1){
			Organization org = new Organization();
			org.setId(Long.parseLong(orgid));
			user.setOrganization(org);
		}
		userService.createUser(user);
		result.setMsg("用户创建成功!");
		result.setStatus(Result.STATUS_OK);
		}catch(Exception e){
			logger.debug("registUser failure  !"+e.getMessage());
			result.setMsg("用户创建失败!");
			result.setStatus(Result.STATUS_FAILED);
		}
		logger.debug("registUser end");
		return result;
	}
	
	/**
	 * 用户修改
	 * @param user
	 * @param orgid
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/modfiyUser",method=RequestMethod.POST)
	public Result modfied(User user,Long orgid){
		Result result = new Result();
		try{
		if(user!=null){
			userService.modfityUser(user);
			result.setMsg("修改用户成功!");
			result.setStatus(Result.STATUS_OK);
		}
		}catch(Exception e){
			result.setMsg("修改用户失败!");
			result.setStatus(Result.STATUS_FAILED);
			logger.debug("修改用户失败!"+e.getMessage());
		}
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value="/removeUser",method=RequestMethod.POST)
	public Result removeUser(Long id,Result result){
		try{
			logger.debug("开始删除用户..");
			userService.removeUser(id);
			result.setMsg("操作成功");
			result.setStatus(Result.STATUS_OK);
		}catch(Exception e){
			logger.debug("删除用户失败"+e.getMessage());
			result.setMsg("操作失败");
			result.setStatus(Result.STATUS_FAILED);
		}
		return result;
	}
}
