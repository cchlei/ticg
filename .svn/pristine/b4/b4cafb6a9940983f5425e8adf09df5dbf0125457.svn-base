package com.trgis.ticg.core.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.trgis.common.jpa.specfication.ConditionGroup;
import com.trgis.common.jpa.specfication.DynamicSpecficationUtil;
import com.trgis.common.jpa.specfication.Operator;
import com.trgis.common.jpa.specfication.OrderBy;
import com.trgis.common.jpa.specfication.PageAndSortUtil;
import com.trgis.common.jpa.specfication.SearchCondition;
import com.trgis.common.jpa.specfication.SearchRelation;
import com.trgis.ticg.core.dao.UserDao;
import com.trgis.ticg.core.model.Organization;
import com.trgis.ticg.core.model.User;
import com.trgis.ticg.core.service.UserService;
import com.trgis.ticg.core.util.BeanUtil;
import com.trgis.ticg.core.util.EnumUtil;

@Transactional
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;
	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	@Override
	public User findUserByUsername(String username) {
		try {
			logger.debug("查询" + username + "的相关信息");
			return userDao.findByUsername(username);
		} catch (Exception e) {
			logger.debug("查询" + username + "的相关信息失败" + e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public User createUser(User user) {
		logger.debug("添加用户start");
		try {
			user.setRegistTime(new Date());
			user.setState(EnumUtil.STATUS.YQY.getValue());
			user = userDao.save(user);
		} catch (Exception e) {
			logger.debug("添加用户失败!" + e.getMessage());
		}
		logger.debug("添加用户end");
		return user;
	}

	@Override
	public User modfityUser(User user) {
		User old = userDao.findOne(user.getId());
		user.setRegistTime(old.getRegistTime());
		user.setOrganization(old.getOrganization());
		return userDao.save(user);
	}

	@Override
	public User modfityPassword(String password) {
		return null;
	}

	@Override
	public void removeUser(Long id) {
		userDao.delete(id);
	}

	@Override
	public User findUserByUser(User user) {
		return userDao.findOne(user.getId());

	}

	/**
	 * 查询用户
	 */
	@Override
	public Page<User> findByConditions(int pageNum, int pageSize, User user, String searchValue) {

		ConditionGroup cgroot = new ConditionGroup();
		cgroot.setSearchRelation(SearchRelation.AND);
		if (BeanUtil.isNotEmpty(user)) {
			List<SearchCondition> conditions = new ArrayList<SearchCondition>();
			if (BeanUtil.isNotEmpty(user.getOrganization()) && BeanUtil.isNotEmpty(user.getOrganization().getId())) {
				conditions.add(new SearchCondition("organization", Operator.EQ, user.getOrganization().getId()));
			}
			if (searchValue != null && searchValue.trim().length() > 0) {
				String[] searchs = searchValue.split(":");
				if (searchs.length > 1 && !StringUtils.isBlank(searchs[1])) {
					conditions.add(new SearchCondition("username", Operator.LIKE, searchs[1]));
				}
			}
			cgroot.setConditions(conditions);
		}
		OrderBy order = new OrderBy("id", "asc");
		Specification<User> specification = DynamicSpecficationUtil.buildSpecfication(cgroot);
		Long count = userDao.count(specification);
		if (count == 0) {
			return null;
		}
		PageRequest page = PageAndSortUtil.buildPageAndSort(count, pageNum, pageSize, order);
		return userDao.findAll(specification, page);
	}

	@Override
	public List<User> findByOrg(Organization organization) {
		return userDao.findByOrganization(organization);
	}

}
