package com.idus.auth.service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.idus.auth.dao.LoginDao;
import com.idus.auth.dto.Authorization;
import com.idus.auth.dto.LoginSession;
import com.idus.auth.dto.Member;
import com.idus.common.exception.IllegalAccessException;
import com.idus.common.util.SecurityManager;


public class AutoLoginInterceptor extends HandlerInterceptorAdapter {
	
	@Autowired
	private LoginDao dao;
	private String autoLoginCookieName = SecurityManager.hashByMd5("autoLogin");  // md5로 암호화된 자동 로그인 관련 쿠키의 이름
	
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
	throws Exception {
		
		// 클라이언트의 세션 확인
		HttpSession session = request.getSession();
		
		// 현재 세션이 로그인 되어 있거나 인터셉터를 통과한 적이 있는지 확인
		if(session.getAttribute("auth") != null || session.getAttribute("autoLogin") != null && (boolean) session.getAttribute("autoLogin")) {
			return true;  // 통과한 적이 있는 경우 method 종료
		}
		
		// 인증 객체 확인
		Authorization auth = (Authorization) session.getAttribute("auth");
		
		// 클라이언트가 로그인 되어 있지 않은 상태
		if (auth == null) {
			
			Cookie[] cookies = request.getCookies();  // request에서 client의 쿠키 확인
			String cookieName = null;
			String sessionId = null;
			
			// 쿠키 존재 시 자동 로그인 관련 쿠키 조회
			if(cookies != null) {
				for (Cookie c : cookies) {
					cookieName = c.getName();
					// 클라이언트로부터 확인된 쿠키의 이름이 자동 로그인 관련 쿠키의 이름과 같을 경우
					if (cookieName.equals(autoLoginCookieName)) {
						sessionId = c.getValue();  // 쿠키로 부터 세션 아이디 확보
						break;
					}
				}  // for end
			}  // if end
			
			
			// 자동 로그인 관련 쿠키가 있을 경우 -> 데이터 베이스에 저장된 세션 검색
			if (sessionId != null) {
				
				// 데이터 베이스에서 로그인세션 검색
				LoginSession loginSession = dao.selectLoginSession(sessionId);
				
				
				if(loginSession != null) {  // 검색된 세션이 존재 할 경우 -> 정상적인 접근
					
					// 데이터베이스에서 세션이 검색 되었을 경우, 로그인 세션의 회원번호 확인
					int memberNo = loginSession.getMemberNo();
					
					// 회원번호로 회원 검색
					Member member = dao.selectMemberByMemberNo(memberNo);
					
					// 검색된 회원이 소셜 회원이 아닌경우만 자동 로그인 처리
					if (member != null && !member.isSocial()) {
						
						// 새로운 인증 객체 생성
						auth = new Authorization();
						auth.setMemberNo(member.getMemberNo());
						auth.setEmail(member.getEmail());
						auth.setName(member.getName());
						auth.setNickName(member.getNickName());
						auth.setThumbnail(member.getThumbnail());
						
						// 세션에 인증객체를 추가하여 현재 세션을 로그인 처리
						session.setAttribute("auth", auth);
						
					}
				} else {
					// 데이터베이스에서 검색된 세션이 없을 경우 -> 비 정상 적인 접근. 로그인 처리 하지 않음
					throw new IllegalAccessException("잘못된 접근입니다.");  // TODO logging 작업 필요
				}
			}
		}
		
		// 현재 세션이 인터셉터를 통과한 적이 있다고 표시
		session.setAttribute("autoLogin", true);
		return true;
		
	}  // preHandle method end

}
