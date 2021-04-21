package com.bitc.zero.dto;

import lombok.Data;

@Data
public class ProductListDto {
	private int productPk;
	private int productCategoryPk;
	private String productName;
	private int productPrice;
	private String storedFilePath;
}
