package Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
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
}
