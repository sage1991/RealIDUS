<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/views/commons/meta.jsp"%>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/shoppingBag.css?var=2">

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
						<div class="profileImage">
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

						<form action="" class="cart-form" method="post">
							 <c:if test="${!empty shoppingBagList}"> 

								<div class="inner-w800">

									<c:forEach var="shoppingBag" items="${shoppingBagList}">

										<table class="cartCard">
											<caption class="hidden">${shoppingBag.artistNick}</caption>
											<colgroup>
												<col width="125px">
												<col width="*">
											</colgroup>
											<thead>
												<tr>
													<th colspan="2"><input type="hidden"
														name="membership_price" value=""> <input
														type="hidden" name="artist_order_price" value="">
														<input type="hidden" name="delivery_charge" value="4000">
														<label> <input class="bp" data-ui="check-linked"
															data-linked-parent="50775489-1a87-49d9-b457-277432aacb5b"
															type="checkbox" checked>
															<div class="txt-group">
																<b class="bold">${shoppingBag.artistNick} 작가님</b>
															</div>
													</label></th>
												</tr>
											</thead>

											<!-- --------------------------------------------------------------------------------------------- -->

											<tbody>

												<tr class="list-item">
													<td class="area-img"><input class="bp" type="checkbox"
														checked>
														<div class="img-bg"
															style="background-image: url(${shoppingBag.url})"></div>
													</td>


													<td class="area-txt">
														<div class="txt-group">
															<a class="bold" href="" target="_blank"> <label
																for="prd-name">${shoppingBag.pieceName}</label>
															</a>
														</div>
													</td>
												</tr>

												<tr>
													<td colspan="2" class="flexible">
														<ul class="list-options">
															<li><input type="hidden"><input
																type="hidden"><input type="hidden"><input
																type="hidden">
																<div class="table-layout">
																	<div class="split">
																		<span class="option-txt">${shoppingBag.options}</span>
																		<div class="input-number" data-state="">
																			<label>수량 </label>
																			<button type="button" data-type="decrement" onclick="DecreaseAmount(${shoppingBag.spbNo},${shoppingBag.amount})">-</button> 
																			<div class="input-area">
																				<input id="shoppingBagAmount_${shoppingBag.spbNo}" name ="amount" class="prd-count" type="number" value="${shoppingBag.amount}" 
																				min="1" max="999" autocomplete="off">
																			</div>
																			<button type="button" data-type="increment" onclick="IncreaseAmount(${shoppingBag.spbNo},${shoppingBag.amount})">+</button>
																		</div>
																	</div>
																	<div class="split">
																		<strong><em class="cost-text">${shoppingBag.totalPrice} 원</em></strong>
																		<span class="btn-group">
																			<button class="ui_btn--small" type="button"
																				data-action="remove_parent"
																				data-product-uuid="93b42a84-ef55-4f32-9c56-0891169c3287"
																				data-cart-index="0">
																				<i class="fas fa-times"></i>
																			</button>
																		</span>
																	</div>
																</div></li>
														</ul>

														<div class="ui_field--onchange" data-uipack="textarea"
															data-state="">
															<div class="ui_field__txtarea">
																<textarea
																	name="cart_list[4c82d400-56c4-4df7-821f-1331e5a449fd][message]"
																	maxlength="500" placeholder="주문 요청사항을 입력해주세요"></textarea>
																<em class="ui_field__chars">500</em>
															</div>
															<button type="submit" class="ui_field__btn"
																data-action="reload">저장</button>
														</div>
													</td>
												</tr>

												<tr class="static-row delivery-segment">
													<th>배송비</th>
													<td><c:if test="${shoppingBag.totalPrice > 60000}">

															<div data-freeship="true" style="display: none">
																<input type="text" name="shipping_price"
																	data-unformated="0" value="무료 배송" readonly
																	disabled="disabled"> <input type="text"
																	style="display: none" name="free_shipping_policy"
																	data-unformated="60000" value="60,000원 이상" readonly
																	disabled="disabled">
															</div>
														</c:if> <c:if test="${shoppingBag.totalPrice < 60000}">

															<div data-freeship="false">
																<input type="text" name="shipping_price"
																	data-unformated="3000" value="3,000 원" readonly
																	disabled="disabled"> <input
																	style="display: none" type="text"
																	name="free_shipping_policy" data-unformated="60000"
																	value="60,000원 이상" readonly disabled="disabled">
															</div>
														</c:if>
													</td>
												
												</tr>

											</tbody>



											<!-- --------------------------------------------------------------------------------------------- -->

										</table>


									 </c:forEach>
 
								</div>

								<div class="mobile-row inner-w800">
									<div class="border-wrapper">

										<table class="table-cost">
											<thead>
												<tr>
													<th><span>총 주문 금액</span></th>
													<th><span>배송비</span></th>
													<th><span>결제 예정금액</span></th>
												</tr>
											</thead>
											<tbody>
												<tr>
													<td><strong data-payment="order">0</strong>원</td>
													<td><span>+</span><strong data-payment="shipping">0</strong>원</td>
													<td class="hilight red"><span>=</span><strong
														data-payment="cart_total">0</strong>원</td>
												</tr>
											</tbody>
										</table>
									</div>

									<div class="scroll-detector" data-ui="sticky">
										<fieldset class="area-btn mfixed">
											<button type="button" class="ui_btn--red--large"
												data-type="payment-all">전체 작품 주문</button>
											<button type="button" class="ui_btn--large submit"
												data-type="payment">선택한 작품 주문</button>
										</fieldset>
									</div>
								</div>

							 </c:if> 

							 <c:if test="${empty shoppingBagList}">

								<div class="inner-w800">

									<div class="banner-empty">
										<span class="sp-icon icon-empty-bag"></span>
										<!-- <i class="fas fa-shopping-bag"></i> -->
										<p>
											마음은 가득찬 당신,<br>하지만 장바구니는 비었네요.
										</p>
									</div>
								</div>

							</c:if>

 						</form>
						<!-- --------------------------------------------------------------------------------------------- -->
					</div>
				</div>
			</div>
		</div>
	</section>

	<!-- footer -->
	<%@ include file="/WEB-INF/views/commons/footer.jsp"%>
	
	<script type="text/javascript">
	
		function DecreaseAmount(shoppingBagNo,shoppingBagAmount) {
			console.log(shoppingBagNo +" / " + shoppingBagAmount);
			
			var ShoppingBagModifyRequest = {
					spbNo : shoppingBagNo,
					amount : shoppingBagAmount,
					different : -1
			}; 
			/* "spbNo=" + shoppingBagNo + "&amount=" + shoppingBagAmount; */
		
			console.log(ShoppingBagModifyRequest);
			console.log(JSON.stringify(ShoppingBagModifyRequest));

			$.ajax({
				type : "post",
				url : "${pageContext.request.contextPath}/myPage/shoppingBag",
				contentType	: "application/json; charset=utf-8;",/* "application/x-www-form-urlencoded; charset=utf-8", */ 
				data :JSON.stringify(ShoppingBagModifyRequest),
				dataType : 'json',
				success : function (data) {
					console.log(data);
					console.log("번호 : " + data.spbNo +" / 수량 : " + data.amount);
					alert("성공");
				},
				fail : function (error) {
					alert("실패");
					console.log(error);
				}
			});
		}
		function IncreaseAmount(shoppingBagNo,shoppingBagAmount) {
			console.log(shoppingBagNo +" / " + shoppingBagAmount);
			
			var ShoppingBagModifyRequest = {
					spbNo : shoppingBagNo,
					amount : shoppingBagAmount,
					different : 1
			}; 
			/* "spbNo=" + shoppingBagNo + "&amount=" + shoppingBagAmount; */
		
			var inputId = "shoppingBagAmount_" + shoppingBagNo;
			console.log(ShoppingBagModifyRequest);
			console.log(JSON.stringify(ShoppingBagModifyRequest));

			$.ajax({
				type : "post",
				url : "${pageContext.request.contextPath}/myPage/shoppingBag",
				contentType	: "application/json; charset=utf-8;",/* "application/x-www-form-urlencoded; charset=utf-8", */ 
				data :JSON.stringify(ShoppingBagModifyRequest),
				dataType : 'json',
				success : function (data) {
					console.log(data);
					console.log("번호 : " + data.spbNo +" / 수량 : " + data.amount);
					console.log(ShoppingBagModifyRequest);
					$("input[name=amount]").val();
					$("input[name=amount]").val(data.amount);
				},
				fail : function (error) {
					alert("실패");
					console.log(error);
				}
			});
		}

	</script>	

</body>
</html>