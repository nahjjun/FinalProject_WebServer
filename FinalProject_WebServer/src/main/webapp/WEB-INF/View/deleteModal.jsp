<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
    String errorMsg = (String) request.getAttribute("errorMsg");
%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
</head>
<body>

<div id="deleteModal" class="modal">
  <div class="modal-content">
    <!-- 모달 닫기 버튼 -->
    <span class="close" onclick="closeDeleteModal()">&times;</span>

    <!-- 제목 -->
    <h2>회원 탈퇴</h2>

    <!-- 안내 문구 -->
    <p>
      탈퇴 시 계정 정보는 복구되지 않으며,<br>
      본인 확인을 위해 현재 비밀번호를 입력해야 합니다.
    </p>

    <!-- 탈퇴 폼 -->
    <form action="DeleteAccountController" method="post">
      <label for="password">현재 비밀번호</label>
      <input type="password" name="password" id="password" required placeholder="현재 비밀번호 입력">

      <!-- 버튼 영역 -->
      <div class="btns">
        <button type="submit" class="btn-primary">탈퇴하기</button>
        <button type="button" class="btn-secondary" onclick="closeDeleteModal()">취소</button>
      </div>

      <!-- 비밀번호 오류 메시지 (스크립틀릿 방식) -->
      <%
        if (errorMsg != null && !errorMsg.isEmpty()) {
      %>
        <p style="color: red; margin-top: 10px;"><%= errorMsg %></p>
      <%
        }
      %>
    </form>
  </div>
</div>

</body>
</html>
