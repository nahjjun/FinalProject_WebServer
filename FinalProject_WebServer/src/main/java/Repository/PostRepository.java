package Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import Entity.Post;
import Util.DBUtil;

public class PostRepository {

    public void save(Post post) {
        String sql = "INSERT INTO Post (user_id, movie_id, title, content, created_at, is_watched, view_count, board_type, image_path) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, post.getUserId());
             
            if (post.getMovieId() != null) {
                pstmt.setInt(2, post.getMovieId());
            } else {
                pstmt.setNull(2, java.sql.Types.INTEGER);
            }

            pstmt.setString(3, post.getTitle());
            pstmt.setString(4, post.getContent());
            pstmt.setTimestamp(5, Timestamp.valueOf(post.getCreatedAt()));
            pstmt.setBoolean(6, post.isWatched());
            pstmt.setInt(7, post.getViewCount());
            pstmt.setString(8, post.getBoardType());
            pstmt.setString(9, post.getImagePath());

            pstmt.executeUpdate();
            
            

        } catch (Exception e) {
        	System.out.println("Post 저장 중 오류 발생: " + e.getMessage());
        }
    }
    
    public Post findById(int postId) {
        String sql = "SELECT p.*, u.name FROM Post p JOIN User u ON p.user_id = u.user_id WHERE p.post_id = ?";
        Post post = null;

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, postId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                post = new Post();
                post.setPostId(rs.getInt("post_id"));
                post.setTitle(rs.getString("title"));
                post.setContent(rs.getString("content"));
                post.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                post.setViewCount(rs.getInt("view_count"));
                post.setUserId(rs.getInt("user_id"));
                post.setUserName(rs.getString("name"));
                post.setBoardType(rs.getString("board_type"));
                post.setWatched(rs.getBoolean("is_watched"));
                post.setImagePath(rs.getString("image_path"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return post;
    }
    
    public List<Post> findByBoardType(String boardType, int offset, int limit) {
        List<Post> list = new ArrayList<>();

        String sql = "SELECT p.post_id, p.title, p.created_at, p.view_count, u.name " +
                     "FROM Post p JOIN User u ON p.user_id = u.user_id " +
                     "WHERE board_type = ? " +
                     "ORDER BY p.post_id DESC LIMIT ?, ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, boardType);
            pstmt.setInt(2, offset);
            pstmt.setInt(3, limit);

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Post post = new Post();
                post.setPostId(rs.getInt("post_id"));
                post.setTitle(rs.getString("title"));
                post.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                post.setViewCount(rs.getInt("view_count"));
                post.setUserName(rs.getString("name"));
                list.add(post);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public int countByBoardType(String boardType) {
        String sql = "SELECT COUNT(*) FROM Post WHERE board_type = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, boardType);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) return rs.getInt(1);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
    
    //조회수 증가
    public void increaseViewCount(int postId) {
        String sql = "UPDATE Post SET view_count = view_count + 1 WHERE post_id = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, postId);
            pstmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void update(Post post) {
        String sql = "UPDATE Post SET title = ?, content = ?, board_type = ?, image_path = ?, is_watched = ? WHERE post_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, post.getTitle());
            pstmt.setString(2, post.getContent());
            pstmt.setString(3, post.getBoardType());
            pstmt.setString(4, post.getImagePath());
            pstmt.setBoolean(5, post.isWatched());
            pstmt.setInt(6, post.getPostId());

            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void delete(int postId) {
        String sql = "DELETE FROM Post WHERE post_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, postId);
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //유저 아이디로 글 갖고와야해
    public List<Post> findByUserId(int userId) {
        List<Post> list = new ArrayList<>();
        String sql = "SELECT * FROM Post WHERE user_id = ? ORDER BY created_at DESC";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Post post = new Post();
                post.setPostId(rs.getInt("post_id"));
                post.setTitle(rs.getString("title"));
                post.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                post.setViewCount(rs.getInt("view_count"));
                list.add(post);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }




}
