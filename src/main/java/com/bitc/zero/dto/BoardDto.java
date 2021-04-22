package com.bitc.zero.dto;

import java.util.List;

import lombok.Data;

@Data
public class BoardDto {
	private int boardPk;
	private int boardNum;
	private String boardTitle;
	private int createId;
	private String createDatetime;
	private String deletedYn;
	private int boardCategoryPk;
	private String boardContents;
	private String customerName;
	private String boardCategoryName;
	List<TFileDto> file;
}
