<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>무비차트</title>
  <link rel="stylesheet" href="./resources/css/bootstrap.min.css" />
  <link rel="stylesheet" href="./resources/css/skvstyle.css" />
  <link rel="stylesheet" href="./resources/css/movieChart.css" />
  
</head>
<body>
  <%@ include file="header.jsp" %>
  <%@ include file="menu.jsp" %>

  <div class="container mt-5">
  <div class="movie-chart-header">
    <h2 class="fw-bold fs-3">무비차트</h2>
    <div class="form-check">
      <input class="form-check-input" type="checkbox" value="" id="onlyShowing">
      <label class="form-check-label" for="onlyShowing">현재 상영작만 보기</label>
    </div>
  </div>

  <div class="row row-cols-1 row-cols-sm-2 row-cols-md-3 row-cols-lg-4 g-4">
    <div class="col">
      <div class="card h-100 shadow-sm movie-card">
        <div class="position-relative">
          <span class="rank-badge">No.1</span>
          <img src="./resources/images/movie1.jpg" class="card-img-top" alt="영화포스터">
        </div>
        <div class="card-body">
          <h5 class="card-title">미션 임파서블: 파이널 데이</h5>
          <p class="card-text">예매율: 23.9% · 관람평: 97%</p>
          <p class="card-text text-muted">개봉일: 2025.05.17</p>
          <a href="#" class="btn btn-outline-danger w-100">예매하기</a>
        </div>
      </div>
    </div>
  </div>
</div>


  <%@ include file="footer.jsp" %>
</body>
</html>
