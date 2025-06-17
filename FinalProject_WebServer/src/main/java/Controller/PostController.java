package Controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import Dto.PostDto;
import Entity.Comment;
import Entity.LoginUser;
import Entity.Post;
import Service.BoardService;
import Service.CommentService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/PostController")
@MultipartConfig(maxFileSize = 1024 * 1024 * 10)

public class PostController extends HttpServlet {
    private BoardService boardService = new BoardService();
    private CommentService commentService = new CommentService();

    public PostController() {
    	super();
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            // 게시판 목록 조회
            String category = request.getParameter("category");
            if (category == null || category.isEmpty()) category = "free";

            int page = 1;
            try { page = Integer.parseInt(request.getParameter("page")); } catch (Exception ignored) {}
            int pageSize = 10;
            int offset = (page - 1) * pageSize;
            
            List<Post> postList = boardService.getPostList(category, offset, pageSize);
            int totalPosts = boardService.countPostByBoardType(category);
            int totalPages = (int) Math.ceil((double) totalPosts / pageSize);
            
            // Post → PostDto 변환
            List<PostDto> dtoList = new ArrayList<>();
            int displayNumber = offset + 1;
            for (Post p : postList) {
                PostDto dto = new PostDto();
                dto.setDisplayNumber(displayNumber++);
                dto.setPostId(p.getPostId());
                dto.setTitle(p.getTitle());
                dto.setContent(p.getContent());
                dto.setCreatedAt(p.getCreatedAt());
                dto.setUserName(p.getUserName());
                dto.setViewCount(p.getViewCount());
                dtoList.add(dto);
            }

            // JSP로 넘길 값 설정
            request.setAttribute("postList", dtoList);
            request.setAttribute("category", category);
            request.setAttribute("currentPage", page);
            request.setAttribute("totalPages", totalPages);
            request.setAttribute("totalPosts", totalPosts);

            request.getRequestDispatcher("/WEB-INF/View/community.jsp").forward(request, response);
            return;
        }

        switch (action) {
        	case "write":
        		HttpSession writeSession = request.getSession();
        		LoginUser writer = (LoginUser) writeSession.getAttribute("loginUser");

        		if (writer == null) {
        			// 로그인 안 된 경우 로그인 페이지로 보냄
        			response.sendRedirect("LoginController");
        		    return;
        		    }

        		 // 로그인 되어 있으면 글쓰기 화면 보여주기
        		request.setAttribute("category", request.getParameter("category")); // 카테고리 유지
        	    request.getRequestDispatcher("/WEB-INF/View/postWrite.jsp").forward(request, response);
        	    
        	    break;
        		
        	case "view":
	            int postId = Integer.parseInt(request.getParameter("postId"));
	            boardService.increaseViewCount(postId);
	            Post post = boardService.getPostDetail(postId);
	            List<Comment> commentList = commentService.getCommentsByPostId(postId);
	            request.setAttribute("post", post);
	            request.setAttribute("commentList", commentList);
	            request.getRequestDispatcher("/WEB-INF/View/postDetail.jsp").forward(request, response);
	            break; 
            
	        case "edit":
	        	int editId = Integer.parseInt(request.getParameter("postId"));
                Post editPost = boardService.getPostDetail(editId);
                request.setAttribute("post", editPost);
                request.getRequestDispatcher("/WEB-INF/View/postEdit.jsp").forward(request, response);
                break;
	            	
	        case "delete":
	        	 int deleteId = Integer.parseInt(request.getParameter("postId"));
	             boardService.deletePost(deleteId);
	             response.sendRedirect("PostController?category=free");
	             break;
	
	            
	        case "like_comment":
	        	processCommentReaction(request, response, true);
	            break;
	
	        case "dislike_comment":
	            processCommentReaction(request, response, false);
	            break;
	            
	        case "edit_comment":
	            int editCommentId = Integer.parseInt(request.getParameter("commentId"));
	            String newContent = request.getParameter("newContent");
	            commentService.updateComment(editCommentId, newContent);
	            response.sendRedirect("PostController?action=view&postId=" + request.getParameter("postId"));
	            break;
	
	        case "delete_comment":
	            int delCommentId = Integer.parseInt(request.getParameter("commentId"));
	            commentService.deleteComment(delCommentId);
	            response.sendRedirect("PostController?action=view&postId=" + request.getParameter("postId"));
	            break;
    }
}

    private void processCommentReaction(HttpServletRequest request, HttpServletResponse response, boolean isLike) throws IOException {
        HttpSession session = request.getSession();
        LoginUser loginUser = (LoginUser) session.getAttribute("loginUser");

        if (loginUser == null) {
            response.sendRedirect("LoginController");
            return;
        }

        int commentId = Integer.parseInt(request.getParameter("commentId"));
        int userId = loginUser.getUserId();

        if (isLike) {
            commentService.likeComment(userId, commentId, 1);
        } else {
            commentService.unlikeComment(userId, commentId, 1);
        }

        response.sendRedirect("PostController?action=view&postId=" + request.getParameter("postId"));
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        switch (action) {
            case "write":
                HttpSession session = request.getSession();
                LoginUser loginUser = (LoginUser) session.getAttribute("loginUser");
                
                if (loginUser == null) {
                    response.sendRedirect("LoginController");
                    return;
                }

                Post post = new Post();
                post.setUserId(loginUser.getUserId());
                post.setTitle(request.getParameter("title"));
                post.setContent(request.getParameter("content"));
                String boardType = request.getParameter("boardType");
                post.setCreatedAt(LocalDateTime.now());
                post.setBoardType(boardType);

                boardService.createPost(post);
                // 등록 완료 메시지 세션에 저장
                session.setAttribute("message", "게시글이 성공적으로 등록되었습니다.");

                // 반드시 category 포함해서 리디렉션해야 글 리스트 뜸
                response.sendRedirect("PostController?category=" + boardType);
                break;


            case "update":
                Post updatedPost = new Post();
                updatedPost.setPostId(Integer.parseInt(request.getParameter("postId")));
                updatedPost.setTitle(request.getParameter("title"));
                updatedPost.setContent(request.getParameter("content"));
                updatedPost.setBoardType(request.getParameter("boardType"));
                updatedPost.setUpdatedAt(LocalDateTime.now());
                // 체크박스 값 처리
                String watchedParam = request.getParameter("watched");
                boolean isWatched = "true".equals(watchedParam); // 체크됐을 때만 true
                updatedPost.setWatched(isWatched);

                boardService.updatePost(updatedPost);
                
             // 피드백 메시지 세팅
                request.getSession().setAttribute("message", "게시글이 성공적으로 수정되었습니다.");

                // 리다이렉트
                response.sendRedirect("PostController?action=view&postId=" + updatedPost.getPostId());
                break;
                
            case "edit_comment":
                int editCommentId = Integer.parseInt(request.getParameter("commentId"));
                String newContent = request.getParameter("newContent");
                commentService.updateComment(editCommentId, newContent);
                response.sendRedirect("PostController?action=view&postId=" + request.getParameter("postId"));
                break;


            case "comment":
                HttpSession commentSession = request.getSession();
                LoginUser commentUser = (LoginUser) commentSession.getAttribute("loginUser");
                if (commentUser == null) {
                    response.sendRedirect("LoginController");
                    return;
                }

                Comment comment = new Comment();
                comment.setPostId(Integer.parseInt(request.getParameter("postId")));
                comment.setUserId(commentUser.getUserId());
                comment.setContent(request.getParameter("content"));
                comment.setCreatedAt(LocalDateTime.now());
                commentService.saveComment(comment);
                response.sendRedirect("PostController?action=view&postId=" + comment.getPostId());
                break;
        }
    }
}

