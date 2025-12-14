package com.brasilburger.repository.impl;

import com.brasilburger.config.DatabaseConnection;
import com.brasilburger.model.Livreur;
import com.brasilburger.repository.LivreurRepository;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LivreurRepositoryImpl implements LivreurRepository {
    private Connection connection;

    public LivreurRepositoryImpl() {
        this.connection = DatabaseConnection.getConnection();
    }

    @Override
    public boolean creer(Livreur livreur) {
        String sql = "INSERT INTO livreurs (nom, prenom, email, telephone, password, zone_id, archived) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, livreur.getNom());
            stmt.setString(2, livreur.getPrenom());
            stmt.setString(3, livreur.getEmail());
            stmt.setString(4, livreur.getTelephone());
            stmt.setString(5, livreur.getPassword());
            stmt.setInt(6, livreur.getZoneId());
            stmt.setBoolean(7, livreur.isArchived());

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    livreur.setId(rs.getInt(1));
                }
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Livreur> listerTous() {
        List<Livreur> livreurs = new ArrayList<>();
        String sql = "SELECT * FROM livreurs WHERE archived = false ORDER BY id";
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Livreur l = new Livreur();
                l.setId(rs.getInt("id"));
                l.setNom(rs.getString("nom"));
                l.setPrenom(rs.getString("prenom"));
                l.setEmail(rs.getString("email"));
                l.setTelephone(rs.getString("telephone"));
                l.setPassword(rs.getString("password"));
                l.setZoneId(rs.getInt("zone_id"));
                l.setArchived(rs.getBoolean("archived"));
                livreurs.add(l);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return livreurs;
    }

    @Override
    public Livreur obtenirParId(int id) {
        String sql = "SELECT * FROM livreurs WHERE id = ? AND archived = false";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Livreur l = new Livreur();
                l.setId(rs.getInt("id"));
                l.setNom(rs.getString("nom"));
                l.setPrenom(rs.getString("prenom"));
                l.setEmail(rs.getString("email"));
                l.setTelephone(rs.getString("telephone"));
                l.setPassword(rs.getString("password"));
                l.setZoneId(rs.getInt("zone_id"));
                l.setArchived(rs.getBoolean("archived"));
                return l;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean modifier(Livreur livreur) {
        String sql = "UPDATE livreurs SET nom = ?, prenom = ?, email = ?, telephone = ?, zone_id = ? WHERE id = ?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, livreur.getNom());
            stmt.setString(2, livreur.getPrenom());
            stmt.setString(3, livreur.getEmail());
            stmt.setString(4, livreur.getTelephone());
            stmt.setInt(5, livreur.getZoneId());
            stmt.setInt(6, livreur.getId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean archiver(int id) {
        String sql = "UPDATE livreurs SET archived = true WHERE id = ?";
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
