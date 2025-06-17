package Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Util.DBUtil;

public class TicketRepository {

	public TicketRepository() {
		// TODO Auto-generated constructor stub
	}

	// 현재 있는 극장 이름들을 return하는 함수
	public List<Map<String, Object>> getTheaterNameList(){
		String sql = "SELECT theater_id, name FROM `Theater`";
		List<Map<String, Object>> theaterList = new ArrayList<Map<String, Object>>();
		try {
			Connection con = DBUtil.getConnection();
			PreparedStatement pstmt = con.prepareStatement(sql);
			
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				Map<String, Object> theaterInfo = new HashMap<String, Object>();
				theaterInfo.put("theater_id", rs.getString("theater_id"));
				theaterInfo.put("name", rs.getString("name"));
				theaterList.add(theaterInfo);
			}
			return theaterList;
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	// 극장 id, 상영 날짜를 기준으로 해당 영화 id, 제목들을 받아오는 함수
	public List<Map<String, Object>> getMovieListByTheaterAndDate(int theater_id, String date){
		String sql = "SELECT m.movie_id, m.title FROM `Screening` s INNER JOIN Movie m ON s.movie_id=m.movie_id WHERE s.theater_id=? AND s.screening_date=?";
		List<Map<String, Object>> movieList = new ArrayList<Map<String, Object>>();
		try {
			Connection con = DBUtil.getConnection();
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, theater_id);
			pstmt.setString(2, date);
			
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				Map<String, Object> movieInfo = new HashMap<String, Object>();
				movieInfo.put("movie_id", rs.getString("movie_id"));
				movieInfo.put("title", rs.getString("title"));
				movieList.add(movieInfo);
			}
			return movieList;
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	// 극장 id, 상영날짜, 영화 id를 기준으로 해당 날짜의 상영 시간들을 받아오는 함수.
	// 시간 데이터에서 10:00 식으로 시간:분 데이터만 뽑아와야함 
	public List<String> getTimeList(int theater_id, String date, int movie_id) {
		// DATE_FORMAT() : 날짜/시간 값을 사람이 읽기 좋은 형태로 바꿈
		// %H : 시간 , %i : 분
		
	    String sql = "SELECT DATE_FORMAT(start_time, '%H:%i') AS time " +
	                 "FROM Screening " +
	                 "WHERE theater_id = ? AND screening_date = ? AND movie_id = ? " +
	                 "ORDER BY start_time ASC";

	    List<String> timeList = new ArrayList<>();

	    try (
	        Connection con = DBUtil.getConnection();
	        PreparedStatement pstmt = con.prepareStatement(sql);
	    ) {
	        pstmt.setInt(1, theater_id);
	        pstmt.setString(2, date);  
	        pstmt.setInt(3, movie_id);

	        ResultSet rs = pstmt.executeQuery();

	        while (rs.next()) {
	            timeList.add(rs.getString("time")); 
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return timeList;
	}

	// 인자로 온 데이터들로 상영 id를 가져온 뒤, 해당 상영 id로 seat 정보들을 가져오는 함수
	public List<Map<String, Object>> getSeatList(int theater_id, String date, int movie_id, String time) {
	    List<Map<String, Object>> seatList = new ArrayList<>();
	    String screeningSql = "SELECT screening_id FROM Screening WHERE theater_id = ? AND screening_date = ? AND movie_id = ? AND DATE_FORMAT(start_time, '%H:%i') = ?";
	    
	    String seatSql = "SELECT seat_id, row_num, col_num, is_reserved FROM Seat WHERE screening_id = ? ORDER BY row_num, col_num";

	    try{
	    	Connection con = DBUtil.getConnection();
	        PreparedStatement pstmt = con.prepareStatement(screeningSql);
	        // 1단계: screening_id 조회
	    	pstmt.setInt(1, theater_id);
	    	pstmt.setString(2, date);
	        pstmt.setInt(3, movie_id);
	        pstmt.setString(4, time);

	        ResultSet rs = pstmt.executeQuery();

	        if (rs.next()) {
	            int screeningId = rs.getInt("screening_id");

	            // 2단계: 해당 screening_id로 좌석 조회
	            pstmt = con.prepareStatement(seatSql);
	            pstmt.setInt(1, screeningId);
                rs = pstmt.executeQuery();

                while (rs.next()) {
                    Map<String, Object> seat = new HashMap<>();
                    seat.put("seat_id", rs.getInt("seat_id"));
                    seat.put("row", rs.getInt("row_num"));
                    seat.put("col", rs.getInt("col_num"));
                    seat.put("is_reserved", rs.getBoolean("is_reserved"));
                    seatList.add(seat);
                }
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return seatList;
	}

	// 주어진 정보들로 상영 정보, 좌석 정보, userId를 가져와서 Ticket 테이블에 저장하는 함수
	public boolean insertTicket(int user_id, int theater_id, int movie_id, int row_num, int col_num, String time, String date) {
		// screening_id를 얻기 위한 sql문
		String screeningSql = "SELECT screening_id FROM `Screening` WHERE theater_id=? AND movie_id=? AND screening_date=? AND DATE_FORMAT(start_time, '%H:%i') = ?";
		
		// seat_id를 얻기 위한 sql문
		String seatIdSql = "SELECT seat_id FROM `Seat` WHERE screening_id=? AND row_num=? AND col_num=? AND is_reserved=FALSE";
		
		
		// Ticket을 추가하기 위한 sql문
		String ticketSql = "INSERT INTO Ticket(user_id, screening_id, seat_id) VALUES (?, ?, ?)"; 
		
		// Seat 좌석 예약 여부 최신화를 위한 sql문
		String seatSql = "UPDATE Seat SET is_reserved = TRUE WHERE seat_id = ?";
		
		try {
			Connection con = DBUtil.getConnection();
			PreparedStatement pstmt = con.prepareStatement(screeningSql);
			
			// 1. screening_id 얻기
			pstmt.setInt(1, theater_id);
			pstmt.setInt(2, movie_id);
			pstmt.setString(3, date);
			pstmt.setString(4, time);
			
			ResultSet rs = pstmt.executeQuery();
			int screening_id = -1;
			if(rs.next()) {
				screening_id = rs.getInt("screening_id");
				
				// 2. seat_id 얻기
				pstmt = con.prepareStatement(seatIdSql);
				pstmt.setInt(1, screening_id);
				pstmt.setInt(2, row_num);
				pstmt.setInt(3, col_num);
				rs = pstmt.executeQuery();
				if(rs.next()) { // 만약 해당 좌석이 있다면, seat_id를 얻어온다
					int seat_id = rs.getInt("seat_id");
					
					// 3. screening_id, user_id, seat_id로 ticket table에 데이터 추가하기
					pstmt = con.prepareStatement(ticketSql);
					pstmt.setInt(1, user_id);
					pstmt.setInt(2, screening_id);
					pstmt.setInt(3, seat_id);
					pstmt.executeUpdate();
					
					// 4. 해당 좌석 예약 여부를 TRUE로 최신화한다.
					pstmt = con.prepareStatement(seatSql);
					pstmt.setInt(1, seat_id);
					pstmt.executeUpdate();					
				} else {
					System.out.println("해당 좌석은 없거나 이미 예약된 좌석입니다!");
					return false;
				}
			} else {
				System.out.println("TicketRepository/insertTicket() -> 상영 id 얻기 실패");
				return false;
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	
}

