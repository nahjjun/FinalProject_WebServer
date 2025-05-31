package Controller;

import java.io.IOException;
import java.util.Map;

import Dto.MovieDto;
import Service.MovieService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


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
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/View/movieDetail.jsp");
		
		// url로 넘어온 영화 이름값을 받아온다.
		MovieDto movieDto = new MovieDto(request.getParameter("title"));
		Map<String, Object> movieInfo = movieService.getMovieInfo(movieDto);
		
		// url로 넘어온 "영화 이름" 데이터를 통해서 해당 영화의 데이터들을 request에 넣어준다.
		request.setAttribute("movieInfo", movieInfo);
		
		// 이후, dispatcher.forward()로 페이지를 변경해준다.
		dispatcher.forward(request, response);
	}

	// 
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

}
