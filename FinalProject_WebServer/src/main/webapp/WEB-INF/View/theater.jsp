<%@ page contentType="text/html; charset=UTF-8" %>
<!-- core tag 사용을 위한 input -->
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ page import="java.util.List, java.util.Map"  %>

<!DOCTYPE html>
<html lang="ko">
<head>
  	<meta charset="UTF-8" />
  	<link rel="stylesheet" href="./resources/css/bootstrap.min.css" />
 	<link rel="stylesheet" href="./resources/css/theater.css" />
 	<script src="./resources/js/theater.js"></script>
 	
  	<title>상영 정보</title>
</head>
<body>
<div class="wrapper">
	<%@ include file="/header.jsp" %>
	<%@ include file="/menu.jsp" %>
	
	<div class="container">
    	<div class="theater-tabs">
    		<!-- 전체 영화관 -->
	    	<c:forEach var="theater" items="${theaterNameList}">
	      		<button>${theater}</button>
	      	</c:forEach>
	      	<input type="hidden" id="selectedTheaterName" value="서경대점">
    	</div>

    	<h2 class="section-title">THEATER</h2>

	    <div class="date-selector">
			<button class="arrow_left" onclick="arrow_left()">&lt;</button>
		    <input type="button" class="day" id="day1" value="" data-date="">
			<input type="button" class="day" id="day2" value="" data-date="">
			<input type="button" class="day" id="day3" value="" data-date="">
			<input type="button" class="day" id="day4" value="" data-date="">
			<input type="button" class="day" id="day5" value="" data-date="">
			<input type="button" class="day" id="day6" value="" data-date="">
			<input type="button" class="day" id="day7" value="" data-date="">
		    <button class="arrow_right" onclick="arrow_right()">&gt;</button>
	    </div>

		<!-- js로 영화 정보들을 넣을 컨테이너 div -->
		<div class="movieInfoContainer">
	    </div>
  	</div>
  
  	<%@ include file="/footer.jsp" %>
	<p>${errorScript}
</div>
</body>
</html>
