package com.bitc.zero.common;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class ExceptionHandler {
//	예외처리 방법
//	try ~ catch 를 이용한 방법
//	각각의 컨트롤러에서 @ExceptionHandler를 이용한 방법
//	@ControllerAdvice 어노테이션을 이용한 방법(전역 예외처리)
//	스프링부트2 에서는 @ControllerAdvice 어노테이션을 이용하여 쉽게 예외처리를 할 수 있음
	
	@org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
	public ModelAndView defaultExceptionHandler(HttpServletRequest request, Exception exception) {
		ModelAndView mv = new ModelAndView("/error/error_default");
		mv.addObject("exception", exception);
		
		log.error("exception", exception);
		
		return mv;
	}
}
