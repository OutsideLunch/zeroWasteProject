package com.bitc.zero.controller;

import org.apache.ibatis.session.SqlSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bitc.zero.common.WebPackage;
import com.bitc.zero.dto.JoinDto;

@Controller
public class JoinController {
	
	protected static final Logger logger = LogManager.getLogger(MypageController.class);
	
	@Autowired
	protected SqlSession sql;
	
	//join페이지 가져오기
	@RequestMapping(value="/zero/join", method=RequestMethod.GET)
	public String join() throws Exception{
		return "/zero/join";
	}
	
	//join -> 회원가입
	@ResponseBody
	@RequestMapping(value="/zero/join", method=RequestMethod.POST)
	public String insertJoin(JoinDto join) {
		
		try {
			// 존재유무 Check
			boolean existYn = sql.selectOne("joinMapper.customerYnChk", join.getCustomerEmail());
			if(existYn) {
				return "2";
			} 
			String encryptionPw = WebPackage.Encrypt256(join.getCustomerPw());
			join.setCustomerPw(encryptionPw);
			sql.insert("joinMapper.insertJoin",join);
			return "1";
		} catch (Exception e) {
			logger.warn("customer Insert Error ::"+e);
			return "3";
		}
		
	}
}
