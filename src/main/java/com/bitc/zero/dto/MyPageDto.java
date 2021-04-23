package com.bitc.zero.dto;


import lombok.Data;

@Data
public class MyPageDto{
	
	private int num;
	private int orderPk;
	private int orderDetailPk;
	private int customerPk;
	private String orderDate;
	private int productPk;
	private int orderCnt;
	private String productName;
	private int productPrice;
	private int totalPrice;
	private byte reviewYn;
	private Criteria cri;

}
