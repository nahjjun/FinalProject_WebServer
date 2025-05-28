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
		// 현재 세션을 확인해서, 로그인이 되어있는지 확인한다. 
		HttpSession session = request.getSession(); // request.getSession()를 하면 기존 세션이 없으면 새로운 세션을 만들어서 반환하고, 있다면 기존 세션을 반환한다. 
		LoginUser loginUser = null;
		RequestDispatcher dispatcher = null;
		if((loginUser = (LoginUser)session.getAttribute("loginUser")) == null) { // 사용자 로그인 정보가 저장이 안되어 있는 경우, 로그인 전 mainPage를 보내준다.  
			dispatcher = request.getRequestDispatcher("/mainPage_logouted.jsp");
		} else {
		
		}
		dispatcher.forward(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

}
