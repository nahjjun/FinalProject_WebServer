package Service;

import java.util.List;
import java.util.Map;

import Entity.Comment;
import Repository.CommentRepository;

public class CommentService {

	private CommentRepository commentRepository = new CommentRepository();

	// 댓글 저장
	public void saveComment(Comment comment) {
		commentRepository.save(comment);
	}

	// 특정 게시글의 댓글 목록 불러오기
	public List<Comment> getCommentsByPostId(int postId) {
		return commentRepository.findByPostId(postId);
	}

	// 댓글 수정
	public void updateComment(int commentId, String newContent) {
		commentRepository.update(commentId, newContent);
	}

	// 댓글 삭제
	public void deleteComment(int commentId) {
		commentRepository.delete(commentId);
	}

	// 좋아요/싫어요 리액션 정보 가져오기
	public Map<String, Object> getReactionInfo(int userId, int commentId) {
		return commentRepository.getCommentReactionInfo(userId, commentId);
	}

	// 댓글 좋아요 설정
	// type: 1 = 좋아요, 2 = 좋아요 취소
	public void likeComment(int userId, int commentId, int type) {
		commentRepository.setCommentLike(userId, commentId, type);
	}

	// 댓글 싫어요 설정
	// type: 1 = 싫어요, 2 = 싫어요 취소
	public void unlikeComment(int userId, int commentId, int type) {
		commentRepository.setCommentUnlike(userId, commentId, type);
	}
}
