package Controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import Service.SearchService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/SearchController")
public class SearchController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private SearchService searchService = new SearchService(); 
	
	public SearchController() {
        super();
    }

	// 전달받은 검색어를 통해서 영화 이름을 검색한 뒤, 해당 검색어가 포함된 영화 리스트를 반환하는 함수 
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8");
		String searchQuery = request.getParameter("search_query");
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/View/searchResult.jsp");
		
		// 입력된 검색어를 기반으로 관련 있는 데이터 리스트를 가져옴
		List<Map<String, Object>> searchInfoList = searchService.getSearchInfoList(searchQuery);
		request.setAttribute("searchInfoList", searchInfoList);
		
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

}
