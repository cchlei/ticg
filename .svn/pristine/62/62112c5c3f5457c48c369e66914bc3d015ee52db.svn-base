package com.trgis.ticg.system.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trgis.ticg.core.model.SubSystemRegister;
import com.trgis.ticg.core.model.Subsystem;
import com.trgis.ticg.core.service.OrganizationService;
import com.trgis.ticg.core.service.SubsystemRegisterService;
import com.trgis.ticg.core.service.SubsystemService;
import com.trgis.ticg.core.util.BeanUtil;
import com.trgis.ticg.core.util.EnumUtil;
import com.trgis.ticg.system.core.model.SystemMenu;
import com.trgis.ticg.system.core.model.SystemRole;
import com.trgis.ticg.system.core.model.SystemUser;
import com.trgis.ticg.system.core.service.SystemMenuService;
import com.trgis.ticg.system.core.service.SystemRoleService;
import com.trgis.ticg.system.core.service.SystemUserService;
import com.trgis.ticg.system.core.util.QTManager;
import com.trgis.ticg.system.core.util.TreeDate;

@Controller
public class IndexController {
	
	@Autowired
	SystemUserService sysuserService;
	
	@Autowired
	SystemRoleService sysroleService;
	
	@Autowired
	SystemMenuService sysmenuService;
	
	@Autowired
	SubsystemService subsystemService;
	
	@Autowired
	OrganizationService organizationService;
	
	@Autowired
	SubsystemRegisterService srService;
	
    @RequestMapping(value = "/")
    public ModelAndView permissionTree(ModelAndView mv) {
    	QTManager systemUser = (QTManager)SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
		mv.addObject("systemUser",systemUser);
		mv.setViewName("index");
		//此处维护用户角色权限列表返回值前台 resultString
    	List<TreeDate> result = new ArrayList<TreeDate>();//构造返回给前段的集合
    	try {
    		SystemUser sysuser = sysuserService.find(systemUser.getId());
    		SystemRole sysrole = sysuser.getSysrole();
	    	if(sysrole!=null){
	    		if(sysrole.getStatus()==EnumUtil.STATUS.WQY.getValue()){
	    			TreeDate roottreedate = new TreeDate();
					roottreedate.setName("您的角色未启用，请联系管理员");
					result.add(roottreedate);
	    		}else{
	    			List<SystemMenu> loginMenulist = sysmenuService.getPermissionsByRoleid(sysrole.getId());//登录者所拥有权限
	    			List<SystemMenu> menuAllList = sysmenuService.getRootMenus();//根节点菜单
	    			for (int i = 0; i < menuAllList.size(); i++) {
	    				if(loginMenulist.contains(menuAllList.get(i))){//证明有此权限
	    					//子系统管理不开放给非系统超管人员
	    					if(BeanUtil.isNotEmpty(sysuser.getOrganization())&&"子系统管理".equals(menuAllList.get(i).getMenuname())){//超管
	    						continue;
	    					}
	    					TreeDate roottreedate = new TreeDate();//父节点
	    					if("子系统管理".equals(menuAllList.get(i).getMenuname())){
	    						roottreedate.setCollapse(false);
	    					}
	    					roottreedate.setName(menuAllList.get(i).getMenuname());
	    					roottreedate.setOpen(menuAllList.get(i).isClosed());
	    					roottreedate.setHref(menuAllList.get(i).getUrl());
	    					List<SystemMenu> children = menuAllList.get(i).getMenus();
	    					if(children!=null&&children.size()>0){//如果有子集
	    						List<TreeDate> treedatelist = getChildren(menuAllList.get(i), loginMenulist);
	    						roottreedate.setChildren(treedatelist);
	    					}
	    					//如果是子系统管理菜单项，根据当前登录用户列出子系统
	    					if("subsystem/list".equals(menuAllList.get(i).getUrl())){
	    						List<Subsystem> list = new ArrayList<Subsystem>();
	    						if(sysuser.getOrganization() == null){//1、超级管理员列出所有子系统
	    							list = subsystemService.findAll();
	    						}else{//2、有组织机构的人员列出组织机构注册的子系统
	    							//查询出用户所在机构注册的子系统
	    							List<SubSystemRegister> srlist = srService.findByOrganization(sysuser.getOrganization());
	    							for (SubSystemRegister subSystemRegister : srlist) {
	    								list.add(subSystemRegister.getSubsystem());
									}
	    						}
	    						if(BeanUtil.isNotEmpty(list)){
	    							List<TreeDate> tlist = new ArrayList<TreeDate>();
	    							for (Subsystem subsystem : list) {
	    								TreeDate treedate = new TreeDate();//父节点
	    								treedate.setName(subsystem.getName());
	    								treedate.setOpen(false);
	    								treedate.setHref("menu/list/"+subsystem.getId());
	    								tlist.add(treedate);
	    							}
	    							roottreedate.setChildren(tlist);
	    						}
	    					}
	    					result.add(roottreedate);
	    				}
	    			}
	    		}
	    	}
	    	ObjectMapper objectMapper = new ObjectMapper();
	    	String resultString="" ;
			resultString = objectMapper.writeValueAsString(result);
			mv.addObject("resultString",resultString);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mv;
	}
    
    //递归构建树状结构菜单
	private  List<TreeDate> getChildren(SystemMenu permissionInfo, List<SystemMenu> loginPlist){
		List<TreeDate> result = new ArrayList<TreeDate>();//构造返回给前端的集合
		List<SystemMenu> pAllList = permissionInfo.getMenus();//子集
    	for (int i = 0; i < pAllList.size(); i++) {
    		if(loginPlist.contains(pAllList.get(i))){//证明有此权限
				TreeDate treedate = new TreeDate();//父节点
				treedate.setName(pAllList.get(i).getMenuname());
				treedate.setOpen(pAllList.get(i).isClosed());
				treedate.setHref(pAllList.get(i).getUrl());
				List<SystemMenu> children = pAllList.get(i).getMenus();
				if(children!=null&&children.size()>0){//如果有子集
					List<TreeDate> treedatelist = getChildren(pAllList.get(i), loginPlist);
					treedate.setChildren(treedatelist);
				}
				result.add(treedate);
    		}
    	}
    	return result;
	}
	
	/**
	 * @author qinm 欢迎页
	 * @return
	 */
	@RequestMapping(value = "/splash", method = RequestMethod.GET)
	public ModelAndView list(ModelAndView mv) {
		QTManager systemUser = (QTManager)SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
		mv.addObject("systemUser",systemUser);
		mv.setViewName("/splash");
		return mv;
	}
}
