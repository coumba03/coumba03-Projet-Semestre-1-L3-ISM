package com.brasilburger.repository.impl;

import com.brasilburger.config.DatabaseConnection;
import com.brasilburger.repository.BurgerRepository;
import com.brasilburger.model.Burger;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BurgerRepositoryImpl implements BurgerRepository {

    private Connection connection;

    public BurgerRepositoryImpl() {
        this.connection = DatabaseConnection.getConnection();
    }

    @Override
    public boolean creer(Burger burger) {
        String sql = "INSERT INTO burgers (nom, prix, image_url, disponible, archived) VALUES (?, ?, ?, ?, ?)";

        try {
            PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, burger.getNom());
            stmt.setDouble(2, burger.getPrix());
            stmt.setString(3, burger.getImageUrl());
            stmt.setBoolean(4, burger.isDisponible());
            stmt.setBoolean(5, burger.isArchived());

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    burger.setId(rs.getInt(1));
                }
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Burger> listerTous() {
        List<Burger> burgers = new ArrayList<>();
        String sql = "SELECT * FROM burgers WHERE archived = false ORDER BY id";

        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Burger burger = new Burger();
                burger.setId(rs.getInt("id"));
                burger.setNom(rs.getString("nom"));
                burger.setPrix(rs.getDouble("prix"));
                burger.setImageUrl(rs.getString("image_url"));
                burger.setDisponible(rs.getBoolean("disponible"));
                burger.setArchived(rs.getBoolean("archived"));

                burgers.add(burger);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return burgers;
    }

    @Override
    public List<Burger> listerDisponibles() {
        List<Burger> burgers = new ArrayList<>();
        String sql = "SELECT * FROM burgers WHERE archived = false AND disponible = true ORDER BY id";

        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Burger burger = new Burger();
                burger.setId(rs.getInt("id"));
                burger.setNom(rs.getString("nom"));
                burger.setPrix(rs.getDouble("prix"));
                burger.setImageUrl(rs.getString("image_url"));
                burger.setDisponible(rs.getBoolean("disponible"));

                burgers.add(burger);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return burgers;
    }

    @Override
    public Burger obtenirParId(int id) {
        String sql = "SELECT * FROM burgers WHERE id = ?";

        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Burger burger = new Burger();
                burger.setId(rs.getInt("id"));
                burger.setNom(rs.getString("nom"));
                burger.setPrix(rs.getDouble("prix"));
                burger.setImageUrl(rs.getString("image_url"));
                burger.setDisponible(rs.getBoolean("disponible"));
                burger.setArchived(rs.getBoolean("archived"));

                return burger;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public boolean modifier(Burger burger) {
        String sql = "UPDATE burgers SET nom = ?, prix = ?, image_url = ?, disponible = ? WHERE id = ?";

        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, burger.getNom());
            stmt.setDouble(2, burger.getPrix());
            stmt.setString(3, burger.getImageUrl());
            stmt.setBoolean(4, burger.isDisponible());
            stmt.setInt(5, burger.getId());

            int rowsAffected = stmt.executeUpdate();

            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean archiver(int id) {
        String sql = "UPDATE burgers SET archived = true, disponible = false WHERE id = ?";

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

    @Override
    public boolean supprimer(int id) {
        String sql = "DELETE FROM burgers WHERE id = ?";

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
