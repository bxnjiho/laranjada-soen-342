package com.laranjada.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.laranjada.db.DBConnection;
import com.laranjada.models.Auction;
import com.laranjada.models.AuctionHouse;
import com.laranjada.models.ObjectOfInterest;
import com.laranjada.models.ServiceRequest;

public class AuctionHouseDAO {

    public static void insertAuctionHouse(AuctionHouse auctionHouse) throws SQLException {
        Connection conn = DBConnection.getInstance().getConnection();
        String sql = "INSERT INTO auctionHouses (name, city, auctions) VALUES (?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, auctionHouse.getName());
        stmt.setString(2, auctionHouse.getCity());
        stmt.executeUpdate();

        // Get the generated auctionHouse ID
        ResultSet generatedKeys = stmt.getGeneratedKeys();
        if (generatedKeys.next()) {
            int auctionHouseId = generatedKeys.getInt(1);

            // Insert each auction into the join table
            if (auctionHouse.getAuctions() != null) {
                for (Auction auction : auctionHouse.getAuctions()) {
                    String junctionSQL = "INSERT INTO auctionHouses_auctions (auctionHouse_id, auction_id) VALUES (?, ?)";
                    PreparedStatement junctionStmt = conn.prepareStatement(junctionSQL);
                    junctionStmt.setInt(1, auctionHouseId);
                    junctionStmt.setInt(2, auction.getId());
                    junctionStmt.executeUpdate();
                }
            }
            else {
                throw new SQLException("Failed to retrieve generated auctionHouse ID.");
            }
        }
    }

    private static ArrayList<Auction> getAuctionsForAuctionHouses(int auctionHouseId, Connection conn) throws SQLException {
        String sql = "SELECT * FROM auctions WHERE auctionHouse_id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, auctionHouseId);
        ResultSet rs = stmt.executeQuery();
    
        ArrayList<Auction> auctions = new ArrayList<>();
    
        while (rs.next()) {
            int auctionId = rs.getInt("id");
            Date date = rs.getDate("date");
            String type = rs.getString("type");
    
            ArrayList<ObjectOfInterest> objects = AuctionDAO.getObjectsForAuction(auctionId, conn);
            ArrayList<ServiceRequest> serviceRequests = AuctionDAO.getServiceRequestsForAuction(auctionId, conn);
    
            Auction auction = new Auction(auctionId, date, type, auctionHouseId, objects, serviceRequests);
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
            int auctionHouseId = rs.getInt("id");
            String name = rs.getString("name");
            String city = rs.getString("city");

            // Get associated auctions and service requests
            ArrayList<Auction> auctions = getAuctionsForAuctionHouses(auctionHouseId, conn);

            AuctionHouse auctionHouse = new AuctionHouse(auctionHouseId, name, city, auctions);
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
