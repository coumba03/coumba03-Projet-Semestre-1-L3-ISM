-- ========================================
-- Script SQL pour ajouter les images
-- Exécutez ce script dans votre base de données PostgreSQL (Neon)
-- COMPATIBLE AVEC POSTGRESQL (pas de LIMIT dans UPDATE)
-- ========================================

-- Insertion de Burgers d'exemple avec images (si la table est vide)
INSERT INTO burgers (nom, prix, image_url, disponible, archived) VALUES
('BrasilBurger Classic', 3500, 'https://images.unsplash.com/photo-1568901346375-23c9450c58cd?w=500&h=500&fit=crop', true, false),
('Double Cheese Brasil', 5000, 'https://images.unsplash.com/photo-1550547660-d9450f859349?w=500&h=500&fit=crop', true, false),
('Bacon Royale', 6000, 'https://images.unsplash.com/photo-1553979459-d2229ba7433b?w=500&h=500&fit=crop', true, false),
('Brasil Veggie', 4000, 'https://images.unsplash.com/photo-1585238341710-4913b706a68a?w=500&h=500&fit=crop', true, false),
('Spicy Chili Burger', 5500, 'https://images.unsplash.com/photo-1572802419224-296b0aeee0d9?w=500&h=500&fit=crop', true, false)
ON CONFLICT DO NOTHING;

-- Insertion de Menus avec images
INSERT INTO menus (nom, image_url, disponible, archived) VALUES
('Menu Classique', 'https://images.unsplash.com/photo-1568901346375-23c9450c58cd?w=500&h=500&fit=crop', true, false),
('Menu Gourmand', 'https://images.unsplash.com/photo-1550547660-d9450f859349?w=500&h=500&fit=crop', true, false),
('Menu Découverte', 'https://images.unsplash.com/photo-1553979459-d2229ba7433b?w=500&h=500&fit=crop', true, false),
('Menu Végétarien', 'https://images.unsplash.com/photo-1585238341710-4913b706a68a?w=500&h=500&fit=crop', true, false)
ON CONFLICT DO NOTHING;

-- Insertion de Complements (Boissons)
INSERT INTO complements (nom, prix, type, disponible, archived) VALUES
('Coca-Cola 33cl', 1000, 'BOISSON', true, false),
('Fanta Orange 33cl', 1000, 'BOISSON', true, false),
('Sprite 33cl', 1000, 'BOISSON', true, false),
('Jus d''Orange Naturel', 1500, 'BOISSON', true, false),
('Eau Gazeuse', 800, 'BOISSON', true, false),
('Eau Plate', 500, 'BOISSON', true, false)
ON CONFLICT DO NOTHING;

-- Insertion de Complements (Frites)
INSERT INTO complements (nom, prix, type, disponible, archived) VALUES
('Frites Régulières', 1500, 'FRITE', true, false),
('Frites Grosses', 2000, 'FRITE', true, false),
('Frites Maison Croquantes', 2500, 'FRITE', true, false),
('Frites Épicées', 2000, 'FRITE', true, false),
('Frites au Fromage', 3000, 'FRITE', true, false)
ON CONFLICT DO NOTHING;

-- Insertion de Zones de livraison
INSERT INTO zones (nom, prix_livraison) VALUES
('Centre-Ville', 1000),
('Plateau', 1500),
('Médina', 1500),
('Parcelles Assainies', 2000),
('Grand Yoff', 2000),
('Ngor', 2000),
('Ouakam', 1500),
('Fann', 1000)
ON CONFLICT DO NOTHING;

-- Vérification des insertions
SELECT 'Burgers Total:' as metric, COUNT(*) FROM burgers
UNION ALL
SELECT 'Menus Total:', COUNT(*) FROM menus
UNION ALL
SELECT 'Complements Total:', COUNT(*) FROM complements
UNION ALL
SELECT 'Zones Total:', COUNT(*) FROM zones;

-- Affichage des burgers avec images
SELECT id, nom, prix, CASE WHEN image_url IS NOT NULL THEN '✓' ELSE '✗' END as "Has Image" 
FROM burgers 
ORDER BY id;

-- Affichage des menus
SELECT id, nom, CASE WHEN image_url IS NOT NULL THEN '✓' ELSE '✗' END as "Has Image" 
FROM menus 
ORDER BY id;

-- Affichage des boissons
SELECT id, nom, prix, type 
FROM complements 
WHERE type = 'BOISSON'
ORDER BY id;

-- Affichage des frites
SELECT id, nom, prix, type 
FROM complements 
WHERE type = 'FRITE'
ORDER BY id;