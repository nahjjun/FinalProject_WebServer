<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>커뮤니티</title>
  <link rel="stylesheet" href="./resources/css/bootstrap.min.css" />
  <link rel="stylesheet" href="resources/css/skvstyle.css" />
</head>
<body>

<%@ include file="/header.jsp" %>
<%@ include file="/menu.jsp" %>

	<c:if test="${not empty sessionScope.message}">
	  <div class="alert alert-success" style="margin: 20px;">
	    ${sessionScope.message}
	  </div>
	  <c:remove var="message" scope="session" />
	</c:if>


	<c:if test="${empty param.view}">
	  <div class="community-top-header">
	    <img src="resources/images/로고.png" alt="SKV 로고" class="community-logo" />
	    <p class="med community-subtext">편안한 커뮤니티를 나눠보세요.</p>
	  </div>
	 

  <div class="community-tabs">
    <button class="tab-button" onclick="location.href='PostController?category=free'">자유게시판</button>
    <button class="tab-button" onclick="location.href='PostController?category=movie'">영화게시판</button>
  </div>
</c:if>

  <div class="community-wrapper">

    <!-- 게시글 개수 + 등록 버튼 -->
    <div class="community-header">
      <div class="total-posts">
        <c:choose>
          <c:when test="${param.category == 'movie'}">
            🎬 영화게시판 전체 <span>${totalPosts}</span>건
          </c:when>
          <c:otherwise>
            💬 자유게시판 전체 <span>${totalPosts}</span>건
          </c:otherwise>
        </c:choose>
      </div>
	      <c:choose>
			  <c:when test="${param.category == 'movie'}">
			    <button class="btn-write" onclick="location.href='PostController?action=write&category=movie'">게시글 작성</button>
			  </c:when>
			  <c:otherwise>
			    <button class="btn-write" onclick="location.href='PostController?action=write&category=free'">게시글 작성</button>
			  </c:otherwise>
		</c:choose>
    </div>

    <!-- 게시글 목록 테이블 -->
    <table class="board-table">
      <thead>
        <tr>
          <th>번호</th>
          <th>제목</th>
          <th>작성자</th>
          <th>작성일</th>
          <th>조회수</th>
        </tr>
      </thead>
      <tbody>
        <c:forEach var="post" items="${postList}">
		  <tr>
		    <td>${post.displayNumber}</td> <!-- DTO에서 만든 번호 -->
		    <td class="title">
		      <a href="PostController?action=view&postId=${post.postId}" style="text-decoration: none; color: var(--main-black);">
				  ${post.title}
			  </a>
		    </td>
		    <td class="author">
		      <span class="author-badge">${post.userName}</span>
		    </td>
		    <td>${post.createdAt}</td>
		    <td>${post.viewCount}</td>
		  </tr>
		</c:forEach>
      </tbody>
    </table>

    <!-- 페이지네이션 -->
    <div class="community-pagination">
      <c:if test="${currentPage > 1}">
        <a href="PostController?category=${param.category}&page=${currentPage - 1}">&lt;</a>
      </c:if>
      <c:forEach begin="1" end="${totalPages}" var="i">
        <a href="PostController?category=${param.category}&page=${i}"
           class="${currentPage == i ? 'active' : ''}">${i}</a>
      </c:forEach>
      <c:if test="${currentPage < totalPages}">
        <a href="PostController?category=${param.category}&page=${currentPage + 1}">&gt;</a>
      </c:if>
    </div>

  </div>
<%@ include file="/footer.jsp" %>
</body>
</html>
