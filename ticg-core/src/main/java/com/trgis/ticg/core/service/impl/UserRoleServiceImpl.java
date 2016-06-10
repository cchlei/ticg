package com.trgis.ticg.core.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.trgis.common.jpa.specfication.ConditionGroup;
import com.trgis.common.jpa.specfication.DynamicSpecficationUtil;
import com.trgis.common.jpa.specfication.Operator;
import com.trgis.common.jpa.specfication.SearchCondition;
import com.trgis.common.jpa.specfication.SearchRelation;
import com.trgis.ticg.core.dao.UserRoleDao;
import com.trgis.ticg.core.model.User;
import com.trgis.ticg.core.model.UserRole;
import com.trgis.ticg.core.service.UserRoleService;
import com.trgis.ticg.core.util.BeanUtil;

@Service
public class UserRoleServiceImpl implements UserRoleService {

	private Logger logger = LoggerFactory.getLogger(UserRoleServiceImpl.class);

	@Autowired
	UserRoleDao userRoleDao;

	@Override
	public List<UserRole> findByUser(User user) {
		logger.debug("start UserRole find");
		ConditionGroup cgroot = new ConditionGroup();
		List<UserRole> userRoles = null;
		cgroot.setSearchRelation(SearchRelation.AND);
		try {
			userRoles =	userRoleDao.findAll(initSpec(user));
		} catch (Exception e) {
			logger.debug("find UserRole filed!"+e.getMessage());
		}
		return userRoles;
	}

	private Specification<UserRole> initSpec(User user){
		ConditionGroup cgroot = new ConditionGroup();
		cgroot.setSearchRelation(SearchRelation.AND);
		if(BeanUtil.isNotEmpty(user)){
			List<SearchCondition> conditions = new ArrayList<SearchCondition>();
			if(BeanUtil.isNotEmpty(user.getId())){
				conditions.add(new SearchCondition("user.id",Operator.EQ,user.getId()));
			}
			cgroot.setConditions(conditions);
		}
		Specification<UserRole> specification = DynamicSpecficationUtil.buildSpecfication(cgroot);
		return specification;
	}

	@Override
	public void registUserRole(User user, List<UserRole> userRoles) {
		try{
			List<UserRole> userRolesOld = userRoleDao.findAllByUser(user);
			if(userRolesOld!=null && userRolesOld.size()>0){				
				userRoleDao.delete(userRolesOld);
			}
			userRoleDao.save(userRoles);
		}catch(Exception e){
			logger.debug("分配角色失败!"+e.getMessage());
			e.printStackTrace();
		}
	}

}
