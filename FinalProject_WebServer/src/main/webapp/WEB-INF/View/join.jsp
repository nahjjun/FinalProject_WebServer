<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
	<link rel="stylesheet" href="./resources/css/bootstrap.min.css" />
	<link rel="stylesheet" href="./resources/css/skvstyle.css" />
	<title>회원가입</title>
</head>
<body>
<%@ include file="/header.jsp" %>
		     
<div class="container d-flex flex-column align-items-center justify-content-center py-5" style="min-height: 100vh;">
	<!-- 환영 영역 -->
	<div class="text-center mb-4">
		<img src="./resources/images/회원가입환영사진.png" alt="환영 이미지" class="img-fluid" style="max-height: 300px;">
		<h4 class="mt-4 title-bold title-highlight">SKV에 오신 걸 환영합니다!</h4>
		<p class="med">SKV 통합 회원으로 다양한 서비스를 즐기세요</p>
	</div>

		<!-- 가입 폼 카드 -->
	<div class="card shadow-sm border-0" style="width: 100%; max-width: 500px;">
		<div class="card-body p-4">
			<h3 class="mb-4 text-left title-highlight">회원가입</h3>
	
			<form action="JoinController" method="post">
				<div class="form-floating mb-3">
					<input type="email" class="form-control" name="email" id="email" placeholder="이메일" required>
					<label for="email">이메일</label>
				</div>
				<div class="form-floating mb-3">
					<input type="password" class="form-control" name="password" id="password" placeholder="비밀번호" required>
					<label for="password">비밀번호</label>
				</div>
				<div class="form-floating mb-3">
					<input type="text" class="form-control" name="name" id="name" placeholder="이름" required>
					<label for="name">이름</label>
				</div>
				<div class="form-floating mb-3">
					<input type="date" class="form-control" name="birth" id="birth" placeholder="생년월일" >
					<label for="birth">생년월일</label>
				</div>
				<button type="submit" class="btn btn-login text-white w-100">가입하기</button>
			</form>
		
		</div>
	</div>
	
</div>
<%@include file ="/footer.jsp" %>

</body>
</html>
