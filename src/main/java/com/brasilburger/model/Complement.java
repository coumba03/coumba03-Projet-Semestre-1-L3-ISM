package com.brasilburger.model;

import com.brasilburger.model.enums.TypeComplement;

/**
 * Classe représentant un Complément (boisson ou frites)
 * VERSION SIMPLE
 */
public class Complement {
    
    private int id;
    private String nom;
    private String type; // "BOISSON" ou "FRITE" (stocké en String pour persistance JDBC)
    private double prix;
    private String imageUrl;
    private boolean disponible;
    private boolean archived;
    
    // Constructeur vide
    public Complement() {
    }
    
    // Constructeur avec paramètres
    public Complement(String nom, String type, double prix) {
        this.nom = nom;
        this.type = type;
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
    
    public String getType() {
        return type;
    }
    
    public void setType(String type) {
        this.type = type;
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
    
    @Override
    public String toString() {
        return "Complement{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", type='" + type + '\'' +
                ", prix=" + prix +
                '}';
    }
}