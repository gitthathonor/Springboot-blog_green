<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="../layout/header.jsp"%>

<div class="container">
	<form>
		<input id="id" type="hidden" value="${boards.id}">
		<div class="mb-3 mt-3">
			<input id="title" type="text" class="form-control" value="${boards.title}" required="required" maxlength="50">
		</div>
		<div class="mb-3">
			<textarea id="content" class="form-control" rows="8" required="required">${boards.content}</textarea>
		</div>
		<button id="btnUpdate" type="button" class="btn btn-primary">수정완료</button>
	</form>
</div>

<script>
	$('#content').summernote({
		tabsize: 2,
		height: 400
	});
</script>

<script>	
	$("#btnUpdate").click(()=>{
		update();
	});
	
	function update() {
		let data = {
			title: $("#title").val(),
			content: $("#content").val()
		};
		
		let id = $("#id").val();
		
		$.ajax("/boards/"+id,{
			type: "PUT",
			dataType: "json",
			data: JSON.stringify(data),
			headers: {
				"Content-Type": "application/json; charset=utf-8"
			}
		}).done((res)=>{
			if(res.code == 1) {
				alert("게시글 수정완료");
				location.href="/boards/"+id;
			} else {
				alert("게시글 수정에 실패하였습니다.");
			}
		});
		
	}
</script>

<%@ include file="../layout/footer.jsp"%>

s