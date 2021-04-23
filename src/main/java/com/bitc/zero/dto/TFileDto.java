package com.bitc.zero.dto;

import lombok.Data;

@Data
public class TFileDto {
	private int idx;
	private int boardIdx;
	private String originalFileName;
	private String storedFilePath;
	private long FileSize; //크기가 커서 int가 아니라 long으로 해야함
	private int createId;
	private String createDatetime;
	private String deletedYn;
	private int productReviewPk;
}
