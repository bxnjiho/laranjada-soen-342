package com.laranjada.dao;

import java.util.Date;

import com.laranjada.models.Auction;
import com.laranjada.models.ObjectOfInterest;
import com.laranjada.models.ServiceRequest;
import com.laranjada.models.Client;
import com.laranjada.models.Expert;
import com.laranjada.db.DBConnection;

import com.laranjada.dao.ClientDAO;

import com.laranjada.utils.ExpertiseArea;
import com.laranjada.utils.Type;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AuctionDAO {

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

            // Get associated objects and service requests
            ArrayList<ObjectOfInterest> objects = getObjectsForAuction(auctionId, conn);
            ArrayList<ServiceRequest> serviceRequests = getServiceRequestsForAuction(auctionId, conn);

            Auction auction = new Auction(date, type, objects, serviceRequests);
            auctions.add(auction);
        }

        return auctions;
    }

    private static ArrayList<ObjectOfInterest> getObjectsForAuction(int auctionId, Connection conn) throws SQLException {
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

    private static ArrayList<ServiceRequest> getServiceRequestsForAuction(int auctionId, Connection conn) throws SQLException {
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
    
}
