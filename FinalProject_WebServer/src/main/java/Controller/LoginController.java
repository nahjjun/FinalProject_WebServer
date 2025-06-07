package Controller;

import java.io.IOException;
import java.util.Map;

import Dto.LoginRequestDto;
import Entity.LoginUser;
import Service.LoginService;
import Util.PasswordUtil;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet({"/LoginController"})
public class LoginController extends HttpServlet {
   private static final long serialVersionUID = 1L;
   private LoginService loginService = new LoginService();

   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      response.setContentType("text/html; charset=UTF-8");
      RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/View/login.jsp");
      dispatcher.forward(request, response);
   }

   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      response.setContentType("text/html; charset=UTF-8");
      String email = request.getParameter("email");
      String password = request.getParameter("password");
      String page = null;
      String errorString = null;

      try {
         switch(PasswordUtil.effectivenessConfirm(email)) {
         case -1:
            page = "/WEB-INF/View/login.jsp";
            errorString = "이메일과 비밀번호 둘다 입력해야합니다!";
            break;
         case 0:
         default:
            break;
         case 1:
            page = "/WEB-INF/View/login.jsp";
            errorString = "이메일 형식이 틀렸습니다!";
            break;
         }

         RequestDispatcher dispatcher;
         if (page != null) {
            dispatcher = request.getRequestDispatcher(page);
            request.setAttribute("errorScript", "<script>alert('" + errorString + "');</script>");
            dispatcher.forward(request, response);
            return;
         }

         LoginRequestDto loginRequestDto = new LoginRequestDto(email, password);
         if (!this.loginService.login(loginRequestDto)) {
            dispatcher = request.getRequestDispatcher("/WEB-INF/View/login.jsp");
            request.setAttribute("errorScript", "<script>alert('로그인에 실패했습니다!');</script>");
            dispatcher.forward(request, response);
         } else {
            System.out.println("로그인 성공!");
            // 로그인 성공 시, 세션에 사용자 정보를 저장한다.
            HttpSession session = request.getSession();
            
            // 사용자 정보를 불러옴
            Map<String, Object> userInfo = loginService.getUserInfo(email);
            int user_id = (int)userInfo.get("user_id");
            String name = (String)userInfo.get("name");
            String user_class = (String)userInfo.get("user_class");
            
            
            LoginUser loginUser = new LoginUser(email, name, user_class, user_id);
            session.setAttribute("loginUser", loginUser);
            
            response.sendRedirect("MainPageController");
         }
      } catch (Exception e) {
         e.printStackTrace();
      }
   }
}
