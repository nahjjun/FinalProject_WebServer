package Controller;

import java.io.IOException;

import Dto.JoinRequestDto;
import Service.JoinService;
import Util.PasswordUtil;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet({"/JoinController"})
public class JoinController extends HttpServlet {
   private static final long serialVersionUID = 1L;
   private JoinService joinService = new JoinService();

   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      response.setContentType("text/html; charset=UTF-8");
      System.out.println("JoinController/doGet() 실행됨");
      RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/View/join.jsp");
      dispatcher.forward(request, response);
   }

   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      response.setContentType("text/html; charset=UTF-8");
      System.out.println("JoinController/doPost() 실행됨");
      String email = request.getParameter("email");
      String password = request.getParameter("password");
      String name = request.getParameter("name");
      String birth = request.getParameter("birth");
      String page = null;
      String errorString = null;

      try {
         switch(PasswordUtil.effectivenessConfirm(email, password, name, birth)) {
         case -1:
            page = "/WEB-INF/View/join.jsp";
            errorString = "정보를 빠짐없이 입력해야합니다!";
         case 0:
         default:
            break;
         case 1:
            page = "/WEB-INF/View/join.jsp";
            errorString = "이메일 형식이 틀렸습니다!";
            break;
         case 2:
            page = "/WEB-INF/View/join.jsp";
            errorString = "비밀번호의 형식이 틀렸습니다!";
            break;
         case 3:
            page = "/WEB-INF/View/join.jsp";
            errorString = "이름은 너무 길게 작성할 수 없습니다!";
            break;
         case 4:
            page = "/WEB-INF/View/join.jsp";
            errorString = "날짜 형식이 틀렸습니다!";
         }

         RequestDispatcher dispatcher;
         if (page != null) {
            dispatcher = request.getRequestDispatcher(page);
            request.setAttribute("errorScript", "<script>alert('" + errorString + "');</script>");
            dispatcher.forward(request, response);
            return;
         }

         JoinRequestDto joinRequestDto = new JoinRequestDto(email, password, name, birth);
         if (!this.joinService.join(joinRequestDto)) {
            dispatcher = request.getRequestDispatcher("/WEB-INF/View/join.jsp");
            request.setAttribute("errorScript", "<script>alert('이미 만들어진 이메일 계정입니다!');</script>");
            dispatcher.forward(request, response);
         } else {
            System.out.println("회원가입 성공!");
            response.sendRedirect("LoginController");
         }
      } catch (Exception var11) {
         var11.printStackTrace();
      }

   }
}
