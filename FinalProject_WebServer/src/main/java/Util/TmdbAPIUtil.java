package Util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

public class TmdbAPIUtil {
	private static final String key = "a4cfd32a5821bb727735fc136a1e6637";
	// 이 requestString에 query, language등의 속성을 넣어서 사용하면 됨
	private static final String requestString = "https://api.themoviedb.org/3/search/movie";
	private static final String basicImageUrl = "https://image.tmdb.org/t/p/w500";
	
	public TmdbAPIUtil() {
		// TODO Auto-generated constructor stub
	}
	
	// 영화 정보를 받아오는 함수
	public static Map<String,Object> getMovieInfo(String title) {
		// 1. API 키 구성
		String apiUrl = requestString + "?api_key="+key
				+"&query=" + URLEncoder.encode(title, StandardCharsets.UTF_8)
				+"&language=ko-KR";
		
		// 2. Http 연결 설정 및 요청
		try {
			// HttpURLConnection 객체를 생성하고, GET 방식으로 요청을 보낸다.
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
			List<Map<String, Object>> movieInfoList = (List<Map<String, Object>>)result.get("results");
			if (movieInfoList == null || movieInfoList.isEmpty()) {
                System.out.println("❌ TMDB 검색 결과 없음 → " + title);
                return null;
            }
			
			Map<String, Object> movieInfo = (Map<String, Object>) movieInfoList.get(0);
			
			// poster url값을 basic url 값을 붙여서 설정해준다.
			movieInfo.put("poster_path", basicImageUrl + (String)movieInfo.get("poster_path"));
			return movieInfo;
		} catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

}
