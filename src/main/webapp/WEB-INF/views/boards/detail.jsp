<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="../layout/header.jsp"%>

<div class="container">
	<br /> <br />

	<c:if test="${boards.usersId == principal.id}">
		<div class="d-flex">
			<form action="/boards/${boards.id}/updateForm" method="get">
				<button type="submit" class="btn btn-warning">수정하러가기</button>
			</form>
			<form>
				<button class="btn btn-danger">삭제</button>
			</form>
		</div>
	</c:if>

	<br />
	<div>
		<h3>${boards.title }</h3>
	</div>
	<hr />

	<div>${boards.content }</div>


</div>

<%@ include file="../layout/footer.jsp"%>

