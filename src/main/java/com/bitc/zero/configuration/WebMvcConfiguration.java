package com.bitc.zero.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.bitc.zero.interceptor.LoginCheckInterceptor;

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {
	@Bean
	public CommonsMultipartResolver multipartResolver() {
		CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
		
//		파일의 인코딩을 UTF-8로 설정
		commonsMultipartResolver.setDefaultEncoding("UTF-8");
//		최대 업로드 파일 크기 설정
//		파일의 크기를 byte 단위로 설정이 가능함 (5 * 1024 * 1024 = 5mb)
//		컴퓨터는 2진수 계산을 하기 때문에 1000을 2의 10승으로 계산함 (1000 = 1024)
		commonsMultipartResolver.setMaxUploadSizePerFile(5 * 1024 * 1024);
		
		return commonsMultipartResolver;
	}
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		//registry.addResourceHandler("/img/**").addResourceLocations("file:///C:/img/");
		registry.addResourceHandler("/upload/**").addResourceLocations("file:///C:/upload/");
	}
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		/*
		로그인 체크를 위한 클래스가 자동생성되어
		매 페이지를 열 때마다 로그인 체크를 진행함
		
		addPathPtterns("패턴") : 인터셉터를 사용할 컨트롤러를 선택
		excludePathPatterns(“주소”) : 사용하지 않을 컨트롤러를 선택
		*/
		registry.addInterceptor(new LoginCheckInterceptor())
		.addPathPatterns("/zero/*")
		.excludePathPatterns("/zero/login")
		.excludePathPatterns("/zero/loginCheck")
		.excludePathPatterns("/zero/zeroFooter");

	}
}
	










