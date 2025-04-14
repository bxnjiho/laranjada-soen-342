package com.laranjada.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.laranjada.db.DBConnection;
import com.laranjada.models.Auction;
import com.laranjada.models.AuctionHouse;
import com.laranjada.models.ObjectOfInterest;
import com.laranjada.models.ServiceRequest;

import java.time.LocalDate;

public class AuctionHouseDAO {

    public static void insertAuctionHouse(AuctionHouse auctionHouse) throws SQLException {
        Connection conn = DBConnection.getInstance().getConnection();
        String sql = "INSERT INTO auctionHouses (name, city) VALUES (?, ?)";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, auctionHouse.getName());
        stmt.setString(2, auctionHouse.getCity());
        stmt.executeUpdate();
    }

    private static ArrayList<Auction> getAuctionsForAuctionHouses(int auctionHouseId, Connection conn) throws SQLException {
        String sql = "SELECT * FROM auctions WHERE auctionHouse_id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, auctionHouseId);
        ResultSet rs = stmt.executeQuery();
    
        ArrayList<Auction> auctions = new ArrayList<>();
    
        while (rs.next()) {
            int auctionId = rs.getInt("id");
            String name = rs.getString("name");
            LocalDateTime startDate = rs.getTimestamp("startDate").toLocalDateTime();
            LocalDateTime endDate = rs.getTimestamp("endDate").toLocalDateTime();
            String type = rs.getString("type");
    
            ArrayList<ObjectOfInterest> objects = AuctionDAO.getObjectsForAuction(auctionId, conn);
            ArrayList<ServiceRequest> serviceRequests = AuctionDAO.getServiceRequestsForAuction(auctionId, conn);
    
            Auction auction = new Auction(auctionId, name, startDate, endDate, type, auctionHouseId, objects, serviceRequests);
            auctions.add(auction);
        }
    
        return auctions;
    }
    

    public static List<AuctionHouse> getAllAuctionHouses() throws SQLException {
        Connection conn = DBConnection.getInstance().getConnection();
        String sql = "SELECT * FROM auctionHouses";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);

        List<AuctionHouse> auctionHouses = new ArrayList<>();
        while (rs.next()) {
            AuctionHouse auctionHouse = new AuctionHouse(
                rs.getString("name"),
                rs.getString("city")
            );
            auctionHouses.add(auctionHouse);
        }

        return auctionHouses;
    }

    public static int getAuctionHouseIdByName(String name) throws SQLException {
        Connection conn = DBConnection.getInstance().getConnection();
        String sql = "SELECT id FROM auctionHouses WHERE name = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, name);
    
        ResultSet rs = stmt.executeQuery();
    
        if (rs.next()) {
            return rs.getInt("id");
        } else {
            throw new SQLException("Auction house with name '" + name + "' not found.");
        }
    }    
    
}
