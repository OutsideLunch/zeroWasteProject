package com.bitc.zero.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.bitc.zero.dto.ProposalDto;
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
		
		//입점제안 POST 실행하고 메인페이지로 돌아가기
		@RequestMapping(value="/zero/proposal", method=RequestMethod.POST)
		public String insertProposal(ProposalDto proposal, MultipartHttpServletRequest uploadFiles) throws Exception {
			zeroService.insertProposal(proposal, uploadFiles);
			
			return "redirect:/zero/main";
		}
}
