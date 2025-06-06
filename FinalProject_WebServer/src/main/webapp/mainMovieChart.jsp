<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>메인무비페이지</title>
</head>
<body>
	<div class="movie-slider-wrapper mt-5">
	  <button class="scroll-btn left">&lt;</button>
	
	  <div class="movie-slider" id="movieSlider">
	    <c:forEach var="movie" items="${boxOfficeList}">
	      <div class="movie-card-horizontal">
	        <img src="${movie.get('poster_url')}" alt="포스터" class="movie-img" />
	        <h5>${movie.get("title")}</h5>
	        <p>개봉일: ${movie.get("date")}</p>
	        <a href="#" class="btn">예매하기</a>
	        <a href="MovieController?action=movieDetail" class="btn">상세정보</a>
	      </div>
	    </c:forEach>
	  </div>
	
	  <button class="scroll-btn right">&gt;</button>
	</div>

<script src="./resources/js/movieSlider.js"></script>
</body>
</html>