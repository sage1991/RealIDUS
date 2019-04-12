package com.idus.auth.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.idus.auth.dao.JoinDao;
import com.idus.auth.dto.JoinRequest;
import com.idus.auth.dto.Member;
import com.idus.auth.exceptions.FailToJoinException;
import com.idus.common.util.ImageSaver;
import com.idus.common.util.ImageType;

@Service
public class JoinService {
	
	@Autowired
	private JoinDao joinDao;
	
	
	// 회원가입
	@Transactional
	public boolean doJoin(JoinRequest joinRequest, HttpServletRequest request) {
		
		boolean isInsertSuccess = false;
		
		// 썸네일 이미지 업로드
		String thumbnailPath = ImageSaver.saveImage(joinRequest.getThumbnail(), request, ImageType.USERTHUMBNAIL);
		
		// 회원 객체 생성 및 초기화
		Member member = new Member();
		joinRequest.transferTo(member);  // join request객체의 정보를 member 객체로 전달
		member.setThumbnail(thumbnailPath);  // 저장된 썸네일 이미지 경로를 member 객체로 전달
		
		// 회원가입 양식에 작성된 회원 정보를 데이터베이스에 저장
		int insertedNum = joinDao.insertNewMember(member);
		
		if(insertedNum > 0) {  // 회원 정보 저장에 성공 시
			isInsertSuccess = true;
		} else {  // 회원 정보 저장에 실패 시
			throw new FailToJoinException("회원 가입에 실패 하였습니다.");
		}
		
		return isInsertSuccess;
		
	}
	
	
	// 이메일 중복 체크
	public boolean emailDuplicationCheck(String email) {
		
		boolean isDuplicated = false;
		
		// 이메일로 회원 정보 검색
		Member member = joinDao.selectMemberByEmail(email);
		
		// 일치하는 회원이 존재 할 시
		if(member != null) {
			isDuplicated = true;
		}
		
		return isDuplicated;
	}
	
	
	// 닉네임 중복 체크
	public boolean nickNameDuplicationCheck(String nickName) {
		
		boolean isDuplicated = false;
		
		// 닉네임으로 회원 정보 검색
		Member member = joinDao.selectMemberByNickName(nickName);
		
		// 일치하는 회원이 존재 할 시
		if (member != null) {
			isDuplicated = true;
		}
		
		return isDuplicated;
	}
	
}
