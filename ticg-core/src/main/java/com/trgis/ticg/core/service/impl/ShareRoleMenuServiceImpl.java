package com.trgis.ticg.core.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.trgis.ticg.core.dao.ShareRoleMenuDao;
import com.trgis.ticg.core.model.ShareRole;
import com.trgis.ticg.core.model.ShareRoleMenu;
import com.trgis.ticg.core.service.ShareRoleMenuService;
/**
 * 共享角色菜单Service
 * 
 * @author Alice
 *
 * 2016年5月19日
 */
@Transactional
@Service
public class ShareRoleMenuServiceImpl implements ShareRoleMenuService {

	@Autowired
	private ShareRoleMenuDao shareRoleMenuDao;
	
	private static final Logger logger = LoggerFactory.getLogger(ShareRoleMenuServiceImpl.class);

	@Override
	public void add(ShareRoleMenu shareRoleMenu) {
		try {
			shareRoleMenuDao.save(shareRoleMenu);
		} catch (Exception e) {
			logger.debug("新增共享角色菜单关系失败"+e.getMessage());
			throw e;
		}
	}

	@Override
	public void del(ShareRoleMenu shareRoleMenu) {
		try {
			shareRoleMenuDao.delete(shareRoleMenu);
			logger.debug("删除共享角色菜单关系成功");
		} catch (Exception e) {
			logger.debug("删除共享角色菜单关系"+e.getMessage());
			throw e;
		}
	}

	@Override
	public Set<Long> findByShareRole(ShareRole sharerole) {
		List<ShareRoleMenu> lists =  shareRoleMenuDao.findBySharerole(sharerole);
		Set<Long> set = new HashSet<Long>();
		for (ShareRoleMenu shareRoleMenu : lists) {
			set.add(shareRoleMenu.getMenu().getId());
		}
		return set;
	}

}
