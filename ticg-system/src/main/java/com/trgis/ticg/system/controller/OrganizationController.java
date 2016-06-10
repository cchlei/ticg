package com.trgis.ticg.system.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.trgis.ticg.core.model.Organization;
import com.trgis.ticg.core.model.Role;
import com.trgis.ticg.core.model.ShareRoleOrg;
import com.trgis.ticg.core.model.SubSystemRegister;
import com.trgis.ticg.core.model.User;
import com.trgis.ticg.core.service.OrganizationService;
import com.trgis.ticg.core.service.RoleService;
import com.trgis.ticg.core.service.ShareRoleOrgService;
import com.trgis.ticg.core.service.SubsystemRegisterService;
import com.trgis.ticg.core.service.UserService;
import com.trgis.ticg.core.util.Result;
import com.trgis.ticg.system.core.service.SystemUserService;

@Controller
@RequestMapping("/organization")
public class OrganizationController {

	private static final Logger logger = LoggerFactory.getLogger(OrganizationController.class);

	@Autowired
	private OrganizationService organizationService;
	@Autowired
	SubsystemRegisterService srService;
	@Autowired
	private SystemUserService systemUserService;
	@Autowired
	private ShareRoleOrgService shareRoleOrgService;
	@Autowired
	private RoleService roleService ;
	@Autowired
	private UserService userService ;

	@ResponseBody
	@RequestMapping(value = "/addOrganization", method = { RequestMethod.GET, RequestMethod.POST })
	public Map<String, Object> addOrganization(Organization org, Long parentId) {
		Map<String, Object> map = new HashMap<String, Object>();
		Organization organization1 = organizationService.findOne(parentId);
		org.setParentOrganization(organization1);
		try {
			organizationService.addOrganization(org);
			logger.debug("添加组织机构!");
		} catch (Exception e) {
			logger.debug("添加组织机构失败!");
			map.put("result", "error");
			map.put("msg", "添加组织机构失败");
		}
		map.put("result", "success");
		map.put("msg", "添加组织机构成功");
		return map;
	}

	@ResponseBody
	@RequestMapping(value = "/editOrganization", method = { RequestMethod.GET, RequestMethod.POST })
	public Map<String, Object> editOrganization(Organization org, Long parentId, String d) {
		Map<String, Object> map = new HashMap<String, Object>();
		Organization organization = organizationService.findOne(parentId);
		org.setParentOrganization(organization);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");// 小写的mm表示的是分钟
		try {
			org.setDate(sdf.parse(d));
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			if (org.getId() != null && !organizationService.findOne(org.getId()).getOrgnazationName()
					.equals(org.getOrgnazationName())) {
				// @author Alice add
				// 若修改了名字级联更新系统用户表中的organizationname列
				systemUserService.updateOrgname(org.getOrgnazationName(), org);
			}
			organizationService.editOrganization(org);
			logger.debug("编辑组织机构!");
		} catch (Exception e) {
			map.put("result", "error");
			map.put("msg", "编辑组织机构失败");
		}
		map.put("result", "success");
		map.put("msg", "编辑组织机构成功");
		return map;
	}

	@ResponseBody
	@RequestMapping(value = "/deleteOrganization", method = { RequestMethod.GET, RequestMethod.POST })
	public Result deleteOrganization(Long id, Result result) {
		try {
			organizationService.deleteOrganization(id);
			result.setStatus(Result.STATUS_OK);
			logger.debug("删除组织机构!");
		} catch (Exception e) {
			result.setStatus(Result.STATUS_FAILED);
			result.setMsg("删除组织机构失败");
		}
		result.setMsg("删除组织机构成功");
		return result;
	}

	@ResponseBody
	@RequestMapping(value = "/searchOrganization", method = { RequestMethod.GET, RequestMethod.POST })
	public Map<String, Object> findSubOrganizations(Organization org) {
		Map<String, Object> map = new HashMap<String, Object>();
		Set<Organization> subOrganizationList = new HashSet<Organization>();
		try {
			subOrganizationList = organizationService.findSubOrganizations(org);
			logger.debug("查询组织机构!");
		} catch (Exception e) {
			map.put("result", "error");
			map.put("msg", "查询组织机构失败");
		}
		map.put("subOrganizationList", subOrganizationList);
		map.put("result", "success");
		return map;
	}

	@RequestMapping(value = "/toIndex", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView toIndex() {
		ModelAndView mod = new ModelAndView();
		mod.setViewName("organization/organizationindex");
		return mod;

	}

	@ResponseBody
	@RequestMapping(value = "/findRootOrganizations", method = { RequestMethod.GET, RequestMethod.POST })
	public List<Organization> findRootOrganizations() {
		List<Organization> organizationList = null;
		try {
			organizationList = organizationService.findRootOrganizations();
			logger.debug("查询组织机构根节点!");
		} catch (Exception e) {
		}
		return organizationList;
	}

	/**
	 * @Description: 组织机构树
	 * @author yanpeng
	 * @date 2016年5月23日 上午11:24:52
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/orgs", method = { RequestMethod.GET, RequestMethod.POST })
	public List<Map<String, Object>> treeOrgs(Long oid) {
		List<Organization> organizationList = null;
		List<Map<String, Object>> tojsons = null;
		try {
			if (oid == null) {
				organizationList = organizationService.findRootOrganizations();
			} else {
				organizationList = organizationService.findChilren(oid);
			}
			tojsons = tojsons(organizationList);
			logger.debug("查询组织机构根节点!");
		} catch (Exception e) {
		}
		return tojsons;
	}

	/**
	 * @Description: 递归分装树
	 * @author yanpeng
	 * @date 2016年5月23日 上午11:24:31
	 * @param organizationList
	 * @return
	 */
	private List<Map<String, Object>> tojsons(List<Organization> organizationList) {
		if (organizationList == null || organizationList.size() == 0) {
			return new ArrayList<Map<String, Object>>();
		}
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = null;
		for (Organization organization : organizationList) {
			map = new HashMap<String, Object>();
			map.put("id", organization.getId());
			map.put("text", organization.getOrgnazationName());
			map.put("children", tojsons(organization.getChildren()));
			list.add(map);
		}
		return list;
	}

	@ResponseBody
	@RequestMapping(value = "/findAllOrganizations", method = { RequestMethod.GET, RequestMethod.POST })
	public List<Organization> findAllOrganizations() {
		List<Organization> organizationList = null;
		try {
			organizationList = organizationService.findAll();
			logger.debug("查询组织机构根节点!");
		} catch (Exception e) {
		}
		return organizationList;
	}

	@ResponseBody
	@RequestMapping(value = "/checkDeleteOrganization", method = { RequestMethod.GET, RequestMethod.POST })
	public Result findSubsystemByOrganization(Long orgid, Result result) {
		List<SubSystemRegister> sublist = new ArrayList<SubSystemRegister>();
		List<ShareRoleOrg> srlist = new ArrayList<ShareRoleOrg>();
		List<Role> rolelist = new ArrayList<Role>();
		List<User> userlist = new ArrayList<User>();
		try {
			Organization organization = organizationService.findOne(orgid);
			sublist = srService.findByOrganization(organization);
			srlist = shareRoleOrgService.findByOrg(orgid);
			rolelist = roleService.findByOrg(orgid);
			userlist = userService.findByOrg(organization);
			if (sublist.size() == 0 && srlist.size() == 0 && rolelist.size() == 0 && userlist.size() == 0) {
				result.setMsg(Result.DEFAUL_MESSAGE);
			} else {
				if (sublist.size() > 0) {
					result.setStatus(Result.STATUS_FAILED);
					result.setMsg("请先删除组织机构下的子系统");
					return result;
				}
				if (srlist.size() > 0) {
					result.setStatus(Result.STATUS_FAILED);
					result.setMsg("请先删除组织机构下的共享角色");
					return result;
				}
				if (rolelist.size() > 0) {
					result.setStatus(Result.STATUS_FAILED);
					result.setMsg("请先删除组织机构下的角色");
					return result;
				}
				if (userlist.size() > 0) {
					result.setStatus(Result.STATUS_FAILED);
					result.setMsg("请先删除组织机构下的用户");
					return result;
				}
			}

		} catch (Exception e) {
			result.setStatus(Result.STATUS_FAILED);
		}
		return result;
	}
}
