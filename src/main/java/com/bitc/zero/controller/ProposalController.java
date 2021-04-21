package com.bitc.zero.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.bitc.zero.service.ZeroService;

@Controller
public class ProposalController {
	@Autowired
	private ZeroService zeroService;
	
	//입점제안 페이지 가져오기
	@RequestMapping(value="/zero/proposal", method=RequestMethod.GET)
	public String proposal() throws Exception{
		return "/zero/proposal";
	}
}
