package com.brasilburger.repository.impl;

import com.brasilburger.config.DatabaseConnection;
import com.brasilburger.model.CommandeItem;
import com.brasilburger.model.enums.TypeItem;
import com.brasilburger.repository.CommandeItemRepository;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CommandeItemRepositoryImpl implements CommandeItemRepository {
    private Connection connection;

    public CommandeItemRepositoryImpl() {
        this.connection = DatabaseConnection.getConnection();
    }

    @Override
    public boolean creer(CommandeItem item) {
        String sql = "INSERT INTO commande_items (commande_id, type_item, item_id, quantite, prix_unitaire, sous_total) VALUES (?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setInt(1, item.getCommandeId());
            stmt.setString(2, item.getTypeItem().toString());
            stmt.setInt(3, item.getItemId());
            stmt.setInt(4, item.getQuantite());
            stmt.setDouble(5, item.getPrixUnitaire());
            stmt.setDouble(6, item.getSousTotal());

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    item.setId(rs.getInt(1));
                }
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<CommandeItem> listerTous() {
        List<CommandeItem> items = new ArrayList<>();
        String sql = "SELECT * FROM commande_items ORDER BY id";
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                CommandeItem item = new CommandeItem();
                item.setId(rs.getInt("id"));
                item.setCommandeId(rs.getInt("commande_id"));
                item.setTypeItem(TypeItem.valueOf(rs.getString("type_item")));
                item.setItemId(rs.getInt("item_id"));
                item.setQuantite(rs.getInt("quantite"));
                item.setPrixUnitaire(rs.getDouble("prix_unitaire"));
                item.setSousTotal(rs.getDouble("sous_total"));
                items.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }

    @Override
    public CommandeItem obtenirParId(int id) {
        String sql = "SELECT * FROM commande_items WHERE id = ?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                CommandeItem item = new CommandeItem();
                item.setId(rs.getInt("id"));
                item.setCommandeId(rs.getInt("commande_id"));
                item.setTypeItem(TypeItem.valueOf(rs.getString("type_item")));
                item.setItemId(rs.getInt("item_id"));
                item.setQuantite(rs.getInt("quantite"));
                item.setPrixUnitaire(rs.getDouble("prix_unitaire"));
                item.setSousTotal(rs.getDouble("sous_total"));
                return item;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<CommandeItem> listerParCommande(int commandeId) {
        List<CommandeItem> items = new ArrayList<>();
        String sql = "SELECT * FROM commande_items WHERE commande_id = ? ORDER BY id";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, commandeId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                CommandeItem item = new CommandeItem();
                item.setId(rs.getInt("id"));
                item.setCommandeId(rs.getInt("commande_id"));
                item.setTypeItem(TypeItem.valueOf(rs.getString("type_item")));
                item.setItemId(rs.getInt("item_id"));
                item.setQuantite(rs.getInt("quantite"));
                item.setPrixUnitaire(rs.getDouble("prix_unitaire"));
                item.setSousTotal(rs.getDouble("sous_total"));
                items.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }

    @Override
    public boolean modifier(CommandeItem item) {
        String sql = "UPDATE commande_items SET type_item = ?, item_id = ?, quantite = ?, prix_unitaire = ?, sous_total = ? WHERE id = ?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, item.getTypeItem().toString());
            stmt.setInt(2, item.getItemId());
            stmt.setInt(3, item.getQuantite());
            stmt.setDouble(4, item.getPrixUnitaire());
            stmt.setDouble(5, item.getSousTotal());
            stmt.setInt(6, item.getId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean supprimer(int id) {
        String sql = "DELETE FROM commande_items WHERE id = ?";
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
