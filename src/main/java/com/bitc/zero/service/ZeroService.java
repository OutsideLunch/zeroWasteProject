package com.bitc.zero.service;

import java.util.List;

import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.bitc.zero.dto.BoardDto;
import com.bitc.zero.dto.CategoryDto;
import com.bitc.zero.dto.JoinDto;
import com.bitc.zero.dto.MyPageDto;
import com.bitc.zero.dto.ReviewDto;
import com.bitc.zero.dto.TFileDto;

public interface ZeroService {
	void insertJoinzerowaste(JoinDto customerPk) throws Exception;
	
	// 커뮤니티 리스트
	List<BoardDto> selectIdeaList() throws Exception;
	// 커뮤니티 리스트 상세보기
	BoardDto selectIdeaDetail(int boardPk) throws Exception;
	// 커뮤니티 리스트 작성
	public void insertIdeaWrite(BoardDto board, MultipartHttpServletRequest uploadFiles) throws Exception;
	// 커뮤니티 리스트 수정
	void ideaUpdate(BoardDto board) throws Exception;
	// 커뮤니티 리스트 삭제
	void ideaDelete(int boardPk) throws Exception;
	
	TFileDto zeroFileInformation(int idx, int boardIdx) throws Exception;
	
	//회원가입정보 DB에 입력
	void insertJoin(JoinDto join) throws Exception;
	// 로그인체크시 email과 pw를 DB에서 검색하고 0또는 1값을 가져옴
	int selectCustomerInfoYn(String customerEmail, String customerPw) throws Exception;
	
	// 로로그인 -> 회원PK 가져오기
	int getCustomerPk(String customerEmail) throws Exception;
	// 마이페이지 상세
	List<MyPageDto> getMypageInfo(int customerPk) throws Exception;
	void postProductReview(ReviewDto review, MultipartHttpServletRequest uploadFiles) throws Exception;
	
	CategoryDto selectCateInfo(int cateCode) throws Exception;
	
	List<ReviewDto> productReview(String customerName) throws Exception;
}
