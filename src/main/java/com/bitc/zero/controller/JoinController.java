package com.bitc.zero.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.bitc.zero.dto.JoinDto;
import com.bitc.zero.service.ZeroService;

@Controller
public class JoinController {
	@Autowired
	private ZeroService zeroService;
	
	//join페이지 가져오기
	@RequestMapping(value="/zero/join", method=RequestMethod.GET)
	public String join() throws Exception{
		return "/zero/join";
	}
	
	//join POST 실행하고 메인페이지로 돌아가기
	@RequestMapping(value="/zero/join", method=RequestMethod.POST)
	public String insertJoin(JoinDto join) throws Exception {
		zeroService.insertJoin(join);
		
		return "redirect:/zero/main";
	}
}
