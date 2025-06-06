<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
	<div id="profileModal" class="modal">
	  <div class="modal-content">
	    <span class="close" onclick="closeProfileModal()">&times;</span>
	    <h4>프로필 사진 수정</h4>
	    <form action="ProfileUpdateController" method="post" enctype="multipart/form-data">
      <label for="profileImage">새 프로필 사진</label>
      <input type="file" name="profileImage" id="profileImage" accept="image/*" required>

      <div class="btns">
        <button type="submit" class="btn-primary">저장</button>
        <button type="button" class="btn-secondary" onclick="closeProfileModal()">취소</button>
      </div>
    </form>
  </div>
</div>
</body>
</html>