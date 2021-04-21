package com.bitc.zero.common;

import java.io.File;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.bitc.zero.dto.TFileDto;

// @Component - @Bean 어노테이션과 동일하게 Spring IoC 에 등록하여 자동 관리하도록 하는 어노테이션, 사용자가 직접 클래스를 생성하여 등록할 경우 @Component를 사용함
@Component
public class FileUtil {
	
	public List<TFileDto> parseFileInfo(int boardIdx, MultipartHttpServletRequest uploadFiles) throws Exception {
//		업로드된 파일이 존재하는지 여부 확인
		if (ObjectUtils.isEmpty(uploadFiles)) {
			return null;
		}
		
//		파일 정보 리스트
		List<TFileDto> fileList = new ArrayList<>();
		
		///////////  서버에 파일을 저장할 디렉토리 생성
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyyMMdd"); // 날짜 형식 지정
		ZonedDateTime current = ZonedDateTime.now(); // 현재 날짜시간 가져오기
		
//		이미지 저장 폴더명 설정 (예 : images/20210316)
//		String path = "images/" + current.format(format);
		String path = "/img/" + current.format(format);
		
//		File 클래스를 통해서 실제 폴더 생성
		File file = new File(path);
//		기존에 동일한 폴더가 존재하는지 확인
		if (file.exists() == false) {
			file.mkdirs(); // 폴더 생성
		}
		///////////  이미지 저장 폴더 생성 부분
		
		Iterator<String> iterator = uploadFiles.getFileNames();
		
		String newFileName, oriFileExtension, contentType;
		
		while (iterator.hasNext()) {
			List<MultipartFile> list = uploadFiles.getFiles(iterator.next());
			
			for (MultipartFile multiFile : list) {
				if (multiFile.isEmpty() == false) {
					contentType = multiFile.getContentType();
					
//					파일 확장자 확인하기
					if (ObjectUtils.isEmpty(contentType)) {
						break;
					}
					else {
						if (contentType.contains("image/jpeg")) {
							oriFileExtension = ".jpg";
						}
						else if (contentType.contains("image/png")) {
							oriFileExtension = ".png";
						}
						else if (contentType.contains("image/gif")) {
							oriFileExtension = ".gif";
						}
						else {
							break;
						}
					}
					
//					업로드된 파일의 이름을 변경 / 서버에 여러 사람이 접속하여 동시에 파일을 업로드할 경우 파일명이 겹칠 수 있기 때문에 날짜, 시간을 사용하여 파일을 이름을 겹치지 않도록 함
					newFileName = Long.toString(System.nanoTime()) + oriFileExtension;
					TFileDto boardFile = new TFileDto();
					boardFile.setBoardIdx(boardIdx);
					boardFile.setFileSize(multiFile.getSize());
					boardFile.setOriginalFileName(multiFile.getOriginalFilename());
					boardFile.setStoredFilePath(path + "/" + newFileName);
					fileList.add(boardFile);
					
//					서버에 업로드된 파일을 실제로 저장
					file = new File(path + "/" + newFileName);
					multiFile.transferTo(file);
				}
			}
		}
		
		return fileList;
	}
	
	public List<TFileDto> parseReviewFileInfo(int productReviewIdx, MultipartHttpServletRequest uploadFiles) throws Exception {
//		업로드된 파일이 존재하는지 여부 확인
		if (ObjectUtils.isEmpty(uploadFiles)) {
			return null;
		}
		
//		파일 정보 리스트
		List<TFileDto> fileList = new ArrayList<>();
		
		///////////  서버에 파일을 저장할 디렉토리 생성
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyyMMdd"); // 날짜 형식 지정
		ZonedDateTime current = ZonedDateTime.now(); // 현재 날짜시간 가져오기
		
//		이미지 저장 폴더명 설정 (예 : images/20210316)
//		String path = "images/" + current.format(format);
		String path = "/img/" + current.format(format);
		
//		File 클래스를 통해서 실제 폴더 생성
		File file = new File(path);
//		기존에 동일한 폴더가 존재하는지 확인
		if (file.exists() == false) {
			file.mkdirs(); // 폴더 생성
		}
		///////////  이미지 저장 폴더 생성 부분
		
		Iterator<String> iterator = uploadFiles.getFileNames();
		
		String newFileName, oriFileExtension, contentType;
		
		while (iterator.hasNext()) {
			List<MultipartFile> list = uploadFiles.getFiles(iterator.next());
			
			for (MultipartFile multiFile : list) {
				if (multiFile.isEmpty() == false) {
					contentType = multiFile.getContentType();
					
//					파일 확장자 확인하기
					if (ObjectUtils.isEmpty(contentType)) {
						break;
					}
					else {
						if (contentType.contains("image/jpeg")) {
							oriFileExtension = ".jpg";
						}
						else if (contentType.contains("image/png")) {
							oriFileExtension = ".png";
						}
						else if (contentType.contains("image/gif")) {
							oriFileExtension = ".gif";
						}
						else {
							break;
						}
					}
					
//					업로드된 파일의 이름을 변경 / 서버에 여러 사람이 접속하여 동시에 파일을 업로드할 경우 파일명이 겹칠 수 있기 때문에 날짜, 시간을 사용하여 파일을 이름을 겹치지 않도록 함
					newFileName = Long.toString(System.nanoTime()) + oriFileExtension;
					TFileDto productReviewFile = new TFileDto();
					//boardFile.setBoardIdx(boardIdx);
					productReviewFile.setProductReviewPk(productReviewIdx);
					productReviewFile.setFileSize(multiFile.getSize());
					productReviewFile.setOriginalFileName(multiFile.getOriginalFilename());
					productReviewFile.setStoredFilePath(path + "/" + newFileName);
					fileList.add(productReviewFile);
					
//					서버에 업로드된 파일을 실제로 저장
					file = new File(path + "/" + newFileName);
					multiFile.transferTo(file);
				}
			}
		}
		
		return fileList;
	}
	
}








