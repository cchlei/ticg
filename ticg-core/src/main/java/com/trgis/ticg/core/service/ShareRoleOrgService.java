package com.trgis.ticg.core.service;

import java.util.List;

import com.trgis.ticg.core.model.ShareRole;
import com.trgis.ticg.core.model.ShareRoleOrg;
/**
 * 组织机构共享角色service
 * 
 * @author Alice
 *
 * 2016年5月26日
 */
public interface ShareRoleOrgService {
	/**
	 * 根据组织机构查询关系
	 * @return
	 */
	List<ShareRoleOrg> findByOrg(Long oid);
	
	/**
	 * 根据共享角色查询
	 * @param sharerole
	 * @return
	 */
	List<ShareRoleOrg> findBySharerole(ShareRole sharerole);
}
