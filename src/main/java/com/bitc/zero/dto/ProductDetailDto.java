package com.bitc.zero.dto;

import java.util.List;

import lombok.Data;

// 상품 상세페이지
@Data
public class ProductDetailDto {
	List<ReviewDto> review;
	List<TFileDto> file;
	private int productPk;
	private int partnerPk;

	
}
