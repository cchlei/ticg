package com.trgis.ticg.core.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.trgis.ticg.core.model.Subsystem;
import com.trgis.ticg.core.service.SubsystemRegisterService;
import com.trgis.ticg.core.service.SubsystemService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext-*.xml" })
@ActiveProfiles("dev")
public class SubsystemServiceTest {
	@Autowired
	private SubsystemService SubsystemService;
	@Autowired
	private SubsystemRegisterService subsystemRegisterService;
	/**
	 * @Description:测试子系统添加
	 * ----测试通过
	 * @Author liuyan 
	 * @Date 2016年5月19日 下午2:08:21
	 */
	@Test
	public void testAdd(){
		Subsystem sub =new Subsystem();
		sub.setName("用户管理子系统");
		sub.setCode("usersystem ");
		sub.setDatabase("user");
		sub.setDescription("user4！");
		SubsystemService.add(sub);
	}
	/**
	 * @Description:修改子系统
	 * ---测试通过
	 * @Author liuyan 
	 * @Date 2016年5月19日 下午2:08:54
	 */
	@Test
	public void testEdit(){
		Subsystem sub =new Subsystem();
		sub.setId(1l);	
		sub.setName("用户权限管理系统");
		sub.setDatabase("不告诉你");
		sub.setUrl("www.ticg.com");
		sub.setDescription("");
		SubsystemService.edit(sub);
	}
	
/**
 * @Description:测试根据子系统id删除对应子系统
 * @Author liuyan 
 * @Date 2016年5月19日 下午2:07:46
 */
	@Test
	public void testDel(){
		List<Long> ids = new ArrayList<Long>();
		
		ids.add(40l);
		
		//SubsystemService.changeStatus(ids,1);
	}
	/**
	 * @Description:测试根据子系统删除注册子系统表====测试通过
	 * @Author liuyan 
	 * @Date 2016年5月27日 下午2:07:46
	 */
	@Test
	public void testDeleteSubSystemRegister(){
		subsystemRegisterService.deleteSubSystemRegister(71l);
	}
/**
 * @Description:测试根据那么和code查找子系统的方法 ---通过
 * @Author liuyan 
 * @Date 2016年5月24日 下午5:59:53
 */
	@Test
	public void testfindSysByNameOrCode(){
		//Subsystem sub =	SubsystemService.findSysByNameOrCode("name","aaa");
		Subsystem sub =	SubsystemService.findSysByNameOrCode("code","aa");
		System.err.println(sub.getName());
	}
}
