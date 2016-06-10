package com.trgis.ticg.system.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.trgis.common.jpa.specfication.ConditionGroup;
import com.trgis.common.jpa.specfication.Operator;
import com.trgis.common.jpa.specfication.OrderBy;
import com.trgis.common.jpa.specfication.SearchCondition;
import com.trgis.common.jpa.specfication.SearchRelation;
import com.trgis.ticg.core.exception.RoleException;
import com.trgis.ticg.core.model.Organization;
import com.trgis.ticg.core.model.Role;
import com.trgis.ticg.core.model.ShareRole;
import com.trgis.ticg.core.service.OrganizationService;
import com.trgis.ticg.core.service.RoleService;
import com.trgis.ticg.core.service.RoleSubsystemService;
import com.trgis.ticg.core.util.JSONPage;
import com.trgis.ticg.core.util.Result;
import com.trgis.ticg.system.core.model.SystemUser;
import com.trgis.ticg.system.core.service.SystemUserService;
import com.trgis.ticg.system.core.util.QTManager;

@Controller
@RequestMapping("/role")
public class RoleController {

	@Autowired
	private RoleService roleService;
	@Autowired
	SystemUserService sysuserService;
	@Autowired
	RoleSubsystemService roleSubsystemService;
	@Autowired
	OrganizationService orgService;
	
	@ResponseBody
	@RequestMapping("/add")
	public Result addRole(Role role,Long oid){
		Result result = new Result();
		try {
			if (role.getRolename()==null||role.getRolename().equals("")) {
				throw new RoleException("角色名称");
			}
			Organization organization = new Organization();
			organization.setId(oid);
			role.setOrganization(organization);
			List<Role> finAll = roleService.findByOrg(oid);
			for (Role role2 : finAll) {
				if (role.getRolename().equals(role2.getRolename())) {
					throw new RoleException("此角色已经存在");
				}
			}
			Long id = roleService.addRole(role);
			result.setData(id);
			result.setStatus(Result.STATUS_OK);
			result.setMsg("保存成功");
		} catch (RoleException e) {
			result.setMsg(e.getMessage());
			result.setStatus(Result.STATUS_FAILED);
		}
		return result;
	}
	@ResponseBody
	@RequestMapping("/edit")
	public Result editRole(Role role,Long oid){
		Result result = new Result();
		try {
			List<Role> roles = roleService.findByOrg(oid);
			for (Role rol : roles) {
				if(rol.getRolename().equals(role.getRolename())&&(!rol.getId().equals(role.getId()))){
					throw new RoleException(role.getRolename()+" 已经存在");
				}
			}
			Role temp = roleService.findOne(role.getId());
			temp.setRolename(role.getRolename());
			roleService.saveAndFlush(temp);
			
			result.setStatus(Result.STATUS_OK);
			result.setMsg("修改成功");
		} catch (RoleException e) {
			result.setMsg("修改失败:"+e.getMessage());
			result.setStatus(Result.STATUS_FAILED);
		}
		return result;
	}
	@ResponseBody
	@RequestMapping("/del")
	public Result del(String id){
		Result result = new Result();
		try {
			//先判断是否有用户引用
			if (id.contains(",")) {
				String[] split = id.split(",");
				Long[] ids = new Long[split.length];
				roleService.del(ids);
			}else{
				roleService.del(Long.valueOf(id));
			}
			result.setMsg("删除成功");
			result.setStatus(Result.STATUS_OK);
		} catch (RoleException e) {
			result.setStatus(Result.STATUS_FAILED);
			result.setMsg("删除失败");
		}
		return result;
	}
	
	@ResponseBody
    @RequestMapping(value = "/list/{oid}", method = RequestMethod.POST)
	public JSONPage getSubsystemList(@PathVariable("oid") Long oid, int rows, int page, String searchVal) {
		// 配置查询条件组合
		ConditionGroup cgRoot = new ConditionGroup();
		cgRoot.setSearchRelation(SearchRelation.AND);// 基本组合关系为AND
		if(!oid.equals(0l)){
			cgRoot.getConditions().add(new SearchCondition("organization", Operator.EQ, orgService.findOne(oid)));
		}
		// 查询条件
		String[] search = null;
		if (!StringUtils.isEmpty(searchVal)) {
			search = searchVal.split(":");// search[0]值search[1]字段
			cgRoot.getConditions().add(new SearchCondition("rolename", Operator.LIKE, search[0]));
		}
		OrderBy order = new OrderBy("id", "desc");
		JSONPage json = new JSONPage();
		try {
			Page<Role> pageResult = roleService.findByConditions(cgRoot, page, rows, order);
			json.setRows(pageResult == null?new ArrayList<ShareRole>():pageResult.getContent());
			json.setTotal(pageResult == null?0:(int)pageResult.getTotalElements());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;
	}
	
	
	@RequestMapping("/index")
	public ModelAndView toIndex(ModelAndView mv){
		SystemUser systemUser = getUser();
		if (systemUser.getOrganization()!=null) {
			mv.addObject("oid", systemUser.getOrganization().getId());
			mv.setViewName("role/list");
		}else{
			List<Organization> findRootOrganizations = orgService.findRootOrganizations();
			if(findRootOrganizations.size()!=0&&findRootOrganizations!=null){
				mv.addObject("oid", findRootOrganizations.get(0).getId());
			}else{
				mv.addObject("oid", 0);
			}
			mv.setViewName("role/orgs");
		}
		return mv;
	}
	/**
	 * @Description: 分配菜单
	 * @author yanpeng
	 * @date 2016年5月26日 下午4:20:59
	 * @param mv
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/addMenu")
	public Result addMenu(Long rid,String ids,Long subid,Long oid,String rolname){
		Result result = new Result();
		try {
			if (rid==-200) {
				rid = null;
			}
			roleService.addMenu(rid,ids,subid,oid,rolname);
			result.setStatus(Result.STATUS_OK);
			result.setMsg("保存成功");
		} catch (RoleException e) {
			result.setMsg("亲，同时操作那么多张表，不容易啊，还是好好的填写数据吧。");
			result.setStatus(Result.STATUS_FAILED);
		}
		return result;
	}
	@ResponseBody
	@RequestMapping("/check")
	public Result check(String checks,Long oid,Long rid){
		Result result = new Result();
		try {
			List<Role> roles = roleService.findByOrg(oid);
			if(rid==null){
				for (Role role : roles) {
					if(role.getRolename().equals(checks)){
						throw new RoleException(checks+" 已经存在");
					}
				}
			}else{
				for (Role role : roles) {
					if(role.getRolename().equals(checks)&&(!role.getId().equals(rid))){
						throw new RoleException(checks+" 已经存在");
					}
				}
			}
			result.setMsg("可以添加");
			result.setStatus(Result.STATUS_OK);
		} catch (RoleException e) {
			e.printStackTrace();
			result.setStatus(Result.STATUS_FAILED);
			result.setMsg(e.getMessage());
		}
		return result;
	}
	
	public SystemUser getUser(){
		QTManager systemUser = (QTManager)SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
		return sysuserService.find(systemUser.getId());
	}
}
