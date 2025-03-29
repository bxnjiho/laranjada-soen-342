package com.laranjada.dao;

import com.laranjada.models.ObjectOfInterest;
import com.laranjada.db.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ObjectOfInterestDAO {

    public static List<ObjectOfInterest> getAllObjects() throws SQLException {
        Connection conn = DBConnection.getInstance().getConnection();
        String sql = "SELECT * FROM objects_of_interest";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);

        List<ObjectOfInterest> objects = new ArrayList<>();
        while (rs.next()) {
            ObjectOfInterest obj = new ObjectOfInterest(
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
        String sql = "INSERT INTO objects_of_interest (description, ownedByInstitution, auctioned) VALUES (?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, obj.getDescription());
        stmt.setBoolean(2, obj.isOwnedByInstitution());
        stmt.setBoolean(3, obj.isAuctioned());
        stmt.executeUpdate();
    }
}
