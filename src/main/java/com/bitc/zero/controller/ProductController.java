package com.bitc.zero.controller;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.bitc.zero.dto.CategoryDto;
import com.bitc.zero.dto.ProductDetailDto;
import com.bitc.zero.dto.ProductListDto;
import com.bitc.zero.dto.ReviewDto;

@Controller
public class ProductController {
	@Autowired
	protected SqlSession sql;
	
	//상품 전체 목록 페이지 가져오기 (카테고리별로 가져오기 구현해야함!)
	@RequestMapping(value="/zero/productList/{productCategoryPk}", method=RequestMethod.GET)
	public ModelAndView productList(@PathVariable("productCategoryPk") int productCategoryPk) throws Exception{
		ModelAndView mv = new ModelAndView("/zero/productList");
		
		List<ProductListDto> list = sql.selectList("productMapper.selectProductList", productCategoryPk);
		
		mv.addObject("data",list);
		
		//전체카테고리정보 가져오기
		List<CategoryDto> category = sql.selectList("commonMapper.selectProductCategoryList");
		mv.addObject("productCategoryData",category);
		
		return mv;
	}
	
	//상품 상세 페이지 가져오기
	@RequestMapping(value="/zero/productDetail/{productPk}", method=RequestMethod.GET)
	public ModelAndView productDetail(@PathVariable("productPk") int productPk) throws Exception{
		ModelAndView mv = new ModelAndView("/zero/productDetail");
		
		ProductDetailDto pd = sql.selectOne("productMapper.selectProductDetail", productPk);
		
		// 상품 상세 설명 이미지 경로 가져오기
		String descPath = sql.selectOne("productMapper.selectProductDesc",productPk);
		pd.setStoredDescFilePath(descPath);

		mv.addObject("data", pd);
		
		//리뷰
		List<ReviewDto> rd = sql.selectList("productMapper.selectReviewList", productPk);
		mv.addObject("reviewData", rd);
		
		return mv;
	}
}
