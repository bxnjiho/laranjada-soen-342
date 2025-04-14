package com.laranjada.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.laranjada.db.DBConnection;
import com.laranjada.models.Availability;
import com.laranjada.models.Expert;


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
        Expert expert = new Expert(
            rs.getInt("id"), 
            rs.getString("email"),
            rs.getString("password"),
            rs.getString("firstname"),
            rs.getString("lastname"),
            rs.getString("areasOfExpertise").split(","),
            rs.getString("licenseNumber")
        );

        List<Availability> availabilities = AvailabilitiesDAO.getAvailabilitiesByExpertId(expert.getId());
        for (Availability a : availabilities) {
            expert.addAvailability(a); 
        }

        return expert;
    }

        return null;
    }

    public static Expert getExpertById(int id) throws SQLException {
        Connection conn = DBConnection.getInstance().getConnection();
        String sql = "SELECT * FROM experts WHERE id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            Expert expert = new Expert(
                rs.getInt("id"), // Needed to fetch availabilities!
                rs.getString("email"),
                rs.getString("password"),
                rs.getString("firstname"),
                rs.getString("lastname"),
                rs.getString("areasOfExpertise").split(","),
                rs.getString("licenseNumber")
            );
    
            List<Availability> availabilities = AvailabilitiesDAO.getAvailabilitiesByExpertId(expert.getId());
            for (Availability a : availabilities) {
                expert.addAvailability(a); // You need this method in Expert class
            }
    
            return expert;
        }
        return null;
    }

    public static List<Expert> getAllExperts() throws SQLException {
        List<Expert> experts = new ArrayList<>();
    
        Connection conn = DBConnection.getInstance().getConnection();
        String sql = "SELECT * FROM experts";
        PreparedStatement stmt = conn.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();
    
        // STEP 1: Build expert objects first
        while (rs.next()) {
            Expert expert = new Expert(
                rs.getInt("id"),
                rs.getString("email"),
                rs.getString("password"),
                rs.getString("firstname"),
                rs.getString("lastname"),
                rs.getString("areasOfExpertise").split(","),
                rs.getString("licenseNumber")
            );
    
            experts.add(expert);
        }
    
        rs.close(); // close result set before any nested SQL calls
        stmt.close(); // also safe to close statement
    
        // STEP 2: Load availabilities afterward
        for (Expert expert : experts) {
            List<Availability> availabilities = AvailabilitiesDAO.getAvailabilitiesByExpertId(expert.getId());
            expert.setAvailabilities(new ArrayList<>(availabilities));
        }
    
        return experts;
    }
    
}
