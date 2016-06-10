package com.trgis.ticg.incident.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.trgis.ticg.gateway.incident.api.EventServiceGateway;
import com.trgis.ticg.incident.model.Events;

@Controller
public class EventController {

	@Autowired
	private EventServiceGateway eventServiceGateway;
	
	@ResponseBody
	@RequestMapping("/user")
	public Events findEvent() {
		long id = 001l;
		return eventServiceGateway.findEvent(id);
	}
	
}
