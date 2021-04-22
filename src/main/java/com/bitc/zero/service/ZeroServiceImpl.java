package com.bitc.zero.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.bitc.zero.common.FileUtil;
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
import com.bitc.zero.mapper.ZeroMapper;

@Service
public class ZeroServiceImpl implements ZeroService {
	@Autowired
	private ZeroMapper zeroMapper;

	@Autowired
	private FileUtil fileUtil;

	// 회원가입정보 DB에 입력
	@Override
	public void insertJoin(JoinDto join) throws Exception {
		zeroMapper.insertJoin(join);
	}

	// 로그인체크시 email과 pw를 DB에서 검색하고 0또는 1값을 가져옴
	@Override
	public int selectCustomerInfoYn(String customerEmail, String customerPw) throws Exception {

		return zeroMapper.selectCustomerInfoYn(customerEmail, customerPw);
	}

	// 상품카테고리
	@Override
	public List<CategoryDto> selectProductCategoryList() throws Exception {
		return zeroMapper.selectProductCategoryList();
	}

	// 상품 전체 목록 불러오기
	@Override
	public List<ProductListDto> selectProductList(int productCategoryPk) throws Exception {
		return zeroMapper.selectProductList(productCategoryPk);
	}

	// 상품 상세 정보 불러오기
	@Override
	public ProductDetailDto selectProductDetail(int productPk) throws Exception {

		ProductDetailDto pd = zeroMapper.selectProductDetail(productPk);

		// 상품 상세 설명 이미지 경로 가져오기
		String descPath = zeroMapper.selectProductDesc(productPk);
		pd.setStoredDescFilePath(descPath);

		return pd;
	}

	// 상품 후기 목록
	@Override
	public ReviewDto selectReviewList(int productPk) throws Exception {
		return zeroMapper.selectReviewList(productPk);
	}

	// 커뮤니티 전체 목록
	@Override
	public List<BoardDto> selectIdeaList() throws Exception {

		return zeroMapper.selectIdeaList();
	}

	// 커뮤니티 상세 보기
	@Override
	public BoardDto selectIdeaDetail(int boardPk) throws Exception {
		BoardDto board = zeroMapper.selectIdeaDetail(boardPk);

		List<TFileDto> fileList = zeroMapper.zeroFileList(boardPk);

		board.setFile(fileList);

		return board;
	}

	// 커뮤니티 글 작성
	@Override
	public void insertIdeaWrite(BoardDto board, MultipartHttpServletRequest uploadFiles) throws Exception {

		zeroMapper.insertIdeaWrite(board);

		List<TFileDto> fileList = fileUtil.parseFileInfo(board.getBoardPk(), uploadFiles);

		if (CollectionUtils.isEmpty(fileList) == false) {
			zeroMapper.insertIdeaFile(fileList);
		}
	}

	// 커뮤니티 글 수정
	@Override
	public void ideaUpdate(BoardDto board) throws Exception {
		zeroMapper.ideaUpdate(board);
	}

	// 커뮤니티 글 삭제
	@Override
	public void ideaDelete(int boardPk) throws Exception {
		zeroMapper.ideaDelete(boardPk);
	}

	// 파일 다운로드 위한 파일 정보
	@Override
	public TFileDto zeroFileInformation(int idx, int boardIdx) throws Exception {
		return zeroMapper.zeroFileInformation(idx, boardIdx);
	}

	// 보드 카테고리 불러오기
	@Override
	public CategoryDto selectBoardCategoryList(int boardCategoryPk) throws Exception {
		return zeroMapper.selectBoardCategoryList(boardCategoryPk);
	}

	// 세션에 로그인된 이메일 이용하여 고객 정보 불러오기
	@Override
	public JoinDto selectCustomerInfo(String customerEmail) throws Exception {
		return zeroMapper.selectCustomerInfo(customerEmail);
	}

	// orders 테이블에 고객번호, 주문날짜 저장하기
	@Override
	public int insertOrder(OrderDto order) throws Exception {
		return zeroMapper.insertOrder(order);
	}
	
	//order_detail 테이블에 order_pk 및 주문정보 저장하기
	public void insertOrderDetail(OrderDto order) throws Exception{
		zeroMapper.insertOrderDetail(order);
	}

	// 마이페이지 정보
	@Override
	public List<MyPageDto> getMypageInfo(int customerPk) throws Exception {

		return zeroMapper.getMypageInfo(customerPk);

	}

	// 상품후기 보내기
	@Override
	public void postProductReview(ReviewDto review, MultipartHttpServletRequest uploadFiles) throws Exception {

		zeroMapper.postProductReview(review);

		List<TFileDto> fileList = fileUtil.parseFileInfo(review.getProductReviewPk(), uploadFiles);

		if (CollectionUtils.isEmpty(fileList) == false) {
			zeroMapper.postProductReviewImg(fileList);
		}
	}

	// 입점제안 정보 DB에 입력
	@Override
	public void insertProposal(ProposalDto proposal, MultipartHttpServletRequest uploadFiles) throws Exception {
		zeroMapper.insertProposal(proposal);

		List<TFileDto> fileList = fileUtil.parseFileInfo(proposal.getPartnerPk(), uploadFiles);

		if (CollectionUtils.isEmpty(fileList) == false) {
			zeroMapper.insertProposalFile(fileList);
		}
	}

	// 공지사항 전체 목록
	@Override
	public List<BoardDto> selectNoticeList() throws Exception {

		return zeroMapper.selectNoticeList();
	}

	// 공지사항 상세 보기
	@Override
	public BoardDto selectNoticeDetail(int boardPk) throws Exception {
		BoardDto board = zeroMapper.selectNoticeDetail(boardPk);

		List<TFileDto> fileList = zeroMapper.zeroFileList(boardPk);

		board.setFile(fileList);

		return board;
	}

	// 공지사항 글 작성
	@Override
	public void insertNoticeWrite(BoardDto board, MultipartHttpServletRequest uploadFiles) throws Exception {

		zeroMapper.insertNoticeWrite(board);

		List<TFileDto> fileList = fileUtil.parseFileInfo(board.getBoardPk(), uploadFiles);

		if (CollectionUtils.isEmpty(fileList) == false) {
			zeroMapper.insertNoticeFile(fileList);
		}
	}

	// 공지사항 글 수정
	@Override
	public void noticeUpdate(BoardDto board) throws Exception {
		zeroMapper.noticeUpdate(board);
	}

	// 공지사항 글 삭제
	@Override
	public void noticeDelete(int boardPk) throws Exception {
		zeroMapper.noticeDelete(boardPk);
	}
	
	// 세션생성 하여 관리자 번호 가져옴
	public JoinDto selectAdminInfoYn(String adminYn, String customerEmail) throws Exception {
		return zeroMapper.selectAdminInfoYn(adminYn, customerEmail);
	}

}
