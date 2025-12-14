package com.brasilburger.repository.impl;

import com.brasilburger.config.DatabaseConnection;
import com.brasilburger.repository.ComplementRepository;
import com.brasilburger.model.Complement;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ComplementRepositoryImpl implements ComplementRepository {

    private Connection connection;

    public ComplementRepositoryImpl() {
        this.connection = DatabaseConnection.getConnection();
    }

    @Override
    public boolean creer(Complement complement) {
        String sql = "INSERT INTO complements (nom, type, prix, image_url, disponible, archived) VALUES (?, ?, ?, ?, ?, ?)";

        try {
            PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, complement.getNom());
            stmt.setString(2, complement.getType());
            stmt.setDouble(3, complement.getPrix());
            stmt.setString(4, complement.getImageUrl());
            stmt.setBoolean(5, complement.isDisponible());
            stmt.setBoolean(6, complement.isArchived());

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    complement.setId(rs.getInt(1));
                }
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Complement> listerTous() {
        List<Complement> complements = new ArrayList<>();
        String sql = "SELECT * FROM complements WHERE archived = false ORDER BY type, nom";

        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Complement complement = new Complement();
                complement.setId(rs.getInt("id"));
                complement.setNom(rs.getString("nom"));
                complement.setType(rs.getString("type"));
                complement.setPrix(rs.getDouble("prix"));
                complement.setImageUrl(rs.getString("image_url"));
                complement.setDisponible(rs.getBoolean("disponible"));
                complement.setArchived(rs.getBoolean("archived"));

                complements.add(complement);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return complements;
    }

    @Override
    public List<Complement> listerParType(String type) {
        List<Complement> complements = new ArrayList<>();
        String sql = "SELECT * FROM complements WHERE archived = false AND type = ? AND disponible = true ORDER BY nom";

        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, type);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Complement complement = new Complement();
                complement.setId(rs.getInt("id"));
                complement.setNom(rs.getString("nom"));
                complement.setType(rs.getString("type"));
                complement.setPrix(rs.getDouble("prix"));
                complement.setImageUrl(rs.getString("image_url"));
                complement.setDisponible(rs.getBoolean("disponible"));

                complements.add(complement);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return complements;
    }

    @Override
    public Complement obtenirParId(int id) {
        String sql = "SELECT * FROM complements WHERE id = ?";

        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Complement complement = new Complement();
                complement.setId(rs.getInt("id"));
                complement.setNom(rs.getString("nom"));
                complement.setType(rs.getString("type"));
                complement.setPrix(rs.getDouble("prix"));
                complement.setImageUrl(rs.getString("image_url"));
                complement.setDisponible(rs.getBoolean("disponible"));
                complement.setArchived(rs.getBoolean("archived"));

                return complement;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public boolean modifier(Complement complement) {
        String sql = "UPDATE complements SET nom = ?, type = ?, prix = ?, image_url = ?, disponible = ? WHERE id = ?";

        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, complement.getNom());
            stmt.setString(2, complement.getType());
            stmt.setDouble(3, complement.getPrix());
            stmt.setString(4, complement.getImageUrl());
            stmt.setBoolean(5, complement.isDisponible());
            stmt.setInt(6, complement.getId());

            int rowsAffected = stmt.executeUpdate();

            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean archiver(int id) {
        String sql = "UPDATE complements SET archived = true, disponible = false WHERE id = ?";

        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, id);

            int rowsAffected = stmt.executeUpdate();

            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
