package com.bitc.zero.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.bitc.zero.common.FileUtil;
import com.bitc.zero.dto.MyPageDto;
import com.bitc.zero.dto.ReviewDto;
import com.bitc.zero.dto.TFileDto;
import com.bitc.zero.mapper.ZeroMapper;

@Controller
public class MypageController {
	
	protected static final Logger logger = LogManager.getLogger(MypageController.class);
	
	@Autowired
	protected SqlSession sql;
	
	@Autowired
	private ZeroMapper zeroMapper;
	
	@Autowired
	private FileUtil fileUtil;
	
	//	마이페이지 -> 주문목록
	@RequestMapping(value="/zero/getMyPageList", method=RequestMethod.GET)
	public ModelAndView myPage(HttpServletRequest req) throws Exception{
		ModelAndView mv = new ModelAndView();
		HttpSession session = req.getSession();
		try {
			if((String)session.getAttribute("customerEmail") == null) {
				mv.setViewName("/zero/main");
				
				return mv;
			}
		} catch (Exception e) {
			logger.warn("::::session error"+e);
		}
		
		int customerPk =  (int) session.getAttribute("customerPk");
		
		List<MyPageDto> mypageInfoList = sql.selectList("myPageMapper.getMypageInfoList", customerPk);
		
		
		mv.addObject("mypageInfoList", mypageInfoList);
		mv.setViewName("/zero/myPage");
		
		return mv;
	}
	
	// 마이페이지 -> 상품후기등록
	@ResponseBody
	@RequestMapping(value="/zero/saveProductReview", method=RequestMethod.POST)
	public String postProductReview(ReviewDto dto, HttpServletRequest req, MultipartHttpServletRequest uploadFiles) {

		try {
			HttpSession session = req.getSession();
			int reviewPk = sql.selectOne("myPageMapper.seletLastProductReviewPk");
			dto.setCustomerPk((int)session.getAttribute("customerPk"));
			dto.setProductReviewPk(reviewPk);
					
			sql.insert("myPageMapper.insertProductReview", dto);
			
			List<TFileDto> fileList = fileUtil.parseReviewFileInfo(reviewPk, uploadFiles);
			
			if (CollectionUtils.isEmpty(fileList) == false) {
				zeroMapper.insertIdeaFile(fileList);
			}
			return "1";
		} catch (Exception e) {
			logger.warn("productInsert Error :::"+e);
			return "2";
			
		}
		
	}
}
