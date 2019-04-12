

function nextPage(pageNo) {
	if (title !== undefined || title !== "") { // title이 있을 경우
		window.location = url + "?pageNo=" + encodeURIComponent(pageNo)
				+ "&title=" + encodeURIComponent(title);
	} else { // title이 없을 경우
		window.location = url + "?pageNo=" + encodeURIComponent(pageNo);
	}
}


function changePageBundle(x) {
	currentBundle += x; // 현재 페이지 묶음에서 +, - 1
	if (title !== undefined || title !== "") { // title이 있을 경우
		window.location = url + "?pageNo=" + (currentBundle * 5 - 4)
				+ "&title=" + encodeURIComponent(title);
	} else { // title이 없을 경우
		window.location = url + "?pageNo=" + (currentBundle * 5 - 4);
	}
}


function viewPost(postNo) {
	if (title !== undefined || title !== "") { // title이 있을 경우
		window.location = postDetailUrl + "?postNo=" + postNo + "&title=" + encodeURIComponent(title) + "&pageNo=" + currentPage;
	} else { // title이 없을 경우
		window.location = postDetailUrl + "?postNo=" + postNo + "&pageNo=" + currentPage;
	}
}


// ajax
function modifyComment(x, y) {
	
	console.log(y);
	
}


// ajax
function deleteComment(x, y) {
	
	var confirmDelete = confirm("정말 삭제 하시겠습니까?");
	console.log(y);
	
	if(confirmDelete) {
		
		var xhr = new XMLHttpRequest();
		
		xhr.open("POST", "/idus/blog/deleteComment", true);
		
		xhr.onreadystatechange = function() {
			
			var readyState = this.readyState;
			var status = this.status;

			if (readyState === 4 && status === 200) {
				if (json["isDeleted"]) {
					alert("삭제 성공!");
					
				}
			}

		}
		
		xhr.setRequestHeader("content-type", "application/x-www-form-urlencoded; charset=utf-8");
		xhr.send("postNo=" + encodeURIComponent(x));
		
	}
	
}