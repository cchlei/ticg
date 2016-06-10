package com.trgis.ticg.core.service.impl;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.trgis.ticg.core.dao.OrganizationDao;
import com.trgis.ticg.core.model.Organization;
import com.trgis.ticg.core.service.OrganizationService;
import com.trgis.ticg.core.util.EnumUtil;

@Transactional
@Service
public class OrganizationServiceImpl implements OrganizationService {
	@Autowired
	private OrganizationDao organizationDao;
	
	@Override
	public Organization findOne(Long id) {
		return organizationDao.findOne(id);
	}
	
	@Override
	public Organization addOrganization(Organization org) {
		return organizationDao.saveAndFlush(org);

	}

	@Override
	public Organization editOrganization(Organization org) {
		return organizationDao.saveAndFlush(org);
	}

	@Override
	public void deleteOrganization(Long id) {
		try {
			organizationDao.delete(id);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public Set<Organization> findSubOrganizations(Organization org) {
		return organizationDao.findSubOrganizations(org.getId(), EnumUtil.DELFLAG.NOMAL.getValue());
	}

	@Override
	public List<Organization> findRootOrganizations() {
		List<Organization> organizations = organizationDao.findRootOrganizations();
		Hibernate.initialize(organizations);//强制加载这样就相当于动态改变为lazy=false
		for (Iterator<Organization> iterator = organizations.iterator(); iterator.hasNext();) {
			Organization organization = (Organization) iterator.next();
			initlize(organization);
		}
		return organizations;
	}
	/**
	 * 递归 完成所有子对象的初始化 .
	 * @param permission .
	 */
	private static void initlize(Organization organization) {
		Hibernate.initialize(organization.getChildren());
		List<Organization> organizations = organization.getChildren();
		Assert.notNull(organizations);
		for (Iterator<Organization> iterator = organizations.iterator(); iterator.hasNext();) {
			Organization o = (Organization) iterator.next();
			initlize(o);
		}
	}

	@Override
	public List<Organization> findAll() {
		return organizationDao.findAll();
	}

	@Override
	public List<Organization> findChilren(Long oid) {
		return organizationDao.findChildren(oid, EnumUtil.DELFLAG.NOMAL.getValue());
	}
	
}
