package com.brasilburger.config;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Classe pour configurer Cloudinary (upload d'images)
 * VERSION SIMPLE POUR DÉBUTANT
 */
public class CloudinaryConfig {
    
    private static Cloudinary cloudinary;
    
    /**
     * Créer et retourner l'objet Cloudinary configuré
     */
    public static Cloudinary getCloudinary() {
        if (cloudinary == null) {
            // Charger les informations depuis config.properties
            Properties props = loadConfig();
            
            cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", props.getProperty("cloudinary.cloud_name"),
                "api_key", props.getProperty("cloudinary.api_key"),
                "api_secret", props.getProperty("cloudinary.api_secret")
            ));
        }
        return cloudinary;
    }
    
    /**
     * Charger le fichier de configuration
     */
    private static Properties loadConfig() {
        Properties props = new Properties();
        try {
            FileInputStream fis = new FileInputStream("config.properties");
            props.load(fis);
            fis.close();
        } catch (IOException e) {
            System.err.println("Erreur: Impossible de charger config.properties");
            e.printStackTrace();
        }
        return props;
    }
}