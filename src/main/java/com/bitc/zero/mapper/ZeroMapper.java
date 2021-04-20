package com.bitc.zero.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.bitc.zero.dto.BoardDto;
import com.bitc.zero.dto.CategoryDto;
import com.bitc.zero.dto.JoinDto;
import com.bitc.zero.dto.MyPageDto;
import com.bitc.zero.dto.ReviewDto;
import com.bitc.zero.dto.TFileDto;

@Mapper
public interface ZeroMapper {
	
	void insertJoinzerowaste(JoinDto customerPk) throws Exception;
	
	// 커뮤니티 리스트
	List<BoardDto> selectIdeaList() throws Exception;
	
	// 커뮤니티 리스트 상세조회
	BoardDto selectIdeaDetail(int boardPk) throws Exception;
	List<TFileDto> zeroFileList(int boardIdx) throws Exception;
	
	// 커뮤니티 리스트 작성페이지
	void insertIdeaWrite(BoardDto board) throws Exception;
	void insertIdeaFile(List<TFileDto> fileList) throws Exception;
	// 커뮤니티 리스트 수정
	void ideaUpdate(BoardDto board) throws Exception;
	// 커뮤니티 리스트 삭제
	void ideaDelete(int boardPk) throws Exception;
	
	TFileDto zeroFileInformation(@Param("idx") int idx, @Param("boardIdx") int boardIdx);
	
	//회원가입정보 DB에 입력
	void insertJoin(JoinDto join) throws Exception;

	// 로그인체크시 email과 pw를 DB에서 검색하고 0또는 1값을 가져옴
	int selectCustomerInfoYn(@Param("customerEmail") String customerEmail, @Param("customerPw") String customerPw) throws Exception;
	
	// 로그인 -> 회원pk
		int getCustomerPk(String customerEmail) throws Exception;
	
	// 마이페이지 상세
	List<MyPageDto> getMypageInfo(@Param("customerPk") int customerPk) throws Exception;
	
	// 상품 후기 보내기
	void postProductReview(ReviewDto review) throws Exception;
	void postProductReviewImg(List<TFileDto> fileList) throws Exception;
	
	CategoryDto selectCateInfo(@Param("cateCode") int cateCode) throws Exception;
	
	List<ReviewDto> productReview(String customerName) throws Exception;
	
	
}
