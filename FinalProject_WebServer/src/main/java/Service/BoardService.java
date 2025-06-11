package Service;

import java.util.List;
import java.util.Map;

import Entity.Post;
import Repository.PostRepository;

public class BoardService {

	private PostRepository postRepository = new PostRepository();
	
	public BoardService() {
		// TODO Auto-generated constructor stub
	}
	

	// 게시글 목록 가져오기
	public List<Post> getPostList(String boardType, int offset, int limit) {
		return postRepository.findByBoardType(boardType, offset, limit);
		}


	// 게시글 상세 조회
		public Post getPostDetail(int postId) {
			return postRepository.findById(postId);
		}

		// 게시글 작성
		public void createPost(Post post) {
			postRepository.save(post);
		}

		// 게시글 수정
		public void updatePost(Post post) {
			postRepository.update(post);
		}

		// 게시글 삭제
		public void deletePost(int postId) {
			postRepository.delete(postId);
		}

		// 게시글 조회수 증가
		public void increaseViewCount(int postId) {
			postRepository.increaseViewCount(postId);
		}

		// 게시글 총 개수
		public int countPostByBoardType(String boardType) {
			return postRepository.countByBoardType(boardType);
		}

		

}
