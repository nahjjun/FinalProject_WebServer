package Util;

import kr.or.kobis.kobisopenapi.consumer.rest.KobisOpenAPIRestService;

public class KoficDBUtil {
	// kofic의 api key
	private final String key = "4efdb259b1c88d86ae23dac5306f08a5";
	
	
	public KoficDBUtil() {
		// TODO Auto-generated constructor stub
	}
	
	// 일정 기간마다 내 DB서버의 데이터들을 kofic api의 데이터를 이용하여 업데이트 해주는 함수
	public void updateMovieDB() {
		// 오픈 API 클라이언트 객체를 발급받은 key로 생성
		KobisOpenAPIRestService kobisRS = new KobisOpenAPIRestService(key);
		
		// - getDailyBoxOffice: (박스오피스)일별 박스오피스
		// - getWeeklyBoxOffice: (박스오피스)주간/주말 박스오피스
		// - getComCodeList: (공통코드)공통코드 조회
		// KobisOpenAPIRestService.getDailyBoxOffice(boolean isJson, String targetDt, String itemPerPage,String multiMovieYn, String repNationCd, String wideAreaCd)
			// 	ㄴ> isJson 값이 true면 String을 json 형태로, false면 xml 타입으로 반환
		
		String jsonResponse =   
		
	}
	

}
