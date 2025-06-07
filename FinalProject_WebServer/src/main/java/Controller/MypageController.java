package Controller;

import java.io.IOException;

import Entity.LoginUser;
import Entity.User;
import Repository.UserRepository;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/MypageController")
public class MypageController extends HttpServlet {
	private UserRepository userRepository = new UserRepository();

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        LoginUser loginUser = (LoginUser) session.getAttribute("loginUser");

        if (loginUser == null) {
            response.sendRedirect("LoginController"); // 로그인 안되어 있으면 로그인으로 보내기
            return;
        }

        String email = loginUser.getEmail();
        User user = userRepository.findByEmail(email);
        
        if (user.getProfileImage() == null || user.getProfileImage().isEmpty()) {
            user.setProfileImage("기본프로필.png");
        }
        
        request.setAttribute("user", user);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/View/mypage.jsp");
        dispatcher.forward(request, response);

    }
}
