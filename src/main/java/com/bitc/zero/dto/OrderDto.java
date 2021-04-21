package com.bitc.zero.dto;

import lombok.Data;

@Data
public class OrderDto {
	private int customerPk;
	private String orderDate;
	private int orderPk;
	private int orderDetailPk;
	private int productPk;
	private int orderCnt;
	private int orderSum; //DB에 orderSum 컬럼 새로 추가함
	private int productStockCnt;
}
