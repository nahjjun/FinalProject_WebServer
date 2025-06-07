package Repository;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Dto.ReviewRegisterDto;
import Util.DBUtil;

public class ReviewRepository {

	public ReviewRepository() {
		// TODO Auto-generated constructor stub
	}

	// review를 DB에 등록하는 함수
	public int registerReviewDB(ReviewRegisterDto dto) {
		String sql = "INSERT INTO `Review`(user_id, movie_id, context, rating) VALUES(?, ?, ?, ?)";
		
		try {
			Connection con = DBUtil.getConnection();
			PreparedStatement pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pstmt.setInt(1, dto.getUser_id());
			pstmt.setInt(2, dto.getMovie_id());
			pstmt.setString(3, dto.getContext());
			pstmt.setInt(4, dto.getRating());
			
			pstmt.executeUpdate();
			
			ResultSet rs = pstmt.getGeneratedKeys();
			if (rs.next()) {
	            return rs.getInt(1);  // 생성된 review_id
	        }
			
			return -1;
		} catch(Exception e) {
			e.printStackTrace();
			return -1;
		}

	}
	
	// DB로부터 해당 영화의 데이터들을 가져오는 함수
	public Map<String, Object> getMovieInfo(int movie_id){
		String sql = "SELECT * FROM `Movie` WHERE movie_id=?";
		Map<String, Object> result = new HashMap<String, Object>();
		
		try {
			Connection con = DBUtil.getConnection();
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, movie_id);
			
			ResultSet rs = pstmt.executeQuery();
			
			// 해당 정보를 찾았다면 result Map에 정보를 넣고 반환, 아니면 null 반환
			if(rs.next()) {
				String title = rs.getString("title");
				String genre = rs.getString("genre");
				int duration = rs.getInt("duration");
				String description = rs.getString("description");
				String poster_url = rs.getString("poster_url");
				int review_point = rs.getInt("review_point");
				
				result.put("movie_id", movie_id);
				result.put("title", title);
				result.put("genre", genre);
				result.put("duration", duration);
				result.put("description", description);
				result.put("poster_url", poster_url);
				result.put("review_point", review_point);
				
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
	
	// 리뷰 데이터들을 불러오는 함수
	public List<Map<String, Object>> getReviewInfoList(int movie_id){
		String sql = "SELECT r.review_id, r.context, r.rating, r.review_date, r.like_count, r.unlike_count, u.name, u.class "
				+ "FROM `Review` r "
				+ "JOIN `User` u "
				+ "ON r.user_id = u.user_id "
				+ "WHERE movie_id=?";
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		
		try {
			Connection con = DBUtil.getConnection();
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, movie_id);
			
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
	
	// review_id로 해당 리뷰를 지우는 함수
	public boolean deleteReview(int review_id) {
		String sql = "DELETE FROM `Review` WHERE review_id=?";
		
		try {
			Connection con = DBUtil.getConnection();
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, review_id);
			
			pstmt.executeUpdate();
			
			pstmt.close();
			con.close();
			return true;
		} catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	
	// ----------------리뷰 리액션 ---------------- //
	// 새로운 리뷰 등록 시, 해당 리뷰에 1대1 연동되는 리뷰 리엑션 데이터를 만들어주는 함수
	public boolean createReviewReaction(int user_id, int review_id) {
		String sql = "INSERT INTO `ReviewReaction`(user_id, review_id) VALUES(?, ?)";
		
		try {
			Connection con = DBUtil.getConnection();
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, user_id);
			pstmt.setInt(2, review_id);
			
			pstmt.executeUpdate();
			
			pstmt.close();
			con.close();
			return true;
		} catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	
	// 해당 리뷰의 리액션 정보를 가져오는 함수
	public Map<String, Object> getReviewReactionInfo(int user_id, int review_id){
		String sql = "SELECT reviewReaction_id, reactionType FROM `ReviewReaction` WHERE user_id=? AND review_id=?";
		Map<String, Object> result = new HashMap<String, Object>();
		
		try {
			Connection con = DBUtil.getConnection();
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, user_id);
			pstmt.setInt(2, review_id);
			
			ResultSet rs = pstmt.executeQuery();
			
			// 해당 정보를 찾았다면 result Map에 정보를 넣고 반환, 아니면 null 반환
			if(rs.next()) {
				int reviewReaction_id = rs.getInt("reviewReaction_id");
				int reactionType = rs.getInt("reactionType");
				
				result.put("reviewReaction_id", reviewReaction_id);
				result.put("reactionType", reactionType);
			
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
	
	// 리뷰 리액션 - 좋아요 증가/감소 함수
	// type  ->  1: 좋아요 증가 / 2: 좋아요 감소
	// reactionTyp  ->  0: 무상태 / 1: 좋아요 상태 / 2: 싫어요 상태
	public void setReviewLikeInfo(int reviewReaction_id, int review_id, int type) {		
		try {
			// 현재 좋아요 개수 가져오기
			String sql = "SELECT like_count FROM `Review` WHERE review_id=?";			
			Connection con = DBUtil.getConnection();
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, review_id);
			
			ResultSet rs = pstmt.executeQuery();
		
			// 해당 리액션을 찾은 경우, 현재 리액션 타입을 검사해서 좋아요 증감을 처리한다.
			if(rs.next()) {
				int like_count = rs.getInt("like_count");
				
				if(type==1) { // 좋아요 선택
					// 현재 좋아요 개수 증가 시키기
					like_count++;
					sql = "UPDATE `Review` SET like_count=? WHERE review_id=?";
					pstmt = con.prepareStatement(sql);	
					pstmt.setInt(1, like_count);
					pstmt.setInt(2, review_id);
					pstmt.executeUpdate();
					
					// reaction table의 reactionType "1(좋아요)"로 변환시키기
					sql = "UPDATE `ReviewReaction` SET reactionType=? WHERE reviewReaction_id=?";
					pstmt = con.prepareStatement(sql);
					pstmt.setInt(1, 1);
					pstmt.setInt(2, reviewReaction_id);
					pstmt.executeUpdate();
					
				} else { // 좋아요 취소
					// 현재 좋아요 개수 감소 시키기
					like_count--;
					sql = "UPDATE `Review` SET like_count=? WHERE review_id=?";
					pstmt = con.prepareStatement(sql);	
					pstmt.setInt(1, like_count);
					pstmt.setInt(2, review_id);
					pstmt.executeUpdate();
					
					// reaction table의 reactionType "0(무상태)"로 변환시키기
					sql = "UPDATE `ReviewReaction` SET reactionType=? WHERE reviewReaction_id=?";
					pstmt = con.prepareStatement(sql);
					pstmt.setInt(1, 0);
					pstmt.setInt(2, reviewReaction_id);
					pstmt.executeUpdate();
					
				}
				

				
			}
		}catch(Exception e) {
			e.printStackTrace();
		}	
	}
	
	// 리뷰 리액션 - 싫어요 증가/감소 함수
	// type  ->  1: 싫어요 증가 / 2: 싫어요 감소
	public void setReviewUnlikeInfo(int reviewReaction_id, int review_id, int type) {
		try {
			// 현재 좋아요 개수 가져오기
			String sql = "SELECT unlike_count FROM `Review` WHERE review_id=?";			
			Connection con = DBUtil.getConnection();
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, review_id);
			
			ResultSet rs = pstmt.executeQuery();
		
			// 해당 리액션을 찾은 경우, 현재 리액션 타입을 검사해서 좋아요 증감을 처리한다.
			if(rs.next()) {
				int unlike_count = rs.getInt("unlike_count");
				
				if(type==1) { // 싫어요 선택
					// 현재 싫어요 개수 증가 시키기
					unlike_count++;
					sql = "UPDATE `Review` SET unlike_count=? WHERE review_id=?";
					pstmt = con.prepareStatement(sql);	
					pstmt.setInt(1, unlike_count);
					pstmt.setInt(2, review_id);
					pstmt.executeUpdate();
					
					// reaction table의 reactionType "2(싫어요)"로 변환시키기
					sql = "UPDATE `ReviewReaction` SET reactionType=? WHERE reviewReaction_id=?";
					pstmt = con.prepareStatement(sql);
					pstmt.setInt(1, 2);
					pstmt.setInt(2, reviewReaction_id);
					pstmt.executeUpdate();
					
				} else { // 싫어요 취소
					// 현재 싫어요 개수 감소 시키기
					unlike_count--;
					sql = "UPDATE `Review` SET unlike_count=? WHERE review_id=?";
					pstmt = con.prepareStatement(sql);	
					pstmt.setInt(1, unlike_count);
					pstmt.setInt(2, review_id);
					pstmt.executeUpdate();
					
					// reaction table의 reactionType "0(무상태)"로 변환시키기
					sql = "UPDATE `ReviewReaction` SET reactionType=? WHERE reviewReaction_id=?";
					pstmt = con.prepareStatement(sql);
					pstmt.setInt(1, 0);
					pstmt.setInt(2, reviewReaction_id);
					pstmt.executeUpdate();
					
				}

			}
		}catch(Exception e) {
			e.printStackTrace();
		}	
	}
}
