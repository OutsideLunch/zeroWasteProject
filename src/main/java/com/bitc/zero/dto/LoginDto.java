package com.bitc.zero.dto;

import lombok.Data;

@Data
public class LoginDto {
	private int customerPk;
	private String customerEmail;
	private String customerPw;
}
