package com.bitc.zero.dto;

import java.util.List;

import lombok.Data;

// 상품 페이지
@Data
public class ProductListDto {
	List<TFileDto> file;
//	private String storedFilePath;
	private String productName;
	private int productPrice;
}
