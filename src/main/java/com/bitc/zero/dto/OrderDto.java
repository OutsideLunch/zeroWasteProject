package com.bitc.zero.dto;

import lombok.Data;

// 주문하기 페이지
@Data
public class OrderDto {
	private int customerPk;
	private String orderDate;
	private int orderPk;
	private int productPk;
	private int orderCnt;
	private int productStockCnt;
	
}
