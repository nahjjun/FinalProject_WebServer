<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
  <title>게시글 수정</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/skvstyle.css">
</head>
<body>
	
	<%@ include file="/header.jsp" %>
	<%@ include file="/menu.jsp" %>
	
	<c:if test="${not empty sessionScope.message}">
	  <div class="alert alert-success" style="margin: 20px;">
	    ${sessionScope.message}
	  </div>
	  <c:remove var="message" scope="session" />
	</c:if>
	
	<h2 class = "title-style" style="padding: 20px;">리뷰 수정</h2>
	
	
	<form action="PostController?action=update" method="post" enctype="multipart/form-data" style="padding: 20px;">
	    <input type="hidden" name="postId" value="${post.postId}" />
	    <input type="hidden" name="boardType" value="${post.boardType}" />
	
	    <label>제목</label><br>
	    <input type="text" name="title" value="${post.title}" class="form-control" required /><br>
	
	    <label>내용</label><br>
	    <textarea name="content" rows="10" class="form-control" required>${post.content}</textarea><br>
	
	    <c:if test="${post.boardType == 'movie'}">
	        <label>이미지 변경 (선택)</label><br>
	        <input type="file" name="image" />
	        <p>현재 이미지: ${post.imagePath}</p>
	    </c:if>
	
	    <label>
	    	<input type="checkbox" name="watched" value="true" 
	  		<c:if test="${post.watched}">checked</c:if> /> 봤어요
	    </label><br><br>
	
	    <button type="submit" class="btn btn-success">수정 완료</button>
	    <button type="button" class="btn btn-secondary" onclick="history.back()">취소</button>
	</form>

<%@ include file="/footer.jsp" %>
</body>
</html>