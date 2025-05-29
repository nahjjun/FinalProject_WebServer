package Util;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

import kr.or.kobis.kobisopenapi.consumer.rest.KobisOpenAPIRestService;
import kr.or.kobis.kobisopenapi.consumer.rest.exception.OpenAPIFault;

public class KoficDBUtil {
	// kofic의 api key
	private final String key = "4efdb259b1c88d86ae23dac5306f08a5";
	private Map<String, String> paramMap; 
// private String targetDt;  // 조회하고자 하는 날짜. (양식 : yyyymmdd)
//	private String itemPerPage; // 결과 row의 개수를 지정 (default:10, 최대:10)
//	private String multiMovieYn; // 다양성 영화/상업 영화를 구분지어 조회할 수 있음 (Y:다양성 영화, N:상업영화)(default: 전체)
//	private String repNationCd; // 한국/외국 영화별로 조회할 수 있습니다. "K" : 한국영화 "F" 외국영화 (default: 전체)
//	private String WideAreaCd; // 상영지역별로 조회할 수 있으며, 지역코드는 공통코드 조회 서비스에서 "0105000000"로서 조회된 지역코드입니다. (default: 전체)
//		
	
	public KoficDBUtil() {
		paramMap = new HashMap<String, String>();
		
	}
	
	// 일정 기간마다 내 DB서버의 데이터들을 kofic api의 데이터를 이용하여 업데이트 해주는 함수
	public void updateMovieDB() {
		// 현재 시간/날짜를 입력받아서 날짜별 순위를 확인해야한다.
		paramMap.put("targetDt", "20250528");
		
		try {
			// 오픈 API 클라이언트 객체를 발급받은 key로 생성
			KobisOpenAPIRestService kobisRS = new KobisOpenAPIRestService(key);
			
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
			
			
			
			
			
			
			
		} catch (OpenAPIFault e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	

}
