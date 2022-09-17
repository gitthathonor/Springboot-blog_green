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
		<div>
			좋아요 : ${like.likeCount }<i id="like" class="fa-regular fa-heart">
			<input id="usersId" type="hidden" value="${principal.id}">
			<input id="boardsId" type="hidden" value="${boards.id}">
			</i>
		</div>
	</div>
	<hr />

	<div>${boards.content }</div>

</div>

<script>
	$("#like").click((event)=>{
		
		let usersId = $("#usersId").val();
		console.log(usersId);
		
		let boardsId = $("#boardsId").val();
		console.log(boardsId);
		
		let check = $("#like").hasClass("fa-solid");
		console.log(check);	
		
		let data = {
			usersId:usersId,
			boardsId:boardsId,
		};
		console.log(data);
				
		if(check == true) { // 좋아요가 눌러진 상태
			$("#like").removeClass("fa-solid");
			$("#like").addClass("fa-regular");
			$("#like").css("color", "black");	
		} else { // 좋아요가 안 눌러진 상태
			
			$.ajax("/boards/"+boardsId+"/like",{
				type: "POST",
				dataType: "json",
				data: JSON.stringify(data),
				headers: {
					"Content-Type": "application/json"
				}
			}).done((res)=>{
				if(res.code==1) {
					$("#like").removeClass("fa-regular");
					$("#like").addClass("fa-solid");
					$("#like").css("color", "red");
				} else {
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

