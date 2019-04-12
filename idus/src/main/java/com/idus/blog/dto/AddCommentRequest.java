package com.idus.blog.dto;

import java.time.LocalDateTime;

import javax.servlet.http.HttpSession;

import com.idus.auth.dto.Authorization;

public class AddCommentRequest {
	
	private int postNo;
	private int memberNo;
	private String content;
	
	public AddCommentRequest() {}
	
	public AddCommentRequest(int postNo, int memberNo, String content) {
		this.postNo = postNo;
		this.memberNo = memberNo;
		this.content = content;
	}
	
	public int getPostNo() {
		return postNo;
	}
	public void setPostNo(int postNo) {
		this.postNo = postNo;
	}
	public int getMemberNo() {
		return memberNo;
	}
	public void setMemberNo(int memberNo) {
		this.memberNo = memberNo;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	public void transferTo(Comment comment, HttpSession session) {
		comment.setPostNo(postNo);
		comment.setMemberNo(((Authorization)session.getAttribute("auth")).getMemberNo());
		comment.setContent(content);
		comment.setCreatedDate(LocalDateTime.now());
	}
}
