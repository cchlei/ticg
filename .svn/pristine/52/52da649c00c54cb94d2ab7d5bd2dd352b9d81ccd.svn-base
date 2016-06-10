package com.trgis.ticg.system.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.trgis.common.jpa.specfication.ConditionGroup;
import com.trgis.common.jpa.specfication.Operator;
import com.trgis.common.jpa.specfication.OrderBy;
import com.trgis.common.jpa.specfication.SearchCondition;
import com.trgis.common.jpa.specfication.SearchRelation;
import com.trgis.ticg.core.model.Organization;
import com.trgis.ticg.core.service.OrganizationService;
import com.trgis.ticg.core.util.BeanUtil;
import com.trgis.ticg.core.util.EnumUtil;
import com.trgis.ticg.core.util.EnumUtil.STATUS;
import com.trgis.ticg.core.util.JSONPage;
import com.trgis.ticg.core.util.Result;
import com.trgis.ticg.system.core.model.SystemMenu;
import com.trgis.ticg.system.core.model.SystemRole;
import com.trgis.ticg.system.core.service.SystemMenuService;
import com.trgis.ticg.system.core.service.SystemRoleService;
import com.trgis.ticg.system.core.service.SystemUserService;
import com.trgis.ticg.system.core.util.MenuJson;
import com.trgis.ticg.system.core.util.OrgTree;

/**
 * @author Alice
 * 系统账号管理
 */
@Controller
@RequestMapping(value = "/systemRole")
public class SystemRoleController {

    protected Logger logger = LoggerFactory.getLogger(getClass());
    
    @Autowired
    protected SystemUserService systemUserService;
    
    @Autowired
    protected SystemRoleService systemRoleService;
    
    @Autowired
    protected SystemMenuService systemMenuService;
    
    @Autowired
    protected OrganizationService orgService;
  
    /**
     * @author Alice
     * 新增用户时使用，初始化选择角色
     * @return
     */
    @ResponseBody
	@RequestMapping(value = "/getRolesToChoice", method = RequestMethod.POST)
	public List<Map<String,Object>> getRolesToChoice(){
		List<SystemRole> roleList = systemRoleService.findAll();
		List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
		for (SystemRole systemRole : roleList) {
			Map<String,Object> role = new HashMap<String,Object>();
			role.put("id", systemRole.getId());
			role.put("text", systemRole.getRolename());
			role.put("state", "open");
			result.add(role);
		}
		return result;
	}
    /**
     * @author Alice
     * 新增用户时使用，初始化选择组织机构
     * @return
     */
    @ResponseBody
	@RequestMapping(value = "/getOrgsToChoice", method = RequestMethod.POST)
	public List<OrgTree> getDepartmentsToChoice() {
		List<OrgTree> result = new ArrayList<OrgTree>();
		List<Organization> orgRootList = orgService.findRootOrganizations();
		OrgTree innerDepartment = new OrgTree();
		for (Organization org : orgRootList) {// 根目录(其实只有一个)
			innerDepartment = getChildNode(org);
		}
		result.add(innerDepartment);
		return result;
	}
	
	private OrgTree getChildNode(Organization obj) {
		OrgTree innerDepartment = new OrgTree();
		innerDepartment.setId(obj.getId().toString());
		innerDepartment.setText(obj.getOrgnazationName());
		innerDepartment.setState("open");
		Map<String, String> attributes = new HashMap<String, String>();
		attributes.put("parentId", obj.getParentOrganization()==null?null:obj.getParentOrganization().getId().toString());
		innerDepartment.setAttributes(attributes);
		List<OrgTree> children = new ArrayList<OrgTree>();
		// 若还有子集(可供选择的)
		Set<Organization> ddList = new HashSet<Organization>();
		ddList = orgService.findSubOrganizations(obj);
		for (Organization d : ddList) {
			children.add(getChildNode(d));
		}
		innerDepartment.setChildren(children);
		return innerDepartment;
	}
	
	/**
	 * @author Alice
	 * 角色列表页面转发
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list() {
		return "system/roleList";
	}
	
	/**
	 * @author Alice
	 * 编辑角色时初始时，初始化权限树
	 * @param roleid
	 * @return
	 */
	@ResponseBody
    @RequestMapping(value = "/initMenu", method = RequestMethod.POST)
    public Map<String, Object> initMenu(Long roleid){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			List<MenuJson> result = new ArrayList<MenuJson>();//构造返回给前段的集合
			List<SystemMenu> rootPermissionList = systemMenuService.getRootMenus(); //order by id(根节点权限集合)
			SystemRole role = null;
			List<SystemMenu> rolePermissionList = new ArrayList<SystemMenu>();//当前角色包含权限
	        if (roleid != null) {
	            //获取要修改的角色的所有权限
	            role = systemRoleService.getRoleById(roleid);
	            rolePermissionList = systemMenuService.getPermissionsByRoleid(roleid);
	            map.put("role", role);
	        }
			MenuJson proot = new MenuJson(0l,"管理系统","root");
			result.add(proot);//先加入根节点，id为0;
			for (SystemMenu permission : rootPermissionList) {
				MenuJson p = new MenuJson(permission.getId(),permission.getMenuname(),"0");//一级
				// 判断要修改的角色是否包含该权限
				if (rolePermissionList != null && rolePermissionList.contains(permission)) {
					p.setChecked(true);
				}
				result.add(p);
				if(BeanUtil.isNotEmpty(permission.getMenus())){//   permission.getMenus()!=null&&permission.getMenus().size()>0){
					result.addAll(getChildren(permission, rolePermissionList));
				}
			}
			map.put("menulist", result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	//	递归
	private List<MenuJson> getChildren(SystemMenu permissionInfo,List<SystemMenu> rolePermissionList){
		List<MenuJson> result = new ArrayList<MenuJson>();//构造返回给前段的集合
		List<SystemMenu> permissionList = permissionInfo.getMenus(); //子集
		 for (SystemMenu permission : permissionList) {
	        	MenuJson p = new MenuJson(permission.getId(),permission.getMenuname(),permissionInfo.getId()+"");//一级
	        	// 判断要修改的角色是否包含该权限
				if (rolePermissionList != null && rolePermissionList.contains(permission)) {
					p.setChecked(true);
				}
	        	result.add(p);
	        	if(BeanUtil.isNotEmpty(permission.getMenus())){//  permission.getMenus()!=null&&permission.getPermissions().size()>0){
	        		result.addAll(getChildren(permission,rolePermissionList));
	        	}
		 }
		 return result;
	}
	
    /**
     * @author Alice
     * 系统角色分页列表
     * @return
     */
	@ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public JSONPage getSystemRoleList(int rows,int page,String searchVal) {
    	// 配置查询条件组合
 		ConditionGroup cgRoot = new ConditionGroup();
 		cgRoot.setSearchRelation(SearchRelation.AND);// 基本组合关系为AND
    	//查询条件
    	String[] search= null;
    	if(!StringUtils.isEmpty(searchVal)){
			search = searchVal.split(":");//search[0]值search[1]字段
			// 查询过滤条件为 子条件组
			ConditionGroup filterGroup = new ConditionGroup();
			filterGroup.setSearchRelation(SearchRelation.OR); // 设置条件关系为OR
			// 判断并添加查询条件
			List<SearchCondition> searchFilters = new ArrayList<SearchCondition>(); // 设置条件
			STATUS status = EnumUtil.getStatusByName(search[0]);
			if ("all".equals(search[1])) {
				 String [] types = {"rolename","remarks","status"};
				 for (int i = 0; i < types.length; i++) {
					 if("status".equals(types[i])){
						 searchFilters.add(new SearchCondition(types[i], Operator.EQ, status==null?status:status.getValue()));
					 }else{
						 searchFilters.add(new SearchCondition(types[i], Operator.LIKE, search[0]));
					 }
				 }
			 }else{
				 if("status".equals(search[1])){
					 searchFilters.add(new SearchCondition(search[1], Operator.EQ, status==null?status:status.getValue()));
				 }else{
					 searchFilters.add(new SearchCondition(search[1], Operator.LIKE, search[0]));
				 }
			 }
			filterGroup.getConditions().addAll(searchFilters);// 将条件集合加入到第二组条件中
			cgRoot.getSubConditions().add(filterGroup);// 将过滤条件组加入根查询条件中
    	}
    	OrderBy order = new OrderBy("id", "desc");
		Page<SystemRole> pageResult = systemRoleService.findByConditions(cgRoot, page, rows, order);//findByConditions(cgRoot, page, rows, order);

		JSONPage json = new JSONPage();
		json.setRows(pageResult == null?new ArrayList<SystemRole>():pageResult.getContent());
		json.setTotal(pageResult == null?0:(int)pageResult.getTotalElements());
		return json;
    }
	
	/**
	 * 新增修改角色
	 * @param request
	 * @param out
	 */
	@ResponseBody
	@RequestMapping(value = "/doEditRole", method = RequestMethod.POST)
    public Result doEditRole(SystemRole systemrole, String selectedPermissionIds) {
		Result result = new Result();
    	try {
			//先判断是否重名
            if (systemrole.getId() == null || !systemRoleService.getRoleById(systemrole.getId()).getRolename().equals(systemrole.getRolename())) {//新增或者修改是改了名字需要验证是否重名
            	SystemRole role = systemRoleService.findRoleByRolename(systemrole.getRolename(), EnumUtil.STATUS.YQY.getValue());
            	if(role!=null){
            		result.setMsg("nameUnique");
            		result.setStatus(Result.STATUS_FAILED);
            		return result;
            	}
            }
            String selectedMenuIds[] = selectedPermissionIds.substring(2).split("\\|");
        	if(systemrole.getId() != null &&!systemRoleService.getRoleById(systemrole.getId()).getRolename().equals(systemrole.getRolename())){
        		//修改了名字级联更新系统用户表中的rolename列
        		systemUserService.updateRolename(systemrole.getRolename(), systemrole);
            }
        	systemRoleService.save(systemrole);
        	systemRoleService.doAssignPermission(systemrole, selectedMenuIds);
        	result.setStatus(Result.STATUS_OK);
		} catch (Exception e) {
			result.setStatus(Result.STATUS_FAILED);
			e.printStackTrace();
		}
    	return result;
    }
	
	/**
	 * @author Alice
	 * 删除角色
	 * @param request
	 * @param out
	 */
	@RequestMapping(value = "/delRole", method = RequestMethod.POST)
    public Result delRole(Long roleid) {
		Result result = new Result();
    	try {
    		if(roleid.equals(1l)){//管理员
    			result.setMsg("系统管理员角色不能删除");
    			result.setStatus(Result.STATUS_FAILED);
    		}else{
    			if(BeanUtil.isNotEmpty(systemUserService.findListByRole(roleid))){
    				result.setMsg("该角色下已具有用户，删除该角色需要先将拥有该角色的用户重新分配到新角色，请到用户管理页面进行处理！");
        			result.setStatus(Result.STATUS_FAILED);
    			}else{
    				//真删除，需要级联删除关系表
    				systemRoleService.delete(roleid);
    				result.setStatus(Result.STATUS_OK);
    			}
    		}
		} catch (Exception e) {
			result.setStatus(Result.STATUS_FAILED);
			e.printStackTrace();
		}
    	return result;
    }
}