package com.idus.blog.dto;

import java.time.LocalDateTime;

public class ModifyCommentRequest {
	
	private int comNo;
	private String content;
	private LocalDateTime modifiedDate;
	
	public ModifyCommentRequest() {
		modifiedDate = LocalDateTime.now();
	}
	
	public int getComNo() {
		return comNo;
	}
	public void setComNo(int comNo) {
		this.comNo = comNo;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public LocalDateTime getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(LocalDateTime modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
}
