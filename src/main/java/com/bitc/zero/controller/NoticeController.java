package com.bitc.zero.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.bitc.zero.service.ZeroService;

@Controller
public class NoticeController {
	@Autowired
	private ZeroService zeroService;
	
	//공지사항 목록 페이지 가져오기
	@RequestMapping(value="/zero/noticeList", method=RequestMethod.GET)
	public String noticeList() throws Exception{
		return "/zero/noticeList";
	}
	
	//공지사항 상세 페이지 가져오기
	@RequestMapping(value="/zero/noticeDetail", method=RequestMethod.GET)
	public String noticeDetail() throws Exception{
		return "/zero/noticeDetail";
	}
}
