package com.brasilburger.service;

public interface ImageService {
    String uploadImage(String cheminFichier, String dossier);
    void supprimerImage(String imageUrl);
}