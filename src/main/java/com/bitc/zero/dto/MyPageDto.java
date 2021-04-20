package com.bitc.zero.dto;


import lombok.Data;

// 내 주문 조회

@Data
public class MyPageDto {
	private int orderPk;
	private int orderDetailPk;
	private int customerPk;
	private String orderDate;
	private int productPk;
	private int orderCnt;
	private String productName;
	private int productPrice;
	private int totalPrice;

}
