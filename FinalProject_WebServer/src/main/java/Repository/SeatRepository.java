package Repository;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.sun.jdi.connect.spi.Connection;

import Util.DBUtil;

public class SeatRepository {
    public List<Seat> findByMovieAndDate(int movieId, LocalDate date) {
        List<Seat> list = new ArrayList<>();
        String sql = "SELECT * FROM Seat WHERE movie_id = ? AND reservation_date = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, movieId);
            pstmt.setDate(2, Date.valueOf(date));
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Seat seat = new Seat();
                seat.setSeatId(rs.getInt("seat_id"));
                seat.setSeatNumber(rs.getString("seat_number"));
                seat.setReserved(rs.getBoolean("is_reserved"));
                list.add(seat);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}

