package com.brasilburger.model;

/**
 * Classe représentant un Gestionnaire
 */
public class Gestionnaire {
    private int id;
    private String nom;
    private String prenom;
    private String email;
    private String telephone;
    private String password;
    private boolean archived;

    public Gestionnaire() {
    }

    public Gestionnaire(String nom, String prenom, String email, String telephone, String password) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.telephone = telephone;
        this.password = password;
        this.archived = false;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getPrenom() { return prenom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getTelephone() { return telephone; }
    public void setTelephone(String telephone) { this.telephone = telephone; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public boolean isArchived() { return archived; }
    public void setArchived(boolean archived) { this.archived = archived; }

    @Override
    public String toString() {
        return "Gestionnaire{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
