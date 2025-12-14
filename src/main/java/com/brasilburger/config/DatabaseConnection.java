package com.brasilburger.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Classe pour gérer la connexion à la base de données PostgreSQL (Neon)
 * VERSION SIMPLE POUR DÉBUTANT
 */
public class DatabaseConnection {
    
    private static Connection connection;
    
    /**
     * Créer et retourner une connexion à la base de données
     */
    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                // Charger les informations depuis config.properties
                Properties props = loadConfig();
                
                String url = props.getProperty("db.url");
                String username = props.getProperty("db.username");
                String password = props.getProperty("db.password");
                
                // Créer la connexion
                connection = DriverManager.getConnection(url, username, password);
                System.out.println("✅ Connexion à la base de données réussie !");
            }
        } catch (SQLException e) {
            System.err.println("❌ Erreur de connexion à la base de données");
            e.printStackTrace();
        }
        return connection;
    }
    
    /**
     * Fermer la connexion
     */
    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Connexion fermée");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
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