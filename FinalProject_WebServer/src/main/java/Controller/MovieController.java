package Controller;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import Dto.MovieDto;
import Service.MovieService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


@WebServlet("/MovieController")
public class MovieController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private MovieService movieService = new MovieService(); 
	
    public MovieController() {
        super();
    }

	// param값으로 넘어온 "영화 이름"값으로 영화 데이터를 가져와서 해당 영화에 대한 데이터를 request에 담아서 "movieDetail" 페이지로 보낸다. 
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8");
		
		switch(request.getParameter("action")) {
			// "action" 파라미터값이 "movieDetail"이면, 영화의 상세 정보 페이지
			case "movieDetail":
				movieDetailFunc(request, response);
				break;
			// "action" 파라미터값이 "movieChart"이면 영화 차트 페이지 
			case "movieChart":
				movieChartFunc(request, response);
				break;
			// "action" 파라미터값이 "movieList"이면 영화 리스트 페이지 
			case "movieList":
				movieListFunc(request, response);
				break;
			
		}

		
	}

	// 영화 순위 페이지 처리 함수
	private void movieChartFunc(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/View/movieChart.jsp");
		
		List<Map<String, Object>> boxOfficeList = movieService.getBoxOfficeList();
		// 박스오피스 영화 리스트를 랭크 기준으로 오름차순 정렬한다.
		boxOfficeList.sort(new Comparator<Map<String, Object>>(){
				@Override
				public int compare(Map<String, Object> m1, Map<String, Object> m2) {
					return (int)m1.get("rank") - (int)m2.get("rank");
				}
			});
		request.setAttribute("boxOfficeList", boxOfficeList);
		
		// 이후, dispatcher.forward()로 페이지를 변경해준다.
		dispatcher.forward(request, response);
	}
	
	// 영화 리스트 페이지 처리 함수
	private void movieListFunc(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/View/movieList.jsp");
		
		List<Map<String, Object>> movieInfoList = movieService.getMovieInfoList();
		request.setAttribute("movieInfoList", movieInfoList);
		
		// 이후, dispatcher.forward()로 페이지를 변경해준다.
		dispatcher.forward(request, response);
	}
	
	// 영화 상세 페이지 처리 함수
	private void movieDetailFunc(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException  {
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/View/movieDetail.jsp");
		
		
		// url로 넘어온 영화 id를 받아온다.
		int movie_id = Integer.parseInt(request.getParameter("movie_id"));
		MovieDto dto = new MovieDto(movie_id);
		Map<String, Object> movieInfo = movieService.getMovieInfo(dto);
		
		// url로 넘어온 "영화 이름" 데이터를 통해서 해당 영화의 데이터들을 request에 넣어준다.
		request.setAttribute("movieInfo", movieInfo);

		// "영화 아이디"를 사용해서 해당 영화의 리뷰 데이터들을 찾아 request에 저장함
		List<Map<String, Object>> reviewInfoList = movieService.getReviewInfoList(dto);
		if(reviewInfoList != null) {
			request.setAttribute("reviewInfoList", reviewInfoList);
			
			for(int i=0; i<reviewInfoList.size(); ++i) {
				System.out.println("review_id : " + reviewInfoList.get(i).get("review_id"));
			}
		}
		
		// 이후, dispatcher.forward()로 페이지를 변경해준다.
		dispatcher.forward(request, response);
	}
	
	
	// 
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

}
