package com.trgis.ticg.core.test;


import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.trgis.ticg.core.dao.SubsystemRegisterDao;
import com.trgis.ticg.core.model.SubSystemRegister;
import com.trgis.ticg.core.model.Subsystem;
import com.trgis.ticg.core.service.RoleSubsystemService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext-*.xml" })
@ActiveProfiles("dev")
public class RoleSubsystemServiceTest {
	@Autowired
	private RoleSubsystemService roleSubsystemService;
	@Autowired
	private SubsystemRegisterDao subsystemRegisterDao;
	
	/**
	 * @Description:测试级联删除角色操作表   ===测试通过
	 * @Author liuyan 
	 * @Date 2016年5月27日 下午1:37:24
	 */
	@Test
	public void testDelBySsRegister(){
		Subsystem subsystem =new Subsystem();
		subsystem.setId(71l);
		//1.根据子系统查找注册子系统表
	    List<SubSystemRegister> sysReg = subsystemRegisterDao.findBySubsystem(subsystem);
	    System.err.println(sysReg.size()+"sss"+sysReg.get(0).getId()+"sss"+sysReg.get(1).getId()+"sss"+sysReg.get(2).getId());
	    roleSubsystemService.delBySsRegister(sysReg);
	}
}

