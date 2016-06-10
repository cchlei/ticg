package com.trgis.ticg.demo.api.impl;

import org.springframework.stereotype.Service;

import com.trgis.ticg.demo.api.DemoService;
import com.trgis.ticg.demo.model.DemoUser;

@Service
public class DemoServiceImpl implements DemoService {
	
	@Override
	public DemoUser findUser(String id) {
		System.out.println("This is a impl service");
		return new DemoUser(id);
	}

}
