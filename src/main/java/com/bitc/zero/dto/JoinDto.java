package com.bitc.zero.dto;

import lombok.Data;

@Data
public class JoinDto {
	private int customerPk;
	private String customerEmail;
	private String customerPw;
	private String customerAddr;
	private String customerName;
	private String customerGender;
	private String customerPhone;
	private String adminYn;
}
