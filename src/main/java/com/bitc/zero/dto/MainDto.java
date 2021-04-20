package com.bitc.zero.dto;

import lombok.Data;

@Data
public class MainDto {
	private int productCategoryPk;
	private String productCategoryName;
	private int boardIdx;
	private String storedFilePath;
	private String createdDatetime;
}
