package com.laranjada.dao;

import com.laranjada.models.ServiceRequest;
import com.laranjada.db.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceRequestDAO {
    public static void insertServiceRequest(ServiceRequest sr) throws SQLException {
        Connection conn = DBConnection.getInstance().getConnection();
        String sql = "INSERT INTO service_requests (name, client_id, expertise, type, claimed, created_at) VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, sr.getName());
        stmt.setInt(2, sr.getClient().getId()); // assumes getId() exists on Client
        stmt.setString(3, sr.getExpertise().name());
        stmt.setString(4, sr.getType().name());
        stmt.setBoolean(5, sr.isClaimed());
        stmt.setTimestamp(6, new java.sql.Timestamp(sr.getDate().getTime()));
        stmt.executeUpdate();
    }    
}
