package Controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

import Entity.LoginUser;
import Service.TicketService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


// 1. 예매 페이지 1에서는 극장 -> 날짜 -> 영화 -> 상영 시간 -> 좌석을 선택한다
// 2. 좌석 선택에서는 위의 데이터를 기반으로 상영 정보를 찾고, 해당 상영 정보의 screening_id로 연관되어있는 좌석들을 전부 가져오고, 해당 좌석들 중 하나를 선택한다
@WebServlet("/TicketController")
public class TicketController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private TicketService ticketService = new TicketService();
   
    public TicketController() {
    }

    // 1. action=default인 경우 : 일반 예매하기
    // 2. action=영화이름인 경우 : 해당 영화 예매하기 버튼
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8");
		
		// 로그인 되어있는 상태인지 확인
		HttpSession session = request.getSession();
        LoginUser loginUser = (LoginUser) session.getAttribute("loginUser");

        if (loginUser == null) {
           RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/View/login.jsp");
           dispatcher.forward(request, response);
           // 로그인 안되어 있으면 로그인으로 보내기
            return;
        }
		
		// 로그인 되어있는 경우, 예매 작업 진행
		switch(request.getParameter("action")){
			// 동적인 것과 관련없이 기본적으로 보내줘야하는 데이터 : 극장 리스트
			case "default":
				sendTheaterList(request, response);
				break;
			// 극장, 날짜를 선택한 경우, 해당 극장/날짜 데이터로 상영 영화 데이터 가져오기
			case "movie":
				sendAvailableMovies(request, response);
				break;
			// 극장, 날짜,영화를 선택한 경우, 해당 극장/날짜 데이터로 상영 영화 데이터 가져오기
			case "time":
				sendAvailableTimes(request, response);
				break;
			// 극장, 날짜, 영화, 시간을 선택한 경우, 해당 극장/날짜 데이터로 상영 영화 데이터 가져오기
			case "seat":
				sendAvailableSeats(request, response);
				break;
				
		}
	}

	// 1. 전체 극장 목록 전송 (첫 화면이므로, dispatcher로 화면 전송)
	private void sendTheaterList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    List<Map<String, Object>> theaterList = ticketService.getTheaterNameList();
	    RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/View/ticket.jsp");
	    request.setAttribute("theaterList", theaterList);
	    dispatcher.forward(request, response);
	}

	// 2. 극장+날짜로 상영 중인 영화 목록
	// ajax로 동적으로 "영화 목록" 부분만 화면을 처리해줄 것이므로, dispatcher를 사용하지 않는다.
	private void sendAvailableMovies(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 사용자가 지금까지 선택한 데이터들 가져옴
	    int theater_Id = Integer.parseInt(request.getParameter("theater_Id"));
	    String date = request.getParameter("date");
	    // 각 영화의 movie_id와 영화 이름이 들어간 배열
	    List<Map<String, Object>> movieList = ticketService.getMovieListByTheaterAndDate(theater_Id, date);	    
        if(movieList == null) System.out.println("movieList가 비었습니다");
        
	    // AJAX 요청이라면 JSON으로 응답
        // 요청에서 보낸 헤더 중 "X-Requested-With"가 "XMLHttpRequest"인지 확인한다.
        if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
            response.setContentType("application/json; charset=UTF-8");

            Map<String, Object> result = new HashMap<>();
            result.put("movieList", movieList);

            // ObjectMapper로 json 형태 문자열 생성
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(result);
            
            // response.getWriter() : 응답 stream 반환 (PrintWriter 객체)
            // .write()로 브라우저에게 json 문자열을 전달한다.cmd
            response.getWriter().write(json);
            return;
        }
	}

	// 3. 극장+날짜+영화로 가능한 상영 시간 목록 받아오기
	private void sendAvailableTimes(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 사용자가 지금까지 선택한 데이터들 가져옴
	    int theater_id = Integer.parseInt(request.getParameter("theater_id"));
	    String date = request.getParameter("date");
	    int movie_id = Integer.parseInt(request.getParameter("movie_id"));
	    List<String> timeList = ticketService.getTimeList(theater_id, date, movie_id);
	    // AJAX 요청이라면 JSON으로 응답
        // 요청에서 보낸 헤더 중 "X-Requested-With"가 "XMLHttpRequest"인지 확인한다.
        if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
            response.setContentType("application/json; charset=UTF-8");

            Map<String, Object> result = new HashMap<>();
            result.put("timeList", timeList);

            // ObjectMapper로 json 형태 문자열 생성
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(result);
            
            // response.getWriter() : 응답 stream 반환 (PrintWriter 객체)
            // .write()로 브라우저에게 json 문자열을 전달한다.cmd
            response.getWriter().write(json);
            return;
        }
	}

	// 4. 상영 정보 기준 좌석 리스트 반환
	private void sendAvailableSeats(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 사용자가 지금까지 선택한 데이터들 가져옴
		int theater_id = Integer.parseInt(request.getParameter("theater_id"));
	    String date = request.getParameter("date");
	    int movie_id = Integer.parseInt(request.getParameter("movie_id"));
	    String time = request.getParameter("time");
	    List<Map<String, Object>> seatList = ticketService.getSeatList(theater_id, date, movie_id, time);
	    
	    if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
            response.setContentType("application/json; charset=UTF-8");

            Map<String, Object> result = new HashMap<>();
            result.put("seatList", seatList);

            // ObjectMapper로 json 형태 문자열 생성
            ObjectMapper mapper = new ObjectMapper();
            
            String json = mapper.writeValueAsString(result);
            // response.getWriter() : 응답 stream 반환 (PrintWriter 객체)
            // .write()로 브라우저에게 json 문자열을 전달한다.cmd
            response.getWriter().write(json);
            return;
        }
	}


	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8");
		
		// 로그인 되어있는 상태인지 확인
		HttpSession session = request.getSession();
        LoginUser loginUser = (LoginUser) session.getAttribute("loginUser");

        if (loginUser == null) {
           RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/View/login.jsp");
           dispatcher.forward(request, response);
           // 로그인 안되어 있으면 로그인으로 보내기
            return;
        }
		
		// 모든 데이터들을 전부 선택한 경우, 해당 데이터들을 바탕으로 Ticket 생성에 필요한 정보들을 만들어서 ticket에 데이터를 추가한다.  
		int user_id = loginUser.getUserId();
		int theater_id = Integer.parseInt(request.getParameter("theater_id"));
	    String date = request.getParameter("date");
	    int movie_id = Integer.parseInt(request.getParameter("movie_id"));
	    String time = request.getParameter("time");
	    int row_num = Integer.parseInt(request.getParameter("row"));
	    int col_num = Integer.parseInt(request.getParameter("col"));
	    
	    
	    if(!ticketService.insertTicket(user_id, theater_id, movie_id, row_num, col_num, time, date)) {
	    	System.out.println("예매 실패");
	    	response.sendRedirect("/WEB-INF/View/ticketFail.jsp");
	    	return;
	    }
        response.sendRedirect("/WEB-INF/View/ticketSuccess.jsp");
	}
	
}
