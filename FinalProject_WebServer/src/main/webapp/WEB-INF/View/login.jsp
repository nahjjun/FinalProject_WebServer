<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<link rel="stylesheet" href="./resources/css/bootstrap.min.css" />
	<link rel="stylesheet" href="./resources/css/skvstyle.css" />
	<title>Login</title>
</head>
<body>
<%@ include file="/header.jsp" %>
<%@ include file="/menu.jsp" %>


<div class="container d-flex flex-column align-items-center justify-content-center" style="min-height: 100vh;">
	<!-- 🔽 로고 이미지 -->
	<img src="./resources/images/로고.png" alt="SKV 로고" class="img-fluid" style="max-width: 180px; margin-bottom: 20px;">

	<!-- 🔽 로그인 폼 카드 -->
	<div class="card shadow-sm border-0" style="width: 100%; max-width: 400px;">
		<div class="card-body p-4">
			<h3 class="title-bold mb-4 text-center title-highlight">로그인</h3>

			<form class="form-signin" action="LoginController" method="post">
				<div class="form-floating mb-3">
					<input type="text" class="form-control" name="email" id="floatingEmail" placeholder="이메일" required autofocus>
					<label for="floatingInput">Email</label>
				</div>
				<div class="form-floating mb-3">
					<input type="password" class="form-control" name="password" id="floatingPassword" placeholder="비밀번호">
					<label for="floatingPassword">Password</label>
				</div>
				<button class="btn btn-lg btn-login text-white w-100" type="submit">로그인</button>
			</form>
		</div>
	</div>
		<!-- 🔽 로그인 카드 아래 회원가입 링크 -->
	<div class="mt-3">
		<p class="text-muted text-center">
			아직 회원이 아니신가요? 
			<a href="JoinController" class="text-decoration-none" style="color: var(--main-green); font-weight: 500;">
				회원가입
			</a>
		</p>
	</div>
</div>
<%@include file ="/footer.jsp" %>
</body>
</html>
