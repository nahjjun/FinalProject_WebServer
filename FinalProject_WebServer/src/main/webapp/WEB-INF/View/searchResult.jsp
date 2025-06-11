<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!-- core tag 사용을 위한 input -->
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ page import="java.util.List, java.util.Map"  %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>검색 결과</title>
  <link rel="stylesheet" href="./resources/css/bootstrap.min.css" />
  <link rel="stylesheet" href="./resources/css/skvstyle.css" />
  <link rel="stylesheet" href="./resources/css/movieList.css" />
  
</head>
	<link rel="stylesheet" href="./resources/css/bootstrap.min.css" />
	<link rel="stylesheet" href="./resources/css/skvstyle.css" />
<body>
	<%@ include file="/header.jsp" %>
  	<%@ include file="/menu.jsp" %>

	<hr>
  	<div class="container mt-5">
  		<div class="movie-chart-header">
    		<h2 class="fw-bold fs-3"><b>검색 결과</b> : ${searchInfoList.size()}개</h2>
    		<div class="form-check">
      			<input class="form-check-input" type="checkbox" value="" id="onlyShowing">
      			<label class="form-check-label" for="onlyShowing">현재 상영작만 보기</label>
    		</div>
  		</div>
		<div class="row row-cols-1 row-cols-sm-2 row-cols-md-3 row-cols-lg-4 g-4">  
			<c:forEach var="search" items="${searchInfoList}"> 
		    	<div class="col">
		      		<div class="card h-100 shadow-sm movie-card">
		        		<div class="position-relative">
		          			<img src="${search.poster_url}" class="card-img-top" alt="영화포스터" onerror="this.onerror=null; this.src='./resources/images/default_poster.png';">
		        		</div>
				        <div class="card-body">
							<h5 class="card-title">${search.title}</h5>
				          	<p class="card-text">관람평: ⭐ ${search.review_point}/5</p>
				          	<p class="card-text text-muted">개봉일: ${search.date}</p>
				          	<a href="#" class="btn w-100">예매하기</a>
							<a href="MovieController?action=movieDetail&movie_id=${search.movie_id}" class="btn w-100">상세정보</a>
				        </div>
		     		</div>
		    	</div>
		   	</c:forEach>
		</div>
	</div>

	<%@ include file="/footer.jsp" %>
</body>
</html>
