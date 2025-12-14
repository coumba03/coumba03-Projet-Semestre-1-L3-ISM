package com.brasilburger.repository.impl;

import com.brasilburger.config.DatabaseConnection;
import com.brasilburger.model.Client;
import com.brasilburger.repository.ClientRepository;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientRepositoryImpl implements ClientRepository {
    private Connection connection;

    public ClientRepositoryImpl() {
        this.connection = DatabaseConnection.getConnection();
    }

    @Override
    public boolean creer(Client client) {
        String sql = "INSERT INTO clients (nom, prenom, email, telephone, password, adresse, archived) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, client.getNom());
            stmt.setString(2, client.getPrenom());
            stmt.setString(3, client.getEmail());
            stmt.setString(4, client.getTelephone());
            stmt.setString(5, client.getPassword());
            stmt.setString(6, client.getAdresse());
            stmt.setBoolean(7, client.isArchived());

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    client.setId(rs.getInt(1));
                }
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Client> listerTous() {
        List<Client> clients = new ArrayList<>();
        String sql = "SELECT * FROM clients WHERE archived = false ORDER BY id";
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Client c = new Client();
                c.setId(rs.getInt("id"));
                c.setNom(rs.getString("nom"));
                c.setPrenom(rs.getString("prenom"));
                c.setEmail(rs.getString("email"));
                c.setTelephone(rs.getString("telephone"));
                c.setPassword(rs.getString("password"));
                c.setAdresse(rs.getString("adresse"));
                c.setArchived(rs.getBoolean("archived"));
                clients.add(c);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clients;
    }

    @Override
    public Client obtenirParId(int id) {
        String sql = "SELECT * FROM clients WHERE id = ? AND archived = false";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Client c = new Client();
                c.setId(rs.getInt("id"));
                c.setNom(rs.getString("nom"));
                c.setPrenom(rs.getString("prenom"));
                c.setEmail(rs.getString("email"));
                c.setTelephone(rs.getString("telephone"));
                c.setPassword(rs.getString("password"));
                c.setAdresse(rs.getString("adresse"));
                c.setArchived(rs.getBoolean("archived"));
                return c;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Client obtenirParEmail(String email) {
        String sql = "SELECT * FROM clients WHERE email = ? AND archived = false";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Client c = new Client();
                c.setId(rs.getInt("id"));
                c.setNom(rs.getString("nom"));
                c.setPrenom(rs.getString("prenom"));
                c.setEmail(rs.getString("email"));
                c.setTelephone(rs.getString("telephone"));
                c.setPassword(rs.getString("password"));
                c.setAdresse(rs.getString("adresse"));
                c.setArchived(rs.getBoolean("archived"));
                return c;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean modifier(Client client) {
        String sql = "UPDATE clients SET nom = ?, prenom = ?, email = ?, telephone = ?, adresse = ? WHERE id = ?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, client.getNom());
            stmt.setString(2, client.getPrenom());
            stmt.setString(3, client.getEmail());
            stmt.setString(4, client.getTelephone());
            stmt.setString(5, client.getAdresse());
            stmt.setInt(6, client.getId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean archiver(int id) {
        String sql = "UPDATE clients SET archived = true WHERE id = ?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
