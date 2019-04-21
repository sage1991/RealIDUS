<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<%@ include file="/WEB-INF/views/commons/meta.jsp"%>
</head>
<body>

	<!-- header -->
	<c:if test="${!empty auth}">
		<%@ include file="/WEB-INF/views/commons/loginHeader.jsp"%>
	</c:if>
	<c:if test="${empty auth}">
		<%@ include file="/WEB-INF/views/commons/noLoginHeader.jsp"%>
	</c:if>

	<section>
		<div class="container">
			<!-- 헤더 -->
			<div class="sectionHeader">
				<h1>포스트</h1>
				<hr>
				<p>작가님의 최근 소식을 접하고 소통 해 보세요!</p>
			</div>

			<!-- 블로그 내용 -->
			<div class="atelier">

				<!-- 좌측 네비게이션 -->
				<div class="leftNav">
					<!-- 프로필 -->
					<div class="blogProfile"
						onclick="window.location='${pageContext.request.contextPath}/blog/${artist.artistNo}'"
						style="cursor: pointer">
						<!-- 프로필 사진 : 블로그 주인 프로필 이미지 받아오기 -->
						<div class="profileImage"
							style="background-image: url(${artist.thumbnail})"></div>
						<!-- 작가 이름 -->
						<div class="name">
							<h3>${artist.nickName}</h3>
						</div>
						<!-- 자기소개 -->
						<div class="introduction">${artist.introduction}</div>
					</div>

					<c:if test="${artist.isManager()}">
						<!-- 자신 소유 블로그 일때 보이는 메뉴 -->
						<div class="loginMenu">
							<ul>
								<li class="menubtn"><a
									href="${pageContext.request.contextPath}/blog/addPiece">상품
										등록</a></li>
								<li class="menubtn"><a
									href="${pageContext.request.contextPath}/blog/writePost">포스트
										작성</a></li>
								<li class="menubtn"><a href="">나의 주문 관리</a></li>
							</ul>
						</div>
					</c:if>

					<!-- 일반 메뉴 -->
					<div class="generalMenu">
						<ul>
							<c:if test="${!artist.isManager() && !empty auth}">
								<!-- 타인의 공방에만 보이는 메뉴 -->
								<li class="menubtn"><a href="">즐겨찾기 추가</a></li>
							</c:if>
							<!-- 공통 메뉴 -->
							<li class="menubtn"><a href="${pageContext.request.contextPath}/blog/${artist.artistNo}/pieceList">상품 보기</a></li>
							<li class="menubtn"><a href="${pageContext.request.contextPath}/blog/${artist.artistNo}/postList">포스트 보기</a></li>
						</ul>
					</div>
					<!-- 공방 정보 -->
					<div class="info">
						<h3>활동 정보</h3>
						<p>팔로우 : ${artist.follower}</p>
					</div>
				</div>

				<!-- 우측 -->
				<div class="blogContent">
					<div class="post">
						<!-- 제목 -->
						<h3>${post.title}</h3>
						<div class="postInfo">
							<!-- 썸네일 -->
                            <img class="thumbnail" src="${post.thumbnail}" alt="썸네일" />
							<!-- 닉네임 -->
							<span class="nickName">${post.nickName}</span>
							<!-- 작성시간(수정시간) -->
							<span class="time">${post.getFormatedCreatedDate()} 
							<c:if test="${!empty post.modifiedDate}"> (${post.getFormatedModifiedDate()} 수정됨)</c:if>
							</span>
						</div>
						<hr>
						<!-- 내용 -->
						<div class="postContent">${post.content}</div>
						<!-- c:if test -> 게시글이 나의 게시물일 경우 -->
						<c:if test="${post.isMine()}">
							<div class="modDelbtn">
								<button onclick="modifyPost(${post.postNo})">수 정</button> <button onclick="deletePost(${post.postNo})">삭 제</button>
							</div>
						</c:if>
					</div>
					
					<div class="comment">
						<h3>댓글</h3>
						<hr>
						<!-- 댓글 등록 양식 : 로그인 시에만 노출 -->
						<c:if test="${!empty auth}">
							<div class="commentInput">
								<form action="${pageContext.request.contextPath}/blog/${artist.artistNo}/addComment" method="post" id="commentForm">
									<input type="hidden" name="postNo" value="${post.postNo}" />
									<input type="hidden" name="title" value="${title}" />
									<input type="hidden" name="pageNo" value="${page.currentPage}" />
									<textarea type="text" name="content" placeholder="가장 먼저 감정을 표현 해 보세요!"></textarea>
									<button type="submit">등 록</button>
								</form>
							</div>
						</c:if>
						<!-- 댓글 리스트 -->
						<ul>
							<c:if test="${!empty commentList}">
								<c:forEach var="comment" items="${commentList}" varStatus="status">
									<li id="com${status.count}">
										<!-- 작성자 닉네임 -->
										<h4>
											<!-- 썸네일 -->
		                                    <a href="${pageContext.request.contextPath}/blog/${comment.memberNo}" title="블로그">
		                                        <img class="thumbnail" src="${comment.thumbnail}" alt="썸네일" />
		                                    </a> 
											${comment.nickName}
										</h4>
										<!-- 작성 시간 -->
										<span>${comment.getFormatedCreatedDate()}</span>
										<!-- 수정 시간 -->
										<span id="modDate${status.count}">
											<c:if test="${!empty comment.modifiedDate}">
												(${comment.getFormatedModifiedDate()} 수정됨)
											</c:if>
										</span>
										<!-- 댓글 내용 -->
										<div class="commentContent" id="comContent${status.count}">${comment.content}</div>
										<!-- c:if -> 내가 쓴 코멘트 일 경우! -->
										<c:if test="${comment.isMine()}">
											<div class="absolultBtn">
												<button onclick="startModify(${comment.comNo}, ${status.count})">수정</button>
												<button onclick="deleteComment(${comment.comNo}, 'com${status.count}')">삭제</button>
											</div>
										</c:if>
									</li>
								</c:forEach>
							</c:if>
							<!-- 출력할 포스트가 없을 경우 -->
							<c:if test="${empty commentList}">
								<li style="color: #ccc;">등록된 댓글이 없습니다.</li>
							</c:if>
						</ul>
					</div>
					
					<hr>
					
					<div class="postContainer">
						<c:if test="${!empty postList}">
							<table>
								<colgroup>
									<col span="1" style="width: 60%;">
									<col span="1" style="width: 20%">
									<col span="1" style="width: 20%;">
								</colgroup>
								<thead>
									<tr>
										<th>제목</th>
										<th>작성자</th>
										<th>작성시간</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach var="postinfo" items="${postList}">
										<tr onclick="viewPost(${postinfo.postNo})">
											<td style="text-align: left; padding-left: 10px">${postinfo.title}(${postinfo.commentCount})</td>
											<td>${postinfo.nickName}</td>
											<td>${postinfo.getFormatedCreatedDate()}</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
							<span class="paging">
								<!-- 이전 항목이 존재 할 경우 -->
								<c:if test="${page.isHasBefore()}">
									<button onclick="changePageBundle(-1)">&lt;&lt; 이전 5개</button>
								</c:if>
								<c:forEach var="pageNo" items="${page.paging}">
									<c:if test="${pageNo == page.currentPage}">
										<button class="currentPage" onclick="nextPage(${pageNo})">${pageNo}</button>
									</c:if>
									<c:if test="${pageNo != page.currentPage}">
										<button onclick="nextPage(${pageNo})">${pageNo}</button>
									</c:if>
								</c:forEach>
								<!-- 다음 항목이 존재 할 경우 -->
								<c:if test="${page.isHasNext()}">
									<button onclick="changePageBundle(1)">다음 5개 &gt;&gt;</button>
								</c:if>
							</span>
							<!-- 상품 이름으로 포스트 검색 -->
							<form action="${pageContext.request.contextPath}/blog/${artist.artistNo}/postList" method="get" class="postSearch">
								<input type="search" placeholder="블로그 내 검색" name="title">
								<button type="submit">검색</button>
							</form>
						</c:if>
						
						<!-- 출력할 포스트가 없을 경우 -->
						<c:if test="${empty postList}">
							<p style="color: #ccc;">등록된 포스트가 없습니다.</p>
						</c:if>
						
					</div>
				</div>
			</div>
		</div>
		
		<div id="modal" class="modal">
			<div class="modalContent">
				<span id="close" class="close">&times;</span>
				<div class="modalInput">
					<textarea id="mod-textarea" type="text" name="content" placeholder="가장 먼저 감정을 표현 해 보세요!"></textarea>
					<button type="button" onclick="modifyComment()">수 정</button>
				</div>
			</div>
		</div>
	</section>
	
	<script>
		// 현재 페이지의 정보
		var currentBundle = ${page.currentBundle};  // 현재 페이지 묶음 정보
		var currentPage = ${page.currentPage};  // 현재 페이지 번호
		
		// title parameter
		var title = "${title}";
		
		// request url
		var url = "${pageContext.request.contextPath}/blog/${artist.artistNo}/postList";
		var postDetailUrl = "${pageContext.request.contextPath}/blog/${artist.artistNo}/postDetail";
		
		// modal 설정
		var modal = document.getElementById("modal");  // 모달 창
		var closeBtn = document.getElementById("close");  // 닫기 버튼
		var modTextarea = document.getElementById("mod-textarea");  // 모달창의 댓글 수정 영역
		
		// 모달창의 닫기 버튼에 이벤트 리스너 등록
		closeBtn.onclick = function() {
			modal.style.display = "none";  // 모달창 숨김
		}
		
		// 윈도우 이벤트 리스너 등록
		window.onclick = function(event) {
			// 클릭 이벤트의 타겟이 모달일 경우
			if(event.target == modal) {
				modal.style.display = "none";  // 모달창 숨김
			}
		}
	</script>
		
	<script src="${pageContext.request.contextPath}/resources/js/postList.js"></script>
	
	
	<!-- footer -->
	<%@ include file="/WEB-INF/views/commons/footer.jsp"%>

</body>
</html>