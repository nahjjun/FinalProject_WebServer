package Controller;

import Dto.LoginRequestDto;
import Service.LoginService;
import Util.PasswordUtil;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

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
         case 0:
         default:
            break;
         case 1:
            page = "/WEB-INF/View/login.jsp";
            errorString = "이메일 형식이 틀렸습니다!";
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
         }
      } catch (Exception var9) {
         var9.printStackTrace();
      }

   }
}
