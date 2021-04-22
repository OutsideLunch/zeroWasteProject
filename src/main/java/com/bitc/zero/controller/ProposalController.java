package com.bitc.zero.controller;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.bitc.zero.common.FileUtil;
import com.bitc.zero.dto.ProposalDto;
import com.bitc.zero.dto.TFileDto;

@Controller
public class ProposalController {
	@Autowired
	protected SqlSession sql;
	
	@Autowired
	private FileUtil fileUtil;
	
	//입점제안 페이지 가져오기
	@RequestMapping(value="/zero/proposal", method=RequestMethod.GET)
	public String proposal() throws Exception{
		return "/zero/proposal";
	}
	
	//입점제안 POST 실행하고 메인페이지로 돌아가기
	@RequestMapping(value="/zero/proposal", method=RequestMethod.POST)
	public String insertProposal(ProposalDto proposal, MultipartHttpServletRequest uploadFiles) throws Exception {
		
		sql.insert("proposalMapper.insertProposal",proposal);
		
		List<TFileDto> fileList = fileUtil.parseFileInfo(proposal.getPartnerPk(), uploadFiles);

		if (CollectionUtils.isEmpty(fileList) == false) {
			sql.insert("proposalMapper.insertProposalFile", fileList);
		}
		
		return "redirect:/zero/main";
	}
}
