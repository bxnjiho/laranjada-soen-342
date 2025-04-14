package com.laranjada.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.laranjada.db.DBConnection;
import com.laranjada.models.Availability;
import com.laranjada.models.Client;
import com.laranjada.models.Expert;
import com.laranjada.models.ServiceRequest;
import com.laranjada.utils.ExpertiseArea;
import com.laranjada.utils.Type;


public class ServiceRequestDAO {
    public static void insertServiceRequest(ServiceRequest sr) throws SQLException {
        if (hasOverlappingServiceRequest(sr.getClient(), sr.getStartDate(), sr.getEndDate())) {
            System.out.println("Client already has a service request during this time. Request will not be created.");
            return;
        }
    
        int expertId = getMatchingExpertId(sr.getStartDate(), sr.getEndDate(), sr.getExpertise());
    
        if (expertId != -1) {
            Expert expert = ExpertDAO.getExpertById(expertId);
            sr.setExpert(expert);
        } else {
            System.out.println("No matching expert available. Request will NOT be created.");
            return;
        }
    
        Connection conn = DBConnection.getInstance().getConnection();
        String sql = "INSERT INTO service_requests (name, client_id, expert_id, expertise, type, startDate, endDate) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(sql);
    
        stmt.setString(1, sr.getName());
        stmt.setInt(2, sr.getClient().getId());
    
        if (sr.getExpert() != null) {
            stmt.setInt(3, sr.getExpert().getId());
        } else {
            stmt.setNull(3, java.sql.Types.INTEGER);
        }
    
        stmt.setString(4, sr.getExpertise().name());
        stmt.setString(5, sr.getType().name());
        stmt.setTimestamp(6, Timestamp.valueOf(sr.getStartDate()));
        stmt.setTimestamp(7, Timestamp.valueOf(sr.getEndDate()));
    
        stmt.executeUpdate();
    }
    

    public static List<ServiceRequest> getServiceRequestsByExpertId(int expertId) throws SQLException {
        Connection conn = DBConnection.getInstance().getConnection();
        String sql = "SELECT * FROM service_requests WHERE expert_id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, expertId);
        ResultSet rs = stmt.executeQuery();

        List<ServiceRequest> requests = new ArrayList<>();

        while (rs.next()) {
            Client client = ClientDAO.getClientById(rs.getInt("client_id"));

            ServiceRequest sr = new ServiceRequest(
                rs.getString("name"),
                client,
                ExpertiseArea.valueOf(rs.getString("expertise")),
                Type.valueOf(rs.getString("type")),
                rs.getTimestamp("startDate").toLocalDateTime(),
                rs.getTimestamp("endDate").toLocalDateTime()
            );

            Expert expert = ExpertDAO.getExpertById(expertId);
            sr.setExpert(expert);

            requests.add(sr);
        }

        return requests;
    }


    public static boolean hasOverlappingServiceRequest(Client client, LocalDateTime newStart, LocalDateTime newEnd) throws SQLException {
        Connection conn = DBConnection.getInstance().getConnection();
        String sql = "SELECT COUNT(*) FROM service_requests " +
                    "WHERE client_id = ? AND " +
                    "(startDate < ? AND endDate > ?)";
                    
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, client.getId());
        stmt.setTimestamp(2, Timestamp.valueOf(newEnd));   // existing.start < newEnd
        stmt.setTimestamp(3, Timestamp.valueOf(newStart)); // existing.end > newStart

        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            return rs.getInt(1) > 0; // overlap exists
        }

        return false;
    }

    public static int getMatchingExpertId(LocalDateTime start, LocalDateTime end, ExpertiseArea requiredExpertise) throws SQLException {
    List<Expert> experts = ExpertDAO.getAllExperts();

    for (Expert expert : experts) {
        for (String expertiseStr : expert.getAreasOfExpertise()) {
            if (expertiseStr.equalsIgnoreCase(requiredExpertise.name())) {
                List<Availability> availabilities = AvailabilitiesDAO.getAvailabilitiesByExpertId(expert.getId());

                for (Availability a : availabilities) {
                    boolean fullyCovers = !a.getStart().isAfter(start) && !a.getEnd().isBefore(end);
                    if (fullyCovers) {
                        return expert.getId();
                    }
                }
            }
        }
    }

    return -1;
}
}
