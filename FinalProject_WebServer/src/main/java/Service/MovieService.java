package Service;

import java.util.List;
import java.util.Map;

import Dto.MovieDto;
import Repository.MovieRepository;

public class MovieService {
	private MovieRepository movieRepository = new MovieRepository();
	
	public MovieService() {
	}

	// 특정 영화의 정보를 가져오는 함수
	public Map<String, Object> getMovieInfo(MovieDto movieDto){
		MovieDto dto = new MovieDto(movieDto.getMovieId());
		return movieRepository.getMovieInfo(dto);
	}
	
	// 영화 박스오피스 리스트를 가져오는 함수
	public List<Map<String, Object>> getBoxOfficeList(){
		return movieRepository.getBoxOfficeList();
	}
	
	// 영화 리스트를 가져오는 함수
	public List<Map<String, Object>> getMovieInfoList(){
		return movieRepository.getMovieInfoList();
	}
	
	// 리뷰 데이터들을 불러오는 함수
	public List<Map<String, Object>> getReviewInfoList(MovieDto dto){
		return movieRepository.getReviewInfoList(dto);
	}
	
}
