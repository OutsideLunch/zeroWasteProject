package com.bitc.zero.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.bitc.zero.dto.MyPageDto;
import com.bitc.zero.dto.ReviewDto;
import com.bitc.zero.service.ZeroService;

@Controller
public class MypageController {
	
	@Autowired
	private ZeroService zeroService;
	
	//	마이페이지 -> 주문목록
		@RequestMapping(value="/zero/getMyPageList", method=RequestMethod.GET)
		public ModelAndView myPage(HttpServletRequest req) throws Exception{
			ModelAndView mv = new ModelAndView();
			
			HttpSession session = req.getSession();
			int customerPk =  (int) session.getAttribute("customerPk");
			
			List<MyPageDto> mypageInfoList = zeroService.getMypageInfo(customerPk);
			
			
			mv.addObject("mypageInfoList", mypageInfoList);
			mv.setViewName("/zero/myPage");
			
			return mv;
		}
		
		// 마이페이지 -> 상품후기등록
		@RequestMapping(value="/zero/saveProductReview", method=RequestMethod.POST)
		public String postProductReview(ReviewDto review, MultipartHttpServletRequest uploadFiles) throws Exception {
			zeroService.postProductReview(review, uploadFiles);
			
			return "redirect:/zero/myPage";
		}
}
