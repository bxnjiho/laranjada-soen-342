package com.laranjada.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.laranjada.db.DBConnection;
import com.laranjada.models.ObjectOfInterest;

public class ObjectOfInterestDAO {

    public static List<ObjectOfInterest> getAllObjects() throws SQLException {
        Connection conn = DBConnection.getInstance().getConnection();
        String sql = "SELECT * FROM objects_of_interest";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);

        List<ObjectOfInterest> objects = new ArrayList<>();
        while (rs.next()) {
            ObjectOfInterest obj = new ObjectOfInterest(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getString("description"),
                rs.getBoolean("ownedByInstitution"),
                rs.getBoolean("auctioned")
            );
            objects.add(obj);
        }
        return objects;
    }

    public static void insertObject(ObjectOfInterest obj) throws SQLException {
        Connection conn = DBConnection.getInstance().getConnection();
        String sql = "INSERT INTO objects_of_interest (name, description, ownedByInstitution, auctioned) VALUES (?, ?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, obj.getName());
        stmt.setString(2, obj.getDescription());
        stmt.setBoolean(3, obj.isOwnedByInstitution());
        stmt.setBoolean(4, obj.isAuctioned());
        stmt.executeUpdate();
    }

    public static int getObjectIdByDescription(String description) throws SQLException {
        Connection conn = DBConnection.getInstance().getConnection();
        String sql = "SELECT id FROM objects_of_interest WHERE description = ? LIMIT 1";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, description);
        ResultSet rs = stmt.executeQuery();
    
        if (rs.next()) {
            return rs.getInt("id");
        } else {
            throw new SQLException("Object with description '" + description + "' not found.");
        }
    }
    
}
