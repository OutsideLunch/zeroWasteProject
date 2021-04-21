package com.bitc.zero.dto;

import java.util.List;

import lombok.Data;

@Data
public class ProductDetailDto {
	private int productPk;
	private int partnerPk;
	private String productName;
	private String partnerName;
	private int productPrice;
	private String storedFilePath; //상품 대표 이미지
	private String storedDescFilePath; //상품 설명 이미지
	private List<ReviewDto> review;
}
