package com.bitc.zero.dto;

import lombok.Data;

// 로그인 페이지
@Data
public class LoginDto {
	private int customerPk;
	private String customerEmail;
	private String customerPw;
}
