package com.bitc.zero.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.bitc.zero.dto.JoinDto;
import com.bitc.zero.dto.OrderDto;
import com.bitc.zero.dto.ProductDetailDto;

@Controller
public class OrderController {
	
	protected static final Logger logger = LogManager.getLogger(MypageController.class);
	
	@Autowired
	protected SqlSession sql;
	
	//주문 페이지 가져오기
	@RequestMapping(value="/zero/order", method=RequestMethod.GET)
	public ModelAndView order(@RequestParam int productPk, @RequestParam int amount, @RequestParam int sum, HttpServletRequest request) throws Exception{
		ModelAndView mv = new ModelAndView("/zero/order");
		
		HttpSession session1 = request.getSession();
		try {
			if((String)session1.getAttribute("customerEmail") == null) {
				mv.setViewName("redirect:/zero/main");
				
				return mv;
			}
		} catch (Exception e) {
			logger.warn("::::session error"+e);
		}
		
		//product_pk, stored_file_path, product_name 가져옴
		ProductDetailDto pd = sql.selectOne("productMapper.selectProductDetail",productPk);
		mv.addObject("pData", pd);
		
		//주문 수량, 총 주문금액
		mv.addObject("amount",amount);
		mv.addObject("sum",sum);
		
		//session에 로그인 상태인 이메일을 이용 
		HttpSession session = request.getSession();
		String customerEmail = (String)session.getAttribute("customerEmail");
		
		//회원 정보가 있는 JoinDto에서 현재 로그인한 고객의 customer_pk, name, phone, addr을 가져옴
		JoinDto data = sql.selectOne("commonMapper.selectCustomerInfo",customerEmail);
		mv.addObject("cData", data);
		
		return mv;
	}
	
	//결제하기 버튼 클릭시 DB에 저장하기
	@ResponseBody
	@RequestMapping(value="/zero/insertOrder", method= RequestMethod.GET)
	public String insertOrder(OrderDto order, @RequestParam Map<String, String> param) throws Exception{
		
		order.setCustomerPk(Integer.parseInt(param.get("customerPk")));
		order.setProductPk(Integer.parseInt(param.get("productPk")));
		order.setOrderCnt(Integer.parseInt(param.get("orderCnt")));
		order.setOrderSum(Integer.parseInt(param.get("orderSum")));
		
		sql.insert("orderMapper.insertOrder",order);

		//order_detail 테이블에 order_pk 및 주문정보 저장하기
		sql.insert("orderMapper.insertOrderDetail",order);
		
		sql.update("orderMapper.updateOrderStock",order);
		
		String result = "주문이 완료되었습니다.";
		
		return result;
	}
}
