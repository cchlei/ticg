package com.trgis.ticg.core.service;

import java.util.List;
import java.util.Set;

import com.trgis.ticg.core.model.Organization;

public interface OrganizationService {
	/**
	 * 根据ID查找
	 * @param id
	 * @return
	 */
	public Organization findOne(Long id);
	/**
	 * 添加组织机构
	 * @param org
	 */
	public Organization addOrganization(Organization org);
	/**
	 * 编辑组织机构信息
	 * @param org
	 * @return
	 */
	public Organization editOrganization(Organization org);
	/**
	 * 删除组织机构
	 * @param org
	 */
	public void deleteOrganization(Long id);
	/**
	 * 查询当前组织机构的子节点
	 * @param org
	 * @return
	 */
	public Set<Organization> findSubOrganizations(Organization org);
	/**
	 * 查询当前组织机构的根节点
	 * @return
	 */
	public List<Organization>  findRootOrganizations();
	/**
	 * 查询所有组织机构
	 * @return
	 */
	public List<Organization>  findAll();
	/**
	 * @Description: 查询子节点
	 * @author yanpeng
	 * @date 2016年5月24日 下午6:54:02
	 * @param oid
	 * @return
	 */
	public List<Organization> findChilren(Long oid);
	
}
