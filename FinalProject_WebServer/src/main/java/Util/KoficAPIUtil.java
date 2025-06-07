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

public class KoficAPIUtil {
	// kofic의 api key
	private static final String key = "4efdb259b1c88d86ae23dac5306f08a5"; 
// private String targetDt;  // 조회하고자 하는 날짜. (양식 : yyyymmdd)
//	private String itemPerPage; // 결과 row의 개수를 지정 (default:10, 최대:100)
//	private String multiMovieYn; // 다양성 영화/상업 영화를 구분지어 조회할 수 있음 (Y:다양성 영화, N:상업영화)(default: 전체)
//	private String repNationCd; // 한국/외국 영화별로 조회할 수 있습니다. "K" : 한국영화 "F" 외국영화 (default: 전체)
//	private String WideAreaCd; // 상영지역별로 조회할 수 있으며, 지역코드는 공통코드 조회 서비스에서 "0105000000"로서 조회된 지역코드입니다. (default: 전체)
//		
	
	public KoficAPIUtil() throws IOException {
		
	}
	

	public static List<HashMap<String, Object>> getDailyBoxOfficeList() {
		// 현재 날짜 구하기
		LocalDate now = LocalDate.now().minusDays(1);
		// 포맷 정의
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
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
			
			// ObjectMapper 객체를 사용해서 json 방식의 데이터를 handling한다.
			ObjectMapper mapper = new ObjectMapper();
			HashMap<String, Object> dailyResult = mapper.readValue(jsonResponse, HashMap.class);
			
			// boxOfficeResult 내부에서 리스트 추출
			Map<String, Object> boxOfficeResult = (Map<String, Object>) dailyResult.get("boxOfficeResult");
			if (boxOfficeResult == null) {
			    System.out.println("boxOfficeResult가 응답에 없습니다.");
			    return null;
			}
			
			// json 데이터 중 "dailyBoxOfficeList" 에 저장되어있는 영화들을 리스트로 가져온다.
			List<HashMap<String, Object>> dailyBoxOfficeList = (List<HashMap<String, Object>>)boxOfficeResult.get("dailyBoxOfficeList");
			
			return dailyBoxOfficeList;
				
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	
	public static List<Map<String, Object>> getMovieInfoList() {
		// 1. API URL 구성
		// http url 직접 호출 방식으로 searchMovieList를 사용해야함
		String apiUrl = "http://www.kobis.or.kr/kobisopenapi/webservice/rest/movie/searchMovieList.json"
		           + "?key=" + key
		           + "&curPage=4"
		           + "&itemPerPage=100";

		// movieCd 예시 :  20170561 -> 블랙팬서 영화 코드

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
			
			return movieList;
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	
	// 건네받은 영화 코드로 해당 영화의 상세정보를 불러오는 코드.
	public static Map<String, Object> getMovieInfo(String movieCd){
		// 1. API URL 구성
		// http url 직접 호출 방식으로 searchMovieInfo를 사용해야함
		String apiUrl = "http://www.kobis.or.kr/kobisopenapi/webservice/rest/movie/searchMovieInfo.json"
		           + "?key=" + key
		           + "&movieCd="+movieCd;
		
		// 예시 url
		// http://www.kobis.or.kr/kobisopenapi/webservice/rest/movie/searchMovieList.json?key=4efdb259b1c88d86ae23dac5306f08a5&movieCd=20170561
		
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
