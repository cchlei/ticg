package com.trgis.ticg.core.test;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.trgis.ticg.core.exception.MenuException;
import com.trgis.ticg.core.model.Menu;
import com.trgis.ticg.core.service.MenuService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext-*.xml" })
@ActiveProfiles("dev")
public class MenuServiceTest {
	@Autowired
	private MenuService menuService;
	
	@Test
	public void testEdit() throws MenuException{
		Menu op =menuService.findOne(1l);
		op.setName("添加操作信息");
		op.setCode("adduser");
		menuService.editMenu(op);
	}
	@Test
	public void testDelMenu() throws MenuException{
	    Long[] ids = new Long[2];
	    /*for(int i=0;i<ids.length;i++){
			ids[0] = 71l;
		}*/
	    ids[0] = 71l;
	    System.out.println(ids[0].toString());
		menuService.delMenu(ids);
	}
	@Test
	public void testFindByNameOrCode() throws MenuException{
		//List<Menu> sub =	menuService.findMenuByNameOrCode(1l,"name","哒哒哒");
		List<Menu> sub =	menuService.findMenuByNameOrCode(1l,"code","zhis");
		System.err.println(sub.get(0).getName()+sub.size());
	}
}

