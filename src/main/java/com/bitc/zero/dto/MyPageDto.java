package com.bitc.zero.dto;


import lombok.Data;

@Data
public class MyPageDto {
	private int orderPk;
	private int customerPk;
	private String orderDate;
	private int productPk;
	private int orderCnt;
	private String productName;
	private int productPrice;

}
