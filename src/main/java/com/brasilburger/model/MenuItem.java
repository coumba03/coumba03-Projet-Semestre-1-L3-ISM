package com.brasilburger.model;

/**
 * Classe représentant un article d'un Menu
 */
public class MenuItem {
    private int id;
    private int menuId;
    private int complementId;
    private String type;

    public MenuItem() {
    }

    public MenuItem(int menuId, int complementId, String type) {
        this.menuId = menuId;
        this.complementId = complementId;
        this.type = type;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getMenuId() { return menuId; }
    public void setMenuId(int menuId) { this.menuId = menuId; }

    public int getComplementId() { return complementId; }
    public void setComplementId(int complementId) { this.complementId = complementId; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    @Override
    public String toString() {
        return "MenuItem{" +
                "id=" + id +
                ", menuId=" + menuId +
                ", complementId=" + complementId +
                ", type='" + type + '\'' +
                '}';
    }
}
