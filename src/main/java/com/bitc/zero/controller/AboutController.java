package com.bitc.zero.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.bitc.zero.service.ZeroService;

@Controller
public class AboutController {
	@Autowired
	private ZeroService zeroService;
	
	//about 상세 페이지 가져오기
	@RequestMapping(value="/zero/about", method=RequestMethod.GET)
	public String about() throws Exception{
		return "/zero/about";
	}
}
