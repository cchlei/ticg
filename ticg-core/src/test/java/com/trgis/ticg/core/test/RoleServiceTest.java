package com.trgis.ticg.core.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.trgis.ticg.core.exception.RoleException;
import com.trgis.ticg.core.model.Role;
import com.trgis.ticg.core.service.RoleService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext-*.xml" })
@ActiveProfiles("dev")
public class RoleServiceTest {
	@Autowired
	private RoleService roleService;
	
	
	@Test
	public void testList(){
//		try {
//			Subsystem subsystem =null;
//		} catch (RoleException e) {
//			e.printStackTrace();
//		}
	}

	@Test
	public void testadd(){
		try {
			Role role = new Role();
			role.setRolename("测试角色");
//			Subsystem subsystem = new Subsystem();
//			subsystem.setId(1l);
//			role.setSubsystem(subsystem);
			roleService.addRole(role);
		} catch (RoleException e) {
			e.printStackTrace();
		}
	}
	
}
