package Util;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DBUtil {
   private static String url = "jdbc:mysql://13.209.7.8:3306/webserver?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Seoul";
   private static String id = "webuser";
   private static String password = "webuser123!";
   private static String driver = "com.mysql.cj.jdbc.Driver";

   public static Connection getConnection() throws SQLException {
      Connection con = null;

      try {
         Class.forName(driver);
         con = DriverManager.getConnection(url, id, password);
      } catch (ClassNotFoundException var2) {
         var2.printStackTrace();
      }

      return con;
   }
   
	// 내 DB서버의 DailyBoxOffice 테이블의 데이터들을 kofic api의 데이터를 이용하여 업데이트 해주는 함수
   public static void updateDailyBoxOfficeDB() {
	// DB에 연동하여 DailyBoxOffice 테이블을 업데이트 해준다.
	   List<HashMap<String, Object>> dailyBoxOfficeList = KoficAPIUtil.getDailyBoxOfficeList();
	   try {
			Connection con = DBUtil.getConnection();
			// 우선 기존 하루 박스오피스 내용을 전부 삭제한다.
			String sql = "delete from DailyBoxOffice";
			PreparedStatement boxStmt = con.prepareStatement(sql);
			boxStmt.executeUpdate();
			
			// 새롭게 해당 테이블에 불러온 박스오피스 데이터들을 저장한다. 
			sql = "INSERT INTO `DailyBoxOffice`(movie_title, movie_rank, target_date) VALUES(?,?,?)";
			boxStmt = con.prepareStatement(sql);
			
			// DailyBoxOffice 테이블 저장 직후, Movie 테이블 업데이트를 위한 준비
			String checkSql = "SELECT COUNT(*) FROM `Movie` WHERE title = ?";
			PreparedStatement checkStmt = con.prepareStatement(checkSql);

			String insertSql = "INSERT INTO `Movie`(title, genre, duration, directors, actors, target_date, description, poster_url) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement insertStmt = con.prepareStatement(insertSql);
			
			for(int i=0; i<dailyBoxOfficeList.size(); ++i) {
				HashMap<String, Object> dailyBoxOffice = dailyBoxOfficeList.get(i);

				// DailyBoxOffice 테이블 최신화
				boxStmt.setString(1, (String)dailyBoxOffice.get("movieNm"));
				boxStmt.setString(2, (String)dailyBoxOffice.get("rank"));
				String openDt = (String) dailyBoxOffice.get("openDt");
				if (openDt.matches("\\d{4}-\\d{2}-\\d{2}")) {
				    // 이미 yyyy-MM-dd 형식이면 바로 저장
				    boxStmt.setDate(3, java.sql.Date.valueOf(openDt));
				} else if (openDt.matches("\\d{8}")) {
				    // yyyyMMdd → yyyy-MM-dd로 변환
				    String formattedDate = openDt.substring(0, 4) + "-" + openDt.substring(4, 6) + "-" + openDt.substring(6);
				    boxStmt.setDate(3, java.sql.Date.valueOf(formattedDate));
				} else {
				    System.out.println("잘못된 날짜 형식: " + openDt);
				    boxStmt.setNull(3, java.sql.Types.DATE);
				}

				boxStmt.executeUpdate();
				
				 // Movie 테이블 중복 확인 후 없으면 추가
			    String movieTitle = (String) dailyBoxOffice.get("movieNm");
			    checkStmt.setString(1, movieTitle);
			    ResultSet rs = checkStmt.executeQuery();
			    int count = 0;
			    if (rs.next()) count = rs.getInt(1);
			    
			    System.out.print("영화 이름: " + dailyBoxOffice.get("movieNm"));
			    if (count == 0) { // Movie 테이블에 해당 영화가 없는 경우.
			    	System.out.println(" -> Movie에 없음. DB에 추가 ");
			        // 영화 코드로 상세정보를 가져오기 위해서는 movieCd 필요
			        String movieCd = (String) dailyBoxOffice.get("movieCd");
			        if (movieCd == null || movieCd.isEmpty()) {
			            System.out.println("movieCd 없음 → " + dailyBoxOffice.get("movieNm"));
			            continue;
			        }
			        Map<String, Object> movieInfo = KoficAPIUtil.getMovieInfo(movieCd);
			        if (movieInfo == null) {
			            System.out.println("movieInfo 조회 실패 → " + movieCd);
			            continue;
			        }
			        // 영화 이름, 장르, 상영시간, 감독, 배우, 개봉일
			        String movieNm = (String) movieInfo.get("movieNm");
		            List<Map<String, String>> genres = (List<Map<String, String>>) movieInfo.get("genres");
		            String showTmStr = (String) movieInfo.get("showTm");
		            List<Map<String, Object>> directors = (List<Map<String, Object>>) movieInfo.get("directors");
		            List<Map<String, Object>> actors = (List<Map<String, Object>>) movieInfo.get("actors");
		            
		            
		            // 1. 영화 이름
		            insertStmt.setString(1, movieNm);
		            // 2. 영화 장르
		            insertStmt.setString(2, genres.isEmpty() ? null : genres.get(0).get("genreNm"));
		            // 3. 영화 상영 시간
		            if (showTmStr != null && !showTmStr.isEmpty()) {
		                insertStmt.setInt(3, Integer.parseInt(showTmStr));
		            } else {
		                insertStmt.setNull(3, java.sql.Types.INTEGER);
		            }

		            // 4. 영화 감독들
		            StringBuilder directorNames = new StringBuilder();
		            for(Map<String, Object>director : directors) {
		            	directorNames.append((String)director.get("peopleNm") +"  ");
		            }
		            insertStmt.setString(4, directorNames.toString());
		            
		            
		            // 5. 영화 배우들
		            StringBuilder actorNames = new StringBuilder();
		            for(Map<String, Object>actor : actors) {
		            	actorNames.append((String)actor.get("peopleNm") +"  ");
		            }
		            insertStmt.setString(5, actorNames.toString());
		            
		            
		            // 6. 영화 개봉일
		            insertStmt.setString(6, openDt);
		            
		            
		            // 영화 설명, 포스터 url
		            String description=null, poster_url=null;
					// TmdbAPIUtil에서 영화 설명, 포스터 url 가져와서 설정
					movieInfo = TmdbAPIUtil.getMovieInfo(movieNm);
					if(movieInfo != null) {
						// 7. 영화 설명
						description = (String)movieInfo.get("overview"); 
						// 8. 영화 포스터 url
						poster_url = (String)movieInfo.get("poster_path");	
					}
					
					// 설명, 포스터 url 설정
					insertStmt.setString(7, description);
					insertStmt.setString(8, poster_url);
					
		            insertStmt.executeUpdate();
		            System.out.println("Movie 테이블에 추가된 영화: " + movieNm);
			    }
			    System.out.println(" -> Movie에 이미 있음.");
			}
		    System.out.println("updateDailyBoxOfficeDB() 종료.");
	   } catch(Exception e) {
		   e.printStackTrace();
	   }

   }
   
	// DB의 Movie 테이블의 데이터들을 업데이트 해주는 함수 (100개의 영화 정보를 가져와서 기존 데베에 없던 영화가 있으면 추가해준다)
   public static void updateMoviesDB() throws IOException {
		List<Map<String, Object>> movieList = KoficAPIUtil.getMovieInfoList();
		try {
			// 영화 정보 중 "이름, 장르, 상영시간, 설명, 포스터 url" DB에 넣어주면서 반복 처리
			Connection con = DBUtil.getConnection();
			
			// 해당 영화 데이터가 현재 Movie Table에 존재하는지 확인하기 위한 sql문 
			String checkSql = "SELECT COUNT(*) FROM Movie WHERE title = ?";
			PreparedStatement checkStmt = con.prepareStatement(checkSql);
			ResultSet rs;
			
			
			// 해당 영화 데이터를 Movie Table에 넣기 위한 sql문
			String sql = "INSERT INTO `Movie`(title, genre, duration, directors, actors, target_date, description, poster_url) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement pstmt = con.prepareStatement(sql);
			
			Map<String, Object> movieData=null, movieInfo=null;
			for(int i=0; i<movieList.size(); ++i) {
				// 해당 영화의 데이터를 가져옴
				movieData = movieList.get(i);
				String title = (String)movieData.get("movieNm");
				
				// 1) 해당 영화가 현재 Movie table에 있는지 title로 확인한다.
				checkStmt.setString(1, title);
				rs = checkStmt.executeQuery();
				int count=0;
				if(rs.next()) count = rs.getInt(1);
				
				// 2-1) 해당 영화가 현재 Movie table에 없는 경우, searchMovieInfo()를 활용해서 해당 영화의 세부정보를 가져와 table에 저장한다.
				if(count == 0) {
					movieInfo = KoficAPIUtil.getMovieInfo((String)movieData.get("movieCd"));
					// 1. 영화 이름
					String movieNm = (String)movieInfo.get("movieNm");
					pstmt.setString(1, movieNm);
					// 2. 영화 장르 리스트
					List<Map<String,String>> genres = (List<Map<String, String>>)movieInfo.get("genres");
					List<Map<String, Object>> directors = (List<Map<String, Object>>) movieInfo.get("directors");
		            List<Map<String, Object>> actors = (List<Map<String, Object>>) movieInfo.get("actors");
					// !!<< 장르에 따라 데베에 추가 X >>!! //
					boolean keep = true;
					for(int j=0; j<genres.size(); ++j) {
						if(genres.get(j).get("genreNm").equals("성인물(에로)")) {
							keep = false;
							break;
						}
					}
					if(!keep) continue;
					
					String genre = genres.isEmpty() ? null : genres.get(0).get("genreNm");
					pstmt.setString(2, genres.isEmpty() ? null : genre);
					
					
					// 3. 영화 상영 시간
					String showTmStr = (String)movieInfo.get("showTm");
					Integer showTm = 0;
					if(showTmStr !=null && !showTmStr.isEmpty()) { // 상영시간이 데이터에 없는 경우, 해당 데이터에 null값을 넣는 설정을 해줌
						showTm = Integer.parseInt((String)movieInfo.get("showTm"));
						pstmt.setInt(3, showTm);						
					} else {
						pstmt.setNull(3, java.sql.Types.INTEGER);
					}
						
					// 4. 감독들					
					StringBuilder directorNames = new StringBuilder();
		            for(Map<String, Object>director : directors) {
		            	directorNames.append((String)director.get("peopleNm") +"  ");
		            }
		            pstmt.setString(4, directorNames.toString());
		            
		            
		            // 5. 영화 배우들
		            StringBuilder actorNames = new StringBuilder();
		            for(Map<String, Object>actor : actors) {
		            	actorNames.append((String)actor.get("peopleNm") +"  ");
		            }
		            pstmt.setString(5, actorNames.toString());


					// 6. 상영 시간
		            String openDt = (String) movieInfo.get("openDt");
					if (openDt.matches("\\d{4}-\\d{2}-\\d{2}")) {
					    // 이미 yyyy-MM-dd 형식이면 바로 저장
						pstmt.setDate(6, java.sql.Date.valueOf(openDt));
					} else if (openDt.matches("\\d{8}")) {
					    // yyyyMMdd → yyyy-MM-dd로 변환
					    String formattedDate = openDt.substring(0, 4) + "-" + openDt.substring(4, 6) + "-" + openDt.substring(6);
					    pstmt.setDate(6, java.sql.Date.valueOf(formattedDate));
					} else {
					    System.out.println("잘못된 날짜 형식: " + openDt);
					    pstmt.setNull(6, java.sql.Types.DATE);
					}
					
					
					// TMDB
					String description=null, poster_url=null;
					// TmdbAPIUtil에서 영화 설명, 포스터 url 가져와서 설정
					movieInfo = TmdbAPIUtil.getMovieInfo(title);
					if(movieInfo != null) {
						// 7. 영화 설명
						description = (String)movieInfo.get("overview"); 
						// 8. 영화 포스터 url
						poster_url = (String)movieInfo.get("poster_path");	
					}
					
					// 설명, 포스터 url 설정
					pstmt.setString(7, description);
					pstmt.setString(8, poster_url);
					
					pstmt.executeUpdate();
				} 
				else { // 2-2) 해당 영화가 현재 Movie table에 없는 경우, table에 추가하지 않고 넘긴다.
					System.out.println("DBUtil/updateMoviesDB() 이미 존재하는 영화입니다: " + title);
					continue;
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
   
}
