package com.bitc.zero.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.bitc.zero.dto.ProductDetailDto;
import com.bitc.zero.dto.ProductListDto;
import com.bitc.zero.dto.ReviewDto;
import com.bitc.zero.service.ZeroService;

@Controller
public class ProductController {
	@Autowired
	private ZeroService zeroService;
	
	//상품 전체 목록 페이지 가져오기 (카테고리별로 가져오기 구현해야함!)
	@RequestMapping(value="/zero/productList/{productCategoryPk}", method=RequestMethod.GET)
	public ModelAndView productList(@PathVariable("productCategoryPk") int productCategoryPk) throws Exception{
		ModelAndView mv = new ModelAndView("/zero/productList");
		
		List<ProductListDto> list = zeroService.selectProductList(productCategoryPk);
		
		mv.addObject("data",list);
		
		return mv;
	}
	
	//상품 상세 페이지 가져오기
	@RequestMapping(value="/zero/productDetail/{productPk}", method=RequestMethod.GET)
	public ModelAndView productDetail(@PathVariable("productPk") int productPk) throws Exception{
		ModelAndView mv = new ModelAndView("/zero/productDetail");
		
		ProductDetailDto pd = zeroService.selectProductDetail(productPk);
		mv.addObject("data", pd);
		
		//리뷰
		ReviewDto rd = zeroService.selectReviewList(productPk);
		mv.addObject("reviewData", rd);
		
		return mv;
	}
}
