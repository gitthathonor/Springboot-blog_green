<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="../layout/header.jsp"%>

<div class="container">
	<form>
		<div class="mb-3 mt-3">
			<input id="username" type="text" class="form-control" placeholder="Enter username" >
			<button id="btnUsernameSameCheck" class="btn btn-warning" type="button">유저네임 중복체크</button>
		</div>
		<div class="mb-3">
			<input id="password" type="password" class="form-control" placeholder="Enter password">
		</div>
		<div class="mb-3">
			<input id="passwordSame" type="password" class="form-control" placeholder="Enter passwordSame">
		</div>
		<div class="mb-3">
			<input id="email" type="email" class="form-control" placeholder="Enter email">
		</div>
		<button id="btnJoin" type="button" class="btn btn-primary">회원가입</button>
	</form>
</div>

<script>
	let isUsernameSameCheck = false;
	
	
	
	$("#username").keydown(()=>{
		if(event.keyCode == 32) {
			alert("공백을 입력할 수 없습니다.");
			$("#username").focus();
			return;
		}
	});
	
	
	
	// 회원가입
	$("#btnJoin").click(()=>{
		
		// 아이디 중복체크 여부를 먼저 확인한다.
		if(isUsernameSameCheck == false) {
			alert("아이디 중복 체크를 진행해주세요!");
			$("#btnUsernameSameCheck").focus();
			return;
		}
		
		
		// 통신 object 생성(get 요청 시에는 필요가 없다. get요청은 body가 없기 때문)
		let data = {
			username:$("#username").val(),
			password:$("#password").val(),
			email:$("#email").val()
		};
		
		$.ajax("/join",{
			type: "POST",
			dataType: "json",
			data: JSON.stringify(data),
			headers: {
				"Content-Type" : "application/json"
			}
		}).done((res)=>{
			if(res.code == 1) {
				location.href="/loginForm";
			}
		});
		// alert("나 실행됨");
	});
	
	
	// 유저네임 중복 체크
	$("#btnUsernameSameCheck").click(()=>{
		// 0. 통신 object 생성(get 요청 시에는 필요가 없다. get요청은 body가 없기 때문)
		
		// 1. 사용자가 적은 username 값을 가져오기(input 태그에 id를 걸자)
		let username = $("#username").val()
		
		
		if(username == "" || username.includes(" ")) {
			alert("username을 입력해주세요");
			return;
		}
		
		// 2. Ajax 통신
		$.ajax("/users/usernameSameCheck?username="+username,{
			type:"GET",
			dataType:"json",
			async:true
		}).done((res)=>{
			console.log(res);
			if(res.code == 1) {
				alert("통신성공");
				if(res.data == false) {
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
	});
	
	
</script>

<%@ include file="../layout/footer.jsp"%>

