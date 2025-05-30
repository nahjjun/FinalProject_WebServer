package Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.HashMap;
import java.util.Iterator;
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
	
	public KoficDBUtil() {
		updateDailyBoxOfficeDB();
	}
	
	// 일정 기간마다 내 DB서버의 DailyBoxOffice 테이블의 데이터들을 kofic api의 데이터를 이용하여 업데이트 해주는 함수
	public static void updateDailyBoxOfficeDB() {
		// 현재 시간/날짜를 입력받아서 날짜별 순위를 확인해야한다.
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("targetDt", "20250528");
		
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
				if(openDt == null || openDt.trim().isEmpty() || openDt.equals(" ")) {
					pstmt.setNull(3, java.sql.Types.DATE); // null값을 저장
				} else {
					// openDt가 yyyyMMdd면 yyyy-MM-dd로 변환 (MySQL DATE 타입은 하이픈 포함해야 안전)
				    if (openDt.matches("\\d{8}")) {
				        // ex: 20250528 → 2025-05-28
				        String formattedDate = openDt.substring(0, 4) + "-" + openDt.substring(4, 6) + "-" + openDt.substring(6);
				        pstmt.setDate(3, java.sql.Date.valueOf(formattedDate));
				    } else {
				        System.out.println("잘못된 날짜 형식: " + openDt);
				        pstmt.setNull(3, java.sql.Types.DATE); // 형식 이상하면 NULL로
				    }
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
	

	// DB의 Movie 테이블의 모든 데이터들을 업데이트 해주는 함수 
	public void updateAllMoviesDB() {
		
	}
}
