package com.bitc.zero.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class AboutController {
	
	//about 상세 페이지 가져오기
	@RequestMapping(value="/zero/about1", method=RequestMethod.GET)
	public String about1() throws Exception{
		return "/zero/about1";
	}
	
	//about 상세 페이지 가져오기
	@RequestMapping(value="/zero/about2", method=RequestMethod.GET)
	public String about2() throws Exception{
		return "/zero/about2";
	}
}
