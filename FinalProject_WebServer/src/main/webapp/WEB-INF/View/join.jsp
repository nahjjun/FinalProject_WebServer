<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
	<link rel="stylesheet" href="./resources/css/bootstrap.min.css" />
	<link rel="stylesheet" href="./resources/css/skvstyle.css" />
	<title>회원가입</title>
</head>
<body>
<div class="header bg-light px-4 py-4 shadow-sm">
	<div class="header_content d-flex justify-content-between align-items-center">
		
		<!--왼쪽: 로고 + 텍스트 -->
		<div class="d-flex align-items-center gap-2">
			<!-- <a> 태그는 get방식으로 밖에 못보낸다  -->
			<a href="/MainPageController">
				<img src="./resources/images/SKV 로고.png" alt="SKV" style="height: 40px;">
			</a>
			<span class="med fs-4">SeoKyeong Video</span>
		</div>

		<!-- 오른쪽: 메뉴 -->
		<ul class="header_wrap list-unstyled d-flex gap-4 align-items-center mb-0">
			<li>
				<a href="LoginController" class="med text-decoration-none d-flex flex-column align-items-center">
					<img src="./resources/images/로그인.png" alt="로그인" style="height: 24px;">
					<span>로그인</span>
				</a>
			</li>
			<li>
				<a href="JoinController" class="med text-decoration-none d-flex flex-column align-items-center">
					<img src="./resources/images/회원가입.png" alt="회원가입" style="height: 24px;">
					<span>회원가입</span>
				</a>
			</li>
			<li>
				<a href="mypage.jsp" class="med text-decoration-none d-flex flex-column align-items-center">
					<img src="./resources/images/마이페이지.png" alt="마이페이지" style="height: 24px;">
					<span>MY SKV</span>
				</a>
			</li>
		</ul>
	</div>
</div>
		     
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
<!-- 경고 팝업창 -->
${errorScript}
<%@include file ="footer.jsp" %>

</body>
</html>
