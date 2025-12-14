package com.brasilburger.model;

/**
 * Classe représentant un Menu
 * Un menu = Burger + Boisson + Frites
 * VERSION SIMPLE
 */
public class Menu {
    
    private int id;
    private String nom;
    private String imageUrl;
    private boolean disponible;
    private boolean archived;
    
    // Composition du menu
    private Burger burger;
    private Complement boisson;
    private Complement frite;
    
    // Constructeur vide
    public Menu() {
    }
    
    // Constructeur avec nom
    public Menu(String nom) {
        this.nom = nom;
        this.disponible = true;
        this.archived = false;
    }
    
    // Getters et Setters
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getNom() {
        return nom;
    }
    
    public void setNom(String nom) {
        this.nom = nom;
    }
    
    public String getImageUrl() {
        return imageUrl;
    }
    
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    
    public boolean isDisponible() {
        return disponible;
    }
    
    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }
    
    public boolean isArchived() {
        return archived;
    }
    
    public void setArchived(boolean archived) {
        this.archived = archived;
    }
    
    public Burger getBurger() {
        return burger;
    }
    
    public void setBurger(Burger burger) {
        this.burger = burger;
    }
    
    public Complement getBoisson() {
        return boisson;
    }
    
    public void setBoisson(Complement boisson) {
        this.boisson = boisson;
    }
    
    public Complement getFrite() {
        return frite;
    }
    
    public void setFrite(Complement frite) {
        this.frite = frite;
    }
    
    /**
     * Calculer le prix total du menu
     * Prix = Prix du burger + Prix boisson + Prix frite
     */
    public double getPrixTotal() {
        double total = 0;
        
        if (burger != null) {
            total += burger.getPrix();
        }
        if (boisson != null) {
            total += boisson.getPrix();
        }
        if (frite != null) {
            total += frite.getPrix();
        }
        
        return total;
    }
    
    @Override
    public String toString() {
        return "Menu{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", prixTotal=" + getPrixTotal() +
                '}';
    }
}