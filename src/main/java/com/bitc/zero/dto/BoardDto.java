package com.bitc.zero.dto;

import java.util.List;

import lombok.Data;

// 게시판 페이지
@Data
public class BoardDto {
	private int boardPk;
	private String boardTitle;
	private int createId;
	private String createDatetime;
	private String deletedYn;
	private int boardCategoryPk;
	private String boardContents;
	private String customerName;
	private String boardCategoryName;
//	private String storedFilePath;
	List<TFileDto> file;
}
