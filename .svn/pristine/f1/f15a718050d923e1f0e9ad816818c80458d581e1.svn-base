package com.trgis.ticg.core.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.trgis.ticg.core.model.Organization;
import com.trgis.ticg.core.model.User;

@Repository
public interface UserDao extends JpaRepository<User, Long>,JpaSpecificationExecutor<User>{
	public User findByUsername(String name);
	
	public List<User>  findByOrganization(Organization organization);
}
