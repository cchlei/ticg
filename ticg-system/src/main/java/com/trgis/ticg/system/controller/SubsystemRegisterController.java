package com.trgis.ticg.system.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
//import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.trgis.common.jpa.specfication.ConditionGroup;
import com.trgis.common.jpa.specfication.Operator;
import com.trgis.common.jpa.specfication.OrderBy;
import com.trgis.common.jpa.specfication.SearchCondition;
import com.trgis.common.jpa.specfication.SearchRelation;
import com.trgis.ticg.core.model.Organization;
import com.trgis.ticg.core.model.RoleSubsystem;
import com.trgis.ticg.core.model.ShareRoleSubsystem;
import com.trgis.ticg.core.model.SubSystemRegister;
import com.trgis.ticg.core.model.Subsystem;
import com.trgis.ticg.core.service.OrganizationService;
import com.trgis.ticg.core.service.RoleSubsystemService;
import com.trgis.ticg.core.service.ShareRoleSubsystemService;
import com.trgis.ticg.core.service.SubsystemRegisterService;
import com.trgis.ticg.core.service.SubsystemService;
import com.trgis.ticg.core.util.EnumUtil;
import com.trgis.ticg.core.util.JSONPage;
import com.trgis.ticg.core.util.Result;

/**
 * 
 */
@Controller
@RequestMapping(value = "/subsystemRegister")
public class SubsystemRegisterController {
	@Autowired
	private SubsystemRegisterService subsystemRegisterService;
	@Autowired
	private SubsystemService subsystemService;
	@Autowired
	private RoleSubsystemService roleSubsystemService;
	@Autowired
	private ShareRoleSubsystemService shareRoleSubsystemService;
	@Autowired
	private OrganizationService organizationService;
	protected Logger logger = LoggerFactory.getLogger(getClass());

	@RequestMapping(value = "/index", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView index() {
		ModelAndView mod = new ModelAndView();
		mod.setViewName("subsystemRegister/subsystemRegistermain");
		return mod;

	}

	@ResponseBody
	@RequestMapping(value = "/findSubsystemRegisterById", method = { RequestMethod.GET, RequestMethod.POST })
	public JSONPage findSubsystemRegisterById(Long organizationid, String status, int rows, int page,
			String searchVal) {
		// 配置查询条件组合
		ConditionGroup cgRoot = new ConditionGroup();
		cgRoot.setSearchRelation(SearchRelation.AND);// 基本组合关系为AND
		cgRoot.getConditions().add(new SearchCondition("organization", Operator.EQ, organizationid));
		String[] search = null;
		if (!StringUtils.isEmpty(searchVal)) {
			search = searchVal.split(":");// search[0]值search[1]字段
			// 查询过滤条件为 子条件组
			ConditionGroup filterGroup = new ConditionGroup();
			filterGroup.setSearchRelation(SearchRelation.OR); // 设置条件关系为OR
			// 判断并添加查询条件
			List<SearchCondition> searchFilters = new ArrayList<SearchCondition>(); // 设置条件
			// 角色 部门
			if ("all".equals(search[1])) {
				String[] types = { "organizationname", "subsystemname" };
				for (int i = 0; i < types.length; i++) {
					searchFilters.add(new SearchCondition(types[i], Operator.LIKE, search[0]));
				}
			} else {
				searchFilters.add(new SearchCondition(search[1], Operator.LIKE, search[0]));
			}
			filterGroup.getConditions().addAll(searchFilters);// 将条件集合加入到第二组条件中
			cgRoot.getSubConditions().add(filterGroup);// 讲过滤条件组加入根查询条件中
		}
		OrderBy order = new OrderBy("id", "desc");
		Page<SubSystemRegister> pageResult = subsystemRegisterService.findByConditions(cgRoot, page, rows, order);// findByConditions(cgRoot,
																													// page,
																													// rows,
																													// order);

		JSONPage json = new JSONPage();
		json.setRows(pageResult == null ? new ArrayList<SubSystemRegister>() : pageResult.getContent());
		json.setTotal(pageResult == null ? 0 : (int) pageResult.getTotalElements());
		return json;

	}

	@ResponseBody
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public List<Subsystem> getSubsystemList(Long orgid) {
		List<Subsystem> list = new ArrayList<Subsystem>();
		if (orgid != null) {
			Organization organization = organizationService.findOne(orgid);
			try {
				list = subsystemRegisterService.findSystemRegister(organization);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return list;
	}

	@ResponseBody
	@RequestMapping(value = "/addSubsystemRegister", method = { RequestMethod.GET, RequestMethod.POST })
	public Map<String, Object> addSubsystemRegister(Long id, String subsystem, String startDate, String endDate,
			String remark) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<SubSystemRegister> list = new ArrayList<SubSystemRegister>();
		Organization organization1 = organizationService.findOne(id);
		SubSystemRegister subSystemRegister = null;
		Subsystem subsystem2 = null;
		String[] subsyestemIds = subsystem.split(",");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");// 小写的mm表示的是分钟
		for (int i = 0; i < subsyestemIds.length; i++) {
			subsystem2 = subsystemService.findOne(Long.valueOf(subsyestemIds[i]));
			subSystemRegister = new SubSystemRegister();
			subSystemRegister.setSubsystem(subsystem2);
			try {
				subSystemRegister.setStartDate(sdf.parse(startDate));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			try {
				subSystemRegister.setEndDate(sdf.parse(endDate));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			subSystemRegister.setSubsystem(subsystem2);
			subSystemRegister.setRemark(remark);
			subSystemRegister.setStatus(EnumUtil.DELFLAG.NOMAL.getValue().toString());
			subSystemRegister.setOrganization(organization1);
			list.add(subSystemRegister);
		}
		try {
			subsystemRegisterService.addSubSystemRegister(list);
		} catch (Exception e) {
		}
		map.put("result", "success");
		map.put("msg", "子系统注册成功");
		return map;
	}

	@ResponseBody
	@RequestMapping(value = "/deleteSubsystemRegister", method = { RequestMethod.GET, RequestMethod.POST })
	public Result deleteSubsystemRegister(Long id, Result result) {
		try {
			subsystemRegisterService.deleteSubSystemRegister(id);
			result.setStatus(Result.STATUS_OK);
			logger.debug("删除子系统!");
		} catch (Exception e) {
			result.setStatus(Result.STATUS_FAILED);
			result.setMsg("删除子系统失败");
		}
		result.setMsg("删除子系统成功");
		return result;
	}

	@ResponseBody
	@RequestMapping(value = "/findAllSubsystemRegister", method = { RequestMethod.GET, RequestMethod.POST })
	public JSONPage findAllSubsystemRegister(int rows, int page, String searchVal) {
		// 配置查询条件组合
		ConditionGroup cgRoot = new ConditionGroup();
		cgRoot.setSearchRelation(SearchRelation.AND);// 基本组合关系为AND
		String[] search = null;
		if (!StringUtils.isEmpty(searchVal)) {
			search = searchVal.split(":");// search[0]值search[1]字段
			// 查询过滤条件为 子条件组
			ConditionGroup filterGroup = new ConditionGroup();
			filterGroup.setSearchRelation(SearchRelation.OR); // 设置条件关系为OR
			// 判断并添加查询条件
			List<SearchCondition> searchFilters = new ArrayList<SearchCondition>(); // 设置条件
			// 角色 部门
			if ("all".equals(search[1])) {
				String[] types = { "organizationname", "subsystemname" };
				for (int i = 0; i < types.length; i++) {
					searchFilters.add(new SearchCondition(types[i], Operator.LIKE, search[0]));
				}
			} else {
				searchFilters.add(new SearchCondition(search[1], Operator.LIKE, search[0]));
			}
			filterGroup.getConditions().addAll(searchFilters);// 将条件集合加入到第二组条件中
			cgRoot.getSubConditions().add(filterGroup);// 讲过滤条件组加入根查询条件中
		}
		OrderBy order = new OrderBy("id", "desc");
		Page<SubSystemRegister> pageResult = subsystemRegisterService.findByConditions(cgRoot, page, rows, order);// findByConditions(cgRoot,
																													// page,
																													// rows,
																													// order);
		JSONPage json = new JSONPage();
		json.setRows(pageResult == null ? new ArrayList<SubSystemRegister>() : pageResult.getContent());
		json.setTotal(pageResult == null ? 0 : (int) pageResult.getTotalElements());
		return json;

	}

	@ResponseBody
	@RequestMapping(value = "/checkDeleteSubsystemRegister", method = { RequestMethod.GET, RequestMethod.POST })
	public Result findSubsystemByOrganization(Long id, Result result) {
		List<RoleSubsystem> rslist = new ArrayList<RoleSubsystem>();
		List<ShareRoleSubsystem> srslist = new ArrayList<ShareRoleSubsystem>();
		try {
			SubSystemRegister subSystemRegister = subsystemRegisterService.findOne(id);
			rslist = roleSubsystemService.findBySubSystemRegister(subSystemRegister);
			srslist = shareRoleSubsystemService.findBySubSystemRegister(subSystemRegister);
			if (rslist.size() == 0 && srslist.size() == 0) {
				result.setMsg(Result.DEFAUL_MESSAGE);
			} else {
				if (rslist.size() > 0) {
					result.setStatus(Result.STATUS_FAILED);
					result.setMsg("请先删除角色已注册子系统");
					return result;
				}
				if (srslist.size() > 0) {
					result.setStatus(Result.STATUS_FAILED);
					result.setMsg("请先删除共享角色已注册子系统");
					return result;
				}
			}

		} catch (Exception e) {
			result.setStatus(Result.STATUS_FAILED);
		}
		return result;
	}

}