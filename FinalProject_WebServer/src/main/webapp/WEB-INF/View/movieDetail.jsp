<%@ page contentType="text/html; charset=UTF-8" %>
<!-- core tag 사용을 위한 input -->
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ page import="java.util.List, java.util.Map"  %>
<!DOCTYPE html>
<html>
<head>
	<title>${movieInfo.title}</title>
	<link rel="stylesheet" href="./resources/css/bootstrap.min.css" />
  	<link rel="stylesheet" href="./resources/css/movieDetail.css" />

</head>
<body>
	<%@ include file="/header.jsp" %>
	<%@ include file="/menu.jsp" %>
	
	<div class="movie_info">
		<img src="${movieInfo.poster_url}" class="movie_poster" alt="영화 포스터">
		<div class="info_box">
			<p>영화 제목</p>
			<p>예매율: </p>
			<hr/>
			<p>감독: / 배우: </p>
			<p>장르: ${movieInfo.genre} </p>
			<p>상영 시간: ${movieInfo.duration}분</p>
			<p>개봉일: </p>
		</div>
		<!-- btn btn-primary는 Bootstrap 스타일이다. (네이티브 버튼처럼 보임) -->
		<a href="#" class="btn btn-primary">예매하기</a>
	</div>
	<div class="movie_description">
		<p> ${movieInfo.description}
	</div>
	
	<div class="movie_review">
		<div class="review_title">평점 리뷰</div>
		<!-- 리뷰를 작성하는 공간 -->
		<div class="review_write">
		  <div class="review_write_top">
		    <div class="user_badge">VIP</div>
		    <div class="review_placeholder">멋쟁이 사자</div>
		  </div>
		
		  <div class="review_textarea">
		    <textarea placeholder="리뷰를 작성해주세요"></textarea>
		  </div>
		
		  <div class="review_info_box">
		    <div class="review_char_count">0 / 500</div>
		    <div class="review_rating_box">
		      <div class="rating_label">별점 주기</div>
		      <div class="rating_stars">
		        <span>☆</span>
		        <span>☆☆</span>
		        <span>☆☆☆</span>
		        <span>☆☆☆☆</span>
		        <span>☆☆☆☆☆</span>
		      </div>
		    </div>
		  </div>
		
		  <div class="review_submit_area">
		    <a href="#" class="btn btn-primary">등록하기</a>
		  </div>
		</div>
		
		<div class="review_list">
			<c:forEach var="review" items="${reviewList}">
				<div class="review_card">
					<div class="review_meta">
						<!-- 사용자 class -->
						<span class="review_badge">${review.level}</span>
				        <span class="review_user">${review.username}</span>
				        <span class="review_date">${review.createdAt}</span>
				        <span class="review_rating">⭐ ${review.rating}/5</span>
					</div>
					<div class="review_content">
						${review.comment}
					</div>
					<div class="review_action">
						<span class="like_btn">👍 ${review.likeCount}</span>
				        <span class="dislike_btn">👎 ${review.dislikeCount}</span>
				    </div>
				</div>
			</c:forEach>
		</div>
	</div>

	

	<%@ include file="/footer.jsp" %>
</body>
</html>
