package com.brasilburger.repository.impl;

import com.brasilburger.config.DatabaseConnection;
import com.brasilburger.model.Commande;
import com.brasilburger.model.enums.EtatCommande;
import com.brasilburger.model.enums.TypeCommande;
import com.brasilburger.repository.CommandeRepository;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CommandeRepositoryImpl implements CommandeRepository {
    private Connection connection;

    public CommandeRepositoryImpl() {
        this.connection = DatabaseConnection.getConnection();
    }

    @Override
    public boolean creer(Commande commande) {
        String sql = "INSERT INTO commandes (client_id, type_commande, etat, date_commande, montant_total, archived) VALUES (?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setInt(1, commande.getClientId());
            stmt.setString(2, commande.getTypeCommande().toString());
            stmt.setString(3, commande.getEtat().toString());
            stmt.setTimestamp(4, Timestamp.valueOf(commande.getDateCommande()));
            stmt.setDouble(5, commande.getMontantTotal());
            stmt.setBoolean(6, commande.isArchived());

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    commande.setId(rs.getInt(1));
                }
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Commande> listerTous() {
        List<Commande> commandes = new ArrayList<>();
        String sql = "SELECT * FROM commandes WHERE archived = false ORDER BY id DESC";
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Commande c = new Commande();
                c.setId(rs.getInt("id"));
                c.setClientId(rs.getInt("client_id"));
                c.setTypeCommande(TypeCommande.valueOf(rs.getString("type_commande")));
                c.setEtat(EtatCommande.valueOf(rs.getString("etat")));
                c.setDateCommande(rs.getTimestamp("date_commande").toLocalDateTime());
                c.setMontantTotal(rs.getDouble("montant_total"));
                c.setArchived(rs.getBoolean("archived"));
                commandes.add(c);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return commandes;
    }

    @Override
    public Commande obtenirParId(int id) {
        String sql = "SELECT * FROM commandes WHERE id = ? AND archived = false";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Commande c = new Commande();
                c.setId(rs.getInt("id"));
                c.setClientId(rs.getInt("client_id"));
                c.setTypeCommande(TypeCommande.valueOf(rs.getString("type_commande")));
                c.setEtat(EtatCommande.valueOf(rs.getString("etat")));
                c.setDateCommande(rs.getTimestamp("date_commande").toLocalDateTime());
                c.setMontantTotal(rs.getDouble("montant_total"));
                c.setArchived(rs.getBoolean("archived"));
                return c;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Commande> listerParClient(int clientId) {
        List<Commande> commandes = new ArrayList<>();
        String sql = "SELECT * FROM commandes WHERE client_id = ? AND archived = false ORDER BY id DESC";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, clientId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Commande c = new Commande();
                c.setId(rs.getInt("id"));
                c.setClientId(rs.getInt("client_id"));
                c.setTypeCommande(TypeCommande.valueOf(rs.getString("type_commande")));
                c.setEtat(EtatCommande.valueOf(rs.getString("etat")));
                c.setDateCommande(rs.getTimestamp("date_commande").toLocalDateTime());
                c.setMontantTotal(rs.getDouble("montant_total"));
                c.setArchived(rs.getBoolean("archived"));
                commandes.add(c);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return commandes;
    }

    @Override
    public boolean modifier(Commande commande) {
        String sql = "UPDATE commandes SET type_commande = ?, etat = ?, montant_total = ? WHERE id = ?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, commande.getTypeCommande().toString());
            stmt.setString(2, commande.getEtat().toString());
            stmt.setDouble(3, commande.getMontantTotal());
            stmt.setInt(4, commande.getId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean archiver(int id) {
        String sql = "UPDATE commandes SET archived = true WHERE id = ?";
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
