package com.laranjada.dao;

import com.laranjada.models.Admin;
import com.laranjada.db.DBConnection;

import java.sql.*;

public class AdminDAO {

    public static Admin getAdminByEmail(String email) throws SQLException {
        Connection conn = DBConnection.getInstance().getConnection();
        String sql = "SELECT * FROM admins WHERE email = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, email);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            return new Admin(
                rs.getString("email"),
                rs.getString("password"),
                rs.getString("firstname"),
                rs.getString("lastname")
            );
        }
        return null;
    }
}
