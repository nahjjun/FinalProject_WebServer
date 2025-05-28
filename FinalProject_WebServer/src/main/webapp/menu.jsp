<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<!-- 네비게이션 메뉴 바 -->
<nav class="main-menu d-flex justify-content-between align-items-center px-5 py-3">
	<!-- 왼쪽: 메뉴 -->
	  <div class="d-flex custom-gap">
		  <a href="movie.jsp" class="menu-item">영화</a>
		  <a href="theater.jsp" class="menu-item">극장</a>
		  <a href="reservation.jsp" class="menu-item">예매</a>
		  <a href="community.jsp" class="menu-item">게시판</a>
	  </div>
	  
	  <!-- 오른쪽: 검색창 -->
	  <form class="d-flex search-form" action="search.jsp" method="get">
		  <input class="form-control me-2" type="search" name="query" placeholder="검색어 입력">
		  	<button class="search-btn" type="submit">
		    	<img src="./resources/images/돋보기.png" alt="검색" style="height: 25px;">
  			</button>
</form>

</nav>

</body>
</html>