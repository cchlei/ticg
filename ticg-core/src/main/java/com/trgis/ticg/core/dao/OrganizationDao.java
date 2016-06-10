package com.trgis.ticg.core.dao;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.trgis.ticg.core.model.Organization;

@Repository
public interface OrganizationDao extends JpaRepository<Organization, Long>,JpaSpecificationExecutor<Organization> {
	
	@Query(nativeQuery=true,value="select * from ticg_organization where parent_organization = ?1 and status = ?2")
	public Set<Organization> findSubOrganizations(Long fatherid, Integer status);
	@Query(nativeQuery=true,value="select * from ticg_organization where parent_organization is null")
	public List<Organization> findRootOrganizations();
	@Query(nativeQuery=true,value="select * from ticg_organization where id = ?1 and status = ?2")
	public List<Organization> findChildren(Long fatherid, Integer status);
}
