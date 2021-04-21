package com.bitc.zero.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.bitc.zero.dto.MyPageDto;
import com.bitc.zero.dto.ReviewDto;
import com.bitc.zero.service.ZeroService;

@Controller
public class MypageController {
	
	protected static final Logger logger = LogManager.getLogger(MypageController.class);
	
	@Autowired
	protected SqlSession sql;
	
	//	마이페이지 -> 주문목록
		@RequestMapping(value="/zero/getMyPageList", method=RequestMethod.GET)
		public ModelAndView myPage(HttpServletRequest req) throws Exception{
			ModelAndView mv = new ModelAndView();
			HttpSession session = req.getSession();
			try {
				if(session.getAttribute("customerPk").toString() == null) {
					mv.setViewName("/zero/main");
					
					return mv;
				}
			} catch (Exception e) {
				logger.warn("::::session error"+e);
			}
			
			int customerPk =  (int) session.getAttribute("customerPk");
			
			List<MyPageDto> mypageInfoList = sql.selectList("myPageMapper.selectCustomerInfoList", customerPk);
			
			
			mv.addObject("mypageInfoList", mypageInfoList);
			mv.setViewName("/zero/myPage");
			
			return mv;
		}
		
		// 마이페이지 -> 상품후기등록
		@ResponseBody
		@RequestMapping(value="/zero/saveProductReview", method=RequestMethod.POST)
		public String postProductReview(ReviewDto dto, HttpServletRequest req) {
			//MultipartHttpServletRequest uploadFiles
			//zeroService.postProductReview(dto, uploadFiles);
			try {
				HttpSession session = req.getSession();
				dto.setCustomerPk((int)session.getAttribute("customerPk"));
				logger.info("param1 ::"+dto.getProductReviewContents());
				logger.info("param2 ::"+dto.getProductReviewScore());
				logger.info("param3 ::"+dto.getOrderDetailPk());
				logger.info("param4 ::"+dto.getCustomerPk());
				
				sql.insert("myPageMapper.insertProductReview", dto);
			} catch (Exception e) {
				logger.warn("productInsert Error :::"+e);
				
			}
			
			return "1";
		}
}
