package Controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import Dto.ReviewRegisterDto;
import Entity.LoginUser;
import Service.ReviewService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


@WebServlet("/ReviewController")
public class ReviewController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ReviewService reviewService = new ReviewService();       

    public ReviewController() {
        super();
        // TODO Auto-generated constructor stub
    }

    // doGet으로 수행할 수 있는 review 관련 작업들은 아래와 같다.
    	// !! 로그인 여부 항상 확인해야함 !!
    // 1. 사용자가 작성한 review 글 DB에 저장 후 해당 리뷰 아래에 추가해주기
    // 2. 사용자가 작성한 review 글 삭제 작업 (본인이 쓴 글인지 확인해야함)
    // 3. 사용자가 작성한 review 글 수정 작업 (본인이 쓴 글인지 확인해야함)
    // 4. 각 review에 좋아요/싫어요 누르기 (둘 중 한번에 하나만 누를 수 있음)
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8");
		
		
		// 리뷰 작업을 한 다음, 다시 영화 상세페이지로 돌아가야하므로 dispatcher는 모두 동일하게 설정
	    switch(request.getParameter("action")) {
    		// 사용자가 작성한 review 글 수정 작업 (본인이 쓴 글인지 확인해야함)
	    	case "edit":
	    		editFunc(request, response);
	    		break;
    		// 사용자가 작성한 review 글 삭제 작업 (본인이 쓴 글인지 확인해야함)
	    	case "delete":   
	    		deleteFunc(request, response);
	    		break;
	    	// 각 review에 좋아요/싫어요 누르기 (둘 중 한번에 하나만 누를 수 있음)
	    	case "like":
	    		likeFunc(request, response);
	    		break;
	    	case "dislike":
	    		dislikeFunc(request, response);
	    		break;
	    }
	    
	    // 각 작업이 끝나고 나서 예외가 없었다면, 영화 상세페이지로 다시 돌아간다.
	    // DB에서 영화 정보를 가져와서, 해당 영화의 상세정보를 가져온다.
	    String movie_id_string = request.getParameter("movie_id");
	    if(movie_id_string == null) {
	    	System.out.println("movie_id를 받아오지 못했습니다.");
	    	return;
	    }
	    int movie_id = Integer.parseInt(movie_id_string);
	    Map<String, Object> movieInfo = reviewService.getMovieInfo(movie_id);
	    request.setAttribute("movieInfo", movieInfo);
	    
	    // 해당 영화에 해당하는(movie_id가 일치하는) 리뷰 데이터들을 불러와서 담는다.
	    List<Map<String, Object>> reviewInfoList = reviewService.getReviewInfoList(movie_id);
	    request.setAttribute("reviewInfoList", reviewInfoList);
	    
	    RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/View/movieDetail.jsp");
		dispatcher.forward(request, response);
	}

	// register 작업(리뷰 등록)
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8");
    	// 사용자가 작성한 review 글 DB에 저장 후 해당 리뷰 아래에 추가해주기
		registerFunc(request, response);
		
	    // 각 작업이 끝나고 나면, 영화 상세페이지로 다시 돌아간다.
	    // DB에서 영화 정보를 가져와서, 해당 영화의 상세정보를 가져온다.
	    int movie_id = Integer.parseInt(request.getParameter("movie_id"));
	    Map<String, Object> movieInfo = reviewService.getMovieInfo(movie_id);
	    request.setAttribute("movieInfo", movieInfo);
	    
	    // 해당 영화에 해당하는(movie_id가 일치하는) 리뷰 데이터들을 불러와서 담는다.
	    List<Map<String, Object>> reviewInfoList = reviewService.getReviewInfoList(movie_id);
	    request.setAttribute("reviewInfoList", reviewInfoList);
	    
	    RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/View/movieDetail.jsp");
		dispatcher.forward(request, response);
	}

	// 사용자가 작성한 review 글 DB에 저장 후 해당 리뷰 아래에 추가해주기
	private void registerFunc(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 만약 로그인된 상태라면, 현재 세션에 저장된 사용자 아이디를 가져와서 "이름", "class", "email"값을 가져와서 request에 넣어준다.
		HttpSession session = request.getSession(false); // false 인자값으로 주면, 해당 세션이 없을 경우 null 반환
		// 로그인되지 않은 상태면 로그인 페이지로 보낸다.
		if(session == null || session.getAttribute("loginUser") == null) {
			response.sendRedirect("LoginController");
			return;
		}
		
		// 로그인된 상태라면, email값을 가져와서 user 정보를 가져온다.
		// Review 테이블에 저장할 데이터들
		LoginUser user = (LoginUser)session.getAttribute("loginUser");
		String email = user.getEmail();

		int user_id = user.getUserId();
		String name = user.getName();
		String user_class = user.getUserClass();
		
		// 클라이언트에 저장되어있는 영화 아이디값을 가져온다.
		int movie_id = Integer.parseInt(request.getParameter("movie_id"));
		
		String context = request.getParameter("review_context"); 
		int rating = Integer.parseInt(request.getParameter("rating"));
		
		
		// Review 테이블에 해당 리뷰를 등록한다.
		ReviewRegisterDto dto = new ReviewRegisterDto(user_id, movie_id, name, email, user_class, context, rating);
		int review_id = reviewService.registerReviewDB(dto);
		
		// 리뷰등록 후, 해당 리뷰에 맞는 리뷰 reaction을 만들어준다.
		if(!reviewService.createReviewReaction(user_id, review_id)) {
			System.out.println("ReviewController/registerFunc() -> 새로운 리엑션 추가하기 실패");
		}
		
	}
	
	// 사용자가 작성한 review 글 수정 작업 (본인이 쓴 글인지 확인해야함)
	private void editFunc(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		// 1. 로그인된 상태인지 확인한다.
		HttpSession session = request.getSession(false); // false 인자값으로 주면, 해당 세션이 없을 경우 null 반환
		// 로그인되지 않은 상태면 로그인 페이지로 보낸다.
		if(session == null || session.getAttribute("loginUser") == null) {
			response.sendRedirect("LoginController");
			return;
		}
		
		// 2. 로그인된 상태라면, 해당 리뷰가 본인이 작성한 글과 동일한 글인지 확인한다.
		LoginUser user = (LoginUser)session.getAttribute("loginUser");
		int user_id = user.getUserId();
		// 본인이 작성한 글이 아닌 경우, 알림창을 띄워준다.
		if(user_id != user.getUserId()) {
			request.setAttribute("errorScript", "<script>alert('본인이 작성한 글만 수정할 수 있습니다!');</script>");
		} else { // 본인이 작성한 글인 경우, 
			
			
		}
		
		
	}
	
	// 사용자가 작성한 review 글 삭제 작업 (본인이 쓴 글인지 확인해야함)
	private void deleteFunc(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		// 1. 로그인된 상태인지 확인한다.
		HttpSession session = request.getSession(false); // false 인자값으로 주면, 해당 세션이 없을 경우 null 반환
		// 로그인되지 않은 상태면 로그인 페이지로 보낸다.
		if(session == null || session.getAttribute("loginUser") == null) {
			response.sendRedirect("LoginController");
			return;
		}
		
		// 2. 로그인된 상태라면, 해당 리뷰가 본인이 작성한 글과 동일한 글인지 확인한다.
		LoginUser user = (LoginUser)session.getAttribute("loginUser");
		int user_id = user.getUserId();
		// 본인이 작성한 글이 아닌 경우, 알림창을 띄워준다.
		if(user_id != user.getUserId()) {
			request.setAttribute("errorScript", "<script>alert('본인이 작성한 글만 삭제할 수 있습니다!');</script>");
		} else { // 본인이 작성한 글인 경우, 해당 리뷰반응 객체와 리뷰글을 삭제한다.
			int review_id = Integer.parseInt(request.getParameter("review_id"));
			if(!reviewService.deleteReviewReaction(user_id, review_id) || !reviewService.deleteReview(review_id)) {
				System.err.println("ReviewController/deleteFunc() -> 리뷰 지우기 실패");
			}
		}
	}

	// 각 review에 좋아요 누르기 (둘 중 한번에 하나만 누를 수 있음)
	private void likeFunc(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		// 만약 로그인된 상태라면, 현재 세션에 저장된 사용자 아이디를 가져와서 "이름", "class", "email"값을 가져와서 request에 넣어준다.
		HttpSession session = request.getSession(false); // false 인자값으로 주면, 해당 세션이 없을 경우 null 반환
		// 로그인되지 않은 상태면 로그인 페이지로 보낸다.
		if(session == null || session.getAttribute("loginUser") == null) {
			response.sendRedirect("LoginController");
			return;
		}
		
		LoginUser user = (LoginUser)session.getAttribute("loginUser");
		int user_id = user.getUserId();
		int review_id = Integer.parseInt(request.getParameter("review_id"));
		System.out.println("[디버깅] user_id : " + user_id);
		System.out.println("[디버깅] review_id : " + review_id);
		
		// 로그인된 상태라면, user 정보를 가져온다.
		Map<String, Object> reviewReactionInfo = reviewService.getReviewReactionInfo(user_id, review_id);
		if(reviewReactionInfo == null) {// 리액션 정보가 없으면, 해당 정보 생성
			reviewService.createReviewReaction(user_id, review_id);
			reviewReactionInfo = reviewService.getReviewReactionInfo(user_id, review_id);
		}
		int reviewReaction_id = (int)reviewReactionInfo.get("reviewReaction_id");
		int reactionType = (int)reviewReactionInfo.get("reactionType");
		switch(reactionType) {
			case 0: // 아직 선택되지 않은 리뷰인 경우, 해당 리뷰 좋아요 증가 설정
				reviewService.setReviewLikeInfo(reviewReaction_id, review_id, 1);
				break;
			case 1: // 좋아요가 선택된 리뷰인 경우, 해당 리뷰 좋아요 감소 설정
				reviewService.setReviewLikeInfo(reviewReaction_id, review_id, 2);
				break;
			case 2: // 싫어요가 선택된 리뷰인 경우
				request.setAttribute("errorScript", "<script>alert('이미 싫어요를 눌렀습니다!');</script>");
				break;
		}
		
	}
	
	
	// 각 review에 싫어요 누르기 (둘 중 한번에 하나만 누를 수 있음)	
	private void dislikeFunc(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		// 만약 로그인된 상태라면, 현재 세션에 저장된 사용자 아이디를 가져와서 "이름", "class", "email"값을 가져와서 request에 넣어준다.
		HttpSession session = request.getSession(false); // false 인자값으로 주면, 해당 세션이 없을 경우 null 반환
		// 로그인되지 않은 상태면 로그인 페이지로 보낸다.
		if(session == null || session.getAttribute("loginUser") == null) {
			response.sendRedirect("LoginController");
			return;
		}
		
		LoginUser user = (LoginUser)session.getAttribute("loginUser");
		int user_id = user.getUserId();
		int review_id = Integer.parseInt(request.getParameter("review_id"));
		
		// 로그인된 상태라면, user 정보를 가져온다.
		Map<String, Object> reviewReactionInfo = reviewService.getReviewReactionInfo(user_id, review_id);
		if(reviewReactionInfo == null) {// 리액션 정보가 없으면, 해당 정보 생성
			reviewService.createReviewReaction(user_id, review_id);
			reviewReactionInfo = reviewService.getReviewReactionInfo(user_id, review_id);
		}
		int reviewReaction_id = (int)reviewReactionInfo.get("reviewReaction_id");
		int reactionType = (int)reviewReactionInfo.get("reactionType");
		switch(reactionType) {
			case 0: // 아직 선택되지 않은 리뷰인 경우, 해당 리뷰 싫어요 증가 설정
				reviewService.setReviewUnlikeInfo(reviewReaction_id, review_id, 1);
				break;
			case 1: // 좋아요가 선택된 리뷰인 경우
				request.setAttribute("errorScript", "<script>alert('이미 좋아요를 눌렀습니다!');</script>");
				break;
			case 2: // 싫어요가 선택된 리뷰인 경우, 해당 리뷰 싫어요 감소 설정
				reviewService.setReviewUnlikeInfo(reviewReaction_id, review_id, 2);
				break;
		}
		
	}

	
}
