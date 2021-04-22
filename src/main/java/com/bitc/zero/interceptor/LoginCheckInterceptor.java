package com.bitc.zero.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;


public class LoginCheckInterceptor implements HandlerInterceptor {
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView mav) throws Exception {
		HttpSession session = request.getSession();
		
//		세션에 저장된 정보 확인
		if((String)session.getAttribute("customerEmail") == null) {
			System.out.println("============== interceptor ========== ====");
			System.out.println("로그아웃 상태 : ");
			System.out.println((String)session.getAttribute("customerEmail"));
			
		}
		
		else {
//			10동안 세션 살림
			System.out.println("============== interceptor ==============");
			System.out.println("로그인 상태 : ");
			System.out.println((String)session.getAttribute("customerEmail"));
			
			session.setMaxInactiveInterval(6000); //100분동안
		}
	}
}
