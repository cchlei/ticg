package com.trgis.ticg.core.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.trgis.ticg.core.dao.ShareRoleOrgDao;
import com.trgis.ticg.core.exception.ShareRoleException;
import com.trgis.ticg.core.model.Organization;
import com.trgis.ticg.core.model.ShareRole;
import com.trgis.ticg.core.model.ShareRoleOrg;
import com.trgis.ticg.core.service.ShareRoleOrgService;
import com.trgis.ticg.core.util.BeanUtil;
/**
 * 共享共享角色
 * 
 * @author Alice
 *
 * 2016年5月26日
 */
@Service
@Transactional
public class ShareRoleOrgServiceImpl implements ShareRoleOrgService {

	private static final Logger log = LoggerFactory.getLogger(ShareRoleOrgServiceImpl.class);

	@Autowired
	private ShareRoleOrgDao shareRoleOrgDao;

	@Override
	public List<ShareRoleOrg> findByOrg(Long oid)  {
		try {
			if (oid==null) {
				throw new ShareRoleException("组织机构id为空");
			}
			Organization org =new Organization();
			org.setId(oid);
			log.debug("根据组织机构"+oid+"查询成功！");
			return shareRoleOrgDao.findByOrganization(org);
		} catch (Exception e) {
			log.debug("共享角色删除失败!"+e.getMessage());
			return new ArrayList<ShareRoleOrg>();
		}
	}

	@Override
	public List<ShareRoleOrg> findBySharerole(ShareRole sharerole) {
		List<ShareRoleOrg> list = new ArrayList<ShareRoleOrg>();
		try {
			if (BeanUtil.isNotEmpty(sharerole)) {
				list = shareRoleOrgDao.findBySharerole(sharerole);
			}
		} catch (Exception e) {
			log.debug("共享角色删除失败!"+e.getMessage());
		}
		return list;
	}
}
