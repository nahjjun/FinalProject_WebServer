package Controller;

import java.io.IOException;

import Entity.LoginUser;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/MainPageController")
public class MainPageController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public MainPageController() {
        super();
    }

	// 메인 페이지 보내주는 함수
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8"); 
		RequestDispatcher dispatcher = request.getRequestDispatcher("/mainPage.jsp");;
		String logout = request.getParameter("logout");
		if("1".equals(logout)) { // 로그아웃 된 상태라면 알림창 보내기 (null exception을 일으키지 않기 위해 "1".equals() 형태로 사용함 
			request.setAttribute("errorScript", "<script>alert('로그아웃 되었습니다!');</script>");
		}
		dispatcher.forward(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

}
