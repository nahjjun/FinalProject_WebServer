package Controller;

import java.io.IOException;

import Entity.LoginUser;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/LogoutController")
public class LogoutController extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public LogoutController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		LoginUser loginUser;
		
		if((loginUser = (LoginUser)session.getAttribute("loginUser")) != null) {
			session.invalidate(); // 세션 제거
		}

		// ✅ 자동로그인 쿠키 제거
		Cookie cookie = new Cookie("autoLogin", null);
		cookie.setMaxAge(0); // 즉시 삭제
		cookie.setPath("/"); // 꼭 동일한 경로로 지정해야 삭제됨
		response.addCookie(cookie);

		// 리다이렉트
		response.sendRedirect("MainPageController?logout=1");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
