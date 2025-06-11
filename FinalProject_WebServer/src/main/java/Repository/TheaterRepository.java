package Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Util.DBUtil;

public class TheaterRepository {

	public TheaterRepository() {
		// TODO Auto-generated constructor stub
	}

	
	// 영화관 이름을 토대로 해당 영화관의 상영 영화 리스트, 그리고 해당 영화의 상영정보들을 가져오는 함수
	public List<Map<String, Object>> getMovieInfoList(String theaterName, String screeningDate){
		List<Integer> movieIdList = null; // 영화 아이디 리스트를 반환하는 함수
		List<Map<String, Object>> movieInfoList = new ArrayList<Map<String, Object>>(); // 영화 정보 리스트
		String sql = "SELECT theater_id FROM `Theater` WHERE name=?";
		int theater_id=0; // 영화관 아이디
		
		try {
			Connection con = DBUtil.getConnection();
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, theaterName);
			ResultSet rs = pstmt.executeQuery();
			
			if(rs.next()) {
				theater_id = rs.getInt("theater_id");
			}
			// 영화관 아이디 리스트 가져오기
			movieIdList = getMovieIdList(theater_id);
			
			for(Integer movie_id : movieIdList) {
				Map<String, Object> movieInfo = new HashMap<String, Object>();
				// 1. 영화 기본 정보 (영화제목, 장르, 상영시간, 개봉날짜 가져오기) 
				sql = "SELECT title, genre, duration, target_date FROM `Movie` WHERE movie_id=?";
				pstmt = con.prepareStatement(sql);
				pstmt.setInt(1, movie_id);
				rs = pstmt.executeQuery();
				String title = null;
				String genre = null;
				String duration = null;
				String target_date = null;
				
				if(rs.next()) {
					title = rs.getString("title");
					genre = rs.getString("genre");
					duration = rs.getString("duration");
					target_date = rs.getString("target_date");
				}
				movieInfo.put("title", title);
				movieInfo.put("genre", genre);
				movieInfo.put("duration", duration);
				movieInfo.put("target_date", target_date);
				 
				// 2. 해당 극장의 해당 영화 상영 정보 가져오기
				
				// screeningInfoList 미리 삽입
				movieInfo.put("screeningInfoList", new ArrayList<Map<String,Object>>());
				
				// 상영 정보 하나에 들어가는 정보 : 시작 시간, 극장 번호, 언어, 포맷(2d/3d), 남은 좌석 개수 
					// 1) 상영 테이블에서 시작시간, 극장번호, 언어, 포맷 가져오기
				sql = "SELECT screening_id, start_time, room_number, language, format FROM `Screening` WHERE movie_id=? AND theater_id=? AND screening_date=?";
				pstmt = con.prepareStatement(sql);
				pstmt.setInt(1, movie_id);
				pstmt.setInt(2, theater_id);
				pstmt.setString(3, screeningDate);			
				
				rs = pstmt.executeQuery();
				// 2) 각 영화의 상영 정보들 (시간대별 상영 정보들)마다 남은 좌석들 현황을 가져온다.
				while(rs.next()) {
					Map<String, Object> screeningInfo = new HashMap<String, Object>();
					// 현재 있는 상영 정보 리스트를 가져온다. 
					List<Map<String, Object>> screeningInfoList = (List<Map<String, Object>>)movieInfo.get("screeningInfoList");
					
					int screening_id = rs.getInt("screening_id");
					String time = rs.getString("start_time");
					String start_time = time.substring(11, 16);
					String room_number = rs.getString("room_number");
					String language = rs.getString("language");
					String format = rs.getString("format");
					
					screeningInfo.put("start_time", start_time);
					screeningInfo.put("room_number", room_number);
					screeningInfo.put("language", language);
					screeningInfo.put("format", format);
					
					// 해당 screening_id에 해당하는 빈 좌석들의 개수를 가져온다.
					sql = "SELECT seat_id, is_reserved FROM `Seat` WHERE screening_id=?";
					pstmt = con.prepareStatement(sql);
					pstmt.setInt(1, screening_id);
					ResultSet rsTmp = pstmt.executeQuery();
					int remainSeat = 0;
					while(rsTmp.next()) {
						if(!rsTmp.getBoolean("is_reserved")) // 아직 예약되지 않은 경우, 남은 좌석을 증가시킴
							remainSeat++;
					}
					screeningInfo.put("remainSeat", remainSeat);
					
					// 상영 정보를 기존 상영 정보 리스트에 삽입한다.
					screeningInfoList.add(screeningInfo);
					movieInfo.put("screeningInfoList", screeningInfoList);
				}
				// 해당 영화의 상영 정보들이 
				movieInfoList.add(movieInfo);
			}
			
			return movieInfoList;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	// 현재 있는 극장 이름들을 return하는 함수
	public List<String> getTheaterNameList(){
		String sql = "SELECT theater_id, name FROM `Theater`";
		List<String> theaterNameList = new ArrayList<String>();
		try {
			Connection con = DBUtil.getConnection();
			PreparedStatement pstmt = con.prepareStatement(sql);
			
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				theaterNameList.add(rs.getString("name"));
			}
			
			return theaterNameList;
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	

	
	
	// 해당 영화관에서 상영하는 영화 id들 리스트를 가져오는 함수
	private List<Integer> getMovieIdList(int theater_id){
		String sql = "SELECT movie_id FROM `Screening` WHERE theater_id=?";
		List<Integer> movieIdList = new ArrayList<Integer>();
		try {
			Connection con = DBUtil.getConnection();
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, theater_id);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				movieIdList.add(rs.getInt("movie_id"));
			}
			return movieIdList;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
