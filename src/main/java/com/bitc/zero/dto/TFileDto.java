package com.bitc.zero.dto;

import lombok.Data;

@Data
public class TFileDto {
	private int idx;
	private int boardIdx;
	private String originalFileName;
	private String storedFilePath;
	private long fileSize;
	private String createdId;
	private String createdDatetime;
	private String updatedId;
	private String updatedDatetime;
	private String deletedYn;
	private int productPk;
	private int productReviewPk;
	private int boardPk;
	
}
