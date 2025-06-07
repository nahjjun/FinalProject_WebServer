package Service;

import java.util.List;
import java.util.Map;

import Dto.ReviewRegisterDto;
import Repository.ReviewRepository;

public class ReviewService {
	private ReviewRepository reviewRepository = new ReviewRepository();
	public ReviewService() {
		// TODO Auto-generated constructor stub
	}

	// review를 DB에 등록하는 함수
	public int registerReviewDB(ReviewRegisterDto dto) {
		int review_id = -1;
		ReviewRegisterDto newDto = new ReviewRegisterDto(dto);
		if((review_id=reviewRepository.registerReviewDB(newDto)) == -1) {
			System.err.printf("ReviewService/registerReviewDB() error");
		}
		return review_id;
	}
	
	// 특정 영화의 정보를 가져오는 함수
	public Map<String, Object> getMovieInfo(int movie_id){
		return reviewRepository.getMovieInfo(movie_id);
	}
	
	// 리뷰 데이터들을 불러오는 함수
	public List<Map<String, Object>> getReviewInfoList(int movie_id){
		return reviewRepository.getReviewInfoList(movie_id);
	}
	
	// 리뷰 삭제 함수
	public boolean deleteReview(int review_id) {
		return reviewRepository.deleteReview(review_id);
	}
	
	
	// -------- 리뷰 리액션 ---------- //
	// 새로운 리뷰 등록 시, 해당 리뷰에 1대1 연동되는 리뷰 리엑션 데이터를 만들어주는 함수
	public boolean createReviewReaction(int user_id, int review_id) {
		return reviewRepository.createReviewReaction(user_id, review_id);
	}
	
	
	// 해당 리뷰의 리액션 정보를 가져오는 함수
	public Map<String, Object> getReviewReactionInfo(int user_id, int review_id){
		return reviewRepository.getReviewReactionInfo(user_id, review_id);
	}
	
	// 리뷰 리액션 - 좋아요 증가/감소 함수
	// type  ->  1: 좋아요 증가 / 2: 좋아요 감소
	public void setReviewLikeInfo(int reviewReaction_id, int review_id, int type) {
		reviewRepository.setReviewLikeInfo(reviewReaction_id, review_id, type);
	}
	
	// 리뷰 리액션 - 싫어요 증가/감소 함수
	// type  ->  1: 싫어요 증가 / 2: 싫어요 감소
	public void setReviewUnlikeInfo(int reviewReaction_id, int review_id, int type) {
		reviewRepository.setReviewUnlikeInfo(reviewReaction_id, review_id, type);
	}
}
