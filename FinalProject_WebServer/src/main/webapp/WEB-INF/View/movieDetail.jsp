<%@ page contentType="text/html; charset=UTF-8" %>
<!-- core tag 사용을 위한 input -->
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ page import="java.util.List, java.util.Map"  %>
<!DOCTYPE html>
<html>
<head>
	<title>${sessionScope.movieInfo.title}</title>
	<link rel="stylesheet" href="./resources/css/bootstrap.min.css" />
  	<link rel="stylesheet" href="./resources/css/movieDetail.css" />
  	<script type="text/javascript"  src="./resources/js/movieDetail.js"></script>
</head>
<body>
	<%@ include file="/header.jsp" %>
	<%@ include file="/menu.jsp" %>
	
	<div class="movie_info">
		<img src="${movieInfo.poster_url}" class="movie_poster" alt="영화 포스터" onerror="this.onerror=null; this.src='./resources/images/default_poster.png';">
		<div class="info_box">
			<p>${movieInfo.title}</p>
			<p><b>평점: ${movieInfo.review_point}</b></p>
			<hr/>
			<p><b>감독:</b> ${movieInfo.directors}</p>
			<p><b>배우:</b> ${movieInfo.actors }</p>
			<p><b>장르:</b> ${movieInfo.genre} </p>
			<p><b>상영 시간:</b> ${movieInfo.duration}분</p>
			<p><b>개봉일:</b> ${movieInfo.date}</p>
		</div>
		<!-- btn btn-primary는 Bootstrap 스타일이다. (네이티브 버튼처럼 보임) -->
		<a href="#" class="btn btn-primary">예매하기</a>
	</div>
	<div class="movie_description">
		<p> ${movieInfo.description}</p>
	</div>
	
	<div class="movie_review">
		<div class="review_title">평점 리뷰</div>
		<!-- 리뷰를 작성하는 공간 -->
		<form action="ReviewController?action=register" method="post" class="review_write">
			<div class="review_write_top">
				<!-- loginUser의 멤버변수명을 쓰면, 내부적으로 getter 함수를 호출한다. -->
		    	<div class="user_badge">${sessionScope.loginUser.userClass}</div>
		    	<div class="user_name">${sessionScope.loginUser.name}</div>
		  	</div>
		
		  	<div class="review_context">
		    	<textarea name="review_context" placeholder="리뷰를 작성해주세요"></textarea>
		  	</div>
		
			<div class="review_info_box">
				<div class="review_char_count">0 / 500</div>
				<div class="review_rating_box">
					<div class="rating_label">별점 주기</div>
					<div class="rating_stars">
				        <span data-value="1">★</span>
				        <span data-value="2">★</span>
				        <span data-value="3">★</span>
				        <span data-value="4">★</span>
						<span data-value="5">★</span>
					</div>
				</div>
			</div>
			
			<input type="hidden" name="rating" id="review_rating" value="0">
			<input type="hidden" name="movie_id" id="movie_id" value="${movieInfo.movie_id}">
			
			<div class="review_submit_area">
		    	<button type="submit" class="btn btn-primary">등록하기</button>
		  	</div>
		</form>
		
		<div class="review_list">
			<c:forEach var="review" items="${reviewInfoList}">
				<div class="review_card">
					<input type="hidden" name="review_id" value="${review.review_id}">
					<div class="review_meta">
						<!-- 사용자 class -->
						
						<span class="review_badge">${review.user_class}</span>
				        <span class="review_user">${review.name}</span>
				        <span class="review_date">${review.review_date}</span>
				        <span class="review_rating">⭐ ${review.rating}/5</span>
					</div>
					<div class="review_content">
						${review.context}
					</div>
					<div class="content_action">
						<a href="${pageContext.request.contextPath}/ReviewController?action=edit&review_id=${review.review_id}&movie_id=${movieInfo.movie_id}" class="edit-btn">수정</a>
    					<span style="margin: 0 5px;">/</span>
    					<a href="${pageContext.request.contextPath}/ReviewController?action=delete&review_id=${review.review_id}&movie_id=${movieInfo.movie_id}" class="delete-btn">삭제</a>
					</div>
					
					<div class="review_action">
						<a href="${pageContext.request.contextPath}/ReviewController?action=like&review_id=${review.review_id}&movie_id=${movieInfo.movie_id}" class="like_btn">👍 ${review.like_count}</a>
				        <a href="${pageContext.request.contextPath}/ReviewController?action=dislike&review_id=${review.review_id}&movie_id=${movieInfo.movie_id}" class="dislike_btn">👎 ${review.unlike_count}</a>
				    </div>
				    
				</div>
			</c:forEach>
		</div>
	</div>

	

	<%@ include file="/footer.jsp" %>
	<p>${errorScript}
</body>
</html>
