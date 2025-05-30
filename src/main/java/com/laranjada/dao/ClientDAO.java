package com.laranjada.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.laranjada.db.DBConnection;
import com.laranjada.models.Client;

public class ClientDAO {

    public static void insertClient(Client client) throws SQLException {
        Connection conn = DBConnection.getInstance().getConnection();
        String sql = "INSERT INTO clients (email, password, firstname, lastname, affiliation, accountApproved) VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, client.getEmail());
        stmt.setString(2, client.getPassword());
        stmt.setString(3, client.getFirstName());
        stmt.setString(4, client.getLastName());
        stmt.setString(5, client.getAffiliation());
        stmt.setBoolean(6, client.isAccountApproved());
        stmt.executeUpdate();
    }

    public static Client getClientByEmail(String email) throws SQLException {
        Connection conn = DBConnection.getInstance().getConnection();
        String sql = "SELECT * FROM clients WHERE email = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, email.trim().toLowerCase()); // 🔧 strip + normalize
        ResultSet rs = stmt.executeQuery();
    
        if (rs.next()) {
            return new Client(
                rs.getInt("id"),
                rs.getString("email"),
                rs.getString("password"),
                rs.getString("firstname"),
                rs.getString("lastname"),
                rs.getString("affiliation"),
                rs.getBoolean("accountApproved")
            );
        }
        return null;
    }
    

    public static Client getClientById(int id) throws SQLException {
        Connection conn = DBConnection.getInstance().getConnection();
        String sql = "SELECT * FROM clients WHERE id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            return new Client(
                rs.getString("email"),
                rs.getString("password"),
                rs.getString("firstname"),
                rs.getString("lastname"),
                rs.getString("affiliation"),
                rs.getBoolean("accountApproved")
            );
        }
        return null;
    }

    public static List<Client> getUnapprovedClients() throws SQLException {
        Connection conn = DBConnection.getInstance().getConnection();
        String sql = "SELECT * FROM clients WHERE accountApproved = false";
        PreparedStatement stmt = conn.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();

        List<Client> unapprovedClients = new ArrayList<>();
        while (rs.next()) {
            Client client = new Client(
                rs.getString("email"),
                rs.getString("password"),
                rs.getString("firstname"),
                rs.getString("lastname"),
                rs.getString("affiliation"),
                false
            );
            unapprovedClients.add(client);
        }

        return unapprovedClients;
    }

    public static void updateClientApproval(String email, boolean approved) throws SQLException {
        Connection conn = DBConnection.getInstance().getConnection();
        String sql = "UPDATE clients SET accountApproved = ? WHERE email = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setBoolean(1, approved);
        stmt.setString(2, email);
        stmt.executeUpdate();
    }
}
