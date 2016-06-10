package com.trgis.ticg.webapp.controller;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.trgis.ticg.core.model.User;
import com.trgis.ticg.core.service.UserService;

@Controller
public class IndexController {
	
	@Autowired
	UserService UserService;
	
	@RequestMapping("/")
	public ModelAndView index(ModelAndView mv){
		String username = (String) SecurityUtils.getSubject().getPrincipal();
		User user = UserService.findUserByUsername(username);
		mv.addObject(user);
		mv.setViewName("/index");
		return mv;
	}
	
	@RequestMapping("/user")
	public String user(){
		return "user";
	}
}
