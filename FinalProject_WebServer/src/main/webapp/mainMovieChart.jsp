<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>메인무비페이지</title>
  <link rel="stylesheet" href="./resources/css/bootstrap.min.css" />
  <link rel="stylesheet" href="./resources/css/skvstyle.css" />
</head>
<body>
	<div class="movie-slider-wrapper mt-5 px-4">
	  <div class="d-flex justify-content-between align-items-center mb-3">
	    <h4 class="fw-bold">무비차트</h4>
	    <a href="MovieController?action=movieChart" class="btn btn-outline-success btn-sm">전체보기</a>
	  </div>
	
	  <div class="movie-slider d-flex overflow-auto gap-3" id="movieSlider">
	    <c:forEach var="movie" items="${boxOfficeList}">
	      <div class="card movie-card shadow-sm ${movie.rank == 1 ? 'rank-1' : ''}" style="min-width: 200px; max-width: 200px; flex: 0 0 auto;">
	        <div class="position-relative">
	          <span class="rank-badge">No.${movie.rank}</span>
	          <img src="${movie.poster_url}" class="card-img-top" alt="영화포스터"
	               onerror="this.onerror=null; this.src='./resources/images/default_poster.png';">
	        </div>
	        <div class="card-body d-flex flex-column p-2">
	          <h6 class="card-title mb-1">${movie.title}</h6>
	          <small class="text-muted">⭐ ${movie.review_point}/5</small>
	          <small class="text-muted">개봉일: ${movie.date}</small>
	          <div class="mt-2 d-flex flex-column gap-1">
	            <a href="TicketController?action=default" class="btn btn-sm btn-outline-success">예매하기</a>
	            <a href="MovieController?action=movieDetail&movie_id=${movie.movie_id}" class="btn btn-sm btn-outline-success">상세정보</a>
	          </div>
	        </div>
	      </div>
	    </c:forEach>
	  </div>
</div>		

<script src="./resources/js/movieSlider.js"></script>
</body>
</html>