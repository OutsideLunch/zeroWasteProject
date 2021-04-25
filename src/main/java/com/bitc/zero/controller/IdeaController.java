package com.bitc.zero.controller;

import java.io.File;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.bitc.zero.common.FileUtil;
import com.bitc.zero.dto.BoardDto;
import com.bitc.zero.dto.CategoryDto;
import com.bitc.zero.dto.JoinDto;
import com.bitc.zero.dto.TFileDto;

@Controller
public class IdeaController {
	@Autowired
	protected SqlSession sql;
	
	@Autowired
	private FileUtil fileUtil;
	
	// 커뮤니티 전체 목록
	@RequestMapping(value="/zero/ideaList", method=RequestMethod.GET)
	public ModelAndView ideaList() throws Exception {
		ModelAndView mv = new ModelAndView("/zero/ideaList");
		List<BoardDto> list = sql.selectList("ideaMapper.selectIdeaList");
		
		for(int i=0; i<list.size(); i++) {
			list.get(i).setBoardNum(i+1);
		}
		
		CategoryDto cate = sql.selectOne("ideaMapper.selectBoardCategoryList",2); // 카테고리 : 2 ,공지사항 : 1
		mv.addObject("data", list);
		mv.addObject("cate", cate);

		return mv;
	}
	
	// 커뮤니티 상세 페이지
	@RequestMapping(value="/zero/ideaDetail/{boardPk}", method=RequestMethod.GET)
	public ModelAndView ideaDetail(@PathVariable("boardPk") int boardPk, HttpServletRequest request) throws Exception {
		ModelAndView mv = new ModelAndView("/zero/ideaDetail");
		
		HttpSession session = request.getSession();
		if ((String)session.getAttribute("customerEmail") != null) {
			int customerPk = (int)session.getAttribute("customerPk");
			
			BoardDto data = sql.selectOne("ideaMapper.selectIdeaDetail",boardPk);

			List<TFileDto> fileList = sql.selectList("commonMapper.zeroFileList",boardPk);

			data.setFile(fileList);
			
			mv.addObject("data", data);
			mv.addObject("customerPk", customerPk);
		}
		else {			
			BoardDto data = sql.selectOne("ideaMapper.selectIdeaDetail",boardPk);

			List<TFileDto> fileList = sql.selectList("commonMapper.zeroFileList",boardPk);

			data.setFile(fileList);
			
			mv.addObject("data", data);
		}
		
		return mv;
	}
	
	// 커뮤니티 글쓰기 페이지 가져오기 (로그인했을때만 글쓰기 가능. 로그아웃상태일시 오류처리해줘야함)
	@RequestMapping(value="/zero/ideaWrite", method=RequestMethod.GET)
	public ModelAndView ideaWrite(HttpServletRequest request) throws Exception {
		ModelAndView mv = new ModelAndView("/zero/ideaWrite");
		//session에 로그인 상태인 이메일을 이용해서 작성자의 customer_pk, customer_name을 가져온다.
		HttpSession session = request.getSession();
		
		if ((String)session.getAttribute("customerEmail") != null) {
			// 회원일 경우
			String customerEmail = (String)session.getAttribute("customerEmail");
			
			//회원 정보가 있는 JoinDto이용, 세션에 저장된 이메일 이용하여 현재 로그인한 고객 정보 불러오기
			JoinDto data = sql.selectOne("commonMapper.selectCustomerInfo",customerEmail);
			mv.setViewName("/zero/ideaWrite");
			mv.addObject("data", data);
			
			return mv;
		}
		else {
			// 비회원일 경우
			mv.setViewName("redirect:/zero/ideaList");
		}
		
		return mv;
	}
	
	// 커뮤니티 글쓰기
	@RequestMapping(value="/zero/ideaWrite", method=RequestMethod.POST)
	public String insertCommunityWrite(BoardDto board, MultipartHttpServletRequest uploadFiles) throws Exception {

		sql.insert("ideaMapper.insertIdeaWrite", board);
		
		List<TFileDto> fileList = fileUtil.parseFileInfo(board.getBoardPk(), uploadFiles);

		if (CollectionUtils.isEmpty(fileList) == false) {
			sql.insert("ideaMapper.insertIdeaFile",fileList);
		}
		
		return "redirect:/zero/ideaList";
	}
	
	// 커뮤니티 글 수정
	@RequestMapping(value="/zero/ideaDetail/{boardPk}", method=RequestMethod.PUT)
	public String ideaUpdate(BoardDto board) throws Exception {
		sql.update("ideaMapper.ideaUpdate",board);
		
		return "redirect:/zero/ideaList";
	}
	
	//커뮤니티 글 삭제
	@RequestMapping(value="/zero/ideaDetail/{boardPk}", method=RequestMethod.DELETE)
	public String ideaDelete(@PathVariable("boardPk") int boardPk) throws Exception {
		sql.update("ideaMapper.ideaDelete",boardPk);
		
		return "redirect:/zero/ideaList";
	}
	
	// 커뮤니티 상세보기 다운로드파일
	@RequestMapping(value="/zero/downloadFile", method=RequestMethod.GET)
	public void downloadFile(@RequestParam int idx, @RequestParam int boardIdx, HttpServletResponse response) throws Exception {
		
		TFileDto dto = new TFileDto();
		dto.setIdx(idx);
		dto.setBoardIdx(boardIdx);
		
		TFileDto zeroFile = sql.selectOne("ideaMapper.zeroFileInformation",dto);
		
		if(ObjectUtils.isEmpty(zeroFile) == false) {
			String fileName = zeroFile.getOriginalFileName();
			
			byte[] files = FileUtils.readFileToByteArray(new File(fileUtil.root+zeroFile.getStoredFilePath()));
			
			
			response.setContentType("application/octet-stream");
			response.setContentLength(files.length);
			response.setHeader("Content-Disposition", "attachment; fileName=\"" + URLEncoder.encode(fileName, "UTF-8") + "\";");
			response.setHeader("Content-Transfer-Encoding", "binary");
			
			response.getOutputStream().write(files);
			response.getOutputStream().flush();
			response.getOutputStream().close();
		}
	}
}


