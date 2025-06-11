package Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Entity.Comment;
import Util.DBUtil;

public class CommentRepository {
	
	 // 댓글 저장
    public void save(Comment comment) {
        String sql = "INSERT INTO Comment (post_id, user_id, content, parent_comment_id) VALUES (?, ?, ?, ?)";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, comment.getPostId());
            pstmt.setInt(2, comment.getUserId());
            pstmt.setString(3, comment.getContent());

            if (comment.getParentCommentId() != null) {
                pstmt.setInt(4, comment.getParentCommentId());
            } else {
                pstmt.setNull(4, java.sql.Types.INTEGER);
            }

            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 특정 게시글의 댓글 목록 가져오기
    public List<Comment> findByPostId(int postId) {
        List<Comment> list = new ArrayList<>();
        String sql = "SELECT c.*, u.name FROM Comment c JOIN User u ON c.user_id = u.user_id WHERE post_id = ? ORDER BY created_at ASC";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, postId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Comment c = new Comment();
                c.setCommentId(rs.getInt("comment_id"));
                c.setPostId(rs.getInt("post_id"));
                c.setUserId(rs.getInt("user_id"));
                c.setUserName(rs.getString("name")); // JOIN한 사용자 이름
                c.setContent(rs.getString("content"));
                c.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                c.setGoodNum(rs.getInt("good_num"));
                c.setBadNum(rs.getInt("bad_num"));
                int parentId = rs.getInt("parent_comment_id");
                if (!rs.wasNull()) {
                    c.setParentCommentId(parentId);
                }

                list.add(c);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
    
  
 // 댓글 리액션 조회
    public Map<String, Object> getCommentReactionInfo(int user_id, int comment_id) {
        String sql = "SELECT commentReaction_id, reactionType FROM CommentReaction WHERE user_id=? AND comment_id=?";
        Map<String, Object> result = new HashMap<>();

        try (Connection con = DBUtil.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setInt(1, user_id);
            pstmt.setInt(2, comment_id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                result.put("commentReaction_id", rs.getInt("commentReaction_id"));
                result.put("reactionType", rs.getInt("reactionType"));
                return result;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // 댓글 좋아요 설정 (type: 1=좋아요, 2=좋아요 취소)
    public void setCommentLike(int user_id, int comment_id, int type) {
        try (Connection con = DBUtil.getConnection()) {
            Map<String, Object> reaction = getCommentReactionInfo(user_id, comment_id);
            PreparedStatement pstmt;

            if (type == 1) { // 좋아요
                if (reaction == null) {
                    // insert + update 좋아요
                    String insert = "INSERT INTO CommentReaction (user_id, comment_id, reactionType) VALUES (?, ?, 1)";
                    pstmt = con.prepareStatement(insert);
                    pstmt.setInt(1, user_id);
                    pstmt.setInt(2, comment_id);
                    pstmt.executeUpdate();

                    String update = "UPDATE Comment SET good_num = good_num + 1 WHERE comment_id = ?";
                    pstmt = con.prepareStatement(update);
                    pstmt.setInt(1, comment_id);
                    pstmt.executeUpdate();
                } else {
                    int reactionType = (int) reaction.get("reactionType");
                    int commentReaction_id = (int) reaction.get("commentReaction_id");
                    if (reactionType == 0) {
                        // 상태가 없던 경우 => 좋아요 처리
                        String updateReaction = "UPDATE CommentReaction SET reactionType = 1 WHERE commentReaction_id = ?";
                        pstmt = con.prepareStatement(updateReaction);
                        pstmt.setInt(1, commentReaction_id);
                        pstmt.executeUpdate();

                        String updateComment = "UPDATE Comment SET good_num = good_num + 1 WHERE comment_id = ?";
                        pstmt = con.prepareStatement(updateComment);
                        pstmt.setInt(1, comment_id);
                        pstmt.executeUpdate();
                    } else if (reactionType == 2) {
                        // 싫어요 -> 좋아요로 변경
                        String updateReaction = "UPDATE CommentReaction SET reactionType = 1 WHERE commentReaction_id = ?";
                        pstmt = con.prepareStatement(updateReaction);
                        pstmt.setInt(1, commentReaction_id);
                        pstmt.executeUpdate();

                        String updateComment = "UPDATE Comment SET good_num = good_num + 1, bad_num = bad_num - 1 WHERE comment_id = ?";
                        pstmt = con.prepareStatement(updateComment);
                        pstmt.setInt(1, comment_id);
                        pstmt.executeUpdate();
                    }
                }
            } else if (type == 2 && reaction != null && (int) reaction.get("reactionType") == 1) {
                // 좋아요 취소
                String updateReaction = "UPDATE CommentReaction SET reactionType = 0 WHERE commentReaction_id = ?";
                pstmt = con.prepareStatement(updateReaction);
                pstmt.setInt(1, (int) reaction.get("commentReaction_id"));
                pstmt.executeUpdate();

                String updateComment = "UPDATE Comment SET good_num = good_num - 1 WHERE comment_id = ?";
                pstmt = con.prepareStatement(updateComment);
                pstmt.setInt(1, comment_id);
                pstmt.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 댓글 싫어요 설정 (type: 1=싫어요, 2=싫어요 취소)
    public void setCommentUnlike(int user_id, int comment_id, int type) {
        try (Connection con = DBUtil.getConnection()) {
            Map<String, Object> reaction = getCommentReactionInfo(user_id, comment_id);
            PreparedStatement pstmt;

            if (type == 1) { // 싫어요
                if (reaction == null) {
                    String insert = "INSERT INTO CommentReaction (user_id, comment_id, reactionType) VALUES (?, ?, 2)";
                    pstmt = con.prepareStatement(insert);
                    pstmt.setInt(1, user_id);
                    pstmt.setInt(2, comment_id);
                    pstmt.executeUpdate();

                    String update = "UPDATE Comment SET bad_num = bad_num + 1 WHERE comment_id = ?";
                    pstmt = con.prepareStatement(update);
                    pstmt.setInt(1, comment_id);
                    pstmt.executeUpdate();
                } else {
                    int reactionType = (int) reaction.get("reactionType");
                    int commentReaction_id = (int) reaction.get("commentReaction_id");
                    if (reactionType == 0) {
                        String updateReaction = "UPDATE CommentReaction SET reactionType = 2 WHERE commentReaction_id = ?";
                        pstmt = con.prepareStatement(updateReaction);
                        pstmt.setInt(1, commentReaction_id);
                        pstmt.executeUpdate();

                        String updateComment = "UPDATE Comment SET bad_num = bad_num + 1 WHERE comment_id = ?";
                        pstmt = con.prepareStatement(updateComment);
                        pstmt.setInt(1, comment_id);
                        pstmt.executeUpdate();
                    } else if (reactionType == 1) {
                        String updateReaction = "UPDATE CommentReaction SET reactionType = 2 WHERE commentReaction_id = ?";
                        pstmt = con.prepareStatement(updateReaction);
                        pstmt.setInt(1, commentReaction_id);
                        pstmt.executeUpdate();

                        String updateComment = "UPDATE Comment SET bad_num = bad_num + 1, good_num = good_num - 1 WHERE comment_id = ?";
                        pstmt = con.prepareStatement(updateComment);
                        pstmt.setInt(1, comment_id);
                        pstmt.executeUpdate();
                    }
                }
            } else if (type == 2 && reaction != null && (int) reaction.get("reactionType") == 2) {
                String updateReaction = "UPDATE CommentReaction SET reactionType = 0 WHERE commentReaction_id = ?";
                pstmt = con.prepareStatement(updateReaction);
                pstmt.setInt(1, (int) reaction.get("commentReaction_id"));
                pstmt.executeUpdate();

                String updateComment = "UPDATE Comment SET bad_num = bad_num - 1 WHERE comment_id = ?";
                pstmt = con.prepareStatement(updateComment);
                pstmt.setInt(1, comment_id);
                pstmt.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

	
	// 댓글 삭제 (필요한 경우)
    public void delete(int commentId) {
        String sql = "DELETE FROM Comment WHERE comment_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, commentId);
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void update(int commentId, String newContent) {
        String sql = "UPDATE Comment SET content = ? WHERE comment_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, newContent);
            pstmt.setInt(2, commentId);
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
