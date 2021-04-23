package com.bitc.zero.dto;

public class Criteria {
	
	private int page = 1;	//보여줄 페이지 번호
	private int perPageNum = 5;	// 페이지당 보여줄 게시글의 수
	private String sortingType;  // 정렬타입
	private String searchKeyword; //검색 단어 
	
	public int getPage() {
		return page;
	}
	
	public void setPage(int page) {
			if(page<=0) {
				this.page=1;
			}else {
				this.page=page;
			}
	}
	
	public int getPerPageNum() {
			return perPageNum;
	}
	
	public void setPerPageNum(int perPageNum) {
			this.perPageNum = perPageNum;
	}
	
	public int getPageStart() {
			return (this.page-1) * perPageNum;
	}
	
	public String getSortingType() {
		return sortingType;
	}

	public void setSortingType(String sortingType) {
		this.sortingType = sortingType;
	}

	public String getSearchKeyword() {
		return searchKeyword;
	}

	public void setSearchKeyword(String searchKeyword) {
		this.searchKeyword = searchKeyword;
	}

}
