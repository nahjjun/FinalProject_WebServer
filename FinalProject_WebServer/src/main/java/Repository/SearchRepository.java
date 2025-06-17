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

import Util.DBUtil;

public class SearchRepository {
	public SearchRepository() {
	}

	public List<Map<String, Object>> getSearchInfoList(String searchQuery){
		String sql = "SELECT movie_id, title, poster_url, review_point, target_date FROM `Movie` WHERE title LIKE ?";
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>(); 
		try {
			Connection con = DBUtil.getConnection();
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, searchQuery);
			
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				Map<String, Object> map = new HashMap<String, Object>();
				String movie_id = rs.getString("movie_id");
				String title = rs.getString("title");
				String poster_url = rs.getString("poster_url");
				String review_point = rs.getString("review_point");
				String dateStr = rs.getString("target_date");
				Date date=null;
				if (dateStr != null && !dateStr.trim().isEmpty()) {
				    date = Date.valueOf(dateStr);  // yyyy-MM-dd 형식만 허용
				}
				String target_date=null;
				if(date == null) {
					System.out.println("SearchRepository/getSearchInfoList() -> 개봉일 정보가 null입니다.");
					target_date = "개봉날짜 미정";
				} else {
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy년 MM월 dd일");
					target_date = sdf.format(date);	
				}
				
				map.put("movie_id", movie_id);
				map.put("title", title);
				map.put("poster_url", poster_url);
				map.put("review_point", review_point);
				map.put("date", target_date);
				
				result.add(map);
			}
			return result;
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
		
		
	}
}
