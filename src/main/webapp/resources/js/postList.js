var commentInfo = {
		comNo:0,
		statusCount:0,
}

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


// 댓글 수정 시작
function startModify(comNo, statusCount) {
	
	commentInfo.comNo = comNo;
	commentInfo.statusCount = statusCount;
	
	console.log(commentInfo);
	console.log(statusCount);
	console.log(document.getElementById("comContent" + statusCount).innerText);
	
	modTextarea.value = document.getElementById("comContent" + statusCount).innerText;
	
	modal.style.display = "block";  // 모달창 표시
	
}


// ajax
function modifyComment() {
	
	var content = document.getElementById("mod-textarea").value;  // 수정된 댓글 내용
	var modDateSpan = document.getElementById("modDate" + commentInfo.statusCount);  // 수정일자 span element
	var contentDiv = document.getElementById("comContent" + commentInfo.statusCount);  // 댓글 내용 div
	
	var xhr = new XMLHttpRequest();
	
	xhr.open("POST", "/idus/blog/modifyComment", true);
	
	xhr.onreadystatechange = function() {
		
		var readyState = this.readyState;
		var status = this.status;

		if (readyState === 4 && status === 200) {
			var json = JSON.parse(this.responseText);
			if (json["isModified"]) {
				alert("수정 성공!");
				modDateSpan.innerHTML = "(" + json["modifiedDate"] + " 수정됨)";
				contentDiv.innerHTML = content;
				modal.style.display = "none";
			} else {
				alert("수정에 실패 했습니다. 관리자에게 문의하세요.");
			}
		}
	}
	
	xhr.setRequestHeader("content-type", "application/x-www-form-urlencoded; charset=utf-8");
	xhr.send("comNo=" + encodeURIComponent(commentInfo.comNo) + "&content=" + encodeURIComponent(content));
	
}


// ajax
function deleteComment(comNo, targetId) {
	
	var confirmDelete = confirm("정말 삭제 하시겠습니까?");
	
	if(confirmDelete) {
		
		var xhr = new XMLHttpRequest();
		
		xhr.open("POST", "/idus/blog/deleteComment", true);
		
		xhr.onreadystatechange = function() {
			
			var readyState = this.readyState;
			var status = this.status;

			if (readyState === 4 && status === 200) {
				var json = JSON.parse(this.responseText);
				if (json["isDeleted"]) {
					alert("삭제 성공!");
					$("#" + targetId).remove();
				} else {
					alert("삭제에 실패 했습니다. 관리자에게 문의하세요.");
				}
			}
		}
		
		xhr.setRequestHeader("content-type", "application/x-www-form-urlencoded; charset=utf-8");
		xhr.send("comNo=" + encodeURIComponent(comNo));
		
	}
	
}