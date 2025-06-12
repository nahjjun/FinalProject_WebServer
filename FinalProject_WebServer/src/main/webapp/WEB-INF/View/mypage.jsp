<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
    
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>마이페이지</title>
  <link rel="stylesheet" href="./resources/css/bootstrap.min.css" />
  <link rel="stylesheet" href="./resources/css/skvstyle.css" />
  <link rel="stylesheet" href="./resources/css/modal.css" />
</head>
<body>
<div class="wrapper">
  <%@ include file="/header.jsp" %>
  <%@ include file="/menu.jsp" %>
<div class="mypage-container">
  
  <!-- 세로 메뉴바 -->
  <div class="mypage-sidebar">
  <ul class="menu-category">
    <li class="menu-title">회원정보</li>
    <li class="submenu-item"><a href="javascript:void(0);" onclick="openModal()">개인정보 설정</a></li>
	<li class="submenu-item"><a href="javascript:void(0);" onclick="openProfileModal()">프로필 수정하기</a></li>
     <li class="submenu-item"><a href="javascript:void(0);" onclick="openDeleteModal()">회원탈퇴</a></li>

      <li class="menu-title"><a href="#" class="menu-link">내가 본 영화</a></li>
    </ul>
  </div>


	    <!-- 오른쪽 메인 콘텐츠 -->
  <div class="mypage-main">
	  <div class="mypage-profile-box">
		  <img class="mypage-profile-img" src="${pageContext.request.contextPath}/resources/images/${user.profileImage}" alt="프로필 이미지">
			  <div class="mypage-info-area">
			  	<h4>${user.name}님 <span class="user-id">${user.email}</span></h4>
			  	<p class="membership-grade">고객님은 <span class="fw-bold text-success">${user.userClass}</span> 입니다.</p>
			  </div>
		  </div>
	
	  <div class="mypage-posts-outer">
		  <div class="mypage-posts-wrapper">
		    <h3 class="title-bold mb-4">내가 작성한 게시글</h3>
		
		    <c:choose>
		      <c:when test="${not empty myPosts}">
		        <table class="board-table">
		          <thead>
		            <tr>
		              <th>제목</th>
		              <th>작성일</th>
		              <th>조회수</th>
		            </tr>
		          </thead>
		          <tbody>
		            <c:forEach var="post" items="${myPosts}">
		              <tr>
		                <td class="title">
		                  <a href="PostController?postId=${post.postId}" class="text-decoration-none">
		                    ${post.title}
		                  </a>
		                </td>
		                <td>${post.formattedCreatedAt}</td>
		                <td>${post.viewCount}</td>
		              </tr>
		            </c:forEach>
		          </tbody>
		        </table>
		      </c:when>
		      <c:otherwise>
		        <p class="text-muted">작성한 게시글이 없습니다.</p>
		      </c:otherwise>
		    </c:choose>
		  </div>
	</div>
</div>
</div>

		
	<%@ include file="editModal.jsp" %> 
	<%@ include file="profileEditModal.jsp" %> 
	<%@ include file="deleteModal.jsp" %>
	

	  <%@ include file="/footer.jsp" %>
	  <script src="./resources/js/modal.js"></script> 
</div>
</body>
</html>