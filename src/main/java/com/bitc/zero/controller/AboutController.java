package com.bitc.zero.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class AboutController {
	
	//about 상세 페이지 가져오기
	@RequestMapping(value="/zero/about", method=RequestMethod.GET)
	public String about() throws Exception{
		return "/zero/about";
	}
}
