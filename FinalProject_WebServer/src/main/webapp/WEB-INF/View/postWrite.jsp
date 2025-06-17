<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>게시글 작성</title>
    <link rel="stylesheet" href="resources/css/skvstyle.css" />
</head>
<body>
	<div class="wrapper">
	<%@ include file="/header.jsp" %>
	<%@ include file="/menu.jsp" %>

	<form action="PostController?action=write" method="post" enctype="multipart/form-data" style="padding: 20px;">
	     <input type="hidden" name="boardType" value="${category}" />
	    <input type="text" name="title" placeholder="제목" class="form-control" style="margin-bottom: 15px;" required />
	
	    <c:if test="${category == 'movie'}">
		     <input type="file" name="image" /><br><br>
		        <label>
  					<input type="checkbox" name="watched" value="true" /> 봤어요
		    </label><br>
		</c:if>
	
	
	    <textarea name="content" rows="10" placeholder="내용을 입력하세요." class="form-control" style="margin-bottom: 15px;" required></textarea>
	
	    <div style="display: flex; justify-content: space-between;">
	        <button type="button" onclick="location.href='PostController?category=${category}'" class="btn btn-secondary">취소</button>
	
	        <button type="submit" class="btn btn-success">등록</button>
	    </div>
	</form>
	<script src="resources/js/postwrite.js"></script>
  	<%@ include file="/footer.jsp" %>
</div>
</body>
</html>