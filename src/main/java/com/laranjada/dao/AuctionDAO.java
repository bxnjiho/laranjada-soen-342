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
import com.laranjada.models.Client;
import com.laranjada.models.Expert;
import com.laranjada.models.ObjectOfInterest;
import com.laranjada.models.ServiceRequest;
import com.laranjada.utils.ExpertiseArea;
import com.laranjada.utils.Type;

public class AuctionDAO {

    public static void insertAuction(Auction auction) throws SQLException {
        Connection conn = DBConnection.getInstance().getConnection();
    
        // Insert the auction into the 'auctions' table
        String sql = "INSERT INTO auctions (date, type) VALUES (?, ?)";
        PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        stmt.setDate(1, new java.sql.Date(auction.getDate().getTime())); // convert java.util.Date to java.sql.Date
        stmt.setString(2, auction.getType());
        stmt.setInt(3, auction.getAuctionHouseId());
        stmt.executeUpdate();
    }
    

    public static List<Auction> getAllAuctions() throws SQLException {
        Connection conn = DBConnection.getInstance().getConnection();
        String sql = "SELECT * FROM auctions";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);

        List<Auction> auctions = new ArrayList<>();

        while (rs.next()) {
            int auctionId = rs.getInt("id");
            Date date = rs.getDate("date");
            String type = rs.getString("type");
            int auctionHouse_id = rs.getInt("auctionHouse_id");

            // Get associated objects and service requests
            ArrayList<ObjectOfInterest> objects = getObjectsForAuction(auctionId, conn);
            ArrayList<ServiceRequest> serviceRequests = getServiceRequestsForAuction(auctionId, conn);

            Auction auction = new Auction(auctionId, date, type, auctionHouse_id, objects, serviceRequests);
            auctions.add(auction);
        }

        return auctions;
    }

    public static ArrayList<ObjectOfInterest> getObjectsForAuction(int auctionId, Connection conn) throws SQLException {
        String sql = "SELECT o.* FROM objects_of_interest o JOIN auction_objects ao ON o.id = ao.object_id WHERE ao.auction_id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, auctionId);
        ResultSet rs = stmt.executeQuery();

        ArrayList<ObjectOfInterest> objects = new ArrayList<>();
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

    public static ArrayList<ServiceRequest> getServiceRequestsForAuction(int auctionId, Connection conn) throws SQLException {
        String sql = "SELECT s.* FROM service_requests s JOIN auction_service_requests asr ON s.id = asr.service_request_id WHERE asr.auction_id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, auctionId);
        ResultSet rs = stmt.executeQuery();
    
        ArrayList<ServiceRequest> requests = new ArrayList<>();
        while (rs.next()) {
            String name = rs.getString("name");
            int clientId = rs.getInt("client_id");
            int expertId = rs.getInt("expert_id");
            ExpertiseArea expertise = ExpertiseArea.valueOf(rs.getString("expertise"));
            Type type = Type.valueOf("type");
            boolean claimed = rs.getBoolean("claimed");
            Date date = rs.getTimestamp("created_at");
    
            // You'll need to implement these methods if they don't exist
            Client client = ClientDAO.getClientById(clientId);
            Expert expert = ExpertDAO.getExpertById(expertId);
    
            ServiceRequest sr = new ServiceRequest(name, client, expertise, type, date);
            sr.setClaimed(claimed);
    
            requests.add(sr);
        }
    
        return requests;
    }

    public static int getAuctionIdByName(String name) throws SQLException {
        Connection conn = DBConnection.getInstance().getConnection();
        String sql = "SELECT id FROM auctions WHERE name = ? LIMIT 1";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, name);
        ResultSet rs = stmt.executeQuery();
    
        if (rs.next()) {
            return rs.getInt("id");
        } else {
            throw new SQLException("Auction with name '" + name + "' not found.");
        }
    }

    public static void addObjectToAuction(String auctionName, String objectDescription) throws SQLException {
        Connection conn = DBConnection.getInstance().getConnection();
    
        int auctionId = getAuctionIdByName(auctionName);
        int objectId = ObjectOfInterestDAO.getObjectIdByDescription(objectDescription);
    
        String sql = "INSERT INTO auctions_objects (auction_id, object_id) VALUES (?, ?)";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, auctionId);
        stmt.setInt(2, objectId);
        stmt.executeUpdate();
    
        System.out.println(" Object added to auction successfully!");
    }
    
    
    
}
