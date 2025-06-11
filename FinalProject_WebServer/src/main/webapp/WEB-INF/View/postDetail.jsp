<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>게시글 상세</title>
  <link rel="stylesheet" href="../resources/css/bootstrap.min.css" />
  <link rel="stylesheet" href="../resources/css/skvstyle.css" />
</head>
<body>

<%@ include file="/header.jsp" %>
<%@ include file="/menu.jsp" %>

<div class="post-detail-wrapper">
  <div class="post-title">${post.title}</div>
  <div class="post-detail-meta">
    <span>작성자: ${post.userName}</span>
    <span>작성일: ${post.createdAt}</span>
    <span>조회수: ${post.viewCount}</span>
  </div>

  <c:if test="${post.imagePath != null}">
    <div class="post-detail-image">
      <img src="../resources/uploads/${post.imagePath}" alt="첨부 이미지" />
    </div>
  </c:if>

  <div class="post-detail-content">${post.content}</div>

  <div style="text-align: right;">
    <a href="PostController?category=${post.boardType}" class="btn btn-secondary">목록으로</a>
  </div>
</div>

<!-- 댓글 구역 -->
<div class="comment-section">
  <h4>댓글</h4>

  <form action="PostController" method="post" class="comment-form">
    <input type="hidden" name="action" value="comment" />
    <input type="hidden" name="postId" value="${post.postId}" />
    <textarea name="content" rows="3" placeholder="댓글을 입력하세요" required></textarea>
    <button type="submit" class="submit-btn">댓글 등록</button>
  </form>

  <div class="comment-box">
    <c:forEach var="comment" items="${commentList}">
      <div class="comment-item">
        <strong>${comment.userName}</strong> 
        <small><fmt:formatDate value="${comment.createdAt}" pattern="yyyy-MM-dd HH:mm:ss" /></small><br/>
        <div>${comment.content}</div>

        <div class="comment-actions">
          <form action="PostController" method="get">
            <input type="hidden" name="action" value="like_comment" />
            <input type="hidden" name="commentId" value="${comment.commentId}" />
            <input type="hidden" name="postId" value="${post.postId}" />
            <button type="submit">❤️ ${comment.goodNum}</button>
          </form>

	
          <form action="PostController" method="get">
            <input type="hidden" name="action" value="dislike_comment" />
            <input type="hidden" name="commentId" value="${comment.commentId}" />
            <input type="hidden" name="postId" value="${post.postId}" />
            <button type="submit">👎 ${comment.badNum}</button>
          </form>
        </div>
      </div>
    </c:forEach>
  </div>
</div>



<%@ include file="/footer.jsp" %>
</body>
</html>
