package com.brasilburger.repository.impl;

import com.brasilburger.config.DatabaseConnection;
import com.brasilburger.model.Gestionnaire;
import com.brasilburger.repository.GestionnaireRepository;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GestionnaireRepositoryImpl implements GestionnaireRepository {
    private Connection connection;

    public GestionnaireRepositoryImpl() {
        this.connection = DatabaseConnection.getConnection();
    }

    @Override
    public boolean creer(Gestionnaire gestionnaire) {
        String sql = "INSERT INTO gestionnaires (nom, prenom, email, telephone, password, archived) VALUES (?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, gestionnaire.getNom());
            stmt.setString(2, gestionnaire.getPrenom());
            stmt.setString(3, gestionnaire.getEmail());
            stmt.setString(4, gestionnaire.getTelephone());
            stmt.setString(5, gestionnaire.getPassword());
            stmt.setBoolean(6, gestionnaire.isArchived());

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    gestionnaire.setId(rs.getInt(1));
                }
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Gestionnaire> listerTous() {
        List<Gestionnaire> gestionnaires = new ArrayList<>();
        String sql = "SELECT * FROM gestionnaires WHERE archived = false ORDER BY id";
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Gestionnaire g = new Gestionnaire();
                g.setId(rs.getInt("id"));
                g.setNom(rs.getString("nom"));
                g.setPrenom(rs.getString("prenom"));
                g.setEmail(rs.getString("email"));
                g.setTelephone(rs.getString("telephone"));
                g.setPassword(rs.getString("password"));
                g.setArchived(rs.getBoolean("archived"));
                gestionnaires.add(g);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return gestionnaires;
    }

    @Override
    public Gestionnaire obtenirParId(int id) {
        String sql = "SELECT * FROM gestionnaires WHERE id = ? AND archived = false";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Gestionnaire g = new Gestionnaire();
                g.setId(rs.getInt("id"));
                g.setNom(rs.getString("nom"));
                g.setPrenom(rs.getString("prenom"));
                g.setEmail(rs.getString("email"));
                g.setTelephone(rs.getString("telephone"));
                g.setPassword(rs.getString("password"));
                g.setArchived(rs.getBoolean("archived"));
                return g;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Gestionnaire obtenirParEmail(String email) {
        String sql = "SELECT * FROM gestionnaires WHERE email = ? AND archived = false";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Gestionnaire g = new Gestionnaire();
                g.setId(rs.getInt("id"));
                g.setNom(rs.getString("nom"));
                g.setPrenom(rs.getString("prenom"));
                g.setEmail(rs.getString("email"));
                g.setTelephone(rs.getString("telephone"));
                g.setPassword(rs.getString("password"));
                g.setArchived(rs.getBoolean("archived"));
                return g;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean modifier(Gestionnaire gestionnaire) {
        String sql = "UPDATE gestionnaires SET nom = ?, prenom = ?, email = ?, telephone = ? WHERE id = ?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, gestionnaire.getNom());
            stmt.setString(2, gestionnaire.getPrenom());
            stmt.setString(3, gestionnaire.getEmail());
            stmt.setString(4, gestionnaire.getTelephone());
            stmt.setInt(5, gestionnaire.getId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean archiver(int id) {
        String sql = "UPDATE gestionnaires SET archived = true WHERE id = ?";
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
