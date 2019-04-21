package com.idus.blog.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.idus.blog.dto.ArtistInfo;
import com.idus.blog.dto.Comment;
import com.idus.blog.dto.ModifyCommentRequest;
import com.idus.blog.dto.Options;
import com.idus.blog.dto.Piece;
import com.idus.blog.dto.PieceImage;
import com.idus.blog.dto.PieceInfo;
import com.idus.blog.dto.Post;
import com.idus.blog.dto.PostInfo;

@Repository
public class BlogDao {
	
	@Autowired
	private SqlSession sqlSession;
	
	
	/* 상품 추가 페이지 */
	
	// 상품 추가
	public int insertPiece(Piece piece) {
		return sqlSession.insert("blog.insertPiece", piece);
	}
	
	// 상품 이미지 추가
	public int insertPieceImage(PieceImage pieceImage) {
		return sqlSession.insert("blog.insertPieceImage", pieceImage);
	}
	
	// 상품 옵션 추가
	public int insertOptions(Options option) {
		return sqlSession.insert("blog.insertOptions", option);
	}
	
	
	
	/////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	/* 블로그 메인화면 및 공통 */
	
	
	// 블로그 작가 정보 검색
	public ArtistInfo selectArtistByMemberNo(int memberNo) {
		return sqlSession.selectOne("blog.selectArtistByMemberNo", memberNo);
	}
	
	// 최근 작품 12개 검색 -> 블로그 메인화면 및 작품 게시판에 활용
	public List<PieceInfo> selectRecentPieces(int memberNo) {
		return sqlSession.selectList("blog.selectRecentPieces", memberNo);
	}
	
	// 최근 포스트 10개 검색 -> 블로그 메인화면에 활용
	public List<PostInfo> selectRecentPosts(int memberNo) {
		return sqlSession.selectList("blog.selectRecentPosts", memberNo);
	}
	
	
	////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/* 포스트 작성 페이지 */
	
	// 포스트 작성
	public int insertPost(Post post) {
		return sqlSession.insert("blog.insertPost", post);
	}
	
	
	////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	/* 상품 게시판 페이지 */
	
	// AJAX 요청에 대해 추가로 상품 리스트 검색
	public List<PieceInfo> selectMorePieces(Map<String, Object> param) {
		return sqlSession.selectList("blog.selectMorePieces", param);
	}
	
	// AJAX 요청에 대해 상품 이름을 이용하여 추가 검색
	public List<PieceInfo> selectMorePieceByPieceName(Map<String, Object> param) {
		return sqlSession.selectList("blog.selectMorePieceByPieceName", param);
	}
	
	// 최근 작품 12개를 상픔 이름을 이용하여 검색
	public List<PieceInfo> selectRecentPieceByPieceName(Map<String, Object> param) {
		return sqlSession.selectList("blog.selectRecentPieceByPieceName", param);
	}
	
	
	////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	/* 포스트 게시판 페이지 */
	
	// 클라이언트에서 요청한 페이지로 포스트 정보 검색
	public List<PostInfo> selectPostByPageNo(Map<String, Object> params) {
		return sqlSession.selectList("blog.selectPostByPageNo", params);
	}
	
	// 클라이언트에서 요청한 페이지, 제목으로 포스트 검색
	public List<PostInfo> selectPostByTitleAndPageNo(Map<String, Object> params) {
		return sqlSession.selectList("blog.selectPostByTitleAndPageNo", params);
	}
	
	// 특정 사용자가 작성한 포스트의 전체 개수 확인
	public int selectPostListLength(Map<String, Object> params) {
		return sqlSession.selectOne("blog.selectPostListLength", params);
	}
	
	// 특정 제목을 포함하는 포스트의 전체 개수 확인
	public int selectPostListLengthByTitle(Map<String, Object> params) {
		return sqlSession.selectOne("blog.selectPostListLengthByTitle", params);
	}
	
	// 포스트의 댓글 개수 확인
	public int selectPostCommentCount(int postNo) {
		return sqlSession.selectOne("blog.selectPostCommentCount", postNo);
	}
	
	// 포스트 내용 검색
	public Post selectPostDetailByPostNo(int postNo) {
		return sqlSession.selectOne("blog.selectPostDetailByPostNo", postNo);
	}
	
	// 특정 포스트의 댓글 검색
	public List<Comment> selectCommentByPostNo(int postNo) {
		return sqlSession.selectList("blog.selectCommentByPostNo", postNo);
	}

	public int insertComment(Comment comment) {
		return sqlSession.insert("blog.insertComment", comment);
	}

	public Comment selectCommentByComNo(int comNo) {
		return sqlSession.selectOne("blog.selectCommentByComNo", comNo);
	}

	public int deleteCommentByComNo(int comNo) {
		return sqlSession.delete("blog.deleteCommentByComNo", comNo);
	}

	public int updateComment(ModifyCommentRequest modifyCommentRequest) {
		return sqlSession.update("blog.updateComment", modifyCommentRequest);
	}
	
}
