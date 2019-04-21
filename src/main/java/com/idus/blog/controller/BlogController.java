package com.idus.blog.controller;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.idus.auth.dto.Authorization;
import com.idus.blog.dto.AddCommentRequest;
import com.idus.blog.dto.AddPieceRequest;
import com.idus.blog.dto.ModifyCommentRequest;
import com.idus.blog.dto.PostWritingRequest;
import com.idus.blog.service.BlogService;
import com.idus.common.exception.IllegalAccessException;
import com.idus.common.util.DateTimeFormmatUtil;
import com.idus.common.util.ImageSaver;
import com.idus.common.util.ImageType;
import com.idus.common.util.JsonStringBuilder;
import com.idus.common.util.ResponseManager;


@Controller
@RequestMapping("/blog")
public class BlogController {
	
	@Autowired
	private BlogService service;
	private String addPieceView = "blog/addPiece";
	private String blogView = "blog/blogHome";
	private String writePostView = "blog/writePost";
	private String pieceListView = "blog/pieceList";
	private String postListView = "blog/postList";
	private String postDetailView = "blog/postDetail";
	
	
	
	// 블로그 메인 화면 handler
	@RequestMapping(value="/{memberNo}", method=RequestMethod.GET)
	public String blogViewHandler(@PathVariable("memberNo") int memberNo, HttpSession session, Model model) {
		
		// 메인화면의 모델 생성
		service.getBlogModel(memberNo, session, model);
		
		// 메인화면 뷰로 forward
		return blogView;
	}
	
	
	
	// 상품 추가 화면 handler
	@RequestMapping(value="/addPiece", method=RequestMethod.GET)
	public String newPieceViewHandler() {
		// 상품 추가 뷰로 forward
		return addPieceView;
	}
	
	
	
	// 상품 추가 처리 핸들러
	@RequestMapping(value="/addPiece", method=RequestMethod.POST)
	public String newPieceHandler(AddPieceRequest addPieceRequest, HttpSession session, HttpServletRequest request) {
		
		// 상품 추가 처리 결과
		boolean isInsertSuccess = false;
		
		// 세션으로부터 회원번호 확인
		int memberNo = ((Authorization) session.getAttribute("auth")).getMemberNo();
		
		// 상품 추가 요청이 null이 아닐경우 -> 서비스 실행
		if(addPieceRequest != null) {
			isInsertSuccess = service.addNewPiece(addPieceRequest, session, request);
		}
		
		// 처리 결과에 따라 해당되는 페이지로 redirect
		if(isInsertSuccess) {
			return "redirect:/blog/" + memberNo + "/pieceList";
		} else {
			// TODO 나중에 리다이렉트 페이지 수정 필요.
			return "redirect:/error/404error";
		}
		
	}
	
	
	
	// 포스트 작성 페이지 뷰 핸들러
	@RequestMapping(value="/writePost", method=RequestMethod.GET)
	public String writePostViewHandler() {
		// 포스트 작성 뷰로 forward
		return writePostView;
	}
	
	
	
	// 포스트 작성 핸들러
	@RequestMapping(value = "/writePost", method = RequestMethod.POST)
	public String writePostHandler(PostWritingRequest postWritingRequest, HttpSession session) {
		
		// 서비스 실행 결과
		boolean isWriteSuccess = false;
		
		// 포스트 저장
		isWriteSuccess = service.writePost(postWritingRequest, session);
		
		// 회원 번호
		int memberNo = ((Authorization) session.getAttribute("auth")).getMemberNo();
		
		// 서비스 실행 결과에 따라 각각의 페이지로 redirect
		if(isWriteSuccess) {
			return "redirect:/blog/" + memberNo + "/postList";  // 자기 블로그의 포스트 목록 화면
		} else {
			// TODO 리다이렉트 주소 지정 필요!
			return "";
		}
	}
	
	
	
	// 포스트 또는 작품 상세 내용의 이미지 업로드 요청(ajax)에 대한 핸들러
	@RequestMapping(value="/saveImage", method=RequestMethod.POST)
	public void pieceImageHandler(MultipartFile multipartFile, HttpSession session, HttpServletRequest request, HttpServletResponse response) {
		
		// 저장된 이미지의 url
		String imageUrl = null;
		// referer url
		String referer = (String) request.getHeader("referer");
		
		// 파일이 null이 아닐경우 -> 이미지 저장
		if(multipartFile != null) {
			// referer url에 따라 다른 경로로 파일 저장
			if(referer.indexOf("addPiece") >= 0) {
				// 상품 이미지 폴더에 저장
				imageUrl = ImageSaver.saveImage(multipartFile, request, ImageType.PIECEDETAILIMAGE);
			} else {
				// 포스트 이미지 폴더에 저장
				imageUrl = ImageSaver.saveImage(multipartFile, request, ImageType.POSTIMAGE);
			}
		}
		
		// JSON 형태의 스트링 생성
		JsonStringBuilder json = new JsonStringBuilder();
		PrintWriter out = ResponseManager.getJSONWriter(response);
		
		// url 처리 결과에 따라 각각 json string 생성
		if(imageUrl != null) {
			json.addEntry("isSaved", true);
			json.addEntry("url", imageUrl);
		} else {
			json.addEntry("isSaved", false);
		}
		
		// client로 전송
		out.write(json.toString());
	}
	
	
	
	// 작품 목록 화면 handler
	@RequestMapping(value="/{memberNo}/pieceList", method=RequestMethod.GET)
	public String pieceListViewHandler(
			@PathVariable("memberNo") int memberNo, 
			@RequestParam(value="pieceName", required=false) String pieceName, 
			HttpSession session, Model model) {
		
		// 작품 리스트 모델 생성
		service.setPieceListModel(memberNo, pieceName, session, model);
		
		// 작품 리스트 뷰로 forward
		return pieceListView;
	}
	
	
	
	// 작품 게시판 추가 로딩(AJAX) 요청에 대한 handler
	@RequestMapping(value="/morePieces", method=RequestMethod.POST)
	public void morePieceHandler(
			int pageNo, 
			int artistNo, 
			@RequestParam(value="pieceName", required=false) String pieceName, HttpServletResponse response) {
		
		// 추가 상품에 대한 정보를 json string 형태로 생성
		JsonStringBuilder json = service.getMorePieces(pageNo, artistNo, pieceName);
		PrintWriter out = ResponseManager.getJSONWriter(response);
		
		// 처리 결과를 client로 전송
		out.write(json.toString());
	}
	
	
	
	// 포스트 게시판 화면 handler
	@RequestMapping(value="/{artistNo}/postList", method=RequestMethod.GET)
	public String postListViewHandler(
			@PathVariable("artistNo") int artistNo,
			@RequestParam(value="pageNo", required=false, defaultValue="1") int pageNo, 
			@RequestParam(value="title", required=false) String title, 
			HttpSession session, Model model) {
		
		// 포스트 리스트의 모델 생성
		service.getPostListModel(artistNo, pageNo, title, session, model);
		
		// 포스트 게시판 뷰로 forward
		return postListView;
	}
	
	
	
	// 포스트 상세 내용
	@RequestMapping(value="/{artistNo}/postDetail", method=RequestMethod.GET)
	public String postDetailViewHandler(
			@PathVariable("artistNo") int artistNo,
			@RequestParam(value="pageNo", required=false, defaultValue="1") int pageNo, 
			@RequestParam(value="postNo", required=true, defaultValue="-1") int postNo, 
			@RequestParam(value="title", required=false) String title,
			HttpSession session, Model model) {
		
		// 포스트 모델 생성
		service.getPostDetailModel(artistNo, pageNo, postNo, title, session, model);
		
		// 포스트 상세 뷰로 forward
		return postDetailView;
		
	}
	
	
	
	// 댓글 작성 핸들러
	@RequestMapping(value="/{artistNo}/addComment", method=RequestMethod.POST)
	public String addPostCommentHandler(
			@PathVariable("artistNo") int artistNo,
			@RequestParam(value="pageNo", required=false, defaultValue="1") int pageNo, 
			@RequestParam(value="title", required=false) String title,
			AddCommentRequest addCommentRequest, HttpSession session) {
		
		// 처리 결과
		boolean isInserted = false;
		
		// 댓글 작성 서비스 실행
		isInserted = service.addNewPostComment(addCommentRequest, session);
		
		// 서비스 실행 결과에 따라 각기 다른 페이지로 redirect
		if(isInserted) {
			return "redirect:/blog/" + artistNo + "/postDetail?pageNo=" + pageNo + "&postNo=" + addCommentRequest.getPostNo() + "&title=" + title;
		} else {
			return "redirect:/error/404error";
		}
	}
	
	
	
	// 댓글 삭제 핸들러
	@RequestMapping(value="/deleteComment", method=RequestMethod.POST)
	public void deletePostCommentHandler(
			@RequestParam(value="comNo", required=true, defaultValue="-1") int comNo,
			HttpSession session, HttpServletResponse response) {
		
		// 댓글 번호가 정상적으로 넘어오지 않았을 경우
		if(comNo <= 0) {
			throw new IllegalAccessException("잘못된 방식으로 댓글 삭제를 시도 하였습니다.");
		}
		
		// 서비스 실행 결과
		boolean isDeleteSuccess = false;
		// 댓글 삭제 서비스 실행
		isDeleteSuccess = service.deletePostComment(comNo, session);
		
		// client로 응답 전송용 객체 생성
		JsonStringBuilder json = new JsonStringBuilder();
		PrintWriter out = ResponseManager.getJSONWriter(response);
		
		// 서비스 처리결과를 json string으로 생성
		json.addEntry("isDeleted", isDeleteSuccess);
		
		// client로 response
		out.write(json.toString());
	}
	
	
	
	// 댓글 수정 핸들러
	@RequestMapping(value="/modifyComment", method=RequestMethod.POST)
	public void modifyPostCommentHandler(ModifyCommentRequest modifyCommentRequest, HttpSession session, HttpServletResponse response) {
		
		// 댓글 수정에 대한 parameter가 정상적으로 넘어오지 않았을 경우
		if(modifyCommentRequest == null) {
			throw new IllegalAccessException("잘못된 방식으로 댓글 수정을 시도 하였습니다.");
		}
		
		// 서비스 실행 결과
		boolean isModifySuccess = false;
		
		// 댓글 수정 서비스 실행
		isModifySuccess = service.modifyPostComment(modifyCommentRequest, session);
		
		// client로 응답 전송용 객체 생성
		JsonStringBuilder json = new JsonStringBuilder();
		PrintWriter out = ResponseManager.getJSONWriter(response);
		
		json.addEntry("isModified", isModifySuccess);
		if(isModifySuccess) {
			json.addEntry("modifiedDate", DateTimeFormmatUtil.format(modifyCommentRequest.getModifiedDate()));
		}
		
		// client로 response
		out.write(json.toString());
	}
	
}