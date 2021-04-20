package com.bitc.zero.dto;

import lombok.Data;

// 회원가입 페이지 
@Data
public class JoinDto {
	private int customerPk;
	private String customerEmail;
	private String customerPw;
	private String customerAddr;
	private String customerName;
	private String customerGender;
	private String customerPhone;
}
