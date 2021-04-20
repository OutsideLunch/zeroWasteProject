package com.bitc.zero.controller;

import java.io.File;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.bitc.zero.dto.BoardDto;
import com.bitc.zero.dto.CategoryDto;
import com.bitc.zero.dto.JoinDto;
import com.bitc.zero.dto.MyPageDto;
import com.bitc.zero.dto.ReviewDto;
import com.bitc.zero.dto.TFileDto;
import com.bitc.zero.service.ZeroService;

@Controller
public class ZeroController {
		
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private ZeroService zeroService;
	
	//메인화면 가져오기
	@RequestMapping(value="/", method=RequestMethod.GET)
	public String main() {
		return "redirect:/zero/main";
	}
	
	@RequestMapping(value="/zero/main", method=RequestMethod.GET)
	public String zeroMain() throws Exception{
		return "/zero/main";
	}
	
	//navbar페이지 가져오기
	@RequestMapping(value="/zero/navbar", method=RequestMethod.GET)
	public String zeroNavbar() throws Exception{
		return "/zero/navbar";
	}
	
	//footer페이지 가져오기
	@RequestMapping(value="/zero/footer", method=RequestMethod.GET)
	public String zeroFooter() throws Exception{
		return "/zero/footer";
	}
	
	//login페이지 가져오기 lginTest참고해서 이메일과 비밀번호 검사하기 만들기
	@RequestMapping(value="/zero/login", method=RequestMethod.GET)
	public String login() throws Exception{
		return "/zero/login";
	}
	
	//	로그인 id 체크
	@ResponseBody
	@RequestMapping(value="zero/loginCheck", method=RequestMethod.POST)
	public boolean loginCheck(@RequestParam Map<String, String> param, HttpServletRequest request) throws Exception {
		String customerEmail = param.get("customerEmail");
		String customerPw = param.get("customerPw");
		
		int count = zeroService.selectCustomerInfoYn(customerEmail, customerPw);
			
		if (count == 1) {
			int customerPk = zeroService.getCustomerPk(customerEmail);
			log.info("pk ::"+customerPk);
			HttpSession session = request.getSession();
			session.setAttribute("customerPk", customerPk);
			session.setAttribute("customerEmail", customerEmail);
			return true;
		}
		else {
			return false;
		}
	}
	
	//	로그아웃
	@RequestMapping(value="/zero/logout", method=RequestMethod.GET)
	public String logout(HttpServletRequest request) throws Exception {
		HttpSession session = request.getSession();
		session.removeAttribute("customerEmail");
		session.invalidate();
		
		return "/zero/logout";
	}
	
	//join페이지 가져오기
	@RequestMapping(value="/zero/join", method=RequestMethod.GET)
	public String join() throws Exception{
		return "/zero/join";
	}
	
	//join POST 실행하고 메인페이지로 돌아가기
	@RequestMapping(value="/zero/join", method=RequestMethod.POST)
	public String insertJoin(JoinDto join) throws Exception {
		zeroService.insertJoin(join);
		
		return "redirect:/zero/main";
	}
	
	//상품 목록 페이지 가져오기 (카테고리별로 가져오기 구현해야함!)
	@RequestMapping(value="/zero/productList", method=RequestMethod.GET)
	public String productList() throws Exception{
		return "/zero/productList";
	}
	
	//상품 상세 페이지 가져오기 (value 수정해야함)
	@RequestMapping(value="/zero/productDetail", method=RequestMethod.GET)
	public ModelAndView productDetail(HttpServletRequest req) throws Exception{
		
		ModelAndView mv = new ModelAndView();
		
		HttpSession session = req.getSession();
		String customerName = session.getAttribute("customerName").toString();
		
		List<ReviewDto> review = zeroService.productReview(customerName);
		
		mv.addObject("review", review);
		mv.setViewName("/zero/productDetail");
		
		return mv;
	}
	
	//마이페이지 (주문목록) 가져오기
	@RequestMapping(value="/zero/myPage", method=RequestMethod.GET)
	public ModelAndView myPage(HttpServletRequest req) throws Exception{
		ModelAndView mv = new ModelAndView();
		
		HttpSession session = req.getSession();
		int customerPk =  (int) session.getAttribute("customerPk");
//		int orderDetailPk = (int) session.getAttribute("orderDetailPk");
		
		List<MyPageDto> mypageInfoList = zeroService.getMypageInfo(customerPk);
		
		
		mv.addObject("mypageInfoList", mypageInfoList);
		mv.setViewName("/zero/myPage");
		
		return mv;
	}
	
	// 마이페이지 상품후기 보내기
	@RequestMapping(value="/zero/myPage", method=RequestMethod.POST)
	public String postProductReview(ReviewDto review, MultipartHttpServletRequest uploadFiles) throws Exception {
		zeroService.postProductReview(review, uploadFiles);
		
		return "redirect:/zero/myPage";
	}
	
	//공지사항 목록 페이지 가져오기
	@RequestMapping(value="/zero/noticeList", method=RequestMethod.GET)
	public String noticeList() throws Exception{
		return "/zero/noticeList";
	}
	
	//공지사항 상세 페이지 가져오기
	@RequestMapping(value="/zero/noticeDetail", method=RequestMethod.GET)
	public String noticeDetail() throws Exception{
		return "/zero/noticeDetail";
	}
	
	//주문 페이지 가져오기
	@RequestMapping(value="/zero/order", method=RequestMethod.GET)
	public String order() throws Exception{
		return "/zero/order";
	}
	
	//입점제안 페이지 가져오기
	@RequestMapping(value="/zero/proposal", method=RequestMethod.GET)
	public String proposal() throws Exception{
		return "/zero/proposal";
	}
		
	//about 상세 페이지 가져오기
	@RequestMapping(value="/zero/about", method=RequestMethod.GET)
	public String about() throws Exception{
		return "/zero/about";
	}
	
	// 커뮤니티 리스트
	@RequestMapping(value="/zero/ideaList", method=RequestMethod.GET)
	public ModelAndView ideaList() throws Exception {
		ModelAndView mv = new ModelAndView("/zero/ideaList");
		List<BoardDto> list = zeroService.selectIdeaList();
//		for(int i=0; i<list.size(); i++) {
//			list.get(i).setBoardNum(i+1);
//		}
		CategoryDto cate = zeroService.selectCateInfo(2); // 카테고리 : 2 ,공지사항 : 1
		mv.addObject("list", list);
		mv.addObject("cate", cate);
		
		return mv;
	}
	
	// 커뮤니티 리스트 상세페이지
	@RequestMapping(value="/zero/ideaDetail/{boardPk}", method=RequestMethod.GET)
	public ModelAndView ideaDetail(@PathVariable("boardPk") int boardPk) throws Exception {
		ModelAndView mv = new ModelAndView("/zero/ideaDetail");
		
		BoardDto data = zeroService.selectIdeaDetail(boardPk);
		log.info("file path : "+data.getFile().get(0).getStoredFilePath());
		mv.addObject("data", data);
		mv.addObject("storedFilePath", data.getFile().get(0).getStoredFilePath());
		
		return mv;
	}
	
	// 커뮤니티 리스트 view
	@RequestMapping(value="/zero/ideaWrite", method=RequestMethod.GET)
	public String ideaWrite() throws Exception {
		return "/zero/ideaWrite";
	}
	
	// 커뮤니티 리스트 form으로 전송 할 부분
	@RequestMapping(value="/zero/ideaWrite", method=RequestMethod.POST)
	public String insertCommunityWrite(BoardDto board, MultipartHttpServletRequest uploadFiles) throws Exception {
		zeroService.insertIdeaWrite(board, uploadFiles);
		
		return "redirect:/zero/ideaList";
	}
	
	// 커뮤니티 리스트 수정
	@RequestMapping(value="/zero/ideaUpdate/{boardPk}", method=RequestMethod.PUT)
	public String ideaUpdate(BoardDto board) throws Exception {
		zeroService.ideaUpdate(board);
		
		return "redirect:/zero/ideaList";
	}
	
	// 커뮤니티 리스트 삭제
	@RequestMapping(value="/zero/ideaDelete/{boardPk}", method=RequestMethod.POST)
	public String ideaDelete(@PathVariable("boardPk") int boardPk) throws Exception {
		zeroService.ideaDelete(boardPk);
		
		return "redirect:/zero/ideaList";
	}
	
	// 커뮤니티 상세보기 다운로드파일
	@RequestMapping(value="/zero/downloadFile", method=RequestMethod.GET)
	public void downloadFile(@RequestParam int idx, @RequestParam int boardIdx, HttpServletResponse response) throws Exception {
		
		TFileDto zeroFile = zeroService.zeroFileInformation(idx, boardIdx);
		
		if(ObjectUtils.isEmpty(zeroFile) == false) {
			String fileName = zeroFile.getOriginalFileName();
			
			byte[] files = FileUtils.readFileToByteArray(new File(zeroFile.getStoredFilePath()));
			
			response.setContentType("application/octet-stream");
			response.setContentLength(files.length);
			response.setHeader("Content-Disposition", "attachment; fileName=\"" + URLEncoder.encode(fileName, "UTF-8") + "\";");
			response.setHeader("Content-Transfer-Encoding", "binary");
			
			response.getOutputStream().write(files);
			response.getOutputStream().flush();
			response.getOutputStream().close();
		}
	}
	
	@RequestMapping(value="/zero/join1", method=RequestMethod.GET)
	public String joinZerowaste() throws Exception {
		return "/zero/join";
	}
	
	@RequestMapping(value="/zero/join1", method=RequestMethod.POST)
	public String insertJoinZerowaste(JoinDto customerPk) throws Exception {
		zeroService.insertJoinzerowaste(customerPk);
		
		return "redirect:/zero/main";
	}
}
