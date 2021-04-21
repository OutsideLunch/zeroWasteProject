package com.bitc.zero.dto;

import lombok.Data;

@Data
public class CategoryDto {
	private int boardCategoryPk; //공지사항, 커뮤니티 카테고리
	private int productCategoryPk; //상품 카테고리
	private String boardCategoryName;
	private String productCategoryName;
}