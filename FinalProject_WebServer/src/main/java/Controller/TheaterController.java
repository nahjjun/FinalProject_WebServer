package Controller;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

import Service.TheaterService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@WebServlet("/TheaterController")
public class TheaterController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private TheaterService theaterService = new TheaterService(); 
	
    public TheaterController() {
        super();
    }

    // 인자로 전달받은 영화관 정보를 토대로 해당 영화관에서 상영하는 영화 정보 & 해당 영화의 상영 정보들을 가져와서  
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	response.setContentType("text/html; charset=UTF-8");
        String theaterName = request.getParameter("theaterName");
        String screeningDateStr = request.getParameter("screeningDate");

        System.out.println("theaterName: " + theaterName);
        System.out.println("screeningDateStr: " + screeningDateStr);
        // 날짜가 null이면 오늘 날짜로 설정
    	if (screeningDateStr == null || screeningDateStr.isEmpty()) {
    	    LocalDate today = LocalDate.now();
    	    screeningDateStr = today.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    	}

    	if (theaterName == null || theaterName.equals("default")) {
    	    theaterName = "서경대점";
    	}

        // 영화관 목록 + 영화 정보 목록 조회
        List<String> theaterNameList = theaterService.getTheaterNameList();
        List<Map<String, Object>> movieInfoList = theaterService.getMovieInfoList(theaterName, screeningDateStr);

        if(theaterNameList == null) System.out.println("theaterNameList가 비었습니다");
        if(movieInfoList == null) System.out.println("movieInfoList가 비었습니다");
        
        // AJAX 요청이라면 JSON으로 응답
        // 요청에서 보낸 헤더 중 "X-Requested-With"가 "XMLHttpRequest"인지 확인한다.
        if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
            response.setContentType("application/json; charset=UTF-8");

            Map<String, Object> result = new HashMap<>();
            result.put("movieInfoList", movieInfoList);

            // ObjectMapper로 json 형태 문자열 생성
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(result);
            
            // response.getWriter() : 응답 stream 반환 (PrintWriter 객체)
            // .write()로 브라우저에게 json 문자열을 전달한다.
            response.getWriter().write(json);
            return;
        }

        // 전체 페이지 로딩용 (기존 JSP forward)
        request.setAttribute("movieInfoList", movieInfoList);
        request.setAttribute("theaterNameList", theaterNameList);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/View/theater.jsp");
        dispatcher.forward(request, response);
        System.out.println("극장 페이지 출력");
    }


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

}
