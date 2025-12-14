package com.brasilburger.repository.impl;

import com.brasilburger.config.DatabaseConnection;
import com.brasilburger.model.MenuItem;
import com.brasilburger.repository.MenuItemRepository;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MenuItemRepositoryImpl implements MenuItemRepository {
    private Connection connection;

    public MenuItemRepositoryImpl() {
        this.connection = DatabaseConnection.getConnection();
    }

    @Override
    public boolean creer(MenuItem item) {
        String sql = "INSERT INTO menu_items (menu_id, complement_id, type) VALUES (?, ?, ?)";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setInt(1, item.getMenuId());
            stmt.setInt(2, item.getComplementId());
            stmt.setString(3, item.getType());

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
    public List<MenuItem> listerTous() {
        List<MenuItem> items = new ArrayList<>();
        String sql = "SELECT * FROM menu_items ORDER BY id";
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                MenuItem item = new MenuItem();
                item.setId(rs.getInt("id"));
                item.setMenuId(rs.getInt("menu_id"));
                item.setComplementId(rs.getInt("complement_id"));
                item.setType(rs.getString("type"));
                items.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }

    @Override
    public MenuItem obtenirParId(int id) {
        String sql = "SELECT * FROM menu_items WHERE id = ?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                MenuItem item = new MenuItem();
                item.setId(rs.getInt("id"));
                item.setMenuId(rs.getInt("menu_id"));
                item.setComplementId(rs.getInt("complement_id"));
                item.setType(rs.getString("type"));
                return item;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<MenuItem> listerParMenu(int menuId) {
        List<MenuItem> items = new ArrayList<>();
        String sql = "SELECT * FROM menu_items WHERE menu_id = ? ORDER BY id";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, menuId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                MenuItem item = new MenuItem();
                item.setId(rs.getInt("id"));
                item.setMenuId(rs.getInt("menu_id"));
                item.setComplementId(rs.getInt("complement_id"));
                item.setType(rs.getString("type"));
                items.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }

    @Override
    public boolean modifier(MenuItem item) {
        String sql = "UPDATE menu_items SET menu_id = ?, complement_id = ?, type = ? WHERE id = ?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, item.getMenuId());
            stmt.setInt(2, item.getComplementId());
            stmt.setString(3, item.getType());
            stmt.setInt(4, item.getId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean supprimer(int id) {
        String sql = "DELETE FROM menu_items WHERE id = ?";
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
