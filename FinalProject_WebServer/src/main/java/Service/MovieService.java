package Service;

import java.util.Map;

import Dto.MovieDto;
import Repository.MovieRepository;

public class MovieService {
	private MovieRepository movieRepository = new MovieRepository();
	
	public MovieService() {
		// TODO Auto-generated constructor stub
	}

	public Map<String, Object> getMovieInfo(MovieDto movieDto){
		MovieDto dto = new MovieDto(movieDto.getTitle());
		return movieRepository.getMovieInfo(dto);
	}
	
}
