package com.idus.blog.dto;

import java.time.LocalDateTime;

import javax.servlet.http.HttpSession;

import com.idus.auth.dto.Authorization;

public class PostWritingRequest {
	
	private String title;
	private String content;
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	public void transferTo(Post post, HttpSession session) {
		post.setMemberNo(((Authorization)session.getAttribute("auth")).getMemberNo());
		post.setTitle(this.getTitle());
		post.setContent(this.getContent());
		post.setCreatedDate(LocalDateTime.now());
	}
	
}