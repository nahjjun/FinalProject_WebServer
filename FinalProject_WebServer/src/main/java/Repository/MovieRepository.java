package Repository;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Dto.MovieDto;
import Util.DBUtil;

public class MovieRepository {

	public MovieRepository() {
		// TODO Auto-generated constructor stub
	}

	// DB로부터 해당 영화의 데이터들을 가져오는 함수
	public Map<String, Object> getMovieInfo(MovieDto movieDto){
		String sql = "SELECT * FROM `Movie` WHERE title=?";
		Map<String, Object> result = new HashMap<String, Object>();
		
		try {
			Connection con = DBUtil.getConnection();
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, movieDto.getTitle());
			
			ResultSet rs = pstmt.executeQuery();
			
			// 해당 정보를 찾았다면 result Map에 정보를 넣고 반환, 아니면 null 반환
			if(rs.next()) {
				String title = rs.getString("title");
				String genre = rs.getString("genre");
				int duration = rs.getInt("duration");
				String description = rs.getString("description");
				String poster_url = rs.getString("poster_url");
				int review_point = rs.getInt("review_point");
				
				result.put("title", title);
				result.put("genre", genre);
				result.put("duration", duration);
				result.put("description", description);
				result.put("poster_url", poster_url);
				result.put("review_point", review_point);
				
				return result;
			}
			else return null; 
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	// 영화 박스오피스 리스트를 가져오는 함수
	public List<Map<String, Object>> getBoxOfficeList(){
		// 두 테이블을 조인하여 포스터 url 정보까지 가져온다.
		String sql = "SELECT d.movie_title, d.movie_rank, d.target_date, m.poster_url, m.review_point "
				+ "FROM DailyBoxOffice d "
				+ "JOIN Movie m "
				+ "ON d.movie_title=m.title";
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		
		try {
			Connection con = DBUtil.getConnection();
			PreparedStatement pstmt = con.prepareStatement(sql);
			
			ResultSet rs = pstmt.executeQuery();
			
			// 해당 정보를 찾았다면 result Map에 정보를 넣고 반환, 아니면 null 반환
			while(rs.next()) {
				String title = rs.getString("movie_title");
				int rank = rs.getInt("movie_rank");
				Date d = rs.getDate("target_date");
				String date=null;
				if(d == null) {
					System.out.println("개봉일 정보가 null입니다.");
					date = "개봉날짜 미정";
				} else {
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy년 MM월 dd일");
					 date = sdf.format(d);	
				}
				
				String poster_url = rs.getString("poster_url");
		        int review_point = rs.getInt("review_point");
				
				Map<String, Object> map = new HashMap<String, Object>();
				
				map.put("title", title);
				map.put("date", date);
				map.put("rank", rank);
				map.put("poster_url", poster_url);
				map.put("review_point", review_point);
				result.add(map);
			}
			
			return result; 
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	// 영화 박스오피스 리스트를 가져오는 함수
	public List<Map<String, Object>> getMovieInfoList(){
		String sql = "SELECT * FROM `Movie`";
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		
		try {
			Connection con = DBUtil.getConnection();
			PreparedStatement pstmt = con.prepareStatement(sql);
			
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				HashMap<String, Object> map = new HashMap<String, Object>(); 
				String title = rs.getString("title");
				String genre = rs.getString("genre");
				int duration = rs.getInt("duration");
				String description = rs.getString("description");
				String poster_url = rs.getString("poster_url");
				int review_point = rs.getInt("review_point");
				
				map.put("title", title);
				map.put("genre", genre);
				map.put("duration", duration);
				map.put("description", description);
				map.put("poster_url", poster_url);
				map.put("review_point", review_point);
				
				result.add(map);
			}
			return result;
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
