package com.trgis.ticg.core.service;



import java.util.List;

import org.springframework.data.domain.Page;

import com.trgis.ticg.core.model.Organization;
import com.trgis.ticg.core.model.User;

public interface UserService {
	
	User findUserByUsername(String username);
	
	User createUser(User user);
	
	User modfityUser(User user);
	
	User modfityPassword(String password);
	
	
	User findUserByUser(User user);
	
	public Page<User> findByConditions(int pageNum, int pageSize,User user,String searchValue);

	void removeUser(Long id);
	
	public List<User> findByOrg(Organization organization)  ;
	
}
