-- Script SQL simplifié pour Brasil Burger
-- À exécuter sur https://neon.tech

-- Table des burgers
CREATE TABLE burgers (
    id SERIAL PRIMARY KEY,
    nom VARCHAR(100) NOT NULL,
    prix DECIMAL(10,2) NOT NULL,
    image_url VARCHAR(500),
    disponible BOOLEAN DEFAULT TRUE,
    archived BOOLEAN DEFAULT FALSE
);

-- Table des compléments
CREATE TABLE complements (
    id SERIAL PRIMARY KEY,
    nom VARCHAR(100) NOT NULL,
    type VARCHAR(20) NOT NULL, -- BOISSON ou FRITE
    prix DECIMAL(10,2) NOT NULL,
    image_url VARCHAR(500),
    disponible BOOLEAN DEFAULT TRUE,
    archived BOOLEAN DEFAULT FALSE
);

-- Table des menus
CREATE TABLE menus (
    id SERIAL PRIMARY KEY,
    nom VARCHAR(100) NOT NULL,
    image_url VARCHAR(500),
    disponible BOOLEAN DEFAULT TRUE,
    archived BOOLEAN DEFAULT FALSE
);

-- Table de composition des menus (quel burger, quelle boisson, quelles frites)
CREATE TABLE menu_composition (
    id SERIAL PRIMARY KEY,
    menu_id INTEGER REFERENCES menus(id),
    burger_id INTEGER REFERENCES burgers(id),
    boisson_id INTEGER REFERENCES complements(id),
    frite_id INTEGER REFERENCES complements(id)
);

-- Quelques données de test
INSERT INTO burgers (nom, prix, disponible) VALUES 
('Burger Classic', 5000, true),
('Burger Cheese', 6000, true);

INSERT INTO complements (nom, type, prix, disponible) VALUES 
('Coca Cola', 'BOISSON', 1000, true),
('Fanta', 'BOISSON', 1000, true),
('Frites Maison', 'FRITE', 1500, true);

CREATE TABLE IF NOT EXISTS clients (
    id SERIAL PRIMARY KEY,
    nom VARCHAR(100) NOT NULL,
    prenom VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    telephone VARCHAR(20) NOT NULL,
    password VARCHAR(255) NOT NULL,
    adresse VARCHAR(255),
    archived BOOLEAN DEFAULT false,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS gestionnaires (
    id SERIAL PRIMARY KEY,
    nom VARCHAR(100) NOT NULL,
    prenom VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    telephone VARCHAR(20) NOT NULL,
    password VARCHAR(255) NOT NULL,
    archived BOOLEAN DEFAULT false,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS zones (
    id SERIAL PRIMARY KEY,
    nom VARCHAR(100) NOT NULL,
    quartiers TEXT,
    prix_livraison DECIMAL(10, 2) NOT NULL,
    archived BOOLEAN DEFAULT false,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS livreurs (
    id SERIAL PRIMARY KEY,
    nom VARCHAR(100) NOT NULL,
    prenom VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    telephone VARCHAR(20) NOT NULL,
    password VARCHAR(255) NOT NULL,
    zone_id INTEGER REFERENCES zones(id),
    archived BOOLEAN DEFAULT false,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tables pour les commandes
CREATE TABLE IF NOT EXISTS commandes (
    id SERIAL PRIMARY KEY,
    client_id INTEGER REFERENCES clients(id),
    type_commande VARCHAR(50),
    etat VARCHAR(50) DEFAULT 'NEW',
    date_commande TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    montant_total DECIMAL(10, 2),
    archived BOOLEAN DEFAULT false
);

CREATE TABLE IF NOT EXISTS commande_items (
    id SERIAL PRIMARY KEY,
    commande_id INTEGER REFERENCES commandes(id) ON DELETE CASCADE,
    type_item VARCHAR(50),
    item_id INTEGER,
    quantite INTEGER,
    prix_unitaire DECIMAL(10, 2),
    sous_total DECIMAL(10, 2)
);

CREATE TABLE IF NOT EXISTS menu_items (
    id SERIAL PRIMARY KEY,
    menu_id INTEGER REFERENCES menus(id),
    complement_id INTEGER REFERENCES complements(id),
    type VARCHAR(100)
);

CREATE TABLE IF NOT EXISTS paiements (
    id SERIAL PRIMARY KEY,
    commande_id INTEGER REFERENCES commandes(id),
    montant DECIMAL(10, 2),
    mode_paiement VARCHAR(50),
    date_paiement TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    valide BOOLEAN DEFAULT false,
    reference_transaction VARCHAR(255)
);

-- Index pour améliorer les performances
CREATE INDEX IF NOT EXISTS idx_clients_email ON clients(email);
CREATE INDEX IF NOT EXISTS idx_gestionnaires_email ON gestionnaires(email);
CREATE INDEX IF NOT EXISTS idx_livreurs_email ON livreurs(email);
CREATE INDEX IF NOT EXISTS idx_livreurs_zone ON livreurs(zone_id);
CREATE INDEX IF NOT EXISTS idx_commandes_client ON commandes(client_id);
CREATE INDEX IF NOT EXISTS idx_commande_items_commande ON commande_items(commande_id);
CREATE INDEX IF NOT EXISTS idx_paiements_commande ON paiements(commande_id);
