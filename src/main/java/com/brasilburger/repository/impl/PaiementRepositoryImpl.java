package com.brasilburger.repository.impl;

import com.brasilburger.config.DatabaseConnection;
import com.brasilburger.model.Paiement;
import com.brasilburger.model.enums.ModePaiement;
import com.brasilburger.repository.PaiementRepository;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PaiementRepositoryImpl implements PaiementRepository {
    private Connection connection;

    public PaiementRepositoryImpl() {
        this.connection = DatabaseConnection.getConnection();
    }

    @Override
    public boolean creer(Paiement paiement) {
        String sql = "INSERT INTO paiements (commande_id, montant, mode_paiement, date_paiement, valide, reference_transaction) VALUES (?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setInt(1, paiement.getCommandeId());
            stmt.setDouble(2, paiement.getMontant());
            stmt.setString(3, paiement.getModePaiement().toString());
            stmt.setTimestamp(4, Timestamp.valueOf(paiement.getDatePaiement()));
            stmt.setBoolean(5, paiement.isValide());
            stmt.setString(6, paiement.getReferenceTransaction());

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    paiement.setId(rs.getInt(1));
                }
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Paiement> listerTous() {
        List<Paiement> paiements = new ArrayList<>();
        String sql = "SELECT * FROM paiements ORDER BY id DESC";
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Paiement p = new Paiement();
                p.setId(rs.getInt("id"));
                p.setCommandeId(rs.getInt("commande_id"));
                p.setMontant(rs.getDouble("montant"));
                p.setModePaiement(ModePaiement.valueOf(rs.getString("mode_paiement")));
                p.setDatePaiement(rs.getTimestamp("date_paiement").toLocalDateTime());
                p.setValide(rs.getBoolean("valide"));
                p.setReferenceTransaction(rs.getString("reference_transaction"));
                paiements.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return paiements;
    }

    @Override
    public Paiement obtenirParId(int id) {
        String sql = "SELECT * FROM paiements WHERE id = ?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Paiement p = new Paiement();
                p.setId(rs.getInt("id"));
                p.setCommandeId(rs.getInt("commande_id"));
                p.setMontant(rs.getDouble("montant"));
                p.setModePaiement(ModePaiement.valueOf(rs.getString("mode_paiement")));
                p.setDatePaiement(rs.getTimestamp("date_paiement").toLocalDateTime());
                p.setValide(rs.getBoolean("valide"));
                p.setReferenceTransaction(rs.getString("reference_transaction"));
                return p;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Paiement obtenirParCommande(int commandeId) {
        String sql = "SELECT * FROM paiements WHERE commande_id = ?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, commandeId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Paiement p = new Paiement();
                p.setId(rs.getInt("id"));
                p.setCommandeId(rs.getInt("commande_id"));
                p.setMontant(rs.getDouble("montant"));
                p.setModePaiement(ModePaiement.valueOf(rs.getString("mode_paiement")));
                p.setDatePaiement(rs.getTimestamp("date_paiement").toLocalDateTime());
                p.setValide(rs.getBoolean("valide"));
                p.setReferenceTransaction(rs.getString("reference_transaction"));
                return p;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Paiement> listerParClient(int clientId) {
        List<Paiement> paiements = new ArrayList<>();
        String sql = "SELECT p.* FROM paiements p JOIN commandes c ON p.commande_id = c.id WHERE c.client_id = ? ORDER BY p.id DESC";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, clientId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Paiement p = new Paiement();
                p.setId(rs.getInt("id"));
                p.setCommandeId(rs.getInt("commande_id"));
                p.setMontant(rs.getDouble("montant"));
                p.setModePaiement(ModePaiement.valueOf(rs.getString("mode_paiement")));
                p.setDatePaiement(rs.getTimestamp("date_paiement").toLocalDateTime());
                p.setValide(rs.getBoolean("valide"));
                p.setReferenceTransaction(rs.getString("reference_transaction"));
                paiements.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return paiements;
    }

    @Override
    public boolean modifier(Paiement paiement) {
        String sql = "UPDATE paiements SET montant = ?, mode_paiement = ?, valide = ?, reference_transaction = ? WHERE id = ?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setDouble(1, paiement.getMontant());
            stmt.setString(2, paiement.getModePaiement().toString());
            stmt.setBoolean(3, paiement.isValide());
            stmt.setString(4, paiement.getReferenceTransaction());
            stmt.setInt(5, paiement.getId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
