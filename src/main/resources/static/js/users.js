/**
 * 
 */

let isUsernameSameCheck = false;

// username 공백체크
$("#username").keydown(() => {
	checkSpace();
});


// 회원가입
$("#btnJoin").click(() => {
	join();
});


// 유저네임 중복 체크
$("#btnUsernameSameCheck").click(() => {
	checkUsername();
});



// 로그인
$("#btnLogin").click(() => {
	login();
});


// 회원수정
$("#btnUpdate").click(() => {
	update();
});


// 회원탈퇴
$("#btnDelete").click(() => {
	resign();
});


function checkSpace() {
	if (event.keyCode == 32) {
		alert("공백을 입력할 수 없습니다.");
		$("#username").focus();
		return;
	}
}



function join() {
	// 아이디 중복체크 여부를 먼저 확인한다.
	if (isUsernameSameCheck == false) {
		alert("아이디 중복 체크를 진행해주세요!");
		$("#btnUsernameSameCheck").focus();
		return;
	}

	// 통신 object 생성(get 요청 시에는 필요가 없다. get요청은 body가 없기 때문)
	let data = {
		username: $("#username").val(),
		password: $("#password").val(),
		email: $("#email").val()
	};

	$.ajax("/join", {
		type: "POST",
		dataType: "json",
		data: JSON.stringify(data),
		headers: {
			"Content-Type": "application/json"
		}
	}).done((res) => {
		if (res.code == 1) {
			location.href = "/loginForm";
		}
	});
}

function checkUsername() {
	// 0. 통신 object 생성(get 요청 시에는 필요가 없다. get요청은 body가 없기 때문)

	// 1. 사용자가 적은 username 값을 가져오기(input 태그에 id를 걸자)
	let username = $("#username").val()


	if (username == "" || username.includes(" ")) {
		alert("username을 입력해주세요");
		return;
	}

	// 2. Ajax 통신
	$.ajax(`/users/usernameSameCheck?username=${username}`, {
		type: "GET",
		dataType: "json",
		async: true
	}).done((res) => {
		if (res.code == 1) {
			alert("통신성공");
			if (res.data == false) {
				alert("아이디가 중복되지 않았습니다.");
				isUsernameSameCheck = true;
				$("#password").focus();
			} else {
				alert("아이디가 중복되었습니다. 다른 아이디를 사용해주세요!");
				isUsernameSameCheck = false;
				$("#username").focus();
			}
		}
	});
}


function login() {
	let data = {
		username: $("#username").val(),
		password: $("#password").val()
	};

	$.ajax("/login", {
		type: "POST",
		dataType: "json", // 응답 데이터
		data: JSON.stringify(data), // http body에 들고갈 요청 데이터
		headers: { // http header에 들고갈 요청 데이터
			"Content-Type": "application/json; charset=utf-8"
		}
	}).done((res) => {
		if (res.code == 1) {
			location.href = "/";
		} else {
			alert("로그인 실패, 아이디 패스워드를 확인해주세요");
		}
	});
}


function resign() {
	let id = $("#id").val();

	$.ajax("/users/" + id, {
		type: "DELETE",
		dataType: "json"
	}).done((res) => {
		if (res.code == 1) {
			alert("회원탈퇴완료");
			location.href = "/";
		} else {
			alert("회원탈퇴 실패");
		}
	});
}


function update() {
	let data = {
		password: $("#password").val(),
		email: $("#email").val()
	};

	let id = $("#id").val();

	$.ajax("/users/" + id, {
		type: "PUT",
		dataType: "json",
		data: JSON.stringify(data),
		headers: {
			"Content-Type": "application/json; charset=utf-8"
		}
	}).done((res) => {
		if (res.code == 1) {
			alert("회원정보 수정완료");
			location.reload();
		} else {
			alert("회원정보수정에 실패하였습니다.");
		}
	});
}


