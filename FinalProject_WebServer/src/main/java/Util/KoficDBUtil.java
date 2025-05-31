package Util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URI;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

import kr.or.kobis.kobisopenapi.consumer.rest.KobisOpenAPIRestService;
import kr.or.kobis.kobisopenapi.consumer.rest.exception.OpenAPIFault;

public class KoficDBUtil {
	// kofic의 api key
	private static final String key = "4efdb259b1c88d86ae23dac5306f08a5"; 
// private String targetDt;  // 조회하고자 하는 날짜. (양식 : yyyymmdd)
//	private String itemPerPage; // 결과 row의 개수를 지정 (default:10, 최대:100)
//	private String multiMovieYn; // 다양성 영화/상업 영화를 구분지어 조회할 수 있음 (Y:다양성 영화, N:상업영화)(default: 전체)
//	private String repNationCd; // 한국/외국 영화별로 조회할 수 있습니다. "K" : 한국영화 "F" 외국영화 (default: 전체)
//	private String WideAreaCd; // 상영지역별로 조회할 수 있으며, 지역코드는 공통코드 조회 서비스에서 "0105000000"로서 조회된 지역코드입니다. (default: 전체)
//		
	
	public KoficDBUtil() throws IOException {
		updateMoviesDB();
	}
	
	// 일정 기간마다 내 DB서버의 DailyBoxOffice 테이블의 데이터들을 kofic api의 데이터를 이용하여 업데이트 해주는 함수
	public static void updateDailyBoxOfficeDB() {
		// 현재 날짜 구하기
		LocalDate now = LocalDate.now();
		// 포맷 정의
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
		// 현재 날짜에 포맷 적용
		String formatedNow = now.format(formatter);
		
		
		// 현재 시간/날짜를 입력받아서 날짜별 순위를 확인해야한다.
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("targetDt", formatedNow);
		
		try {
			// 오픈 API 클라이언트 객체를 발급받은 key로 생성
			KobisOpenAPIRestService kobisRS = new KobisOpenAPIRestService(key);
			System.out.println("KobisOpenApi 가져옴");
			
			
			// - getDailyBoxOffice: (박스오피스)일별 박스오피스
			// - getWeeklyBoxOffice: (박스오피스)주간/주말 박스오피스
			// - getComCodeList: (공통코드)공통코드 조회
			// KobisOpenAPIRestService.getDailyBoxOffice(boolean isJson, String targetDt, String itemPerPage,String multiMovieYn, String repNationCd, String wideAreaCd)
				// 	ㄴ> isJson 값이 true면 String을 json 형태로, false면 xml 타입으로 반환

			// json 방식으로 데이터를 불러온다
			String jsonResponse = kobisRS.getDailyBoxOffice(true, paramMap);
			System.out.println("getDailyBoxOffice 성공");
			
			// ObjectMapper 객체를 사용해서 json 방식의 데이터를 handling한다.
			ObjectMapper mapper = new ObjectMapper();
			HashMap<String, Object> dailyResult = mapper.readValue(jsonResponse, HashMap.class);
			System.out.println("mapper로 json 읽기 성공");
			
			// boxOfficeResult 내부에서 리스트 추출
			Map<String, Object> boxOfficeResult = (Map<String, Object>) dailyResult.get("boxOfficeResult");
			if (boxOfficeResult == null) {
			    System.out.println("boxOfficeResult가 응답에 없습니다.");
			    return;
			}
			
			// json 데이터 중 "dailyBoxOfficeList" 에 저장되어있는 영화들을 리스트로 가져온다.
			List<HashMap<String, Object>> dailyBoxOfficeList = (List<HashMap<String, Object>>)boxOfficeResult.get("dailyBoxOfficeList");
			if (dailyBoxOfficeList == null) {
			    System.out.println("KOBIS 응답에 dailyBoxOfficeList가 존재하지 않습니다.");
			    return; // 혹은 에러 처리
			}
			
			// DB에 연동하여 DailyBoxOffice 테이블을 업데이트 해준다.
			Connection con = DBUtil.getConnection();
			// 우선 기존 하루 박스오피스 내용을 전부 삭제한다.
			String sql = "delete from DailyBoxOffice";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.executeUpdate();
			
			// 새롭게 해당 테이블에 불러온 박스오피스 데이터들을 저장한다. 
			sql = "INSERT INTO `DailyBoxOffice`(movie_title, movie_rank, target_date) VALUES(?,?,?)";
			pstmt = con.prepareStatement(sql);
			for(int i=0; i<dailyBoxOfficeList.size(); ++i) {
				HashMap<String, Object> dailyBoxOffice = dailyBoxOfficeList.get(i);
				
				pstmt.setString(1, (String)dailyBoxOffice.get("movieNm"));
				pstmt.setString(2, (String)dailyBoxOffice.get("rank"));
				String openDt = (String) dailyBoxOffice.get("openDt");
				if (openDt.matches("\\d{4}-\\d{2}-\\d{2}")) {
				    // 이미 yyyy-MM-dd 형식이면 바로 저장
				    pstmt.setDate(3, java.sql.Date.valueOf(openDt));
				} else if (openDt.matches("\\d{8}")) {
				    // yyyyMMdd → yyyy-MM-dd로 변환
				    String formattedDate = openDt.substring(0, 4) + "-" + openDt.substring(4, 6) + "-" + openDt.substring(6);
				    pstmt.setDate(3, java.sql.Date.valueOf(formattedDate));
				} else {
				    System.out.println("잘못된 날짜 형식: " + openDt);
				    pstmt.setNull(3, java.sql.Types.DATE);
				}

				pstmt.executeUpdate();
			}
			
			System.out.println("KoficDBUtil/updateDailyBoxOfficeDB()를 성공적으로 수행했습니다.");
		} catch (OpenAPIFault e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	// DB의 Movie 테이블의 데이터들을 업데이트 해주는 함수 (100개의 영화 정보를 가져와서 기존 데베에 없던 영화가 있으면 추가해준다)
	public void updateMoviesDB() throws IOException {
		// 1. API URL 구성
		// http url 직접 호출 방식으로 searchMovieList를 사용해야함
		String apiUrl = "http://www.kobis.or.kr/kobisopenapi/webservice/rest/movie/searchMovieList.json"
		           + "?key=" + key
		           + "&curPage=2"
		           + "&itemPerPage=100";
		

		// 2. HTTP 연결 설정 및 요청
		// HttpURLConnection 객체를 생성하고 GET 방식으로 요청 보냄
		try {
			URI uri = URI.create(apiUrl); // 문자열 -> URI
			URL url = uri.toURL(); // URI -> URL
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			
			// 3. 응답 처리
			// 응답 코드가 200~300이면 정상이다 -> InputStream에서 응답 본문을 읽는다.
			BufferedReader rd;
			if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
			    rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			} else {
			    rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
			}
			
			// 4. 응답 json 문자열로 읽기
			// 응답을 한줄씩 읽어서 StringBuilder로 합친다 (최종 json 문자열 완성하는 것)
			StringBuilder sb = new StringBuilder();
			String line;
			while ((line = rd.readLine()) != null) {
			    sb.append(line);
			}
			rd.close();
			conn.disconnect();

			// 5. json 파싱
			// ObjectMapper 사용
			// ObjectMapper로 json 형태에 저장되어있는 영화 정보 리스트 가져옴
			ObjectMapper mapper = new ObjectMapper();
			Map<String, Object> result = mapper.readValue(sb.toString(), Map.class);
			Map<String, Object> movieListResult = (Map<String, Object>)result.get("movieListResult");
			List<Map<String, Object>> movieList = (List<Map<String, Object>>) movieListResult.get("movieList");
			
			
			// 6. 영화 정보 중 "이름, 장르, 상영시간" DB에 넣어주면서 반복 처리
			// ! 상영시간 불러올 수 없음
			Connection con = DBUtil.getConnection();
			
			// 해당 영화 데이터가 현재 Movie Table에 존재하는지 확인하기 위한 sql문 
			String checkSql = "SELECT COUNT(*) FROM Movie WHERE title = ?";
			PreparedStatement checkStmt = con.prepareStatement(checkSql);
			ResultSet rs;
			
			
			// 해당 영화 데이터를 Movie Table에 넣기 위한 sql문
			String sql = "INSERT INTO `Movie`(title, genre, duration) VALUES (?, ?, ?)";
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
					movieInfo = getMovieInfo((String)movieData.get("movieCd"));
					String movieNm = (String)movieInfo.get("movieNm");
					List<Map<String,String>> genres = (List<Map<String, String>>)movieInfo.get("genres");
					String showTmStr = (String)movieInfo.get("showTm");
					Integer showTm = 0;
							
					// 영화 이름, 장르, 상영 시간 설정
					pstmt.setString(1, movieNm);
					pstmt.setString(2, genres.get(0).get("genreNm"));
					if(showTmStr !=null && !showTmStr.isEmpty()) { // 상영시간이 데이터에 없는 경우, 해당 데이터에 null값을 넣는 설정을 해줌
						showTm = Integer.parseInt((String)movieInfo.get("showTm"));
						pstmt.setInt(3, showTm);						
					} else {
						pstmt.setNull(3, java.sql.Types.INTEGER);
					}
					
					pstmt.executeUpdate();
				} 
				else { // 2-2) 해당 영화가 현재 Movie table에 없는 경우, table에 추가하지 않고 넘긴다.
					System.out.println("KoficDBUtil/updateMoviesDB() 이미 존재하는 영화입니다: " + title);
					continue;
				}
				
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	// 건네받은 영화 코드로 해당 영화의 상세정보를 불러오는 코드.
	private Map<String, Object> getMovieInfo(String movieCd){
		// 1. API URL 구성
		// http url 직접 호출 방식으로 searchMovieInfo를 사용해야함
		String apiUrl = "http://www.kobis.or.kr/kobisopenapi/webservice/rest/movie/searchMovieInfo.json"
		           + "?key=" + key
		           + "&movieCd="+movieCd;
		

		// 2. HTTP 연결 설정 및 요청
		// HttpURLConnection 객체를 생성하고 GET 방식으로 요청 보냄
		try {
			URI uri = URI.create(apiUrl); // 문자열 -> URI
			URL url = uri.toURL(); // URI -> URL
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			
			// 3. 응답 처리
			// 응답 코드가 200~300이면 정상이다 -> InputStream에서 응답 본문을 읽는다.
			BufferedReader rd;
			if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
			    rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			} else {
			    rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
			}
			
			// 4. 응답 json 문자열로 읽기
			// 응답을 한줄씩 읽어서 StringBuilder로 합친다 (최종 json 문자열 완성하는 것)
			StringBuilder sb = new StringBuilder();
			String line;
			while ((line = rd.readLine()) != null) {
			    sb.append(line);
			}
			rd.close();
			conn.disconnect();

			// 5. json 파싱
			// ObjectMapper 사용
			// ObjectMapper로 json 형태에 저장되어있는 영화 정보 리스트 가져옴
			ObjectMapper mapper = new ObjectMapper();
			Map<String, Object> result = mapper.readValue(sb.toString(), Map.class);
			Map<String, Object> movieInfoResult = (Map<String, Object>)result.get("movieInfoResult");
			Map<String, Object> movieInfo = (Map<String, Object>) movieInfoResult.get("movieInfo");
			return movieInfo;
		} catch (MalformedURLException e) {
			e.printStackTrace();
			return null;
		} catch (ProtocolException e) {
			e.printStackTrace();
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
