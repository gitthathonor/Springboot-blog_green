<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="../layout/header.jsp"%>

<div class="container">
	<br /> <br />
	<c:if test="${boards.usersId == principal.id && principal.id != null}">
		<div class="d-flex">
				<a href="/boards/${boards.id}/updateForm"><button type="submit" class="btn btn-warning">수정하러가기</button></a>
			<form>
				<button class="btn btn-danger">삭제</button>
			</form>
		</div>
	</c:if>

	<br />
	<div class="d-flex justify-content-between">
		<h3>${boards.title }</h3>
		<div>종아요 : <span id="count"></span>&nbsp<i id="like" class="fa-solid fa-heart"></i></div>
	</div>
	<hr />

	<div>${boards.content }</div>

</div>

<script>
	let count = 0;

	$("#like").click((event)=>{
		console.log(event.target);
		let check = $("#like").hasClass("fa-solid");
		console.log(check);
		
		if(check == true) {
			$("#like").removeClass("fa-solid");
			$("#like").addClass("fa-regular");
		} else {
			$("#like").removeClass("fa-regular");
			$("#like").addClass("fa-solid");
		}
		count++;
		$("#count").text(count);
	});

</script>



<%@ include file="../layout/footer.jsp"%>

