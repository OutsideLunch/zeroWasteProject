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

import com.bitc.zero.common.WebPackage;
import com.bitc.zero.dto.CategoryDto;
import com.bitc.zero.dto.JoinDto;
import com.bitc.zero.dto.ProductDetailDto;


@Controller
public class ZeroController {
	
	@Autowired
	protected SqlSession sql;
	
	//루트 진입시 메인화면
	@RequestMapping(value="/", method=RequestMethod.GET)
	public String zeroMainRoot() throws Exception{
		return "redirect:/zero/main";
	}
	
	//메인화면 가져오기
	@RequestMapping(value="/zero/main", method=RequestMethod.GET)
	public ModelAndView zeroMain() throws Exception{
		ModelAndView mv = new ModelAndView("/zero/main");
		
		List<ProductDetailDto> list1 = sql.selectList("mainMapper.selectNewArrival");
		List<ProductDetailDto> list2 = sql.selectList("mainMapper.selectRecommendation");
		
		mv.addObject("data1",list1);
		mv.addObject("data2",list2);
		
		return mv;
	}
	
	//navbar페이지 가져오기
	@RequestMapping(value="/zero/navbar", method=RequestMethod.GET)
	public ModelAndView zeroNavbar() throws Exception{
		ModelAndView mv = new ModelAndView("/zero/navbar");
		
		List<CategoryDto> list = sql.selectList("commonMapper.selectProductCategoryList");
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
		
		JoinDto dto = new JoinDto();
		String customerEmail = param.get("customerEmail");
		String encryptionPw = WebPackage.Encrypt256(param.get("customerPw"));
		dto.setCustomerEmail(customerEmail);
		dto.setCustomerPw(encryptionPw);
		
		int count = sql.selectOne("loginMapper.selectCustomerInfoYn", dto);
			
		if (count == 1) {
			HttpSession session = request.getSession();
			JoinDto jd = sql.selectOne("commonMapper.selectCustomerInfo", customerEmail);
			session.setAttribute("customerEmail", customerEmail);
			session.setAttribute("customerPk", jd.getCustomerPk());
			session.setAttribute("adminYn", jd.getAdminYn());
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
