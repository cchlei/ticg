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
import org.springframework.web.servlet.ModelAndView;

import com.trgis.common.jpa.specfication.ConditionGroup;
import com.trgis.common.jpa.specfication.Operator;
import com.trgis.common.jpa.specfication.OrderBy;
import com.trgis.common.jpa.specfication.SearchCondition;
import com.trgis.common.jpa.specfication.SearchRelation;
import com.trgis.ticg.core.exception.AuthcException;
import com.trgis.ticg.core.exception.MenuException;
import com.trgis.ticg.core.model.Menu;
import com.trgis.ticg.core.model.Role;
import com.trgis.ticg.core.model.ShareRole;
import com.trgis.ticg.core.model.Subsystem;
import com.trgis.ticg.core.service.AuthcService;
import com.trgis.ticg.core.service.MenuService;
import com.trgis.ticg.core.service.RoleMenuService;
import com.trgis.ticg.core.service.ShareRoleMenuService;
import com.trgis.ticg.core.service.SubsystemService;
import com.trgis.ticg.core.util.BeanUtil;
import com.trgis.ticg.core.util.JSONPage;
import com.trgis.ticg.core.util.Result;

/**
 * 菜单资源
 * 
 * @author Alice
 *
 * 2016年5月20日
 */
@Controller
@RequestMapping(value = "/menu")
public class MenuController {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    protected MenuService menuService;
    
    @Autowired
    protected SubsystemService subsystemService;
    
    @Autowired
    protected RoleMenuService roleMenuService;
    
    @Autowired
    protected ShareRoleMenuService shareRoleMenuService;
    
    @Autowired
    protected AuthcService authcService;
    
	/**
	 * @author Alice
	 * 菜单列表页面转发
	 * @return
	 */
	@RequestMapping(value = "/list/{id}", method = RequestMethod.GET)
	public ModelAndView list(@PathVariable("id") Long systemid, ModelAndView mv) {
		mv.addObject("systemid",systemid);
		mv.setViewName("/subsystem/menulist");
		return mv;
	}
	
    /**
     * @author Alice
     * 菜单资源分页列表
     * @return
     */
	@ResponseBody
    @RequestMapping(value = "/list/{id}", method = RequestMethod.POST)
    public JSONPage getMenuList(@PathVariable("id") Long systemid, int rows, int page, String searchVal) {
    	// 配置查询条件组合
 		ConditionGroup cgRoot = new ConditionGroup();
 		cgRoot.setSearchRelation(SearchRelation.AND);// 基本组合关系为AND
 		Subsystem subsystem = subsystemService.findOne(systemid);
 		cgRoot.getConditions().add(new SearchCondition("subsystem", Operator.EQ, subsystem));
 		JSONPage json = new JSONPage();
 		try {
	    	//查询条件
	    	String[] search= null;
	    	if(!StringUtils.isEmpty(searchVal)){
				search = searchVal.split(":");//search[0]值search[1]字段
				// 查询过滤条件为 子条件组
				ConditionGroup filterGroup = new ConditionGroup();
				filterGroup.setSearchRelation(SearchRelation.OR); // 设置条件关系为OR
				// 判断并添加查询条件
				List<SearchCondition> searchFilters = new ArrayList<SearchCondition>(); // 设置条件
				if ("all".equals(search[1])) {
					 String [] types = {"name","code","url"};
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
			Page<Menu> pageResult = menuService.findByConditions(cgRoot, page, rows, order);
			json.setRows(pageResult == null?new ArrayList<Menu>():pageResult.getContent());
			json.setTotal(pageResult == null?0:(int)pageResult.getTotalElements());
		} catch (MenuException e) {
			e.printStackTrace();
		}
		return json;
    }
	
	/**
	 * @Description:添加菜单
	 * @Author liuyan 
	 * @Date 2016年5月20日 下午15:46:12
	 *
	 * @param result
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/addmenu/{id}",method=RequestMethod.POST)
	public Result menuAdd(@PathVariable("id") Long sysid,Menu menu,Result result){
		try{
			//穿过了的menu带有id
			Menu mu= new Menu();
			mu.setCode(menu.getCode());
			mu.setName(menu.getName());
			mu.setUrl(menu.getUrl());
			//先判断是否重名
	        if (mu.getId() == null || !menuService.findOne(mu.getId()).getName().equals(mu.getName())) { //新增或者修改了用户名(即需要做重名判断)
	        	List<Menu> muu = menuService.findMenuByNameOrCode(sysid,"name", mu.getName());
	        	if(muu.size()>0){
	        		result.setMsg("name已存在！");
	        		result.setStatus(Result.STATUS_FAILED);
	        		logger.debug("name重复啦");
	        		return result;
	        	}
	        }
	        //判断code是否重复
	        if (mu.getId() == null || !menuService.findOne(mu.getId()).getCode().equals(mu.getCode())) { //新增或者修改了code(即需要做重名判断)
	        	List<Menu> muu =menuService.findMenuByNameOrCode(sysid,"code", mu.getCode());
	        	if(muu.size()>0){
	        		result.setMsg("code已存在！");
	        		result.setStatus(Result.STATUS_FAILED);
	        		logger.debug("code重复啦");
	        		return result;
	        	}
	        }
			Subsystem sys = subsystemService.findOne(sysid);
			if(sys!=null){
				mu.setSubsystem(sys);
			}
			menuService.addMenu(mu);
			result.setStatus(Result.STATUS_OK);
			result.setMsg("菜单添加成功！");
		}catch(Exception e){
			result.setStatus(Result.STATUS_FAILED);
			result.setMsg("菜单添加失败！");
			logger.debug(e.getMessage());
		}
		return result;
	}
	
	/**
	 * @Description:修改菜单
	 * @Author liuyan 
	 * @Date 2016年5月20日
	 * @param menu
	 * @param result
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/editmenu/{id}",method=RequestMethod.POST)
	public Result menuEdit(@PathVariable("id") Long sysid, Menu menu,Result result){
		try{
			//先判断是否重名
	        if (menu.getId() == null || !menuService.findOne(menu.getId()).getName().equals(menu.getName())) { //新增或者修改了用户名(即需要做重名判断)
	        	List<Menu> muu = menuService.findMenuByNameOrCode(sysid,"name", menu.getName());
	        	if(muu.size()>0){
	        		result.setMsg("name已存在！");
	        		result.setStatus(Result.STATUS_FAILED);
	        		logger.debug("name重复啦");
	        		return result;
	        	}
	        }
	        //判断code是否重复
	        if (menu.getId() == null || !menuService.findOne(menu.getId()).getCode().equals(menu.getCode())) { //新增或者修改了code(即需要做重名判断)
	        	List<Menu> muu =menuService.findMenuByNameOrCode(sysid,"code", menu.getCode());
	        	if(muu.size()>0){
	        		result.setMsg("code已存在！");
	        		result.setStatus(Result.STATUS_FAILED);
	        		logger.debug("code重复啦");
	        		return result;
	        	}
	        }
			Subsystem sys = subsystemService.findOne(sysid);
			menu.setSubsystem(sys);
			menuService.editMenu(menu);
			result.setStatus(Result.STATUS_OK);
			result.setMsg("菜单修改成功！");
		}catch(Exception e){
			result.setStatus(Result.STATUS_FAILED);
			result.setMsg("菜单修改失败！");
			logger.debug(e.getMessage());
		}
		return result;
	}
	
	
	/**
	 * @Description:删除
	 * @Author liuyan 
	 * @Date 2016年5月20日 上午9:49:10
	 * @param menu
	 * @param result
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/delmenu",method=RequestMethod.POST)
	public Result delMenu(String ids,Result result){
		//待优化  获取前端的id数组转变为LONG型数组
		String idss = ids.substring(1, ids.length()-1);
		String[] id = idss.split(",");
		Long[] ints = new Long[id.length];
		for(int i=0;i<id.length;i++){
			ints[i] = Long.parseLong(id[i]);
		}

		try{
			menuService.delMenu(ints);
			result.setStatus(Result.STATUS_OK);
			result.setMsg("菜单删除成功！");
		}catch(Exception e){
			result.setStatus(Result.STATUS_FAILED);
			result.setMsg("菜单删除失败！");
			logger.debug(e.getMessage());
		}
		return result;
	}

	
	/**
	 * @Description: 根据子系统id获取菜单列表
	 * @author yanpeng
	 * @date 2016年5月24日 下午2:31:22
	 * @param sysid
	 * @param Long rid 角色id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/menus/{id}")
	public JSONPage menus(@PathVariable("id") Long sysid,Long rid){
		JSONPage jsonPage = new JSONPage();
		try {
			List<Map<String,Object>> rows = new ArrayList<Map<String,Object>>();
			Map<String,Object> map = null;
			List<Menu> list = menuService.findBySystem(sysid);
			Set<Long> meuns = new HashSet<Long>();
			if(rid!=null){
				Role role = new Role();
				role.setId(rid);
				meuns = roleMenuService.findByRole(role);
			}
			for (Menu menu : list) {
				map = new HashMap<String,Object>();
				map.put("id", menu.getId());
				map.put("name", menu.getName());
				//操作
				List<Map<String, Object>> authcs = authcService.findByMenu(menu.getId(), rid);
				String str = "";
				for (Map<String, Object> map2 : authcs) {
					if (!(boolean) map2.get("checked")) {
						str += "<input type='checkbox' name='authc"+menu.getId()+"' value='"+map2.get("id")+"'>"+map2.get("name")+"</input>";
					}else{
						str += "<input type='checkbox' name='authc"+menu.getId()+"' value='"+map2.get("id")+"' checked='true'>"+map2.get("name")+"</input>";
					}
				}
				map.put("authcs", str);
				
				if (meuns.contains(menu.getId())) {
					map.put("ck", true);
				}
				rows.add(map);
			}
			jsonPage.setRows(rows);
		} catch (MenuException | AuthcException e) {
			e.printStackTrace();
		}
		return jsonPage;
	}
	/**
	 * 获取共享菜单
	 * @param sysid
	 * @param rid
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/sharemenus/{id}")
	public JSONPage sharemenus(@PathVariable("id") Long sysid,Long srid){
		JSONPage jsonPage = new JSONPage();
		try {
			List<Map<String,Object>> rows = new ArrayList<Map<String,Object>>();
			Map<String,Object> map = null;
			List<Menu> list = menuService.findBySystem(sysid);
			Set<Long> meuns = new HashSet<Long>();
			if(BeanUtil.isNotEmpty(srid)){
				ShareRole sharerole = new ShareRole();
				sharerole.setId(srid);
				meuns = shareRoleMenuService.findByShareRole(sharerole);
			}
			for (Menu menu : list) {
				map = new HashMap<String,Object>();
				map.put("id", menu.getId());
				map.put("name", menu.getName());
				//操作
				List<Map<String, Object>> authcs = authcService.findByShareMenu(menu.getId(), srid);
				String str = "";
				for (Map<String, Object> map2 : authcs) {
					if (!(boolean) map2.get("ck")) {
						str += "<input type='checkbox' name='authc"+menu.getId()+"' value='"+map2.get("id")+"'>"+map2.get("name")+"</input>";
					}else{
						str += "<input type='checkbox' name='authc"+menu.getId()+"' value='"+map2.get("id")+"' checked='true'>"+map2.get("name")+"</input>";
					}
				}
				map.put("authcs", str);
				
				if (meuns.contains(menu.getId())) {
					map.put("ck", true);
				}else{
					map.put("ck", false);
				}
				rows.add(map);
			}
			jsonPage.setRows(rows);
		} catch (MenuException | AuthcException e) {
			e.printStackTrace();
		}
		return jsonPage;
	}
}