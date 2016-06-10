package test.com.trgis.ticg.incident.web.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Repeat;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.trgis.ticg.incident.model.Events;
import com.trgis.ticg.incident.web.controller.EventController;

@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath*:/applicationContext-*.xml","classpath*:/springmvc.xml"})
public class TestEventController {

	@Autowired
	private EventController eventController;
	
	@Test
	@Repeat(10)
	public void testFindEvent(){
		Events event = eventController.findEvent();
		System.out.println(event.getId());
	}
	
}
