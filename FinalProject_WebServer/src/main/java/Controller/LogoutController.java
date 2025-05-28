package Controller;

import java.io.IOException;

import Entity.LoginUser;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
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

    // 로그아웃 (세션 제거)를 수행해주는 함수
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		LoginUser loginUser;
		// 로그인 되어있는 상태라면,
		if((loginUser = (LoginUser)session.getAttribute("loginUser")) != null) {
			session.invalidate(); // 세션 전체 제거 (완전한 로그아웃)
		}
		// 로그아웃 되었는지 여부를 해당 서블릿으로 보냄
		response.sendRedirect("MainPageController?logout=1");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
