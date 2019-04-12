package com.idus.blog.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import com.idus.auth.dto.Authorization;
import com.idus.blog.dao.BlogDao;
import com.idus.blog.dto.AddCommentRequest;
import com.idus.blog.dto.AddPieceRequest;
import com.idus.blog.dto.ArtistInfo;
import com.idus.blog.dto.Comment;
import com.idus.blog.dto.Options;
import com.idus.blog.dto.Page;
import com.idus.blog.dto.Piece;
import com.idus.blog.dto.PieceImage;
import com.idus.blog.dto.PieceInfo;
import com.idus.blog.dto.Post;
import com.idus.blog.dto.PostInfo;
import com.idus.blog.dto.PostWritingRequest;
import com.idus.common.exception.IllegalAccessException;
import com.idus.common.util.ImageSaver;
import com.idus.common.util.ImageType;
import com.idus.common.util.JsonStringBuilder;

@Service
public class BlogService {
	
	@Autowired
	private BlogDao dao;
	
	
	// 블로그 상품 추가 서비스
	@Transactional
	public boolean addNewPiece(AddPieceRequest addPieceRequest, HttpSession session, HttpServletRequest request) {
		
		boolean isInsertSuccess = false;
		
		/* 1. 상품 이미지 저장 단계  */
		
		MultipartFile thumbnailFile = addPieceRequest.getThumbnail();  // 상품의 썸네일 이미지
		List<MultipartFile> imagesFile = addPieceRequest.getImages();  // 기타 이미지
		
		// 상품 추가 요청으로 부터 상품 객체 생성
		Piece piece = new Piece();
		addPieceRequest.transferTo(piece, session);
		
		// 데이터베이스에 상품 데이터 저장
		int pieceInsertNum = dao.insertPiece(piece);
		int pieceNo = piece.getPieceNo();  // 저장된 상품의 상품 번호
		
		
		/* 2. 상품 옵션 저장 단계 */
		
		List<Options> options = new ArrayList<Options>();  // 상품 옵션 리스트 선언(declare product option list)
		
		// 클라이언트가 추가한 상품 옵션을 선언된 리스트에 추가 (add product option into the option list from client's request)
		for(String o : addPieceRequest.getOptions()) {
			Options option = new Options();
			option.setPieceNo(pieceNo);
			option.setOptions(o);
			options.add(option);
		}
		
		// 옵션을 데이터베이스에 저장
		int optionsInsertNum = 0;
		for(Options option : options) {
			optionsInsertNum += dao.insertOptions(option);
		}
		
		// 썸네일 이미지
		PieceImage thumbnailImage = new PieceImage();
		thumbnailImage.setPieceNo(pieceNo);
		thumbnailImage.setUrl(ImageSaver.saveImage(thumbnailFile, request, ImageType.PIECEIMAGE));
		thumbnailImage.setThumbnail(true);
		
		// 일반 이미지 배열
		List<PieceImage> images = new ArrayList<PieceImage>();
		
		for(MultipartFile file : imagesFile) {
			PieceImage image = new PieceImage();
			image.setPieceNo(pieceNo);
			image.setUrl(ImageSaver.saveImage(file, request, ImageType.PIECEIMAGE));
			image.setThumbnail(false);
			images.add(image);
		}
		
		// 상품 썸네일 이미지 저장
		int imageInsertNum = dao.insertPieceImage(thumbnailImage);
		
		// 상품 일반 이미지 저장
		for(PieceImage img : images) {
			imageInsertNum += dao.insertPieceImage(img);
		}
		
		// 모든 과정이 정상적으로 처리 되었을 경우
		if(pieceInsertNum > 0 && imageInsertNum > 0 && imageInsertNum == 1 + imagesFile.size() && optionsInsertNum > 0) {
			isInsertSuccess = true;
		}
		
		return isInsertSuccess;
	}
	
	
	
	// 블로그 메인 화면의 모델 생성 서비스
	public void getBlogModel(int memberNo, HttpSession session, Model model) {
		
		// 데이터베이스에서 작가 검색
		ArtistInfo artist = dao.selectArtistByMemberNo(memberNo);
		
		// 해당 회원 번호가 존재하지 않는경우
		if(artist == null) {
			throw new IllegalAccessException();  // 존재하지 않는 회원 번호로 잘못된 접근
		}
		
		Authorization auth = (Authorization) session.getAttribute("auth");
		
		// 현재 로그인한 사용자가 블로그 관리자인지 확인
		if(auth == null) {
			artist.setManager(false);
		} else if(auth != null && auth.getMemberNo() == memberNo) {
			artist.setManager(true);
		}
		
		// 최근 등록된 상품 12개를 데이터 베이스에서 검색
		List<PieceInfo> pieceList = dao.selectRecentPieces(memberNo);
		
		// 최근 등록된 포스트 10개를 데이터베이스에서 검색
		List<PostInfo> postList = dao.selectRecentPosts(memberNo);
		
		model.addAttribute("artist", artist);
		model.addAttribute("pieceList", pieceList);
		model.addAttribute("postList", postList);
		
	}
	
	
	
	// 블로그 포스트 작성 서비스
	@Transactional
	public boolean writePost(PostWritingRequest postWritingRequest, HttpSession session) {
		
		// 서비스 실행 결과
		boolean isWriteSuccess = false;
		
		// 포스트 작성 요청이 제대로 처리되지 않은경운
		if(postWritingRequest == null) {
			return isWriteSuccess;
		}
		
		// 포스트 작성 요청으로부터 포스트 객체 생성
		Post post = new Post();
		postWritingRequest.transferTo(post, session);
		
		// 데이터베이스에 포스트 저장
		int postNo = dao.insertPost(post);
		
		// 정상적으로 저장이 실행 되었을 경우
		if(postNo > 0) {
			isWriteSuccess = true;
		}
		
		// 서비스 실행결과 반환
		return isWriteSuccess;
	}
	
	
	
	// 블로그 작품 리스트 뷰의 모델 생성 서비스
	@Transactional
	public void setPieceListModel(int memberNo, String pieceName, HttpSession session, Model model) {
		
		// 데이터베이스에서 작가 검색
		ArtistInfo artist = dao.selectArtistByMemberNo(memberNo);
		
		// 해당 회원 번호가 존재하지 않는경우
		if(artist == null) {
			throw new IllegalAccessException();  // 존재하지 않는 회원 번호로 잘못된 접근
		}
		
		Authorization auth = (Authorization) session.getAttribute("auth");
		
		// 현재 로그인한 사용자가 블로그 관리자인지 확인
		if(auth == null) {
			artist.setManager(false);
		} else if(auth != null && auth.getMemberNo() == memberNo) {
			artist.setManager(true);
		}
		
		// 상품 리스트 12개 검색
		List<PieceInfo> pieceList = null;
		
		if(pieceName == null) {
			pieceList = dao.selectRecentPieces(memberNo);
		} else {
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("memberNo", memberNo);
			param.put("pieceName", pieceName);
			pieceList = dao.selectRecentPieceByPieceName(param);
			
		}
		
		if(pieceList == null) {
			pieceList = new ArrayList<PieceInfo>();
		}
		
		
		model.addAttribute("artist", artist);
		model.addAttribute("pieceList", pieceList);
		model.addAttribute("pieceName", pieceName);
		
	}
	
	
	
	// 블로그 작품 리스트에서 작품 목록 추가 요청에(AJAX) 대한 서비스
	@Transactional
	public JsonStringBuilder getMorePieces(int pageNo, int artistNo, String pieceName) {
		
		// 쿼리 parameter
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("artistNo", artistNo);
		param.put("pageNo", pageNo);
		
		// 작품 리스트 변수 선언
		List<PieceInfo> pieceList = null;
		
		if(pieceName == null) {  // 쿼리 조건에 상품 이름이 없을 경우
			// 데이터베이스에서 상품 추가로 검색
			pieceList = dao.selectMorePieces(param);
		} else {  // 쿼리 조건에 상품 이름이 있을 경우
			param.put("pieceName", pieceName);
			pieceList = dao.selectMorePieceByPieceName(param);
		}
		
		// client로 데이터를 전송하기 위해 json string 생성
		JsonStringBuilder json = new JsonStringBuilder();
		
		if(pieceList == null || pieceList.size() == 0) {  // 작품 리스트가 null이거나 검색된 작품이 없을 경우
			json.addEntry("empty", true);
		} else {  // 검색된 작품이 있을 경우
			json.addEntry("empty", false);
			json.addEntry("pieceList", pieceList);
		}
		
		// 처리 결과 반환
		return json;
	}

	
	
	// 블로그의 포스트 목록 화면 모델 생성 서비스
	public void getPostListModel(int artistNo, int pageNo, String title, HttpSession session, Model model) {
		
		// 데이터베이스에서 작가 검색
		ArtistInfo artist = dao.selectArtistByMemberNo(artistNo);
		
		// 해당 회원 번호가 존재하지 않는경우
		if(artist == null) {
			throw new IllegalAccessException();  // 존재하지 않는 회원 번호로 잘못된 접근
		}
		
		// 세션의 사용자 인증 객체
		Authorization auth = (Authorization) session.getAttribute("auth");
		
		// 인증 객체로부터 현재 로그인한 사용자가 블로그 관리자인지 확인
		if(auth == null || auth.getMemberNo() != artistNo) {
			// 로그인 하지 않은 상태 이거나 사용자가 블로그의 주인이 아닐경우
			artist.setManager(false);
		} else if(auth != null && auth.getMemberNo() == artistNo) {
			// 로그인 한 사용자가 블로그의 주인일 경우
			artist.setManager(true);
		}
		
		// 페이지 번호로 쿼리 시작점 계산
		int offset = (pageNo-1)*10;
		
		// 특정 사용자가 올린 포스트의 전체 개수
		int totalPostCount = 0;
		
		// 포스트 리스트 변수 선언
		List<PostInfo> postList = null;
		
		// 쿼리 parameter
		Map<String, Object> paramForPost = new HashMap<String, Object>();
		paramForPost.put("offset", offset);  // 페이징 시작지점
		paramForPost.put("memberNo", artistNo);  // 회원 번호
		
		// 쿼리 parameter
		Map<String, Object> paramForLength = new HashMap<String, Object>();
		paramForLength.put("artistNo", artistNo);  // 작가 번호
		
		// 쿼리 조건에 따라 처리
		if(title == null) {  // 쿼리 조건에 포스트 제목이 없을 경우
			
			// 포스트 및 전체 포스트 개수 조회
			postList = dao.selectPostByPageNo(paramForPost);  // 데이터베이스에서 포스트 조회
			totalPostCount = dao.selectPostListLength(paramForLength); // 데이터베이스에서 전체 포스트 개수 조회
		
		} else {  // 쿼리에 제목 조건이 있을 경우
			
			// 쿼리 조건에 title parameter 추가
			paramForPost.put("title",  "%" + title + "%");
			paramForLength.put("title", "%" + title + "%");
			
			// 포스트 및 전체 포스트 개수 조회
			postList = dao.selectPostByTitleAndPageNo(paramForPost);  // 데이터베이스에서 포스트 조회
			totalPostCount = dao.selectPostListLengthByTitle(paramForLength); // 데이터베이스에서 해당 제목을 포함하는 포스트 개수 조회
		}
		
		// postList가 null이 아닐 경우
		if(postList != null) {
			
			// 각각의 포스트에 대해 댓글 수 계산
			for(PostInfo p : postList) {
				int commentCount = dao.selectPostCommentCount(p.getPostNo());
				p.setCommentCount(commentCount);
			}
			
		} else {
			postList = new ArrayList<PostInfo>();
		}
		
		// 현재 페이지 묶음 번호(예. 1~5페이지 : 1번 / 6~10페이지 : 2번)
		int currentPageBundle = pageNo/5 + ((pageNo%5 == 0)? 0:1);
		
		// 전체 페이지 묶음 개수(예. 102개 게시글 -> 한 묶음당 50개의 게시글, 총 3개 묶음)
		int totalPageBundle = totalPostCount/50 + ((totalPostCount%50 == 0)? 0:1);
		
		// 현재 페이지 묶음에서 필요한 페이지 번호의 개수(예. 23개의 게시글 -> 3개의 페이지 번호가 필요)
		int pageCountInCurrentPageBundle = 0;
		if(currentPageBundle*50 <= totalPostCount) { // 현재 페이지 묶음 까지의 포스트 수 <= 전체 포스트 수 : 현재 묶음에서 5개의 페이지 번호 필요
			pageCountInCurrentPageBundle = 5;
		} else {  // 그 이외의 경우
			pageCountInCurrentPageBundle = (totalPostCount - (currentPageBundle-1)*50)/10 + ((totalPostCount - (currentPageBundle-1)*50)%10==0 ? 0:1);
		}
		
		
		/* 페이징 처리 */
		
		// 페이징 처리 정보 전달용 페이지 객체 생성
		Page page = new Page();
		page.setCurrentPage(pageNo);  // 현재 페이지 정보 초기화
		
		// 다음 페이지 묶음 존재 여부 정보 초기화
		if(currentPageBundle < totalPageBundle) {  // 현재 페이지 묶음 번호가 전체 페이지 묶음 개수보다 작을 경우 : 다음 페이지 묶음 존재
			page.setHasNext(true);
		} else {  // 그 외의 경우 : 다음 페이지 묶음 존재하지 않음
			page.setHasNext(false);
		}
		
		// 이전 페이지 묶음 존재여부 정보 초기화
		if(currentPageBundle <= 1) {  // 현재 페이지 묶음이 1번 : 이전페이지 묶음이 없음
			page.setHasBefore(false);
		} else {  // 그 외의 경우 : 이전 페이지 묶음 존재
			page.setHasBefore(true);
		}
		
		// 게시판 페이지 번호 목록
		List<Integer> paging = new ArrayList<Integer>();
		
		// 현재 페이지 묶음에서 필요한 게시판 페이지 갯수를 이용하여 게시판 페이지의 번호목록 생성
		// 예) 페이지 묶음 : 4, 필요한 게시판 페이지 갯수 : 3 -> 페이지 번호 목록 = [16, 17, 18]
		for(int i = 4; i > 4 - pageCountInCurrentPageBundle; i--) {
			paging.add(currentPageBundle*5 - i);
		}
		
		// 현재 게시판 페이지 번호 목록 초기화
		page.setPaging(paging);
		
		// 현제 페이지 묶음 정보 초기화
		page.setCurrentBundle(currentPageBundle);
		
		// 모델에 추가
		model.addAttribute("page", page);
		model.addAttribute("artist", artist);
		model.addAttribute("postList", postList);
		model.addAttribute("title", title);
	}
	
	
	
	// 포스트 상세보기 화면의 모델 생성 서비스
	public void getPostDetailModel(int artistNo, int pageNo, int postNo, String title, HttpSession session, Model model) {
		
		// 현재 페이지의 게시판 모델 생성
		getPostListModel(artistNo, pageNo, title, session, model);
		
		// 데이터베이스에서 포스트 번호를 이용하여 포스트 검색
		Post post = dao.selectPostDetailByPostNo(postNo);
		
		// 검색된 포스트가 없을 경우
		if(post == null) {
			throw new IllegalAccessException("잘못된 포스트 번호로의 접근입니다.");
		}
		
		// 헌재 세션의 인증 정보 확인
		Authorization auth = (Authorization) session.getAttribute("auth");
		
		// 인증 정보의 상태에 따라 처리
		if(auth == null || post.getMemberNo() != auth.getMemberNo()) {  // 로그인 되어있지 않거나 검색된 포스트의 작성자가 아닐경우
			post.setMine(false);  // 포스트 소유권 정보 false로 초기화
		} else {  // 그 외의 경우
			post.setMine(true);  // 포스트 소유권 정보 true로 초기화
		}
		
		// 댓글 리스트 검색
		List<Comment> commentList = dao.selectCommentByPostNo(postNo);
		
		// 댓글 작성자와 현재 세션의 사용자 정보 비교 후 댓글 수정권한 부여
		for(Comment c : commentList) {
			System.out.println(c.getMemberNo());
			System.out.println(auth.getMemberNo());
			if(auth != null && c.getMemberNo() == auth.getMemberNo()) {
				System.out.println("111");
				c.setMine(true);
			} else {
				System.out.println("2222");
				c.setMine(false);
			}
		}
		
		// 모델에 추가
		model.addAttribute("post", post);
		model.addAttribute("commentList", commentList);
	}
	
	
	
	// 댓글 달기 서비스
	@Transactional
	public boolean addNewPostComment(AddCommentRequest addCommentRequest, HttpSession session) {
		
		// 서비스 실행 결과
		boolean isInsertSuccess = false;
		
		// 클라이언트의 요청이 정상적으로 처리되지 않았을 경우
		if(addCommentRequest == null) {
			return isInsertSuccess;
		}
		
		// 댓글 객체 생성 및 클라이언트의 요청 정보로 초기화
		Comment comment = new Comment();
		addCommentRequest.transferTo(comment, session);
		
		// 데이터 베이스에 댓글 저장
		int insertedNum = dao.insertComment(comment);
		
		// 저장 성공 시
		if(insertedNum > 0) {
			isInsertSuccess = true;  // 서비스 실행 결과를 true로 설정
		}
		
		// 실행 결과 반환
		return isInsertSuccess;
	}
	
}
