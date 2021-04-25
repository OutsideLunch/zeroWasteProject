package com.bitc.zero.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.bitc.zero.common.FileUtil;
import com.bitc.zero.dto.BoardDto;
import com.bitc.zero.dto.CategoryDto;
import com.bitc.zero.dto.JoinDto;
import com.bitc.zero.dto.TFileDto;

@Controller
public class NoticeController {
	
	@Autowired
	protected SqlSession sql;
	
	@Autowired
	private FileUtil fileUtil;
	
	//공지사항 목록 페이지 가져오기
	@RequestMapping(value="/zero/noticeList", method=RequestMethod.GET)
	public ModelAndView noticeList() throws Exception{
		ModelAndView mv = new ModelAndView("/zero/noticeList");
		List<BoardDto> list = sql.selectList("noticeMapper.selectNoticeList");
		for(int i=0; i<list.size(); i++) {
			list.get(i).setBoardNum(i+1);
		}
		
		CategoryDto cate = sql.selectOne("ideaMapper.selectBoardCategoryList",1); // 카테고리 : 2 ,공지사항 : 1
		mv.addObject("data", list);
		mv.addObject("cate", cate);
		
		return mv;
	}
	
	//공지사항 상세 페이지 가져오기
	@RequestMapping(value="/zero/noticeDetail/{boardPk}", method=RequestMethod.GET)
	public ModelAndView noticeDetail(@PathVariable("boardPk") int boardPk) throws Exception{
		ModelAndView mv = new ModelAndView("/zero/noticeDetail");
		
		BoardDto data = sql.selectOne("noticeMapper.selectNoticeDetail", boardPk);
		
		List<TFileDto> fileList = sql.selectList("commonMapper.zeroFileList",boardPk);

		data.setFile(fileList);
		
		mv.addObject("data", data);

		return mv;
	}
	
	//공지사항 글쓰기 페이지 가져오기 (admin만 접근 가능)
	@RequestMapping(value="/zero/noticeWrite", method=RequestMethod.GET)
	public ModelAndView noticeWrite(HttpServletRequest request) throws Exception {
		ModelAndView mv = new ModelAndView();
		
		HttpSession session = request.getSession();
		
		String adminYn = (String)session.getAttribute("adminYn");
		String customerEmail = (String)session.getAttribute("customerEmail");
		
			
		if (adminYn.equals("Y")) {
			// 관리자일 경우
			JoinDto data = sql.selectOne("commonMapper.selectCustomerInfo", customerEmail);
				
			mv.setViewName("/zero/noticeWrite");
			mv.addObject("data", data);
				
			return mv;
		}
		else {
			// 일반 고객일 경우
			mv.setViewName("redirect:/zero/noticeList");
		}
		
		return mv;
	}
	
	// 공지사항 글쓰기
	@RequestMapping(value="/zero/noticeWrite", method=RequestMethod.POST)
	public String insertNoticeWrite(BoardDto board, MultipartHttpServletRequest uploadFiles) throws Exception {

		sql.insert("noticeMapper.insertNoticeWrite", board);
		
		List<TFileDto> fileList = fileUtil.parseFileInfo(board.getBoardPk(), uploadFiles);
		
		if (CollectionUtils.isEmpty(fileList) == false) {
			sql.insert("ideaMapper.insertIdeaFile",fileList);
		}
		
		return "redirect:/zero/noticeList";
	}
	
	// 공지사항 글 수정
	@RequestMapping(value="/zero/noticeDetail/{boardPk}", method=RequestMethod.PUT)
	public String noticeUpdate(BoardDto board) throws Exception {
		sql.update("noticeMapper.noticeUpdate", board);
		
		return "redirect:/zero/noticeList";
	}
	
	//공지사항 글 삭제
	@RequestMapping(value="/zero/noticeDetail/{boardPk}", method=RequestMethod.DELETE)
	public String noticeDelete(@PathVariable("boardPk") int boardPk) throws Exception {
		sql.update("noticeMapper.noticeDelete", boardPk);
		
		return "redirect:/zero/noticeList";
	}
	
	
}
