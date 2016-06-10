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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.trgis.common.jpa.specfication.ConditionGroup;
import com.trgis.common.jpa.specfication.Operator;
import com.trgis.common.jpa.specfication.OrderBy;
import com.trgis.common.jpa.specfication.SearchCondition;
import com.trgis.common.jpa.specfication.SearchRelation;
import com.trgis.ticg.core.exception.AuthcException;
import com.trgis.ticg.core.exception.MenuException;
import com.trgis.ticg.core.model.Authc;
import com.trgis.ticg.core.model.Menu;
import com.trgis.ticg.core.service.AuthcService;
import com.trgis.ticg.core.service.MenuService;
import com.trgis.ticg.core.service.RoleAuthcService;
import com.trgis.ticg.core.service.SubsystemService;
import com.trgis.ticg.core.util.BeanUtil;
import com.trgis.ticg.core.util.JSONPage;
import com.trgis.ticg.core.util.Result;

/**
 * 操作
 * 
 * @author Alice
 *
 * 2016年5月20日
 */
@Controller
@RequestMapping(value = "/authc")
public class AuthcController {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    protected AuthcService authcService;
    
    @Autowired
    protected MenuService menuService;
    
    @Autowired
    protected SubsystemService subsystemService;
    @Autowired
    protected RoleAuthcService roleAuthcService;
    
    /**
     * @author Alice
     * 操作分页列表
     * @return
     */
	@ResponseBody
    @RequestMapping(value = "/list/{id}", method = RequestMethod.POST)
    public JSONPage getMenuList(@PathVariable("id") Long menuid, int rows, int page, String searchVal) {
    	// 配置查询条件组合
 		ConditionGroup cgRoot = new ConditionGroup();
 		cgRoot.setSearchRelation(SearchRelation.AND);// 基本组合关系为AND
 		JSONPage json = new JSONPage();
 		try {
	 		Menu menu = menuService.findOne(menuid);
	 		cgRoot.getConditions().add(new SearchCondition("menu", Operator.EQ, menu));
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
					 String [] types = {"name","code","note"};
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
			Page<Authc> pageResult = authcService.findByConditions(cgRoot, page, rows, order);
			json.setRows(pageResult == null?new ArrayList<Menu>():pageResult.getContent());
			json.setTotal(pageResult == null?0:(int)pageResult.getTotalElements());
 		} catch (MenuException e) {
 			logger.debug(menuid + "id菜单查询失败!"+e.getMessage());
			e.printStackTrace();
 		} catch (AuthcException e) {
			e.printStackTrace();
		}
		return json;
    }
	/**
	 * @Description:添加修改操作
	 * @Author liuyan 
	 * @Date 2016年5月25日 下午5:27:30
	 * @param muid
	 * @param authc
	 * @param result
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/addauthc/{id}",method=RequestMethod.POST)
	public Result authAdd(@PathVariable("id") Long muid,Authc authc,Result result){
		
			//先判断是否重名
	        try {
				if (authc.getId() == null || !authcService.findOne(authc.getId()).getName().equals(authc.getName())) { //新增或者修改了用户名(即需要做重名判断)
					List<Authc> au= authcService.findByNameOrCode(muid.toString(),"name", authc.getName());
					if(au.size()>0){
						result.setMsg("name已存在！");
						result.setStatus(Result.STATUS_FAILED);
						logger.debug("name重复啦");
						return result;
					}
				}
			
	        //判断code是否重复
	        if (authc.getId() == null || !authcService.findOne(authc.getId()).getCode().equals(authc.getCode())) { //新增或者修改了code(即需要做重名判断)
	        	List<Authc> au= authcService.findByNameOrCode(muid.toString(),"code", authc.getCode());
	        	if(au.size()>0){
	        		result.setMsg("code已存在！");
	        		result.setStatus(Result.STATUS_FAILED);
	        		logger.debug("code已存在");
	        		return result;
	        	}
	        }
	        
	        } catch (AuthcException e2) {
	        	logger.debug("重复性检测部分出错");
				e2.printStackTrace();
			}
	        try {
				Menu menu =menuService.findOne(muid);
	                if(BeanUtil.isNotEmpty(menu)&& menu!=null){
	                	authc.setMenu(menu);//赋值
	                }
			        if (authc.getId() == null){//添加
			        	try {
			        		authcService.add(authc);
			        		result.setStatus(Result.STATUS_OK);
							result.setMsg("操作添加成功！");
						} catch (Exception e) {
							result.setStatus(Result.STATUS_FAILED);
							result.setMsg("操作添加失败！");
							logger.debug(e.getMessage());
						}
			        	
					}else{
						try {//修改
							if(authcService.findOne(authc.getId()).equals(authc)){
								result.setStatus(Result.STATUS_FAILED);
								result.setMsg("操作没有改动！");
							}else{
								authcService.edit(authc);
								result.setStatus(Result.STATUS_OK);
								result.setMsg("操作修改成功！");
							}
						} catch (Exception e) {
							result.setStatus(Result.STATUS_FAILED);
							result.setMsg("操作修改失败！");
							logger.debug(e.getMessage());
						}
						
					}
	        } catch (MenuException e1) {
				logger.debug("操作查找失败");
				e1.printStackTrace();
			}     
		return result;
	}
	/**
	 * @Description:删除
	 * @Author liuyan 
	 * @Date 2016年5月25日 下午5:23:47
	 * @param id
	 * @param result
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/delauthc",method=RequestMethod.POST)
	public Result delauthc(String id,Result result){
		try{
			authcService.del(Long.parseLong(id));
			result.setStatus(Result.STATUS_OK);
			result.setMsg("操作删除成功！");
		}catch(Exception e){
			result.setStatus(Result.STATUS_FAILED);
			result.setMsg("操作删除失败！");
			logger.debug(e.getMessage());
		}
		return result;
	}
	
	
}