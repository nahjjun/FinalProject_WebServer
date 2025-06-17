<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!-- core tag 사용을 위한 input -->
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ page import="java.util.List, java.util.Map"  %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>무비 리스트</title>
  <link rel="stylesheet" href="./resources/css/bootstrap.min.css" />
  <link rel="stylesheet" href="./resources/css/skvstyle.css" />
  <link rel="stylesheet" href="./resources/css/movieList.css" />
  
</head>
	<link rel="stylesheet" href="./resources/css/bootstrap.min.css" />
	<link rel="stylesheet" href="./resources/css/skvstyle.css" />
<body>
<div class="wrapper">
  <%@ include file="/header.jsp" %>
  <%@ include file="/menu.jsp" %>

  <div class="container mt-5">
  <div class="movie-chart-header">
    <h2 class="fw-bold fs-3">무비리스트</h2>
  </div>

  <div class="row row-cols-1 row-cols-sm-2 row-cols-md-3 row-cols-lg-4 g-4">
	  
	  <c:forEach var="movie" items="${movieInfoList}"> 
	    <div class="col">
	      <div class="card h-100 shadow-sm movie-card">
	        <div class="position-relative">
	          <img src="${movie.poster_url}" class="card-img-top" alt="영화포스터" onerror="this.onerror=null; this.src='./resources/images/default_poster.png';">
	        </div>
	        <div class="card-body">
	          <h5 class="card-title">${movie.title}</h5>
	          <p class="card-text">관람평: ⭐ ${movie.review_point}/5</p>
	          <p class="card-text text-muted">개봉일: ${movie.date}</p>
	          <a href="TicketController?action=default" class="btn w-100">예매하기</a>
			<a href="MovieController?action=movieDetail&movie_id=${movie.movie_id}" class="btn w-100">상세정보</a>

	        </div>
	      </div>
	    </div>
	   </c:forEach>
    
  </div>
  
</div>


  <%@ include file="/footer.jsp" %>
  </div>
</body>
</html>
