package com.bitc.zero.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.bitc.zero.dto.CategoryDto;
import com.bitc.zero.dto.MyPageDto;
import com.bitc.zero.service.ZeroService;


@Controller
public class ZeroController {
	
	@Autowired
	private ZeroService zeroService;
	
	@Autowired
	protected SqlSession sql;
	
	//루트 진입시 메인화면
	@RequestMapping(value="/", method=RequestMethod.GET)
	public String zeroMaiRootn() throws Exception{
		return "redirect:/zero/main";
	}
	
	//메인화면 가져오기
	@RequestMapping(value="/zero/main", method=RequestMethod.GET)
	public String zeroMain() throws Exception{
		return "/zero/main";
	}
	
	//navbar페이지 가져오기
	@RequestMapping(value="/zero/navbar", method=RequestMethod.GET)
	public ModelAndView zeroNavbar() throws Exception{
		ModelAndView mv = new ModelAndView("/zero/navbar");
		
		List<CategoryDto> list = zeroService.selectProductCategoryList();
		mv.addObject("productCategoryData",list);
		return mv;
	}
	
	//footer페이지 가져오기
	@RequestMapping(value="/zero/footer", method=RequestMethod.GET)
	public String zeroFooter() throws Exception{
		return "/zero/footer";
	}
	
	//login페이지 가져오기 loginTest참고해서 이메일과 비밀번호 검사하기 만들기
	@RequestMapping(value="/zero/login", method=RequestMethod.GET)
	public String login() throws Exception{
		return "/zero/login";
	}
	
	//	로그인 id 체크
	@ResponseBody //view가 아니라 값 반환
	@RequestMapping(value="zero/loginCheck", method=RequestMethod.POST)
	public boolean loginCheck(@RequestParam Map<String, String> param, HttpServletRequest request) throws Exception {
		String customerEmail = param.get("customerEmail");
		String customerPw = param.get("customerPw");
		
		int count = zeroService.selectCustomerInfoYn(customerEmail, customerPw);
			
		if (count == 1) {
			HttpSession session = request.getSession();
			int customerPk = sql.selectOne("myPageMapper.selectCustomerInfoList", customerEmail);
			session.setAttribute("customerEmail", customerEmail);
			session.setAttribute("customerPk", customerPk);
			return true;
		}
		else {
			return false;
		}
		
	}
	
	//	로그아웃
	@RequestMapping(value="/zero/logout", method=RequestMethod.GET)
	public String logout(HttpServletRequest request) throws Exception {
		HttpSession session = request.getSession();
		session.removeAttribute("customerEmail");
		session.invalidate();
		
		return "/zero/logout";
	}
	
	
}
