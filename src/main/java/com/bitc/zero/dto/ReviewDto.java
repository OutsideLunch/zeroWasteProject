package com.bitc.zero.dto;

import lombok.Data;

@Data
public class ReviewDto {
	// 후기 입력 출력 Dto
	private String productReviewTitle;
	private int customerPk;
	private String customerName;
	private String productReviewContents;
	private String storedFilePath;
	private int productReviewPk;
	private int orderDetailPk;
	private int productReviewScore;
	List<TFileDto> file;
}
