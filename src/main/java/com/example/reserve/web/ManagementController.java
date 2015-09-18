package com.example.reserve.web;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ManagementController{

	public ManagementController() {
	}
	
	@RequestMapping(value="/management")
	@Transactional(readOnly = true)
	public String management() {
		return "management";
	}
}
