<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>개인정보 수정</title>
	<link rel="stylesheet" href="./resources/css/bootstrap.min.css" />
	<link rel="stylesheet" href="./resources/css/skvstyle.css" />
	<link rel="stylesheet" href="./resources/css/modal.css" />
</head>
<body>
	<div id="editModal" class="modal">
  <div class="modal-content">
    <span class="close">&times;</span>
    <h2>개인정보 수정</h2>
    <form action="${pageContext.request.contextPath}/MypageEditController" method="post">

      <label>이메일</label>
      <input type="text" name="email" value="${user.email}" readonly>

      <label>전화번호</label>
      <input type="text" name="phone" value="${user.phone}" required>

      <label>현재 비밀번호</label>
      <input type="password" name="currentPassword" required>

      <label>새 비밀번호</label>
      <input type="password" name="newPassword">

      <label>비밀번호 확인</label>
      <input type="password" name="confirmPassword">

       <div class="btns">
		    <button type="submit" class="btn-primary">저장하기</button>
		    <button type="button" class="btn-secondary" id="closeModalBtn">취소</button>
	  </div>
</form>
  </div>
</div>
</body>
</html>