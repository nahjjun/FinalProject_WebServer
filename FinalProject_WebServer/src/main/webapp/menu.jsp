<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>

<meta charset="UTF-8">
<title>menu</title>
<link rel="stylesheet" href="./resources/css/bootstrap.min.css" />
<link rel="stylesheet" href="./resources/css/skvstyle.css" />
</head>
<body>
<!-- 네비게이션 메뉴 바 -->
<nav class="main-menu d-flex justify-content-between align-items-center px-5 py-3">
	<!-- 왼쪽: 메뉴 -->
	  <div id ="menu" class="d-flex custom-gap">
		  <ul class = "d-flex gap-4 mb-0">
		    <li>
		    <!-- 영화 리스트들이 나열되어있는 페이지 -->
		      <a href="MovieController?action=movieList" class="menu-item">영화</a>
		      <ul>
		        <li><a href="MovieController?action=movieChart">무비차트</a></li>
		      </ul>
		    </li>
		    <li>
		      <a href="theater.jsp" class="menu-item">극장</a>
		      <ul>
		        <li><a href="#">SGV 극장</a></li>
		      </ul>
		    </li>
		    <li>
		      <a href="reservation.jsp" class="menu-item">예매</a>
		      <ul>
		        <li><a href="#">예매하기</a></li>
		        <li><a href="#">상영 스케줄</a></li>
		      </ul>
		    </li>
		    <li>
		      <a href="community.jsp" class="menu-item">게시판</a>
		      <ul>
		        <li><a href="#">자유 게시판</a></li>
		        <li><a href="#">영화 게시판</a></li>
		      </ul>
		    </li>
		  </ul>
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