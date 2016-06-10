package com.trgis.ticg.system.core.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.trgis.ticg.system.core.model.SystemMenu;
import com.trgis.ticg.system.core.service.SystemMenuService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext-*.xml" })
@ActiveProfiles("dev")
public class SystemMenuServiceTest {
	
	@Autowired
	private SystemMenuService menuService;
	
	@Test
	public void init(){
		//应用子系统管理模块
		SystemMenu m1 = new SystemMenu();
		m1.setMenuname("子系统管理");
		m1.setClosed(true);
		m1.setUrl("subsystem/list");
		menuService.save(m1);
		
		//组织机构管理模块
		SystemMenu m2 = new SystemMenu();
		m2.setMenuname("组织机构管理");
		m2.setClosed(true);
		m2.setUrl("");
		menuService.save(m2);
		
		//用户管理模块
		SystemMenu m3 = new SystemMenu();
		m3.setMenuname("运维管理");
		m3.setClosed(true);
		SystemMenu m31 = new SystemMenu();
		m31.setMenuname("账号管理");
		m31.setParent(m3);
		m31.setUrl("");
		SystemMenu m32 = new SystemMenu();
		m32.setMenuname("角色管理");
		m32.setParent(m3);
		m32.setUrl("");
		SystemMenu m33 = new SystemMenu();
		m33.setMenuname("共享角色管理");
		m33.setParent(m3);
		m33.setUrl("");
		menuService.save(m3);
		menuService.save(m31);
		menuService.save(m32);
		menuService.save(m33);
		
		//消息通知管理模块
		SystemMenu m4 = new SystemMenu();
		m4.setMenuname("消息通知管理");
		m4.setClosed(false);
		SystemMenu m41 = new SystemMenu();
		m41.setMenuname("消息发布管理");
		m41.setParent(m4);
		m41.setUrl("");
		SystemMenu m42 = new SystemMenu();
		m42.setMenuname("历史消息管理");
		m42.setParent(m4);
		m42.setUrl("");
		menuService.save(m4);
		menuService.save(m41);
		menuService.save(m42);
		
		//消息通知管理模块
		SystemMenu m5 = new SystemMenu();
		m5.setMenuname("系统管理");
		m5.setClosed(true);
		SystemMenu m51 = new SystemMenu();
		m51.setMenuname("系统账号管理");
		m51.setParent(m5);
		m51.setUrl("");
		SystemMenu m52 = new SystemMenu();
		m52.setMenuname("角色权限管理");
		m52.setParent(m5);
		m52.setUrl("");
		SystemMenu m53 = new SystemMenu();
		m53.setMenuname("组织机构子系统分配");
		m53.setParent(m5);
		m53.setUrl("");
		menuService.save(m5);
		menuService.save(m51);
		menuService.save(m52);
		menuService.save(m53);
	}

}
