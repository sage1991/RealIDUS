package com.idus.blog.dto;

import java.time.LocalDateTime;

import com.idus.common.util.DateTimeFormmatUtil;

public class Comment {
	
	private int comNo;
	private int postNo;
	private int memberNo;
	private String nickName;
	private String thumbnail;
	private String content;
	private LocalDateTime createdDate;
	private LocalDateTime modifiedDate;
	private boolean isMine;
	
	public int getComNo() {
		return comNo;
	}
	public void setComNo(int comNo) {
		this.comNo = comNo;
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
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getThumbnail() {
		return thumbnail;
	}
	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public LocalDateTime getCreatedDate() {
		return createdDate;
	}
	public String getFormatedCreatedDate() {
		return DateTimeFormmatUtil.format(createdDate);
	}
	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}
	public LocalDateTime getModifiedDate() {
		return modifiedDate;
	}
	public String getFormatedModifiedDate() {
		return DateTimeFormmatUtil.format(modifiedDate);
	}
	public void setModifiedDate(LocalDateTime modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	public boolean isMine() {
		return isMine;
	}
	public void setMine(boolean isMine) {
		this.isMine = isMine;
	}
}