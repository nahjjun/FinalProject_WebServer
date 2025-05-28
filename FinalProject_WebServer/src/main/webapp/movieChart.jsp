<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<!-- 캐러셀을 적용시키기 위해서는 Bootstrap JS 파일을 불러와야함 -->
<script src = "https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>

<!-- ✅ 영화 슬라이더(부트스트랩 캐러셀) 영역 -->
<!-- data-bs-interval: 자동으로 넘기기 위한 시간 간격 설정 -
<!-- data-bs-ride: 캐러셀 요소가 자동으로 슬라이드 전환되도록 만들어줌 -->

<div id="movieCarousel" class="carousel slide" data-bs-ride="carousel" data-bs-interval="3000">
  <div class="carousel-inner">
    <div class="carousel-item active">
      <img src="./resources/images/poster1.png" class="d-block w-100" alt="영화1 포스터">
    </div>
    <div class="carousel-item">
      <img src="./resources/images/poster2.png" class="d-block w-100" alt="영화2 포스터">
    </div>
    <div class="carousel-item">
      <img src="./resources/images/poster3.png" class="d-block w-100" alt="영화3 포스터">
    </div>
    <!-- 필요 시 더 추가 -->
  </div>
</div>


<!-- ✅ 무비 차트 영역 -->
<div class="container my-5">
    <div class="d-flex justify-content-between align-items-center mb-3">
        <h4 class="fw-bold">무비 차트</h4>
        <div>
            <button class="btn btn-sm btn-outline-secondary me-2">무비 차트</button>
            <button class="btn btn-sm btn-outline-secondary">상영 예정작</button>
        </div>
    </div>
    <div class="d-flex gap-3 overflow-auto">
        <div class="text-center">
            <div class="bg-secondary" style="width:120px; height:180px;"></div>
            <div class="fw-bold mt-2">1</div>
            <div class="text-muted">아바타<br>예매율 49.2%</div>
        </div>
        <%-- 2~5번 자리 카드 반복 --%>
        <c:forEach var="i" begin="2" end="5">
            <div class="text-center">
                <div class="bg-secondary" style="width:120px; height:180px;"></div>
                <div class="fw-bold mt-2"><c:out value="${i}" /></div>
            </div>
        </c:forEach>
    </div>
</div>