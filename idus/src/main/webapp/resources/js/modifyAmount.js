function DecreaseAmount(shoppingBagNo, shoppingBagAmount) {
	var spbamount = $('#shoppingBagAmount_' + shoppingBagNo).val();
	console.log(spbamount);

	var ShoppingBagModifyRequest = {
		spbNo : shoppingBagNo,
		amount : spbamount,
		different : -1
	};

	console.log(ShoppingBagModifyRequest);

	$.ajax({
		type : "post",
		url : "${pageContext.request.contextPath}/myPage/shoppingBag",
		contentType : "application/json; charset=utf-8;",
		data : JSON.stringify(ShoppingBagModifyRequest),
		dataType : 'json',
		success : function(data) {
			console.log(data);
			// java 영역에서 data를 resultList 라는 변수에 담아서 던져줬을때

			var spbNo = "#shoppingBagAmount_" + data.spbNo // 순차적으로 data에서
															// spbNo 가져온다
			console.log(spbNo);
			var amount = +data.amount // 순차적으로 data에서 amount 가져온다

			$(spbNo).val(amount); // 해당 input 아이디에 값을 넣어준다.
			// $(spbNo).attr("value", amount); //경우에 따라서 이걸 사용할 수도 있습니다.

			console.log($(spbNo).val());
			$('#totalPrice').html(data.totalPrice);

		},
		fail : function(error) {
			alert("실패");
			console.log(error);
		}
	});
}

function IncreaseAmount(shoppingBagNo, shoppingBagAmount) {

	var spbamount = $('#shoppingBagAmount_' + shoppingBagNo).val();
	console.log(spbamount);

	var ShoppingBagModifyRequest = {
		spbNo : shoppingBagNo,
		amount : spbamount,
		different : 1
	};

	console.log(ShoppingBagModifyRequest);

	$.ajax({
		type : "post",
		url : "${pageContext.request.contextPath}/myPage/shoppingBag",
		contentType : "application/json; charset=utf-8;",
		data : JSON.stringify(ShoppingBagModifyRequest),
		dataType : 'json',
		success : function(data) {

			// java 영역에서 data를 resultList 라는 변수에 담아서 던져줬을때
			var spbNo = "#shoppingBagAmount_" + data.spbNo // 순차적으로 data에서
															// spbNo 가져온다
			var amount = +data.amount // 순차적으로 data에서 amount 가져온다

			$(spbNo).val(amount); // 해당 input 아이디에 값을 넣어준다.
			// $(spbNo).attr("value", amount); //경우에 따라서 이걸 사용할 수도 있습니다.

			$('#totalPrice').html(data.totalPrice);
		},
		fail : function(error) {
			alert("실패");
			console.log(error);
		}
	});
}