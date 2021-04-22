package com.bitc.zero.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.bitc.zero.dto.BoardDto;
import com.bitc.zero.dto.CategoryDto;
import com.bitc.zero.dto.JoinDto;
import com.bitc.zero.service.ZeroService;

@Controller
public class NoticeController {
	@Autowired
	private ZeroService zeroService;
	
	//공지사항 목록 페이지 가져오기
	@RequestMapping(value="/zero/noticeList", method=RequestMethod.GET)
	public ModelAndView noticeList() throws Exception{
		ModelAndView mv = new ModelAndView("/zero/noticeList");
		List<BoardDto> list = zeroService.selectNoticeList();
		
		for(int i=0; i<list.size(); i++) {
			list.get(i).setBoardNum(i+1);
		}
		
		CategoryDto cate = zeroService.selectBoardCategoryList(1); // 카테고리 : 2 ,공지사항 : 1
		mv.addObject("data", list);
		mv.addObject("cate", cate);
		
		return mv;
	}
	
	//공지사항 상세 페이지 가져오기
	@RequestMapping(value="/zero/noticeDetail/{boardPk}", method=RequestMethod.GET)
	public ModelAndView noticeDetail(@PathVariable("boardPk") int boardPk) throws Exception{
		ModelAndView mv = new ModelAndView("/zero/noticeDetail");
		
		BoardDto data = zeroService.selectNoticeDetail(boardPk);
		mv.addObject("data", data);
		mv.addObject("storedFilePath", data.getFile().get(0).getStoredFilePath());

		return mv;
	}
	
	//공지사항 글쓰기 페이지 가져오기 (admin만 접근 가능)
	@RequestMapping(value="/zero/noticeWrite", method=RequestMethod.GET)
	public ModelAndView noticeWrite(HttpServletRequest request) throws Exception {
		ModelAndView mv = new ModelAndView("/zero/noticeWrite");
		
		HttpSession session = request.getSession();
		String customerEmail = (String)session.getAttribute("customerEmail");
		
		JoinDto data = zeroService.selectCustomerInfo(customerEmail);
		mv.addObject("data", data);
		
		return mv;
	}
	
	// 공지사항 글쓰기
	@RequestMapping(value="/zero/noticeWrite", method=RequestMethod.POST)
	public String insertNoticeWrite(BoardDto board, MultipartHttpServletRequest uploadFiles) throws Exception {
		zeroService.insertNoticeWrite(board, uploadFiles);
		
		return "redirect:/zero/noticeList";
	}
	
	// 공지사항 글 수정
	@RequestMapping(value="/zero/noticeDetail/{boardPk}", method=RequestMethod.PUT)
	public String noticeUpdate(BoardDto board) throws Exception {
		zeroService.noticeUpdate(board);
		
		return "redirect:/zero/noticeList";
	}
	
	//공지사항 글 삭제
	@RequestMapping(value="/zero/noticeDetail/{boardPk}", method=RequestMethod.DELETE)
	public String noticeDelete(@PathVariable("boardPk") int boardPk) throws Exception {
		zeroService.noticeDelete(boardPk);
		
		return "redirect:/zero/noticeList";
	}
	
	// 공지사항 상세보기 다운로드파일
//	@RequestMapping(value="/zero/downloadFile", method=RequestMethod.GET)
//	public void downloadFile(@RequestParam int idx, @RequestParam int boardIdx, HttpServletResponse response) throws Exception {
//		
//		TFileDto zeroFile = zeroService.zeroFileInformation(idx, boardIdx);
//		
//		if(ObjectUtils.isEmpty(zeroFile) == false) {
//			String fileName = zeroFile.getOriginalFileName();
//			
//			byte[] files = FileUtils.readFileToByteArray(new File(zeroFile.getStoredFilePath()));
//			
//			response.setContentType("application/octet-stream");
//			response.setContentLength(files.length);
//			response.setHeader("Content-Disposition", "attachment; fileName=\"" + URLEncoder.encode(fileName, "UTF-8") + "\";");
//			response.setHeader("Content-Transfer-Encoding", "binary");
//			
//			response.getOutputStream().write(files);
//			response.getOutputStream().flush();
//			response.getOutputStream().close();
//		}
//	}	
}
