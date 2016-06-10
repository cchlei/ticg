package com.trgis.ticg.demo.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.trgis.ticg.demo.model.DemoUser;
import com.trgis.ticg.gateway.demo.api.DemoServiceGateway;

@Controller
public class DemoController {

	@Autowired
	private DemoServiceGateway demoServiceGateway;
	
	@ResponseBody
	@RequestMapping("/user")
	public DemoUser findUser() {
		String id = "demo-id-001";
		return demoServiceGateway.findUser(id);
	}
	
}
