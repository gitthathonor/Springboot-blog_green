/**
 * 
 */

// summernote
$('#content').summernote({
	tabsize: 2,
	height: 400
});


// 글 작성 이벤트
$("#btnSave").click(() => {
	save();
});


// 글 수정 이벤트
$("#btnUpdate").click(() => {
	update();
});

// 글 삭제 이벤트
$("#btnDelete").click(() => {
	deleteById();
});


// 하트 아이콘을 클릭했을때의 로직(좋아요)
$("#iconLove").click(() => {
	let isLovedState = $("#iconLove").hasClass("fa-solid");
	if (isLovedState) {
		deleteLove();
	} else {
		insertLove();
	}
});



// 빨간색 하트 그리기
function renderLoves() {
	$("#iconLove").removeClass("fa-regular");
	$("#iconLove").addClass("fa-solid");
}

// 검정색 하트 그리기
function renderCancelLoves() {
	$("#iconLove").removeClass("fa-solid");
	$("#iconLove").addClass("fa-regular");
}



// 글 작성 함수
function save() {
	let data = {
		title: $("#title").val(),
		content: $("#content").val()
	};

	$.ajax("/s/api/boards", {
		type: "POST",
		dataType: "json",
		data: JSON.stringify(data),
		headers: {
			"Content-Type": "application/json"
		}
	}).done((res) => {
		if (res.code == 1) {
			location.href = "/";
		}
	});
}



//글 수정 함수
function update() {
	let data = {
		title: $("#title").val(),
		content: $("#content").val()
	};

	let id = $("#id").val();

	$.ajax("/s/api/boards/" + id, {
		type: "PUT",
		dataType: "json",
		data: JSON.stringify(data),
		headers: {
			"Content-Type": "application/json; charset=utf-8"
		}
	}).done((res) => {
		if (res.code == 1) {
			alert("게시글 수정완료");
			location.href = "/boards/" + id;
		} else {
			alert("게시글 수정에 실패하였습니다.");
		}
	});

}

// 글 삭제 함수
function deleteById() {
	let id = $("#id").val();

	let page = $("#page").val();
	let keyword = $("#keyword").val();

	$.ajax("/s/api/boards/" + id, {
		type: "DELETE",
		dataType: "json" // 응답 데이터
	}).done((res) => {
		if (res.code == 1) {
			//location.href = document.referrer;
			location.href = "/?page=" + page + "&keyword=" + keyword;  //  /?page=?&keyword=?
		} else {
			alert("글삭제 실패");
		}
	});
}


// DB에 insert 요청하기(좋아요)
function insertLove() {
	let id = $("#id").val();

	$.ajax("/s/api/boards/" + id + "/loves", {
		type: "POST",
		dataType: "json"
	}).done((res) => {
		if (res.code == 1) {
			renderLoves();
			// 좋아요 수 1 증가
			let count = $("#countLove").text();
			$("#countLove").text(++count);
		} else {
			alert(res.msg);
			location.href = "/loginForm";
		}
	});
}

// DB에 delete 요청하기(좋아요 취소)
function deleteLove() {
	let id = $("#id").val();

	let data = {
		usersId: $("#usersId").val(),
		boardsId: $("#boardsId").val()
	};

	console.log(data);

	$.ajax("/s/api/boards/" + id + "/loves", {
		type: "DELETE",
		dataType: "json",
		data: JSON.stringify(data), // http body에 들고갈 요청 데이터
		headers: { // http header에 들고갈 요청 데이터
			"Content-Type": "application/json; charset=utf-8"
		}
	}).done((res) => {
		if (res.code == 1) {
			renderCancelLoves();
			// 좋아요 수 1 감소
			let count = $("#countLove").text();
			$("#countLove").text(--count);
		} else {
			alert(res.msg);
			location.href = "/loginForm";
		}
	});
}