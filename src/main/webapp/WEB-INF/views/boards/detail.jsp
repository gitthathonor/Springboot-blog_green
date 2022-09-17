<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="../layout/header.jsp"%>

<div class="container">
	<br /> <br />
	<c:if test="${boards.usersId == principal.id && principal.id != null}">
		<div class="d-flex">
				<a href="/boards/${boards.id}/updateForm"><button type="submit" class="btn btn-warning">수정하러가기</button></a>
			<form>
				<input id="id" type="hidden" value="${boards.id }">
				<button id="btnDeleteBoards" type="button" class="btn btn-danger">삭제</button>
			</form>
		</div>
	</c:if>

	<br />
	<div class="d-flex justify-content-between">
		<h3>${boards.title }</h3>
		<div>좋아요 : <span id="count">${boards.likeCounts }</span>개&nbsp<i id="like" class="fa-regular fa-heart"></i></div>
		<input id="boardsId" type="hidden" value="${boards.id }">
		<input id="usersId" type="hidden" value="${principal.id }">
	</div>
	<hr />

	<div>${boards.content }</div>

</div>

<script>
	$("#like").click((event)=>{
		
		let id = $("#boardsId").val();
		console.log(id);
		
		let check = $("#like").hasClass("fa-regular");
		console.log(check);	
		
		let data = {
			usersId: $("#usersId").val(),
			boardsId: $("#boardsId").val()
		}
		
		if(check == true) {
			$.ajax("/boards/"+id+"/like", {
				type: "POST",
				dataType: "json",
				data: JSON.stringify(data),
				headers: {
					"Content-Type": "application/json; charset=utf-8"
				}
			}).done((res)=>{
				if(res.code == 1) {
					alert("종아요~");
					$("#count").text(res.data);
					$("#like").removeClass("fa-regular");
					$("#like").addClass("fa-solid");
					$("#like").css("color", "red");
				} else {
					alert("로그인이 필요합니다");
					location.href="/loginForm";
				} 
			});
			
		} else {
			$.ajax("/boards/"+id+"/like", {
				type: "DELETE",
				dataType: "json",
			}).done((res)=>{
				if(res.code == 1) {
					alert("종아요 취소");
					$("#count").text(res.data);
					$("#like").removeClass("fa-solid");
					$("#like").addClass("fa-regular");
					$("#like").css("color", "black");
				} else {
					alert("로그인이 필요합니다");
					location.href="/loginForm";
				} 
			});
		}
	});
	
	
	$("#btnDeleteBoards").click(()=>{
		deleteBoards();
	});
	
	function deleteBoards() {
		
		let id = $("#id").val();
		console.log(id);

		$.ajax("/boards/" + id, {
			type: "DELETE",
			dataType: "json"
		}).done((res) => {
			if (res.code == 1) {
				alert("게시글 삭제완료");
				location.href = "/";
			} else {
				alert("게시글 삭제실패");
			}
		});
	}
</script>



<%@ include file="../layout/footer.jsp"%>

