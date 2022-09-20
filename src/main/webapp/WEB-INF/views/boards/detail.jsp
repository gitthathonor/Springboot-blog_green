<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="../layout/header.jsp"%>

<input id="page" type="hidden" value="${sessionScope.referrer.page}">
<input id="keyword" type="hidden" value="${sessionScope.referrer.keyword}">
<div class="container">
   <br /> <br /> 
   <input id="id" type="hidden" value="${detailDto.boards.id}" />
   
   <c:if test="${!empty principal}">
	   <div class="d-flex">
	      <a href="/boards/${detailDto.boards.id}/updateForm" class="btn btn-warning">수정하러가기</a>
	      <form>
	         <button id="btnDelete" class="btn btn-danger">삭제</button>
	      </form>
	   </div>
   </c:if>

   <br />
   <div class="d-flex justify-content-between">
      <h3>${detailDto.boards.title}</h3>
      <div>
         좋아요수 : <span id="countLove">${detailDto.lovesDto.count}</span> 
         <i id="iconLove" class='${detailDto.lovesDto.loved ? "fa-solid" : "fa-regular"} fa-heart my_pointer my_red'>
		         <input id="usersId" type="hidden" value="${sessionScope.principal.id}">
		         <input id="boardsId" type="hidden" value="${detailDto.boards.id}">
         </i>
      </div>
   </div>
   <hr />

   <div>${detailDto.boards.content}</div>
</div>

<script src="/js/boards.js"></script>

<%@ include file="../layout/footer.jsp"%>


