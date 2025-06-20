<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!-- core tag 사용을 위한 input -->
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ page import="java.util.List, java.util.Map"  %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>무비차트</title>
  <link rel="stylesheet" href="./resources/css/bootstrap.min.css" />
  <link rel="stylesheet" href="./resources/css/skvstyle.css" />
  <link rel="stylesheet" href="./resources/css/movieChart.css" />
  
</head>
	<link rel="stylesheet" href="./resources/css/bootstrap.min.css" />
	<link rel="stylesheet" href="./resources/css/skvstyle.css" />
<body>
  <%@ include file="/header.jsp" %>
  <%@ include file="/menu.jsp" %>

  <div class="container mt-5">
  <div class="movie-chart-header">
    <h2 class="fw-bold fs-3">무비차트</h2>
  </div>

  <div class="row row-cols-1 row-cols-sm-2 row-cols-md-3 row-cols-lg-4 g-4">
	  
	  <c:forEach var="movie" items="${boxOfficeList}"> 
	    <div class="col">
	      <div class="card h-100 d-flex flex-column shadow-sm movie-card">
	        <div class="position-relative">
	          	<span class="rank-badge">No.${movie.rank}</span>
	          	<img src="${movie.poster_url}" class="card-img-top" alt="영화포스터" onerror="this.onerror=null; this.src='./resources/images/default_poster.png';">
	        </div>
	        <div class="card-body d-flex flex-column">
				<div>
				  	<h5 class="card-title">${movie.get("title")}</h5>
				  	<p class="card-text">관람평: ⭐ ${movie.review_point}/5</p>
					<p class="card-text text-muted">개봉일: ${movie.get("date")}</p>
				</div>
				<div class="mt-auto d-flex flex-column gap-2">
				  	<a href="TicketController?action=default" class="btn btn-outline-success w-100">예매하기</a>
				  	<a href="MovieController?action=movieDetail&movie_id=${movie.movie_id}" class="btn btn-outline-success w-100">상세정보</a>
				</div>
			</div>
	      </div>
	    </div>
	   </c:forEach>
  </div>
</div>
  <%@ include file="/footer.jsp" %>
</body>
</html>
