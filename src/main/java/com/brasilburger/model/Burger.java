package com.brasilburger.model;

/**
 * Classe représentant un Burger
 * VERSION SIMPLE - Juste un objet avec getters/setters
 */
public class Burger {
    
    private int id;
    private String nom;
    private double prix;
    private String imageUrl;
    private boolean disponible;
    private boolean archived;
    
    // Constructeur vide
    public Burger() {
    }
    
    // Constructeur avec paramètres
    public Burger(String nom, double prix) {
        this.nom = nom;
        this.prix = prix;
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
    
    public double getPrix() {
        return prix;
    }
    
    public void setPrix(double prix) {
        this.prix = prix;
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
    
    // Méthode pour afficher les infos du burger
    @Override
    public String toString() {
        return "Burger{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", prix=" + prix +
                ", disponible=" + disponible +
                '}';
    }
}