package com.idus.common.util;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import com.idus.auth.exceptions.FileSaveFailException;

public class ImageSaver {
	
	
	public static String saveImage(MultipartFile multipartFile, HttpServletRequest request, ImageType type) {
		
		// 웹 상에서의 이미지의 절대경로
		String imagePath = null;
		
		// 파일이 null이 아니고 비어있지 않을경우
		if(multipartFile != null && !multipartFile.isEmpty()) {
			
			// 이미지 저장 폴더의 경로 = 기본 이미지 저장 경로 + 이미지 타입에 따른 경로 + 오늘 날짜 경로
			LocalDate today = LocalDate.now();
			String dirPath = request.getServletContext().getRealPath(
				"/resources/user/image/" 
				+ type.toString() 
				+ today.getYear() + "_" + today.getMonthValue() + "_" + today.getDayOfMonth()
			);
			System.out.println(dirPath);
			
			// unique한 파일 이름 생성
			UUID uuid = UUID.randomUUID();
			String fileName = uuid.toString() + "_" + multipartFile.getOriginalFilename().replaceAll("[\\(|\\)|\\s]", "");  // 이미지 이름에서 괄호 및 공백 제거
			
			// 생성한 경로로 파일 객체 생성
			File file = new File(dirPath, fileName);
			
			// 해당 파일 및 해당 경로까지의 디렉터리가 존재하지 않을 경우 -> 생성
			if(!file.exists()) {
				file.mkdirs();
			}
			
			try {
				multipartFile.transferTo(file);  // 파일 저장
			} catch (IllegalStateException | IOException e) {
				throw new FileSaveFailException("이미지 저장 실패");
			}
			
			// 파일이 저장된 웹상에서의 절대경로
			imagePath = request.getContextPath() + "/resources/user/image/" 
					+ type.toString() // 이미지 타입에 따른 경로
					+ today.getYear() + "_" + today.getMonthValue() + "_" + today.getDayOfMonth() // 날짜
					+ "/" + fileName;  // 파일이름
			
		} else {  // 파일이 비어있는 경우 -> 각각의 기본이미지 절대경로
			
			// 이미지가 썸네일일 경우
			if(type.equals(ImageType.USERTHUMBNAIL)) {
				// 썸네일 이미지의 기본경로
				imagePath = request.getContextPath() + "/resources/user/image/userThumbnail/default_thumbnail.png";
			}
			
			// TODO 각각의 이미지 타입에 맞게 기본 이미지 절대 경로 설정 필요!
			
		}
		
		// 이미지 경로 반환
		return imagePath;
	}
	
	
}
