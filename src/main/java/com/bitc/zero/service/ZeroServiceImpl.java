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
import com.bitc.zero.dto.ReviewDto;
import com.bitc.zero.dto.TFileDto;
import com.bitc.zero.mapper.ZeroMapper;

@Service
public class ZeroServiceImpl implements ZeroService {
	
	
	@Autowired
	private ZeroMapper zeroMapper;
	
	@Autowired
	private FileUtil fileUtil;

	@Override
	public void insertJoinzerowaste(JoinDto customerPk) throws Exception {
		zeroMapper.insertJoinzerowaste(customerPk);
	}
	// 커뮤니티 리스트 조회
	@Override
	public List<BoardDto> selectIdeaList() throws Exception {
		
		return zeroMapper.selectIdeaList();
	}
	// 커뮤니티 리스트 상세
	@Override
	public BoardDto selectIdeaDetail(int boardPk) throws Exception {
		BoardDto board = zeroMapper.selectIdeaDetail(boardPk);
		
		List<TFileDto> fileList = zeroMapper.zeroFileList(boardPk);
		
		board.setFile(fileList);
		
		return board;
	}
	// 커뮤니티 리스트 작성
	@Override
	public void insertIdeaWrite(BoardDto board, MultipartHttpServletRequest uploadFiles) throws Exception {
		
		zeroMapper.insertIdeaWrite(board);
		
		List<TFileDto> fileList = fileUtil.parseFileInfo(board.getBoardPk(), uploadFiles);
		
		if (CollectionUtils.isEmpty(fileList) == false) {
			zeroMapper.insertIdeaFile(fileList);
		}
	}
	// 커뮤니티 리스트 수정
	@Override
	public void ideaUpdate(BoardDto board) throws Exception {
		zeroMapper.ideaUpdate(board);
	}
	// 커뮤니티 리스트 삭제
	@Override
	public void ideaDelete(int boardPk) throws Exception {
		zeroMapper.ideaDelete(boardPk);
	}
	
	@Override
	public TFileDto zeroFileInformation(int idx, int boardIdx) throws Exception {
		return zeroMapper.zeroFileInformation(idx, boardIdx);
	}
	
	//회원가입정보 DB에 입력
	@Override
	public void insertJoin(JoinDto join) throws Exception {
		zeroMapper.insertJoin(join);
	}
	
	// 로그인체크시 email과 pw를 DB에서 검색하고 0또는 1값을 가져옴
	@Override
	public int selectCustomerInfoYn(String customerEmail, String customerPw) throws Exception{

		return zeroMapper.selectCustomerInfoYn(customerEmail, customerPw);
	}
	
	// 게시판 카테고리 
	@Override
	public CategoryDto selectCateInfo(int cateCode) throws Exception {
		
		return zeroMapper.selectCateInfo(cateCode);
	}
	
	// 로그인 -> 회원PK 가져오기
	@Override
	public int getCustomerPk(String customerEmail) throws Exception {

		return zeroMapper.getCustomerPk(customerEmail);
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
	
	@Override
	public List<ReviewDto> productReview(String customerName) throws Exception {
		
		return zeroMapper.productReview(customerName);
	}
}
	










