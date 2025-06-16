<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
  <title>리뷰 수정</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css" />
  <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/skvstyle.css" />
</head>
<body>

<%@ include file="/header.jsp" %>
<%@ include file="/menu.jsp" %>

<h2 class = "title-style" style="padding: 20px;">리뷰 수정</h2>

<form action="${pageContext.request.contextPath}/ReviewController?action=edit" method="post" style="padding: 20px;">
    <input type="hidden" name="review_id" value="${editReview.review_id}" />
    <input type="hidden" name="movie_id" value="${editReview.movie_id}" />

    <label>리뷰 내용</label><br>
    <textarea name="review_context" rows="8" class="form-control" required>${editReview.context}</textarea><br>

    <label>별점</label><br>
    <select name="rating" class="form-select" style="width: 120px;">
        <c:forEach var="i" begin="1" end="5">
            <option value="${i}" <c:if test="${editReview.rating == i}">selected</c:if>>${i}점</option>
        </c:forEach>
    </select><br>

    <button type="submit" class="btn btn-success">수정 완료</button>
    <button type="button" class="btn btn-secondary" onclick="history.back()">취소</button>
</form>

<%@ include file="/footer.jsp" %>
</body>
</html>
