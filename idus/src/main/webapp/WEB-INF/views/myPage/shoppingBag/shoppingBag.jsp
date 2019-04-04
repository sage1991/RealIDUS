<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/views/commons/meta.jsp"%>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/shoppingBag.css">

<meta charset="UTF-8">
<title>장바구니</title>
</head>
<body>

	<c:if test="${!empty auth}">
		<%@ include file="/WEB-INF/views/commons/loginHeader.jsp"%>
	</c:if>
	<c:if test="${empty auth}">
		<%@ include file="/WEB-INF/views/commons/noLoginHeader.jsp"%>
	</c:if>

	<section>
		<div class="container">

			<div class="atelier">

				<div class="leftNav">
					<!-- 프로필 -->
					<div class="blogProfile">
						<!-- 프로필 사진 : 블로그 주인 프로필 이미지 받아오기 -->
						<div class="profileImage"
							style="background-image: url(../../resources/user/image/userThumbnail/default_thumbnail.png)">
							<img class="thumbnail" src="${auth.thumbnail}" alt="썸네일"
								style="width: 100%; height: 100%;" />
						</div>
						<!-- 작가 이름 -->
						<div class="name">
							<span class="userName">${auth.name} 님</span>
						</div>
					</div>
					<!-- 일반 메뉴 -->
					<div class="generalMenu">
						<ul>
							<li class="menubtn"><a
								href="${pageContext.request.contextPath}/myPage/myOrders">
									나의 주문정보 </a></li>
							<li class="menubtn"><a
								href="${pageContext.request.contextPath}/myPage/shoppingBag">
									장바구니 </a></li>
							<li class="menubtn"><a
								href="${pageContext.request.contextPath}/myPage/authCheck?path=myInfo">
									내 정보 수정 </a></li>
							<li class="menubtn"><a
								href="${pageContext.request.contextPath}/myPage/authCheck?path=dropMembership">
									탈퇴하기 </a></li>
						</ul>
					</div>
					<!-- 공방 정보 -->
					<div class="info">
						<h3>활동 정보</h3>
						<p>팔로우 :</p>
					</div>
				</div>

				<div class="blogContent">
					<div class="inner-w800">
						<div class="title-style clf">
							<h2 class="txt fl">장바구니</h2>
							<ol class="page-location fr">
								<li class="active"><em class="icon-num">1</em> <span>장바구니</span>
									<i class="fa fa-angle-right"></i></li>
								<li><em class="icon-num">2</em> <span>주문결제</span> <i
									class="fa fa-angle-right"></i></li>
								<li><em class="icon-num">3</em> <span>주문완료</span></li>
							</ol>
						</div>

						<!-- ----------------작업중----------------------------------------------------------------------------- -->
						<form action="" class="cart-form" method="post">

							<div class="inner-w800">

								<%-- <c:forEach var="orderInformation" items="${orderList}"> --%>

								<table class="cartCard">
									<caption class="hidden">${auth.name}작가</caption>
									<colgroup>
										<col width="125px">
										<col width="*">
									</colgroup>
									<!-- --------------------------------------------------------------------------------------------- -->
									<thead>
										<tr>
											<th colspan="2"><input type="hidden"
												name="membership_price" value=""> <input
												type="hidden" name="artist_order_price" value=""> <input
												type="hidden" name="delivery_charge" value="4000"> <label>
													<input class="bp" type="checkbox" checked="checked" />
													<div class="txt-group">
														<b class="bold">${auth.name} 작가님</b>
													</div>
											</label></th>
										</tr>
									</thead>

									<!-- --------------------------------------------------------------------------------------------- -->

									<tbody>
										<tr class="list-item">
											<td class="area-img"><input class="bp" type="checkbox"
												checked="">
												<div class="img-bg" style="background-image:${bag.pieceimg}"></div>
											</td>
										</tr>
										<tr>
											<td class="area-txt">
												<div class="txt-group">
													<a class="bold" href="" target="_blank"> <label
														for="prd-name">제품 명</label>
													</a> <em class="light">주문시 제작</em>
												</div>
											</td>
										</tr>

										<tr>
											<td colspan="2" class="flexible">
												<ul>
													<li><input type="hidden"><input type="hidden"><input
														type="hidden"><input type="hidden">
														<div class="table-layout">
															<div class="split">
																<span class="option-txt">옵션 명 : 옵션 내용</span>
																<div class="input-number" data-ui="input-number"
																	data-state="">
																	<label
																		for="cart_list[93b42a84-ef55-4f32-9c56-0891169c3287][item][0][count]">수량</label>
																	<button type="button" data-type="decrement">-</button>
																	<div class="input-area">
																		<input class="prd-count"
																			name="cart_list[93b42a84-ef55-4f32-9c56-0891169c3287][item][0][count]"
																			type="number" value="1" min="1" max="999"
																			autocomplete="off" data-product-price="15000"
																			data-option-price="0"
																			data-product-uuid="93b42a84-ef55-4f32-9c56-0891169c3287"
																			data-price="15000" data-status="1"
																			data-status-value="1" data-action="change_count">
																	</div>
																	<button type="button" data-type="increment">+</button>
																</div>
															</div>
															<div class="split">
																<strong><em class="cost-text">15,000원</em></strong> <span
																	class="btn-group">
																	<button class="ui_btn--small" type="button"
																		data-modal-trigger="edit-prd-options"
																		data-modal="open" data-action="edit_option"
																		data-product-uuid="93b42a84-ef55-4f32-9c56-0891169c3287">
																		<i class="ui_icon--cogwheel"></i>
																	</button>
																	<button class="ui_btn--small" type="button"
																		data-action="remove_parent"
																		data-product-uuid="93b42a84-ef55-4f32-9c56-0891169c3287"
																		data-cart-index="0">
																		<i class="ui_icon--close"></i>
																	</button>
																</span>
															</div>
														</div></li>
												</ul>
											</td>
										</tr>

										<tr>

										</tr>
									</tbody>



									<!-- --------------------------------------------------------------------------------------------- -->

								</table>
							</div>

						</form>



						<!-- --------------------------------------------------------------------------------------------- -->
					</div>
				</div>
			</div>
		</div>
	</section>

	<!-- footer -->
	<%@ include file="/WEB-INF/views/commons/footer.jsp"%>

</body>
</html>