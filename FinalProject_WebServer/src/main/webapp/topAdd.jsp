<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" href="./resources/css/skvstyle.css" />
<meta charset="UTF-8">
<title>광고창</title>
</head>
<body>
<script src="./resources/js/videoControl.js"></script>

<!-- 동영상 배너 섹션 -->
<div class="sgv-banner">
  <video class="sgv-video" autoplay muzed loop>
    <source src="https://adimg.cgv.co.kr/images/202505/Bring/BringHerBack_1080x608_PC.mp4" type="video/mp4">
    브라우저가 video 태그를 지원하지 않습니다.
  </video>

  <div class="sgv-banner-text">
    <h1>브링 허 백</h1>
    <p>A24 최고 흥행 &lt;톡 투 미&gt; 감독 작품<br>6월 6일 SKV 단독 개봉</p>
    <a href="movie.jsp" class="sgv-detail-btn">상세보기</a>
    <button id="playBtn" onclick="togglePlay()">정지하기 ⏸</button>
    <button id="muteBtn" onclick="toggleMute()">음소거 🔇</button>


  </div>
</div>


</body>
</html>