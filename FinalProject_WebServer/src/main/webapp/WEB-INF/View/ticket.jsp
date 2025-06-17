<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!-- core tag 사용을 위한 input -->
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ page import="java.util.List, java.util.Map"  %>
<!DOCTYPE html>
<html lang="ko">
<head>
  	<meta charset="UTF-8" />
  	<link rel="stylesheet" href="./resources/css/bootstrap.min.css" />
 	<link rel="stylesheet" href="./resources/css/ticket.css" />
 	<script src="./resources/js/ticket.js"></script>
  	<title>예매</title>
</head>
<body>
	<%@ include file="/header.jsp" %>
	<%@ include file="/menu.jsp" %>
	<form action="TicketController" method="post">
		<table>
			<thead>
				<tr>
					<th>극장</th>
					<th>날짜</th>
					<th>영화</th>
					<th>시간</th>
					<th>좌석</th>
		        </tr>
      		</thead>
	      	<tbody>
	        	<tr>
		        <!-- 극장 -->
				<td>
		            <select name="theaterId" id="theaterSelect">
		              	<c:forEach var="theater" items="${theaterList}">
		                	<option value="${theater.theater_id}">${theater.name}</option>
		              	</c:forEach>
		            </select>
	          	</td>
				<input type="hidden" id="theater_id", value="">
				
	          	<!-- 날짜 -->
	          	<td>
	            	<div class="date-selector">
						<button class="arrow_left" onclick="arrow_left()">⬆️;</button>
					    <input type="button" class="day" id="day1" value="" data-date="">
						<input type="button" class="day" id="day2" value="" data-date="">
						<input type="button" class="day" id="day3" value="" data-date="">
						<input type="button" class="day" id="day4" value="" data-date="">
						<input type="button" class="day" id="day5" value="" data-date="">
						<input type="button" class="day" id="day6" value="" data-date="">
						<input type="button" class="day" id="day7" value="" data-date="">
					    <button class="arrow_right" onclick="arrow_right()">⬇️;</button>
	    			</div>
	          	</td>
	          	<input type="hidden" id="date", value="">
	
	          	<!-- 영화 -->
	          	<td>
	            	<select name="movieId" id="movieSelect">
	              		<option>영화를 선택하세요</option>
	            	</select>
	          	</td>
	          	<input type="hidden" id="movie_id", value="">
	
	          	<!-- 시간 -->
	          	<td>
	            	<select name="time" id="timeSelect">
	              		<option>시간 선택</option>
	            	</select>
	          	</td>
	          	<input type="hidden" id="time", value="">
	
	          	<!-- 좌석 -->
	          	<td>
	            	<div class="seat-input">
	              		<label for="row">행 :</label>
	              		<input type="number" id="row" name="row" min="1" required>
	              		<label for="col">열 :</label>
	              		<input type="number" id="col" name="col" min="1" required>
	            	</div>
	          	</td>
	        	</tr>
	      	</tbody>
		</table>
	    <br>
	    <input type="button" onclick="ticketFunc()">예매하기</button>
	  </form>
</body>
</html>