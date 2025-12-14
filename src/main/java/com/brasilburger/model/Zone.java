package com.brasilburger.model;

/**
 * Classe représentant une Zone de livraison
 */
public class Zone {
    private int id;
    private String nom;
    private String quartiers;
    private double prixLivraison;
    private boolean archived;

    public Zone() {
    }

    public Zone(String nom, String quartiers, double prixLivraison) {
        this.nom = nom;
        this.quartiers = quartiers;
        this.prixLivraison = prixLivraison;
        this.archived = false;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getQuartiers() { return quartiers; }
    public void setQuartiers(String quartiers) { this.quartiers = quartiers; }

    public double getPrixLivraison() { return prixLivraison; }
    public void setPrixLivraison(double prixLivraison) { this.prixLivraison = prixLivraison; }

    public boolean isArchived() { return archived; }
    public void setArchived(boolean archived) { this.archived = archived; }

    @Override
    public String toString() {
        return "Zone{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", prixLivraison=" + prixLivraison +
                '}';
    }
}
