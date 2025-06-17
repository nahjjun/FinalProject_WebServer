package Controller;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import Service.MovieService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/MainPageController")
public class MainPageController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private MovieService movieService = new MovieService();

	public MainPageController() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8");

		// 무비차트 데이터 가져오기
		List<Map<String, Object>> boxOfficeList = movieService.getBoxOfficeList();
		boxOfficeList.sort((m1, m2) -> (int) m1.get("rank") - (int) m2.get("rank"));
		request.setAttribute("boxOfficeList", boxOfficeList);

		// 로그아웃 알림 체크
		String logout = request.getParameter("logout");
		if ("1".equals(logout)) {
			request.setAttribute("errorScript", "<script>alert('로그아웃 되었습니다!');</script>");
		}

		RequestDispatcher dispatcher = request.getRequestDispatcher("/mainPage.jsp");
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}
}
