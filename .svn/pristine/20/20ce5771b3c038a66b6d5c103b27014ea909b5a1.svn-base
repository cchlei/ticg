package com.trgis.ticg.system.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
//import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.trgis.ticg.core.service.OrganizationService;
import com.trgis.ticg.core.util.JSONPage;
import com.trgis.ticg.core.util.Result;
import com.trgis.ticg.system.core.model.SystemUser;
import com.trgis.ticg.system.core.service.SystemRoleService;
import com.trgis.ticg.system.core.service.SystemUserService;
import com.trgis.ticg.system.core.util.UserEncrypt;

/**
 * 系统账号管理
 */
@Controller
@RequestMapping(value = "/systemUser")
public class SystemUserController {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    protected SystemUserService systemUserService;
    @Autowired
    protected SystemRoleService systemRoleService;
    @Autowired
    protected OrganizationService orgService;
  
	/**
	 * @author Alice
	 * 系统账号页面转发
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list() {
		return "system/userList";
	}
	
    /**
     * @author Alice
     * 系统账号分页列表
     * @return
     */
	@ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public JSONPage getSystemUserList(int rows,int page,String searchVal) {
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
			//角色  部门
			if ("all".equals(search[1])) {
				 String [] types = {"username","rolename","organizationname"};
				 for (int i = 0; i < types.length; i++) {
					 searchFilters.add(new SearchCondition(types[i], Operator.LIKE, search[0]));
				 }
			 }else{
				 searchFilters.add(new SearchCondition(search[1], Operator.LIKE, search[0]));
			 }
			filterGroup.getConditions().addAll(searchFilters);// 将条件集合加入到第二组条件中
			cgRoot.getSubConditions().add(filterGroup);// 讲过滤条件组加入根查询条件中
    	}
    	OrderBy order = new OrderBy("id", "desc");
		Page<SystemUser> pageResult = systemUserService.findByConditions(cgRoot, page, rows, order);//findByConditions(cgRoot, page, rows, order);

		JSONPage json = new JSONPage();
		json.setRows(pageResult == null?new ArrayList<SystemUser>():pageResult.getContent());
		json.setTotal(pageResult == null?0:(int)pageResult.getTotalElements());
		return json;
    }
	
	/**
	 * @author Alice
	 * 新增系统账户
	 * @param systemUser
	 * @param roleid
	 * @param orgid
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public Result addSystenUser(SystemUser systemUser, Long roleid, Long orgid){
		Result result = new Result();
		//先判断是否重名
        if (systemUser.getId() == null || !systemUserService.find(systemUser.getId()).getUsername().equals(systemUser.getUsername())) { //新增或者修改了用户名(即需要做重名判断)
        	SystemUser user = systemUserService.findSystemUserByUsername(systemUser.getUsername());
        	if(user!=null){
        		result.setMsg("nameUnique");
        		result.setStatus(Result.STATUS_FAILED);
        		return result;
        	}
        }
		systemUser.setSysrole(systemRoleService.getRoleById(roleid));
		systemUser.setOrganization(orgService.findOne(orgid));
		if (systemUser.getId() == null){
			systemUserService.create(systemUser);
		}else{
			systemUser.setSalt(systemUserService.find(systemUser.getId()).getSalt());
			systemUser.setPassword(systemUserService.find(systemUser.getId()).getPassword());
			systemUserService.edit(systemUser);
		}
		result.setStatus(Result.STATUS_OK);
		return result;
	}
	
    /**
     * @author Alice
     * 重置系统账户密码
     * @param userid
     * @param request
     * @param session
     * @param out
     */
	@ResponseBody
    @RequestMapping(value = "/resetPwd", method = RequestMethod.POST)
    public Result resetPwd(@RequestParam("id") Long id) {
		Result result = new Result();
		try {
			SystemUser systemUser = systemUserService.find(id);
			systemUserService.resetPwd(systemUser);
			result.setStatus(Result.STATUS_OK);
		} catch (Exception e) {
    		result.setStatus(Result.STATUS_FAILED);
			e.printStackTrace();
		}
		return result;
    }
	
	/**
	 * 删除用户
	 * @param id
	 * @return
	 */
	@ResponseBody
    @RequestMapping(value = "/delUser", method = RequestMethod.POST)
    public Result delUser(@RequestParam("id") Long id) {
		Result result = new Result();
    	try {
    		systemUserService.delete(id);
            result.setStatus(Result.STATUS_OK);
		} catch (Exception e) {
    		result.setStatus(Result.STATUS_FAILED);
			e.printStackTrace();
		}
    	return result;
    }
	
	/**
     * @author qinm
     * 修改系统账户密码
     */
	@ResponseBody
    @RequestMapping(value = "/changePwd", method = RequestMethod.POST)
    public Result changePwd(String username, String originalPwd, String changePwd) {
		Result result = new Result();
		try {
			SystemUser systemUser = systemUserService.findSystemUserByUsername(username);
			//初始密码是否正确
			boolean isEqual = UserEncrypt.md5hash(originalPwd, systemUser.getSalt()).equals(systemUser.getPassword());
			if(isEqual){
				systemUserService.changePwd(systemUser,changePwd);
				result.setStatus(Result.STATUS_OK);
				result.setMsg("修改成功！");
			}else{
				result.setMsg("修改失败,初始密码不正确！");
				result.setStatus(Result.STATUS_FAILED);
			}
			
		} catch (Exception e) {
    		result.setStatus(Result.STATUS_FAILED);
			e.printStackTrace();
		}
		return result;
    }
	
}