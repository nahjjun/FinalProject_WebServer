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

@WebServlet("/MypageEditController")
public class MypageEditController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserService userService = new UserService();

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();
		LoginUser loginUser = (LoginUser) session.getAttribute("loginUser");

		if (loginUser == null) {
			 request.setAttribute("message", "로그인이 필요합니다.");
			 request.setAttribute("nextPage", "MypageController");
				request.getRequestDispatcher("/WEB-INF/View/editResult.jsp").forward(request, response);

	         return;
		}
		
		String currentEmail = loginUser.getEmail();
		String phone = request.getParameter("phone");
		String currentPassword = request.getParameter("currentPassword");
		String newPassword = request.getParameter("newPassword");
		String confirmPassword = request.getParameter("confirmPassword");
		
		// ✅ 전화번호 형식 확인
		String phonePattern = "^010-\\d{4}-\\d{4}$";
		if (!phone.matches(phonePattern)) {
		    request.setAttribute("message", "전화번호 형식이 올바르지 않습니다. 예: 010-1234-5678");
		    request.setAttribute("nextPage", "MypageController");
			request.getRequestDispatcher("/WEB-INF/View/editResult.jsp").forward(request, response);

		    return;
		}

		// 현재 로그인된 유저 정보 불러오기
		User user = userService.getUserByEmail(currentEmail);
		if (user == null) {
			request.setAttribute("message", "사용자 정보를 찾을 수 없습니다.");
			request.setAttribute("nextPage", "MypageController");
			request.getRequestDispatcher("/WEB-INF/View/editResult.jsp").forward(request, response);

			return;
		}

		// 1단계: 현재 비밀번호 검증
		if (!PasswordUtil.matches(currentPassword, user.getEncodedPassword())) {
			request.setAttribute("message", "현재 비밀번호가 일치하지 않습니다.");
			request.setAttribute("nextPage", "MypageController");
			request.getRequestDispatcher("/WEB-INF/View/editResult.jsp").forward(request, response);

			return;
		}

		// 2단계: 새 비밀번호가 입력되었으면 일치 여부 검증
		if (newPassword != null && !newPassword.isEmpty()) {
			if (!newPassword.equals(confirmPassword)) {
				request.setAttribute("message", "새 비밀번호가 일치하지 않습니다.");
				request.setAttribute("nextPage", "MypageController");
				request.getRequestDispatcher("/WEB-INF/View/editResult.jsp").forward(request, response);

				return;
			}
			//기존 비밀번호와 동일하면 변경하지 않음
			if (PasswordUtil.matches(newPassword, user.getEncodedPassword())) {
		        request.setAttribute("message", "새 비밀번호가 기존 비밀번호와 같습니다.");
		        request.setAttribute("nextPage", "MypageController");
		        request.getRequestDispatcher("/WEB-INF/View/editResult.jsp").forward(request, response);
		        return;
		    }
			 // 비밀번호 암호화 후 설정
			String encoded = PasswordUtil.encode(newPassword);
			user.setEncodedPassword(encoded);
			System.out.println("비밀번호 암호화 완료: " + encoded);
		}

		// 정보 업데이트
		user.setPhone(phone);
		userService.updateUserInfo(currentEmail, phone, user.getEncodedPassword());
		System.out.println("[newPassword 값] " + newPassword);
		System.out.println("[confirmPassword 값] " + confirmPassword);

		request.setAttribute("message", "회원정보가 성공적으로 수정되었습니다.");
		request.setAttribute("nextPage", "MypageController");
		request.getRequestDispatcher("/WEB-INF/View/editResult.jsp").forward(request, response);

		// 마이페이지로 이동
		//response.sendRedirect("MypageController");
	}
}



