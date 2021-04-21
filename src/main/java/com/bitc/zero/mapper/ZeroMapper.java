package com.bitc.zero.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

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

@Mapper
public interface ZeroMapper {
	//회원가입정보 DB에 입력
	void insertJoin(JoinDto join) throws Exception;
	
	// 로그인체크시 email과 pw를 DB에서 검색하고 0또는 1값을 가져옴
	int selectCustomerInfoYn(@Param("customerEmail") String customerEmail, @Param("customerPw") String customerPw) throws Exception;
	
	
	//상품카테고리
	List<CategoryDto> selectProductCategoryList() throws Exception;

	//상품 전체 목록 불러오기
	List<ProductListDto> selectProductList(int productCategoryPk) throws Exception;
	
	//상품 상세 보기
	ProductDetailDto selectProductDetail(int productPk) throws Exception;
	
	//상품 상세 설명 이미지 가져오기
	String selectProductDesc(int productPk) throws Exception;
	
	//상품 후기 목록
	ReviewDto selectReviewList(int productPk) throws Exception;
	
	
	//커뮤니티 전체 목록
	List<BoardDto> selectIdeaList() throws Exception;
		
	//커뮤니티 상세 보기
	BoardDto selectIdeaDetail(int boardPk) throws Exception;
	List<TFileDto> zeroFileList(int boardIdx) throws Exception;
	
	//커뮤니티 글 작성
	void insertIdeaWrite(BoardDto board) throws Exception;
	void insertIdeaFile(List<TFileDto> fileList) throws Exception;
	
	//커뮤니티 글 수정
	void ideaUpdate(BoardDto board) throws Exception;
	
	//커뮤니티 글 삭제
	void ideaDelete(int boardPk) throws Exception;
	
	//파일 다운로드 위한 파일 정보
	TFileDto zeroFileInformation(@Param("idx") int idx, @Param("boardIdx") int boardIdx) throws Exception;
	
	//보드 카테고리 불러오기
	CategoryDto selectBoardCategoryList(@Param("boardCategoryPk") int boardCategoryPk) throws Exception;
	
	//세션에 로그인된 이메일 이용하여 고객 정보 불러오기
	JoinDto selectCustomerInfo(String customerEmail) throws Exception;

	//orders 테이블에 고객번호, 주문날짜 저장하기
	int insertOrder(OrderDto order) throws Exception;

	//order_detail 테이블에 order_pk 및 주문정보 저장하기
	void insertOrderDetail(OrderDto order) throws Exception;
	
	// 마이페이지 상세
	List<MyPageDto> getMypageInfo(@Param("customerPk") int customerPk) throws Exception;
	
	// 상품 후기 보내기
	void postProductReview(ReviewDto review) throws Exception;
	void postProductReviewImg(List<TFileDto> fileList) throws Exception;
	
	// 입점제안정보 DB에 입력
		void insertProposal(ProposalDto proposal) throws Exception;
	// 입점제안 파일 입력
		void insertProposalFile(List<TFileDto> fileList) throws Exception;
}
