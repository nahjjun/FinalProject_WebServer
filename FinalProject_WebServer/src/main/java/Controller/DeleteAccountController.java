package Controller;

import java.io.IOException;

import Entity.LoginUser;
import Entity.User;
import Service.UserService;
import Util.PasswordUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet({"/DeleteAccountController"})
public class DeleteAccountController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserService userService = new UserService();

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();
		LoginUser loginUser = (LoginUser) session.getAttribute("loginUser");

		if (loginUser == null) {
			request.setAttribute("message", "로그인이 필요합니다.");
			request.setAttribute("nextPage", "login.jsp");
			request.getRequestDispatcher("/WEB-INF/View/editResult.jsp").forward(request, response);
			return;
		}

		String email = loginUser.getEmail();
		String password = request.getParameter("password");

		User user = userService.getUserByEmail(email);
		if (user == null || !PasswordUtil.matches(password, user.getEncodedPassword())) {
			request.setAttribute("message", "비밀번호가 일치하지 않습니다.");
			request.setAttribute("nextPage", "MypageController");
			request.getRequestDispatcher("/WEB-INF/View/editResult.jsp").forward(request, response);
			return;
		}

		// 실제 DB에서 삭제
		userService.deleteUserByEmail(email);

		// 세션 초기화
		session.invalidate();

		// 메시지 출력
		request.setAttribute("message", "회원 탈퇴가 완료되었습니다. 이용해주셔서 감사합니다.");
		request.setAttribute("nextPage", "mainPage.jsp"); // 또는 메인 페이지
		request.getRequestDispatcher("/WEB-INF/View/editResult.jsp").forward(request, response);
		
		
		//response.sendRedirect("MypageController");
	}
}
