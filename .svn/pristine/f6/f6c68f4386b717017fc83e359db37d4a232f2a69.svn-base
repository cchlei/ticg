package com.trgis.ticg.system.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
//import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.trgis.common.jpa.specfication.ConditionGroup;
import com.trgis.common.jpa.specfication.Operator;
import com.trgis.common.jpa.specfication.OrderBy;
import com.trgis.common.jpa.specfication.SearchCondition;
import com.trgis.common.jpa.specfication.SearchRelation;
import com.trgis.ticg.core.model.Menu;
import com.trgis.ticg.core.model.Organization;
import com.trgis.ticg.core.model.Role;
import com.trgis.ticg.core.model.RoleSubsystem;
import com.trgis.ticg.core.model.ShareRole;
import com.trgis.ticg.core.model.ShareRoleSubsystem;
import com.trgis.ticg.core.model.SubSystemRegister;
import com.trgis.ticg.core.model.Subsystem;
import com.trgis.ticg.core.service.RoleSubsystemService;
import com.trgis.ticg.core.service.ShareRoleSubsystemService;
import com.trgis.ticg.core.service.SubsystemRegisterService;
import com.trgis.ticg.core.service.SubsystemService;
import com.trgis.ticg.core.util.JSONPage;
import com.trgis.ticg.core.util.Result;

/**
 * 应用子系统
 */
@Controller
@RequestMapping(value = "/subsystem")
public class SubsystemController {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    protected SubsystemService subsystemService;
    @Autowired
	private SubsystemRegisterService subsystemRegisterService;
    @Autowired
    private RoleSubsystemService roleSubsystemService;
    @Autowired
    private ShareRoleSubsystemService shareRoleSubsystemService;
  
	/**
	 * @author Alice
	 * 应用子系统页面转发
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list() {
		return "subsystem/sublist";
	}
	
    /**
     * @author Alice
     * 应用子系统分页列表
     * @return
     */
	@ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public JSONPage getSubsystemList(int rows,int page,String searchVal) {
    	// 配置查询条件组合
 		ConditionGroup cgRoot = new ConditionGroup();
 		cgRoot.setSearchRelation(SearchRelation.AND);// 基本组合关系为AND
 		ConditionGroup filterGroup = new ConditionGroup();
 		List<SearchCondition> searchFilters = new ArrayList<SearchCondition>(); // 设置条件
    	//查询条件
    	String[] search= null;
    	if(!StringUtils.isEmpty(searchVal)){
			search = searchVal.split(":");//search[0]值search[1]字段
			// 查询过滤条件为 子条件组
			filterGroup.setSearchRelation(SearchRelation.OR); // 设置条件关系为OR
			// 判断并添加查询条件
			if ("all".equals(search[1])) {
				 String [] types = {"name","code","database","url","description"};
				 for (int i = 0; i < types.length; i++) {
//					 if("status".equals(types[i])){
//						 searchFilters.add(new SearchCondition(types[i], Operator.EQ, UserStatus.getStatusByName(search[0])));
//					 }else{
						 searchFilters.add(new SearchCondition(types[i], Operator.LIKE, search[0]));
//					 }
				 }
			 }else{
//				 if("status".equals(search[1])){
//					 searchFilters.add(new SearchCondition(search[1], Operator.EQ, UserStatus.getStatusByName(search[0])));
//				 }else{
					 searchFilters.add(new SearchCondition(search[1], Operator.LIKE, search[0]));
//				 }
			 }
    	}
    	filterGroup.getConditions().addAll(searchFilters);// 将条件集合加入到第二组条件中
    	cgRoot.getSubConditions().add(filterGroup);// 讲过滤条件组加入根查询条件中
    	OrderBy order = new OrderBy("id", "desc");
		Page<Subsystem> pageResult = subsystemService.findByConditions(cgRoot, page, rows, order);

		JSONPage json = new JSONPage();
        //构造操作按钮
//        List<Subsystem> user = page==null?new ArrayList<Subsystem>():userPage.getContent();
//        for (Subsystem s : subsystem) {
//        	if(u.getStatus()==UserStatus.LOCKED)
//			u.setOperation("<a class='viewInfo' href='javascript:void(0);' onclick='unlock("+u.getId()+");'>解冻</a>");
//		}
		json.setRows(pageResult == null?new ArrayList<Menu>():pageResult.getContent());
		json.setTotal(pageResult == null?0:(int)pageResult.getTotalElements());
		return json;
    }
	
	
		/**
		 * @Description:添加应用子系统
		 * @Author liuyan 
		 * @Date 2016年5月19日 上午10:46:12
		 * @param tag
		 * @param result
		 * @return
		 */
		@ResponseBody
		@RequestMapping(value="/addsys",method=RequestMethod.POST)
		public Result systemAdd(Subsystem subsystem,Result result){
			
				//先判断是否重名
		        if (subsystem.getId() == null || !subsystemService.findOne(subsystem.getId()).getName().equals(subsystem.getName())) { //新增或者修改了用户名(即需要做重名判断)
		        	Subsystem sub = subsystemService.findSysByNameOrCode("name", subsystem.getName());
		        	if(sub!=null){
		        		result.setMsg("name已存在！");
		        		result.setStatus(Result.STATUS_FAILED);
		        		logger.debug("name重复啦");
		        		return result;
		        	}
		        }
		        //判断code是否重复
		        if (subsystem.getId() == null || !subsystemService.findOne(subsystem.getId()).getCode().equals(subsystem.getCode())) { //新增或者修改了code(即需要做重名判断)
		        	Subsystem sub = subsystemService.findSysByNameOrCode("code", subsystem.getCode());
		        	if(sub!=null){
		        		result.setMsg("code已存在！");
		        		result.setStatus(Result.STATUS_FAILED);
		        		logger.debug("code重复啦");
		        		return result;
		        	}
		        }
		        if (subsystem.getId() == null){
		        	try {
		        		subsystemService.add(subsystem);
		        		result.setStatus(Result.STATUS_OK);
						result.setMsg("子系统添加成功！");
					} catch (Exception e) {
						result.setStatus(Result.STATUS_FAILED);
						result.setMsg("子系统添加失败！");
						logger.debug(e.getMessage());
					}
		        	
				}else{
					try {
						subsystemService.edit(subsystem);
						result.setStatus(Result.STATUS_OK);
						result.setMsg("子系统修改成功！");
					} catch (Exception e) {
						result.setStatus(Result.STATUS_FAILED);
						result.setMsg("子系统修改失败！");
						logger.debug(e.getMessage());
					}
					
				}
			return result;
		}
	
		/**
		 * @Description:删除
		 * @Author liuyan 
		 * @Date 2016年5月20日 上午9:49:10
		 * @param subsystem
		 * @param result
		 * @return
		 */
		@ResponseBody
		@RequestMapping(value="/delsys",method=RequestMethod.POST)
		public Result delSys(String id,Result result){
			try{
				subsystemService.del(Long.parseLong(id));
				result.setStatus(Result.STATUS_OK);
				result.setMsg("子系统删除成功！");
			}catch(Exception e){
				result.setStatus(Result.STATUS_FAILED);
				result.setMsg("子系统删除失败！");
				logger.debug(e.getMessage());
			}
			return result;
		}
		/**
		 * @Description: 根据组织机构角色加载已注册的子系统
		 * @author yanpeng
		 * @date 2016年5月20日 下午2:08:06
		 * @return
		 */
    	@ResponseBody
	    @RequestMapping(value = "/lists/{id}", method = RequestMethod.POST)
	    public List<Map<String,Object>> getSubsystemList(@PathVariable("id") Long oid,Long rid) {
			List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
			Organization organization = new Organization();
			organization.setId(oid);
			Set<Long> set = new HashSet<Long>();
			if (rid!=null) {
				Role role =new Role();
				role.setId(rid);
				List<RoleSubsystem> byRoles = roleSubsystemService.findByRole(role);
				for (RoleSubsystem roleSubsystem : byRoles) {
					set.add(roleSubsystem.getSsRegister().getSubsystem().getId());
				}
			}
			List<SubSystemRegister> temp = subsystemRegisterService.findByOrganization(organization);
			Map<String,Object> map = null;
			for (SubSystemRegister subSystemRegister : temp) {
				map = new HashMap<String,Object>();
				map.put("id", subSystemRegister.getSubsystem().getId());
				map.put("name", subSystemRegister.getSubsystem().getName());
				if (set.contains(subSystemRegister.getSubsystem().getId())) {
					map.put("ck",true);
				}
				list.add(map);
			}
			return list;
	    }
		/**
		 * @Description: 根据组织机构及 共享 角色加载已注册的子系统
		 * @author Alice
		 * @date 2016年5月28日 下午9:35:26
		 * @return
		 */
    	@ResponseBody
	    @RequestMapping(value = "/sharelists/{oid}", method = RequestMethod.POST)
	    public List<Map<String,Object>> sharelists(@PathVariable("oid") Long oid,Long srid) {
			List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
			Organization organization = new Organization();
			organization.setId(oid);
			Set<Long> set = new HashSet<Long>();
			if (srid!=null) {
				ShareRole sharerole =new ShareRole();
				sharerole.setId(srid);
				List<ShareRoleSubsystem> srslist = shareRoleSubsystemService.findByShareRole(sharerole);
				for (ShareRoleSubsystem srs : srslist) {
					set.add(srs.getSsRegister().getSubsystem().getId());
				}
			}
			List<SubSystemRegister> temp = subsystemRegisterService.findByOrganization(organization);
			Map<String,Object> map = null;
			for (SubSystemRegister subSystemRegister : temp) {
				map = new HashMap<String,Object>();
				map.put("id", subSystemRegister.getSubsystem().getId());
				map.put("name", subSystemRegister.getSubsystem().getName());
				if (set.contains(subSystemRegister.getSubsystem().getId())) {
					map.put("ck",true);
				}
				list.add(map);
			}
			return list;
	    }
    	
}