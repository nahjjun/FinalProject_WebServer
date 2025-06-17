package Service;

import java.util.List;
import java.util.Map;

import Repository.TicketRepository;

public class TicketService {
	private TicketRepository ticketRepository = new TicketRepository(); 
			
	public TicketService() {
		// TODO Auto-generated constructor stub
	}

	public List<Map<String, Object>> getTheaterNameList(){
		return ticketRepository.getTheaterNameList();
	}
	
	public List<Map<String, Object>> getMovieListByTheaterAndDate(int theater_id, String date){
		return ticketRepository.getMovieListByTheaterAndDate(theater_id, date);
	}
	
	public List<String> getTimeList(int theater_id, String date, int movie_id){
		return ticketRepository.getTimeList(theater_id, date, movie_id);
	}
	
	public List<Map<String, Object>> getSeatList(int theater_id, String date, int movie_id, String time){
		return ticketRepository.getSeatList(theater_id, date, movie_id, time);
	}
	
	// 주어진 정보들로 상영 정보, 좌석 정보, userId를 가져와서 Ticket 테이블에 저장하는 함수
	public boolean insertTicket(int user_id, int theater_id, int movie_id, int row_num, int col_num, String time, String date) {
		return ticketRepository.insertTicket(user_id, theater_id, movie_id, row_num, col_num, time, date);
	}
	
}
