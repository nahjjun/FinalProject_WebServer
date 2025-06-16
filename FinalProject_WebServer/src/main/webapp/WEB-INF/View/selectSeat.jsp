<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>좌석 선택</title>
    <style>
        .seat { width: 40px; height: 40px; margin: 5px; }
        .available { background-color: #90ee90; } /* 초록 */
        .reserved { background-color: #d3d3d3; cursor: not-allowed; } /* 회색 */
        .selected { background-color: #f08080; } /* 빨강 */
    </style>
    <script>
        function toggleSeat(seatId) {
            const seat = document.getElementById(seatId);
            if (seat.classList.contains('reserved')) return;
            seat.classList.toggle('selected');
            const input = document.getElementById('selectedSeats');
            const selected = document.querySelectorAll('.selected');
            input.value = Array.from(selected).map(s => s.dataset.seat).join(',');
        }
    </script>
</head>
<body>
<h2>좌석 선택</h2>

<form action="ReservationController" method="post">
    <input type="hidden" name="movieId" value="${param.movieId}">
    <input type="hidden" name="reservationDate" value="${param.date}">
    <input type="hidden" id="selectedSeats" name="selectedSeats">

    <div>
        <c:forEach var="seat" items="${seatList}">
            <button type="button"
                    id="seat${seat.seatId}"
                    data-seat="${seat.seatNumber}"
                    class="seat ${seat.reserved ? 'reserved' : 'available'}"
                    onclick="toggleSeat('seat${seat.seatId}')">
                ${seat.seatNumber}
            </button>
            <c:if test="${seat.index % 5 == 4}"><br/></c:if>
        </c:forEach>
    </div>

    <br/>
    <input type="submit" value="예매 완료">
</form>
</body>
</html>
