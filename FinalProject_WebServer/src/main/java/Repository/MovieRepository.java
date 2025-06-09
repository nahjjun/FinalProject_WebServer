package Repository;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
	public Map<String, Object> getMovieInfo(MovieDto dto){
		String sql = "SELECT * FROM `Movie` WHERE movie_id=?";
		Map<String, Object> result = new HashMap<String, Object>();
		
		try {
			Connection con = DBUtil.getConnection();
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, dto.getMovieId());
			
			ResultSet rs = pstmt.executeQuery();
			
			// 해당 정보를 찾았다면 result Map에 정보를 넣고 반환, 아니면 null 반환
			if(rs.next()) {
				int movie_id = rs.getInt("movie_id");
				String title = rs.getString("title");
				String genre = rs.getString("genre");
				int duration = rs.getInt("duration");
				String description = rs.getString("description");
				String poster_url = rs.getString("poster_url");
				int review_point = rs.getInt("review_point");
				String directors = rs.getString("directors");
				String actors = rs.getString("actors");
				Date d = rs.getDate("target_date");
				String date=null;
				if(d == null) {
					System.out.println("개봉일 정보가 null입니다.");
					date = "개봉날짜 미정";
				} else {
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy년 MM월 dd일");
					 date = sdf.format(d);	
				}
				
				result.put("movie_id", movie_id);
				result.put("title", title);
				result.put("genre", genre);
				result.put("duration", duration);
				result.put("description", description);
				result.put("poster_url", poster_url);
				result.put("review_point", review_point);
				result.put("directors", directors);
				result.put("actors", actors);
				result.put("date", date);
				
				rs.close();
				pstmt.close();
				con.close();
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
		String sql = "SELECT d.movie_title, d.movie_rank, d.target_date, m.movie_id, m.poster_url, m.review_point "
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
				int movie_id = rs.getInt("movie_id");
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
				
				map.put("movie_id", movie_id);
				map.put("title", title);
				map.put("date", date);
				map.put("rank", rank);
				map.put("poster_url", poster_url);
				map.put("review_point", review_point);
				result.add(map);
			}
			rs.close();
			pstmt.close();
			con.close();
			return result; 
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	// 영화 정보 리스트를 가져오는 함수
	public List<Map<String, Object>> getMovieInfoList(){
		String sql = "SELECT movie_id, title, poster_url, poster_url, target_date FROM `Movie`";
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		
		try {
			Connection con = DBUtil.getConnection();
			PreparedStatement pstmt = con.prepareStatement(sql);
			
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				HashMap<String, Object> map = new HashMap<String, Object>();
				String movie_id = rs.getString("movie_id");
				String title = rs.getString("title");
				String poster_url = rs.getString("poster_url");
				int review_point = rs.getInt("review_point");
				Date d = rs.getDate("target_date");
				String date=null;
				if(d == null) {
					System.out.println("개봉일 정보가 null입니다.");
					date = "개봉날짜 미정";
				} else {
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy년 MM월 dd일");
					 date = sdf.format(d);	
				}
				
				
				map.put("movie_id", movie_id);
				map.put("title", title);
				map.put("poster_url", poster_url);
				map.put("review_point", review_point);
				map.put("date", date);
				
				result.add(map);
			}
			rs.close();
			pstmt.close();
			con.close();
			return result;
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	// 리뷰 데이터들을 불러오는 함수
	public List<Map<String, Object>> getReviewInfoList(MovieDto dto){
		String sql = "SELECT r.review_id, r.context, r.rating, r.review_date, r.like_count, r.unlike_count, u.name, u.class "
				+ "FROM `Review` r "
				+ "JOIN User u "
				+ "ON r.user_id = u.user_id "
				+ "WHERE movie_id=?";
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		
		try {
			Connection con = DBUtil.getConnection();
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, dto.getMovieId());
			
			ResultSet rs = pstmt.executeQuery();
			
			// 해당 정보를 찾았다면 result Map에 정보를 넣고 반환, 아니면 null 반환
			while(rs.next()) {
				Map<String, Object> reviewInfo = new HashMap<String, Object>();
				int review_id = rs.getInt("review_id");
				String context = rs.getString("context");
				int rating = rs.getInt("rating");
				Date review_date = rs.getDate("review_date");
				int like_count = rs.getInt("like_count");
				int unlike_count = rs.getInt("unlike_count");
				String name = rs.getString("name");
				String user_class = rs.getString("class");
				
				reviewInfo.put("review_id", review_id);
				reviewInfo.put("context", context);
				reviewInfo.put("rating", rating);
				reviewInfo.put("review_date", review_date);
				reviewInfo.put("like_count", like_count);
				reviewInfo.put("unlike_count", unlike_count);
				reviewInfo.put("name", name);
				reviewInfo.put("user_class", user_class);
				
				result.add(reviewInfo);
			}
			
			// 모든 리뷰들을 가져온 이후, 해당 리뷰를 review_date값 순서대로 정렬한다. (글 등록 시기가 빠른 순서대로)
			if (!result.isEmpty()) {
			    Collections.sort(result, new Comparator<Map<String, Object>>() {
			        @Override
			        public int compare(Map<String, Object> r1, Map<String, Object> r2) {
			            Date d1 = (Date) r1.get("review_date");
			            Date d2 = (Date) r2.get("review_date");
			            return d1.compareTo(d2); // 오름차순 정렬
			        }
			    });
			}
			rs.close();
			pstmt.close();
			con.close();
			return result;
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
