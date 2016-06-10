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
import com.trgis.ticg.core.exception.ShareRoleException;
import com.trgis.ticg.core.model.Organization;
//import com.trgis.ticg.core.model.Role;
import com.trgis.ticg.core.model.ShareRole;
import com.trgis.ticg.core.model.ShareRoleOrg;
import com.trgis.ticg.core.service.OrganizationService;
import com.trgis.ticg.core.service.ShareRoleOrgService;
//import com.trgis.ticg.core.service.RoleService;
//import com.trgis.ticg.core.service.RoleSubsystemService;
import com.trgis.ticg.core.service.ShareRoleService;
import com.trgis.ticg.core.service.ShareRoleSubsystemService;
import com.trgis.ticg.core.service.UserShareRoleService;
import com.trgis.ticg.core.util.BeanUtil;
import com.trgis.ticg.core.util.JSONPage;
import com.trgis.ticg.core.util.Result;
import com.trgis.ticg.system.core.model.SystemUser;
import com.trgis.ticg.system.core.service.SystemUserService;
import com.trgis.ticg.system.core.util.QTManager;
/**
 * 共享角色控制器
 * 
 * @author Alice
 *
 * 2016年5月28日
 */
@Controller
@RequestMapping("/sharerole")
public class ShareRoleController {

	@Autowired
	private ShareRoleService shareroleService;
	
	@Autowired
	private ShareRoleOrgService shareroleOrgService;
	
	@Autowired
	OrganizationService orgService;
	
	@Autowired
	SystemUserService sysuserService;
	
	@Autowired
	ShareRoleSubsystemService srsService;
	
	@Autowired
	OrganizationService organizationService;
	
	@Autowired
	UserShareRoleService userShareRoleService;
	/**
	 * 共享角色列表
	 * @param oid 组织机构id 系统超级管理员没有oid请传0
	 * @param rows
	 * @param page
	 * @param searchVal
	 * @return
	 */
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
			Page<ShareRole> pageResult = shareroleService.findByConditions(cgRoot, page, rows, order);
			json.setRows(pageResult == null?new ArrayList<ShareRole>():pageResult.getContent());
			json.setTotal(pageResult == null?0:(int)pageResult.getTotalElements());
		} catch (ShareRoleException e) {
			e.printStackTrace();
		}
		return json;
	}
	
	/**
	 * 根据当前登录用户判断跳转页面
	 * @param mv
	 * @return
	 */
	@RequestMapping("/index")
	public ModelAndView toIndex(ModelAndView mv){
		SystemUser systemUser = getUser();
		if (systemUser.getOrganization()!=null) {
			mv.addObject("oid", systemUser.getOrganization().getId());
			mv.setViewName("sharerole/list");
		}else{
			List<Organization> findRootOrganizations = orgService.findRootOrganizations();
			if(findRootOrganizations.size()!=0&&findRootOrganizations!=null){
				mv.addObject("oid", findRootOrganizations.get(0).getId());
			}else{
				mv.addObject("oid", 0);
			}
			mv.setViewName("sharerole/orgs");
		}
		return mv;
	}
	
	/**
	 * 分配菜单及操作
	 * @param roleid	角色id
	 * @param rolename	角色名称
	 * @param menus		菜单ids
	 * @param autthcs	操作ids
	 * @param subid		子系统ids
	 * @param oid		所属组织机构
	 * @param oids		共享租住机构ids
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/saveShareRole")
	public Result saveShareRole(Long roleid,String rolename,String menus,String authcs,String subids,Long oid,String oids){
		Result result = new Result();
		try {
			//检查是否重名
			if(BeanUtil.isEmpty(rolename.trim())){
				result.setMsg("请填写角色名称");
				result.setStatus(Result.STATUS_FAILED);
				return result;
			}
			if (BeanUtil.isEmpty(roleid) || !shareroleService.findOne(roleid).getRolename().equals(rolename)) {//新增或者修改是改了名字需要验证是否重名
            	ShareRole role = shareroleService.findByRolename(rolename);
            	if(role != null){
            		result.setMsg("角色名称已存在");
            		result.setStatus(Result.STATUS_FAILED);
            		return result;
            	}
			}
			ShareRole sharerole = new ShareRole();
			if(BeanUtil.isNotEmpty(roleid)){
				sharerole = shareroleService.findOne(roleid);
			}
			if(BeanUtil.isNotEmpty(oid)){
				sharerole.setOrganization(organizationService.findOne(oid));
			}
			sharerole.setRolename(rolename);
			shareroleService.saveShareRole(sharerole,menus,authcs,subids,oid,oids);
			result.setStatus(Result.STATUS_OK);
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus(Result.STATUS_FAILED);
		}
		return result;
	}
	
	/**
	 * 获取共享角色组织机构关系
	 * @param roleid
	 * @param oid
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getShareroleOrg")
	public Result getShareroleOrg(Long srid){
		Result result = new Result();
		try {
			ShareRole sharerole = new ShareRole();
			if(BeanUtil.isNotEmpty(srid)){
				sharerole = shareroleService.findOne(srid);
			}
			//改角色共享出去的组织机构集合，既谁可见
			List<ShareRoleOrg> list = shareroleOrgService.findBySharerole(sharerole);
			List<Long> orgids = new ArrayList<Long>();
			for (ShareRoleOrg sro : list) {
				orgids.add(sro.getOrganization().getId());
			}
			result.setData(orgids);
			result.setStatus(Result.STATUS_OK);
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus(Result.STATUS_FAILED);
		}
		return result;
	}
	
	/**
	 * 批量删除
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/del")
	public Result del(String id){
		Result result = new Result();
		try {
			if (id.contains(",")) {
				String[] split = id.split(",");
				Long[] ids = new Long[split.length];
				shareroleService.del(ids);
			}else{
				shareroleService.del(Long.valueOf(id));
			}
			result.setMsg("删除成功");
			result.setStatus(Result.STATUS_OK);
		} catch (Exception e) {
			result.setStatus(Result.STATUS_FAILED);
			result.setMsg("删除失败");
		}
		return result;
	}
	
	public SystemUser getUser(){
		QTManager systemUser = (QTManager)SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
		return sysuserService.find(systemUser.getId());
	}
}
