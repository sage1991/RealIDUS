var pageNo = 1;
var hasMore = true;
var xhr = new XMLHttpRequest();


$(document).ready(function () {
    $(window).scroll(function () {
        if ($(window).scrollTop() === ($(document).height() - $(window).height())) {
            if(hasMore) {
                getMorePieces();
            }
        }
    });
});


function getMorePieces() {
    
    xhr.open("post", "/idus/blog/morePieces", true);

    xhr.onreadystatechange = function () {

        var readyState = this.readyState;
        var status = this.status;

        if (readyState === 4 && status === 200) {
            
            var response = this.responseText;
            var json = JSON.parse(response);
            
            
            if(json["empty"]) {  // 받아온 작품이 없을 경우
            	hasMore = !json["empty"];  // 더 이상 ajax 요청을 하지 않도록 flag를 false로 설정
            	return;  // function 종료
            } else {  // 받아온 작품이 존재 할 경우
            	
            	// 작품 리스트에서 상품을 하나씩 꺼내 화면에 표시
            	for(i in json["pieceList"]) {
                    
            		// 작품 object
                    var myPiece = json["pieceList"][i];
                    
                    // html element(작품 카드) 생성
                    var pieceContainer = $("<div class='piece' onclick='pieceDetail(" + myPiece["pieceNo"] + ")'></div>");
                    var pieceImage = $("<div class='pieceImage' style='background-image: url(" + myPiece["thumbnail"] + ")'></div>");
                    var nickName = $("<h4 class='nickName'>" + myPiece["artist"] + "</h4>");
                    var title = $("<p class='title'>" + myPiece["title"] + "</p>");
                    
                    // 작품 가격
                    var price = $("<p class='price'></p>");
                    var discount = $("<span class='discount'>[" + myPiece["discount"] + "%] \\" + (1-myPiece["discount"]*0.01)*myPiece["price"] + "</span>");
                    var originalPrice = $("<span class='originalPrice'>\\" + myPiece["price"] + "</span>");
                    price.append(discount);
                    price.append(originalPrice);
                    
                    var hr = $("<hr/>");
                    
                    // 작품 별점
                    var star = $("<span style='color: gold' class='star'></span>");
                    for(let j = 0; j < myPiece["star"]; j++) {
                        star.append($("<i class='fas fa-star'></i>"));
                    }
                    for(let j = 0; j < 5-myPiece["star"]; j++) {
                        star.append($("<i class='far fa-star'></i>"));
                    }
                    
                    // 작품 카드 생성 후 타겟 페이지에 append
                    pieceContainer.append(pieceImage);
                    pieceContainer.append(nickName);
                    pieceContainer.append(title);
                    pieceContainer.append(price);
                    pieceContainer.append(hr);
                    pieceContainer.append(star);
                    $("#target").append(pieceContainer);
                    
                }
                
                // 새로 받아온 리스트의 길이가 12보다 작을 경우 -> 더 조회 할 작품이 없음.
                if(json["pieceList"].length < 12) {
                    hasMore = false;
                }
            }
            
            pageNo++;
        }
    }
    
    xhr.setRequestHeader("content-type", "application/x-www-form-urlencoded; charset=utf-8");
    
    if(pieceName != "") {
    	xhr.send("pageNo=" + encodeURIComponent(pageNo*12) + "&artistNo=" + encodeURIComponent(artistNo) + "&pieceName=" + encodeURIComponent(pieceName));
    } else {
    	xhr.send("pageNo=" + encodeURIComponent(pageNo*12) + "&artistNo=" + encodeURIComponent(artistNo));
    }
    
}
