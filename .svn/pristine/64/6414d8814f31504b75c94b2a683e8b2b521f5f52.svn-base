package com.trgis.ticg.system.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.trgis.common.jpa.specfication.ConditionGroup;
import com.trgis.common.jpa.specfication.Operator;
import com.trgis.common.jpa.specfication.OrderBy;
import com.trgis.common.jpa.specfication.SearchCondition;
import com.trgis.common.jpa.specfication.SearchRelation;
import com.trgis.ticg.core.model.Role;
import com.trgis.ticg.core.model.User;
import com.trgis.ticg.core.model.UserRole;
import com.trgis.ticg.core.service.RoleService;
import com.trgis.ticg.core.service.UserRoleService;
import com.trgis.ticg.core.service.UserService;
import com.trgis.ticg.core.util.JSONPage;
import com.trgis.ticg.core.util.Result;
import com.trgis.ticg.core.vo.UserRoleVo;

/**
 * 
 * @author majl
 * @Description 用户分配角色
 * @data 2016年5月25日
 */
@Controller
@RequestMapping(value="/userrole")
public class UserRoleController {

	@Autowired
	RoleService roleService;

	@Autowired
	UserRoleService userRoleService;

	@Autowired
	UserService userService;

	@ResponseBody
	@RequestMapping(value="/find",method=RequestMethod.POST)
	public JSONPage findAllUserRoll(int rows,int page,String userid,String orgid){
		JSONPage jsonPage = new JSONPage();

		try{
			ConditionGroup cgroot = new ConditionGroup();
			cgroot.setSearchRelation(SearchRelation.AND);
			OrderBy order = new OrderBy("id", "desc");
			if(StringUtils.isNotBlank(orgid)){
				cgroot.addCondition(new SearchCondition("organization",Operator.EQ,Long.parseLong(orgid)));
			}
			Page<Role> roles = roleService.findByConditions(cgroot, page, rows, order);
			User user = null;
			if(StringUtils.isNotBlank(userid)){
				user = new User();
				user.setId(Long.parseLong(userid));
			}
			List<UserRole>	userRoles = userRoleService.findByUser(user);
			List<UserRoleVo> userRoleVos = initUserRoleVos(roles.getContent(),userRoles,user);
			jsonPage.setRows(userRoleVos);
			jsonPage.setTotal(roles==null?0:(int)roles.getTotalElements());
		}catch(Exception e){
			e.printStackTrace();
		}
		return jsonPage;
	}

	private List<UserRoleVo> initUserRoleVos(List<Role> roles ,List<UserRole> userRoles,User user){
		List<UserRoleVo> userRoleVos = new ArrayList<UserRoleVo>();
		if(roles!=null){
			for(Role role : roles){
				UserRoleVo vo = new UserRoleVo();
				vo.setIsCheck(false);
				if(userRoles!=null){
					for(UserRole userRole : userRoles){
						long urole = userRole.getRole().getId();
						long roleid = role.getId();
						if(roleid==urole){
							vo.setIsCheck(true);
							vo.setUser(user);
						}
					}
				}
				vo.setRole(role);
				userRoleVos.add(vo);
			}
		}
		return userRoleVos;
	}

	@ResponseBody
	@RequestMapping(value="/sync",method=RequestMethod.POST)
	public Result sync(@RequestParam(value="roles[]")Long[]  roles,Long userid){
		Result result = new Result();
		try{
			List<UserRole> userRoles = null;
			User user = new User();
			user.setId(userid);
			user = userService.findUserByUser(user);
			if(roles!=null && roles.length>0){
				userRoles = new ArrayList<UserRole>();
				for(Long roleid : roles){
					Role role = new Role();
					role.setId(roleid);
					UserRole userRole = new UserRole();
					userRole.setRole(role);
					userRole.setUser(user);
					userRoles.add(userRole);
				}
				userRoleService.registUserRole(user, userRoles);
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
