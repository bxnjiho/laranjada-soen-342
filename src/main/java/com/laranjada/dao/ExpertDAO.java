package com.laranjada.dao;

import com.laranjada.models.Expert;
import com.laranjada.db.DBConnection;

import java.sql.*;

public class ExpertDAO {

    public static void insertExpert(Expert expert) throws SQLException {
        Connection conn = DBConnection.getInstance().getConnection();
        String sql = "INSERT INTO experts (email, password, firstname, lastname, areasOfExpertise, licenseNumber) VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, expert.getEmail());
        stmt.setString(2, expert.getPassword());
        stmt.setString(3, expert.getFirstName());
        stmt.setString(4, expert.getLastName());
        stmt.setString(5, String.join(",", expert.getAreasOfExpertise())); 
        stmt.setString(6, expert.getLicenseNumber());
        stmt.executeUpdate();
    }

    public static Expert getExpertByEmail(String email) throws SQLException {
        Connection conn = DBConnection.getInstance().getConnection();
        String sql = "SELECT * FROM experts WHERE email = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, email);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            return new Expert(
                rs.getString("email"),
                rs.getString("password"),
                rs.getString("firstname"),
                rs.getString("lastname"),
                rs.getString("areasOfExpertise").split(","),
                rs.getString("licenseNumber")
            );
        }
        return null;
    }
}
