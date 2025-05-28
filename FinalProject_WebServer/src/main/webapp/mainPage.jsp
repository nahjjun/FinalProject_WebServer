<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<link rel="stylesheet" href="./resources/css/bootstrap.min.css" />
	<link rel="stylesheet" href="./resources/css/skvstyle.css" />
<meta charset="UTF-8">
<title>SKV</title>
</head>
<body>
<div class="header bg-light px-4 py-4 shadow-sm">
	<div class="header_content d-flex justify-content-between align-items-center">
		
		<!--왼쪽: 로고 + 텍스트 -->
		<div class="d-flex align-items-center gap-2">
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
</body>
</html>