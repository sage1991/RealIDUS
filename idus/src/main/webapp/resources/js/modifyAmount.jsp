<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<script type="text/javascript">
	
	var shoppingBagList = [];
	var shoppingBag_total = 0;
	var shoppingBag_fee =0;
	
	// 쇼핑백 update,delete시 타겟이되는 쇼핑백을 잡기위한 jstl문 
	<c:forEach var="shoppingBag" items="${shoppingBagList}">
		shoppingBagList.push({
			id:${shoppingBag.spbNo},
			amount:${shoppingBag.amount},
			price:${shoppingBag.price},
		});
		shoppingBag_total += ${shoppingBag.amount} * ${shoppingBag.price};
		shoppingBag_fee += ${shoppingBag.deliveryCharge};
	</c:forEach>

	// 페이지 준비 완료시 총주문금액 + 배송비 = 결재금액 값 설정
	$(document).ready(function(){
		var order_price = shoppingBag_fee+shoppingBag_total;
		$("#shoppingBag_total").html(shoppingBag_total);
		$("#shoppingBag_fee").html(shoppingBag_fee);
		$("#order_price").html(order_price);
	});
	
	// 페이지에서 delete 버튼 클릭시 해당 쇼핑백 삭제
	function deleteShoppingBag(shoppingBagNo, customerNo, statusIndex) {
	
		
		ShoppingBagModifyRequest={
				spbNo: shoppingBagNo, // 삭재를 위한 쇼핑백 번호
				customerNo: customerNo, // 삭제를 위한 구매자 번호
				different : 3 // 삭제버튼 키값
		};	
		
		// ajax를 통해 페이지에서 서버로 값을 json 형태로 전송
		$.ajax({
			type : "post",
			url : "${pageContext.request.contextPath}/myPage/shoppingBag",
			contentType	: "application/json; charset=utf-8;", 
			data :JSON.stringify(ShoppingBagModifyRequest),
			dataType : 'json',
			success : function (data) {
	
				// 전송후 처리된 결과값이 true일경우 페이지 단 처리
				if(data.isDeleteSuccess) {
					
					let targetShoppingBag = shoppingBagList[statusIndex];  // 쇼핑백 리스트에서 삭제 대상 검색
					
					$('#shoppingBag_'+shoppingBagNo).remove(); // 해당 쇼핑백 테이블 삭제
					
					// 삭제된 쇼핑백 가격만큼 쇼핑백 전체 가겨 조정
					 var totalprice = targetShoppingBag["price"]*targetShoppingBag["amount"];
					shoppingBag_total -= totalprice; 
					
					$("#shoppingBag_total").html(shoppingBag_total);

					var order_price = shoppingBag_fee+shoppingBag_total;
					$("#order_price").html(order_price);
					
				}
			},
			fail : function (error) {
				alert("실패");
				console.log(error);
			}
		});

	}

	
	// 감소 버튼 클릭시 해당 쇼핑백 수량 감소
	function DecreaseAmount(shoppingBagNo,shoppingBagAmount, statusIndex) {
	
		// 변경되는 수량 값 받아오기위한 변수
		var spbamount = $('#shoppingBagAmount_'+shoppingBagNo).val();

		if(spbamount == 1){
			alert("최소값 입니다");
		} else{
			var ShoppingBagModifyRequest = {
					spbNo : shoppingBagNo,
					amount : spbamount,
					different : -1 // 감소 버튼 키값
			};

			$.ajax({
				type : "post",
				url : "${pageContext.request.contextPath}/myPage/shoppingBag",
				contentType	: "application/json; charset=utf-8;", 
				data :JSON.stringify(ShoppingBagModifyRequest),
				dataType : 'json',
				success : function (data) {
	
					// ajax 전송후 받아온 데이터값이 true 일경우 페이지의 수량 변경
					if(data.isUpdateSuccess) {

						let targetShoppingBag = shoppingBagList[statusIndex];  // 쇼핑백 리스트에서 업데이트 대상 검색
						targetShoppingBag["amount"]--;  // 해당 쇼핑백의 수량을 1증가
						
						// 페이지 반영을 위한 input Id 값 생성
						var spbNo = "#shoppingBagAmount_" + shoppingBagNo;
						// 페이지에 업데이트 된 수량 반영
						$(spbNo).val(targetShoppingBag["amount"]);

						// 업데이트된 값에의한 전체 가격 수정
						var totalprice = targetShoppingBag["price"]*targetShoppingBag["amount"];
						$('#totalPrice_'+shoppingBagNo).html(totalprice);

						shoppingBag_total -= targetShoppingBag["price"]; 
						$("#shoppingBag_total").html(shoppingBag_total);

						var order_price = shoppingBag_fee+shoppingBag_total;
						$("#order_price").html(order_price);
					}
				},
				fail : function (error) {
					alert("실패");
					console.log(error);
				}
			});
		}
	}


	// 증가 버튼 클릭시 해당 쇼핑백 수량 증가
	function IncreaseAmount(shoppingBagNo, shoppingBagAmount, statusIndex) {
		
		var spbamount = $('#shoppingBagAmount_'+shoppingBagNo).val();
		
		if(spbamount == 999){
			alert("최대값 입니다.");
		}else{
			var ShoppingBagModifyRequest = {
					spbNo : shoppingBagNo,
					amount : spbamount,
					different : 1
			}; 
		
		
			$.ajax({
				type : "post",
				url : "${pageContext.request.contextPath}/myPage/shoppingBag",
				contentType	: "application/json; charset=utf-8;", 
				data :JSON.stringify(ShoppingBagModifyRequest),
				dataType : 'json',
				success : function (data) {
					
					// ajax 전송후 받아온 데이터값이 true 일경우 페이지의 수량 변경
					if(data.isUpdateSuccess) {
						// 배열에서의 번호
						let targetShoppingBag = shoppingBagList[statusIndex];  // 쇼핑백 리스트에서 업데이트 대상 검색
						targetShoppingBag["amount"]++;  // 해당 쇼핑백의 수량을 1증가
						
						// 페이지 반영을 위한 input Id 값 생성
						var spbNo = "#shoppingBagAmount_" + shoppingBagNo;
						// 페이지에 업데이트 된 수량 반영
						$(spbNo).val(targetShoppingBag["amount"]);
						
						// 업데이트된 값에의한 전체 가격 수정
						var totalprice = targetShoppingBag["price"]*targetShoppingBag["amount"];
						$('#totalPrice_'+shoppingBagNo).html(totalprice);
						
						shoppingBag_total += targetShoppingBag["price"]; 
						$("#shoppingBag_total").html(shoppingBag_total);

						var order_price = shoppingBag_fee+shoppingBag_total;
						$("#order_price").html(order_price);

					}
					
				},
				fail : function (error) {
					alert("실패");
					console.log(error);
				}
			});
		}
	}
	
</script>