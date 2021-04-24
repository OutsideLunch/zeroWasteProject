package com.bitc.zero.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.bitc.zero.controller.MypageController;


public class LoginCheckInterceptor implements HandlerInterceptor {
	
	protected static final Logger logger = LogManager.getLogger(MypageController.class);
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView mav) throws Exception {
		HttpSession session = request.getSession();
		
		/* 세션에 저장된 정보 확인 */
		if((String)session.getAttribute("customerEmail") == null) {
			logger.info("============== interceptor ========== ====");
			logger.info("Logout Status :");
			logger.info((String)session.getAttribute("customerEmail"));
			
		}
		
		else {
			/* 10동안 세션 살림 */
			logger.info("============== interceptor ========== ====");
			logger.info("Login Status :");
			logger.info((String)session.getAttribute("customerEmail"));
			
			session.setMaxInactiveInterval(6000); //100분동안
		}
	}
}
