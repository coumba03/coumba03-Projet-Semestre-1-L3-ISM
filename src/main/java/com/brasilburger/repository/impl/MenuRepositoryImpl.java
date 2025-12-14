package com.brasilburger.repository.impl;

import com.brasilburger.config.DatabaseConnection;
import com.brasilburger.repository.MenuRepository;
import com.brasilburger.repository.BurgerRepository;
import com.brasilburger.repository.ComplementRepository;
import com.brasilburger.model.Menu;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MenuRepositoryImpl implements MenuRepository {

    private Connection connection;
    private BurgerRepository burgerDAO;
    private ComplementRepository complementDAO;

    public MenuRepositoryImpl() {
        this.connection = DatabaseConnection.getConnection();
        this.burgerDAO = new com.brasilburger.repository.impl.BurgerRepositoryImpl();
        this.complementDAO = new com.brasilburger.repository.impl.ComplementRepositoryImpl();
    }

    @Override
    public boolean creer(Menu menu, int burgerId, int boissonId, int friteId) {
        try {
            String sqlMenu = "INSERT INTO menus (nom, image_url, disponible, archived) VALUES (?, ?, ?, ?)";
            PreparedStatement stmtMenu = connection.prepareStatement(sqlMenu, Statement.RETURN_GENERATED_KEYS);
            stmtMenu.setString(1, menu.getNom());
            stmtMenu.setString(2, menu.getImageUrl());
            stmtMenu.setBoolean(3, menu.isDisponible());
            stmtMenu.setBoolean(4, menu.isArchived());

            int rowsAffected = stmtMenu.executeUpdate();

            if (rowsAffected > 0) {
                ResultSet rs = stmtMenu.getGeneratedKeys();
                if (rs.next()) {
                    menu.setId(rs.getInt(1));
                }

                String sqlComposition = "INSERT INTO menu_composition (menu_id, burger_id, boisson_id, frite_id) VALUES (?, ?, ?, ?)";
                PreparedStatement stmtComp = connection.prepareStatement(sqlComposition);
                stmtComp.setInt(1, menu.getId());
                stmtComp.setInt(2, burgerId);
                stmtComp.setInt(3, boissonId);
                stmtComp.setInt(4, friteId);

                stmtComp.executeUpdate();

                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Menu> listerTous() {
        List<Menu> menus = new ArrayList<>();
        String sql = "SELECT m.*, mc.burger_id, mc.boisson_id, mc.frite_id " +
                     "FROM menus m " +
                     "LEFT JOIN menu_composition mc ON m.id = mc.menu_id " +
                     "WHERE m.archived = false " +
                     "ORDER BY m.id";

        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Menu menu = new Menu();
                menu.setId(rs.getInt("id"));
                menu.setNom(rs.getString("nom"));
                menu.setImageUrl(rs.getString("image_url"));
                menu.setDisponible(rs.getBoolean("disponible"));
                menu.setArchived(rs.getBoolean("archived"));

                int burgerId = rs.getInt("burger_id");
                int boissonId = rs.getInt("boisson_id");
                int friteId = rs.getInt("frite_id");

                if (burgerId > 0) {
                    menu.setBurger(burgerDAO.obtenirParId(burgerId));
                }
                if (boissonId > 0) {
                    menu.setBoisson(complementDAO.obtenirParId(boissonId));
                }
                if (friteId > 0) {
                    menu.setFrite(complementDAO.obtenirParId(friteId));
                }

                menus.add(menu);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return menus;
    }

    @Override
    public Menu obtenirParId(int id) {
        String sql = "SELECT m.*, mc.burger_id, mc.boisson_id, mc.frite_id " +
                     "FROM menus m " +
                     "LEFT JOIN menu_composition mc ON m.id = mc.menu_id " +
                     "WHERE m.id = ?";

        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Menu menu = new Menu();
                menu.setId(rs.getInt("id"));
                menu.setNom(rs.getString("nom"));
                menu.setImageUrl(rs.getString("image_url"));
                menu.setDisponible(rs.getBoolean("disponible"));
                menu.setArchived(rs.getBoolean("archived"));

                int burgerId = rs.getInt("burger_id");
                int boissonId = rs.getInt("boisson_id");
                int friteId = rs.getInt("frite_id");

                if (burgerId > 0) {
                    menu.setBurger(burgerDAO.obtenirParId(burgerId));
                }
                if (boissonId > 0) {
                    menu.setBoisson(complementDAO.obtenirParId(boissonId));
                }
                if (friteId > 0) {
                    menu.setFrite(complementDAO.obtenirParId(friteId));
                }

                return menu;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public boolean modifier(Menu menu, int burgerId, int boissonId, int friteId) {
        try {
            String sqlMenu = "UPDATE menus SET nom = ?, image_url = ?, disponible = ? WHERE id = ?";
            PreparedStatement stmtMenu = connection.prepareStatement(sqlMenu);
            stmtMenu.setString(1, menu.getNom());
            stmtMenu.setString(2, menu.getImageUrl());
            stmtMenu.setBoolean(3, menu.isDisponible());
            stmtMenu.setInt(4, menu.getId());

            int rowsAffected = stmtMenu.executeUpdate();

            if (rowsAffected > 0) {
                String sqlComp = "UPDATE menu_composition SET burger_id = ?, boisson_id = ?, frite_id = ? WHERE menu_id = ?";
                PreparedStatement stmtComp = connection.prepareStatement(sqlComp);
                stmtComp.setInt(1, burgerId);
                stmtComp.setInt(2, boissonId);
                stmtComp.setInt(3, friteId);
                stmtComp.setInt(4, menu.getId());

                stmtComp.executeUpdate();

                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean archiver(int id) {
        String sql = "UPDATE menus SET archived = true, disponible = false WHERE id = ?";

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
