package com.trgis.ticg.system.core.service;

import java.util.List;

import com.trgis.ticg.system.core.model.SystemMenu;

/**
 * 系统超级管理员权限service
 * 
 * @author Alice
 *
 * 2016年5月18日
 */
public interface SystemMenuService {

    /**
     * @param menu
     * 新增权限
     */
    void save(SystemMenu menu);

    /**
     * @return
     * 得到所有的根目录
     */
    List<SystemMenu> getRootMenus();
    
    /**
     * 得到所有权限 .
     * @return .
     */
//    List<SystemMenu> getAllPermissions();
    /**
     * @param roleid .
     * @return
     * 根据角色获取权限
     */
    List<SystemMenu> getPermissionsByRoleid(Long roleid);
}
