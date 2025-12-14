package com.brasilburger.repository.impl;

import com.brasilburger.config.DatabaseConnection;
import com.brasilburger.model.Zone;
import com.brasilburger.repository.ZoneRepository;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ZoneRepositoryImpl implements ZoneRepository {
    private Connection connection;

    public ZoneRepositoryImpl() {
        this.connection = DatabaseConnection.getConnection();
    }

    @Override
    public boolean creer(Zone zone) {
        String sql = "INSERT INTO zones (nom, quartiers, prix_livraison, archived) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, zone.getNom());
            stmt.setString(2, zone.getQuartiers());
            stmt.setDouble(3, zone.getPrixLivraison());
            stmt.setBoolean(4, zone.isArchived());

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    zone.setId(rs.getInt(1));
                }
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Zone> listerTous() {
        List<Zone> zones = new ArrayList<>();
        String sql = "SELECT * FROM zones WHERE archived = false ORDER BY id";
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Zone z = new Zone();
                z.setId(rs.getInt("id"));
                z.setNom(rs.getString("nom"));
                z.setQuartiers(rs.getString("quartiers"));
                z.setPrixLivraison(rs.getDouble("prix_livraison"));
                z.setArchived(rs.getBoolean("archived"));
                zones.add(z);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return zones;
    }

    @Override
    public Zone obtenirParId(int id) {
        String sql = "SELECT * FROM zones WHERE id = ? AND archived = false";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Zone z = new Zone();
                z.setId(rs.getInt("id"));
                z.setNom(rs.getString("nom"));
                z.setQuartiers(rs.getString("quartiers"));
                z.setPrixLivraison(rs.getDouble("prix_livraison"));
                z.setArchived(rs.getBoolean("archived"));
                return z;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean modifier(Zone zone) {
        String sql = "UPDATE zones SET nom = ?, quartiers = ?, prix_livraison = ? WHERE id = ?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, zone.getNom());
            stmt.setString(2, zone.getQuartiers());
            stmt.setDouble(3, zone.getPrixLivraison());
            stmt.setInt(4, zone.getId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean archiver(int id) {
        String sql = "UPDATE zones SET archived = true WHERE id = ?";
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
