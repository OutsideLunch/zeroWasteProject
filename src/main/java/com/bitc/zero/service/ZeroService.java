package com.bitc.zero.service;

import java.util.List;

import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.bitc.zero.dto.BoardDto;
import com.bitc.zero.dto.CategoryDto;
import com.bitc.zero.dto.JoinDto;
import com.bitc.zero.dto.MyPageDto;
import com.bitc.zero.dto.OrderDto;
import com.bitc.zero.dto.ProductDetailDto;
import com.bitc.zero.dto.ProductListDto;
import com.bitc.zero.dto.ProposalDto;
import com.bitc.zero.dto.ReviewDto;
import com.bitc.zero.dto.TFileDto;

public interface ZeroService {
	// 회원가입정보 DB에 입력
	void insertJoin(JoinDto join) throws Exception;

	// 로그인체크시 email과 pw를 DB에서 검색하고 0또는 1값을 가져옴
	int selectCustomerInfoYn(String customerEmail, String customerPw) throws Exception;

	// 상품카테고리
	List<CategoryDto> selectProductCategoryList() throws Exception;

	// 상품 전체 목록
	List<ProductListDto> selectProductList(int productCategoryPk) throws Exception;

	// 상품 상세 정보
	ProductDetailDto selectProductDetail(int productPk) throws Exception;

	// 상품 후기 목록
	ReviewDto selectReviewList(int productPk) throws Exception;

	// 커뮤니티 전체 목록
	List<BoardDto> selectIdeaList() throws Exception;

	// 커뮤니티 상세 보기
	BoardDto selectIdeaDetail(int boardPk) throws Exception;

	// 커뮤니티 글 작성
	public void insertIdeaWrite(BoardDto board, MultipartHttpServletRequest uploadFiles) throws Exception;

	// 커뮤니티 글 수정
	void ideaUpdate(BoardDto board) throws Exception;

	// 커뮤니티 글 삭제
	void ideaDelete(int boardPk) throws Exception;

	// 파일 다운로드 위한 파일 정보
	TFileDto zeroFileInformation(int idx, int boardIdx) throws Exception;

	// 보드 카테고리 불러오기
	CategoryDto selectBoardCategoryList(int boardCategoryPk) throws Exception;

	// 세션에 로그인된 이메일 이용하여 고객 정보 불러오기
	JoinDto selectCustomerInfo(String customerEmail) throws Exception;

	// orders 테이블에 고객번호, 주문날짜 저장하기
	void insertOrder(OrderDto order) throws Exception;

	// 마이페이지 상세
	List<MyPageDto> getMypageInfo(int customerPk) throws Exception;

	void postProductReview(ReviewDto review, MultipartHttpServletRequest uploadFiles) throws Exception;

	// 입점제안 정보 DB에 입력
	public void insertProposal(ProposalDto proposal, MultipartHttpServletRequest uploadFiles) throws Exception;


	// 공지사항 전체 목록
	List<BoardDto> selectNoticeList() throws Exception;

	// 공지사항 상세 보기
	BoardDto selectNoticeDetail(int boardPk) throws Exception;

	// 공지사항 글 작성
	public void insertNoticeWrite(BoardDto board, MultipartHttpServletRequest uploadFiles) throws Exception;

	// 공지사항 글 수정
	void noticeUpdate(BoardDto board) throws Exception;

	// 공지사항 글 삭제
	void noticeDelete(int boardPk) throws Exception;

}
