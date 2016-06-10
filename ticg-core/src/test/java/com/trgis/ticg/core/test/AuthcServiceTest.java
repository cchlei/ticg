package com.trgis.ticg.core.test;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.trgis.ticg.core.exception.AuthcException;
import com.trgis.ticg.core.model.Authc;
import com.trgis.ticg.core.model.RoleAuthc;
import com.trgis.ticg.core.service.AuthcService;
import com.trgis.ticg.core.util.EnumUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext-*.xml" })
@ActiveProfiles("dev")
public class AuthcServiceTest {
	@Autowired
	private AuthcService authcService;
	
	@Test
	public void testAdd() throws AuthcException{
		Authc op =new Authc();
		op.setName("添加操作信息");
		op.setCode("adduser");
		op.setStatus(EnumUtil.STATUS.YQY.getValue());
		op.setNote("我的第一个操作是添加操作信息");
		authcService.add(op);
	}
	
	@Test
	public void testDel() throws AuthcException{
	
		authcService.del(421l);
	}
	
	@Test
	public void testEdit() throws AuthcException{
//		Authc op =authcService.findByMenu(2l).get(2);
//		op.setName("添加操作信息");
//		op.setCode("adduser");
//		op.setStatus(EnumUtil.STATUS.YQY.getValue());
//		op.setNote("我的第一个操作是添加操作信息");
//		authcService.edit(op);
	}
	@Test
	public void testFindByNameOrCode(){
		//List<Authc> sub =	authcService.findByNameOrCode("2","code","add");
		List<Authc> sub =	authcService.findByNameOrCode("2","name","添加");
		System.err.println(sub.get(1).getCode()+sub.size());
	}
	/**
	 * @Description:测试级联删除角色操作表
	 * @Author liuyan 
	 * @Date 2016年5月27日 下午1:37:24
	 */
	@Test
	public void testDelByAuthc(){
		RoleAuthc roleAuthc =new RoleAuthc();
		Authc authc =new Authc();
		authc.setId(6l);
		roleAuthc.setAuthc(authc);
		//roleAuthcService.delByAuthc(authc);
	}
}

