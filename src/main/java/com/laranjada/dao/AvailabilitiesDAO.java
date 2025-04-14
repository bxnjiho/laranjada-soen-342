package com.laranjada.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.laranjada.db.DBConnection;
import com.laranjada.models.Availability;

public class AvailabilitiesDAO {

    public static void insertAvailability(Availability availability) throws SQLException {
        String sql = "INSERT INTO availabilities (expert_id, startDate, endDate) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, availability.getExpertId());
            stmt.setTimestamp(2, Timestamp.valueOf(availability.getStart()));
            stmt.setTimestamp(3, Timestamp.valueOf(availability.getEnd()));

            stmt.executeUpdate();
        }
    }

    public static List<Availability> getAvailabilitiesByExpertId(int expertId) throws SQLException {
        List<Availability> availabilities = new ArrayList<>();
        String sql = "SELECT * FROM availabilities WHERE expert_id = ?";

        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, expertId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                LocalDateTime start = rs.getTimestamp("startDate").toLocalDateTime();
                LocalDateTime end = rs.getTimestamp("endDate").toLocalDateTime();
                availabilities.add(new Availability(id, expertId, start, end));
            }
        }
        return availabilities;
    }

    public static void deleteAvailability(int id) throws SQLException {
        String sql = "DELETE FROM availabilities WHERE id = ?";

        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}
