package Service;

import java.util.List;
import java.util.Map;

import Repository.TheaterRepository;

public class TheaterService {
	private TheaterRepository theaterRepository = new TheaterRepository(); 

	public TheaterService() {
	}
	
	// 영화관 이름을 토대로 해당 영화관의 상영 영화 리스트, 그리고 해당 영화의 상영정보들을 가져오는 함수
	public List<Map<String, Object>> getMovieInfoList(String theaterName, String screeningDate){
		return theaterRepository.getMovieInfoList(theaterName, screeningDate);
	}

	public List<String> getTheaterNameList(){
		return theaterRepository.getTheaterNameList();
	}
}
