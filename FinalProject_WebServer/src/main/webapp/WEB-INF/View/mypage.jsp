<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>마이페이지</title>
  <link rel="stylesheet" href="./resources/css/bootstrap.min.css" />
  <link rel="stylesheet" href="./resources/css/skvstyle.css" />
</head>
<body>
  <%@ include file="/header.jsp" %>
  <%@ include file="/menu.jsp" %>
<div class="mypage-section d-flex justify-content-center gap-5 mt-5">
  
  <!-- 세로 메뉴바 -->
  <div class="mypage-sidebar">
  <ul class="menu-category">
    <li class="menu-title">회원정보</li>
    <li class="submenu-item"><a href="#">개인정보 설정</a></li>
    <li class="submenu-item"><a href="#">프로필 수정하기</a></li>
    <li class="submenu-item"><a href="#">회원탈퇴</a></li>

    <li class="menu-title"><a href="#" class="menu-link">내가 본 영화</a></li>
  </ul>
</div>


  <!-- 기존 프로필 박스 -->
  <div class="mypage-profile-box">
    <img src="./resources/images/기본프로필.png" alt="프로필" class="mypage-profile-img">
    <div class="mypage-info-area">
      <h4>임다현님 <span class="user-id">ockda0423</span></h4>
      <p class="membership-grade">고객님은 <span class="fw-bold text-success">Basic</span> 입니다.</p>
      <p class="sgv-label">⚙ 자주 가는 SGV 설정하기</p>
      <div class="sgv-buttons">
        <button class="btn btn-outline-success rounded-3 px-3">1 CGV 연남</button>
        <button class="btn btn-outline-success rounded-3 px-3">2 CGV 연남</button>
        <button class="btn btn-outline-success rounded-3 px-3">3 CGV 연남</button>
        <button class="btn btn-outline-success rounded-3 px-3">4 CGV 연남</button>
        <button class="btn btn-outline-success rounded-3 px-3">5 CGV 연남</button>
      </div>
    </div>
  </div>

</div>


<%@ include file="/footer.jsp" %>
</body>
</html>