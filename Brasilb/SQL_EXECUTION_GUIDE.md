# 🗄️ Guide Exécution Script SQL - BrasilBurger

## ⚠️ Important - AVANT DE COMMENCER

Le script `SQL_INSERT_IMAGES.sql` a été **corrigé pour PostgreSQL/Neon**. Il n'utilise plus `LIMIT` qui cause une erreur.

---

## 🚀 Méthode 1: Neon Dashboard (RECOMMANDÉE)

### Étapes

#### 1. Accéder à Neon Console
- Allez sur [console.neon.tech](https://console.neon.tech)
- Connectez-vous avec votre compte Neon
- Sélectionnez votre projet **MORETRY**

#### 2. Naviguer vers SQL Editor
```
Dashboard
  → Projects
  → MORETRY (Votre projet)
  → SQL Editor (Menu de gauche)
```

#### 3. Copier le Script
- Ouvrez le fichier `Brasilb/SQL_INSERT_IMAGES.sql`
- Sélectionnez tout (Ctrl+A)
- Copiez (Ctrl+C)

#### 4. Coller dans Neon
- Dans Neon SQL Editor, cliquez dans la zone de texte
- Collez le code (Ctrl+V)
- Vous devriez voir le code SQL entier

#### 5. Exécuter
```
Bouton: [Run] ou [Execute]
Ou: Ctrl+Enter
```

#### 6. Vérifier les Résultats
```
✅ Attendez 2-3 secondes
✅ Vous verrez les résultats:
   - Burgers Total: 5
   - Menus Total: 4
   - Complements Total: 11
   - Zones Total: 8

   + Affichage de tous les produits créés
```

---

## 🚀 Méthode 2: pgAdmin (Alternative)

### Prérequis
- Installer [pgAdmin](https://www.pgadmin.org/download/)

### Étapes

#### 1. Ouvrir pgAdmin
- Lancez pgAdmin
- Connexion: localhost:5050 (par défaut)

#### 2. Ajouter Serveur Neon
```
Servers (Right-click)
  → Register
  → Server...

Nom: Neon-MORETRY
Host: ep-sparkling-river-ahs28is5-pooler.c-3.us-east-1.aws.neon.tech
Database: neondb
Username: neondb_owner
Password: [Votre mot de passe]
```

#### 3. Naviguer vers Query Tool
```
Serveur Neon
  → Databases
  → neondb (Right-click)
  → Query Tool
```

#### 4. Coller le Script
- Copiez le contenu de `SQL_INSERT_IMAGES.sql`
- Collez dans la zone de texte

#### 5. Exécuter
```
Bouton: F5 (ou Play/Execute)
```

#### 6. Vérifier
- Regardez dans le panneau "Messages"
- Vous devriez voir les INSERT réussis

---

## 🚀 Méthode 3: Command Line (psql)

### Prérequis
```bash
# Installer psql (PostgreSQL client)
# Windows: https://www.postgresql.org/download/windows/
# Mac: brew install postgresql
# Linux: sudo apt install postgresql-client
```

### Étapes

#### 1. Ouvrir Terminal/PowerShell
```bash
# Windows PowerShell
# ou Command Prompt
```

#### 2. Tester la Connexion
```bash
psql -U neondb_owner \
  -h ep-sparkling-river-ahs28is5-pooler.c-3.us-east-1.aws.neon.tech \
  -d neondb \
  -c "SELECT version();"
```

#### 3. Exécuter le Script
```bash
# Méthode A: Depuis le fichier
psql -U neondb_owner \
  -h ep-sparkling-river-ahs28is5-pooler.c-3.us-east-1.aws.neon.tech \
  -d neondb \
  -f "c:\Users\Hp\Downloads\MORETRY\Brasilb\SQL_INSERT_IMAGES.sql"

# Méthode B: Interactive
psql -U neondb_owner \
  -h ep-sparkling-river-ahs28is5-pooler.c-3.us-east-1.aws.neon.tech \
  -d neondb

# Puis dans psql:
\i 'c:\Users\Hp\Downloads\MORETRY\Brasilb\SQL_INSERT_IMAGES.sql'
```

#### 4. Vérifier
```sql
SELECT COUNT(*) as total_burgers FROM burgers;
SELECT COUNT(*) as total_menus FROM menus;
SELECT COUNT(*) as total_complements FROM complements;
SELECT COUNT(*) as total_zones FROM zones;
```

---

## 🚀 Méthode 4: DBeaver (Professionnel)

### Prérequis
- Installer [DBeaver Community](https://dbeaver.io/download/)

### Étapes

#### 1. Créer Connexion PostgreSQL
```
Database
  → New Database Connection
  → PostgreSQL
  → Next

Configuration:
  Server Host: ep-sparkling-river-ahs28is5-pooler.c-3.us-east-1.aws.neon.tech
  Database: neondb
  Username: neondb_owner
  Password: [Votre mot de passe]
  Port: 5432
  
Test Connection (recommandé)
```

#### 2. Naviguer vers Query Editor
```
Votre Connexion
  → Right-click
  → SQL Editor
  → New SQL Script
```

#### 3. Ouvrir le Fichier
```
File
  → Open File
  → Sélectionnez SQL_INSERT_IMAGES.sql
```

#### 4. Exécuter
```
Ctrl+Enter (ou bouton Execute)
```

#### 5. Vérifier
- Voir les résultats en bas
- Vérifier les counts affichés

---

## ✅ Résultats Attendus

### Si Succès ✓

```
INSERT 0 5        (Burgers insérés)
INSERT 0 4        (Menus insérés)
INSERT 0 6        (Boissons insérées)
INSERT 0 5        (Frites insérées)
INSERT 0 8        (Zones insérées)

Les SELECT affichent:
Burgers Total:        5
Menus Total:          4
Complements Total:   11
Zones Total:          8

Affichage détaillé de tous les produits
```

### Si Erreur ✗

```
Erreur courante: "relation does not exist"
→ Vérifie que tu es connecté à la bonne BD

Erreur: "duplicate key"
→ Normal si exécuté plusieurs fois (ON CONFLICT DO NOTHING)

Erreur: "LIMIT not supported"
→ Utilise la version CORRIGÉE du script
```

---

## 🔍 Vérification Post-Exécution

### Via Neon/pgAdmin

```sql
-- Vérifier les burgers
SELECT id, nom, prix, image_url FROM burgers ORDER BY id;

-- Vérifier les menus
SELECT id, nom, image_url FROM menus ORDER BY id;

-- Vérifier les boissons
SELECT id, nom, prix FROM complements WHERE type = 'BOISSON' ORDER BY id;

-- Vérifier les frites
SELECT id, nom, prix FROM complements WHERE type = 'FRITE' ORDER BY id;

-- Vérifier les zones
SELECT id, nom, prix_livraison FROM zones ORDER BY id;

-- Total général
SELECT 
  (SELECT COUNT(*) FROM burgers) as burgers,
  (SELECT COUNT(*) FROM menus) as menus,
  (SELECT COUNT(*) FROM complements) as complements,
  (SELECT COUNT(*) FROM zones) as zones;
```

---

## 🖼️ Vérifier Images en App

### Dans l'Application

1. Lancez: `dotnet run`
2. Allez sur: `https://localhost:5001/Catalog`
3. Vérifiez:
   - ✅ 5 burgers affichés
   - ✅ Chacun avec une image (Unsplash)
   - ✅ Prix corrects
   - ✅ 4 menus avec images
   - ✅ Boissons et frites dans le filtre

### Si Images ne s'Affichent Pas

```
1. Vérifier la console du navigateur (F12)
2. Chercher erreurs CORS
3. Vérifier que les URLs Unsplash sont valides
4. Essayer une URL directement dans le navigateur
5. Vérifier que le script SQL a bien inséré les URLs
```

---

## 🐛 Dépannage

### Problème: Connexion Refusée
```
Solution:
1. Vérifier le mot de passe (copy-paste depuis Neon)
2. Vérifier l'adresse host exacte
3. Vérifier que le VPN/Firewall permet PostgreSQL
4. Tester avec ping au host
```

### Problème: Base de Données Non Trouvée
```
Solution:
1. Vérifier le nom: "neondb" (pas "brasilb")
2. Vérifier dans Neon Console que la BD existe
3. Vérifier les permissions de l'utilisateur
```

### Problème: Erreur de Syntaxe SQL
```
Solution:
1. Utiliser le script CORRIGÉ (sans LIMIT)
2. Vérifier que tu copies le bon fichier
3. Vérifier qu'il n'y a pas d'accents mal encodés
```

### Problème: Données Doublées (Exécuté Plusieurs Fois)
```
Solution:
C'est normal! Le script utilise "ON CONFLICT DO NOTHING"
Résultat: INSERT 0 (aucune ligne insérée car déjà présentes)
Aucune donnée n'est doublée
```

---

## 📊 Commandes Utiles

### Supprimer les Données (si besoin)

```sql
-- Attention: Va supprimer TOUTES les données!

TRUNCATE TABLE burgers CASCADE;
TRUNCATE TABLE menus CASCADE;
TRUNCATE TABLE complements CASCADE;
TRUNCATE TABLE zones CASCADE;

-- Puis réexécuter le script SQL
```

### Mettre à Jour les Images

```sql
UPDATE burgers 
SET image_url = 'https://nouvelle-url-image' 
WHERE nom = 'Burger Name';
```

### Ajouter des Produits Supplémentaires

```sql
INSERT INTO burgers (nom, prix, image_url, disponible, archived)
VALUES ('Mon Burger', 4500, 'https://url-image', true, false);
```

---

## ✨ Tips & Tricks

### Copier Facilement l'URL de Connexion Neon
```
Neon Console
  → Connection details (ou icône copier)
  → Copy connection string
```

### Accélérer la Connexion
```
Utiliser le pooler Neon (par défaut)
Host: ...pooler.c-3.us-east-1.aws.neon.tech
(Plus rapide pour les connexions fréquentes)
```

### Tester Rapidement
```sql
-- Une seule ligne pour tout tester
SELECT COUNT(*) FROM burgers; SELECT COUNT(*) FROM menus; SELECT COUNT(*) FROM complements;
```

---

## 📋 Checklist Finale

- [ ] Script SQL copié (Brasilb/SQL_INSERT_IMAGES.sql)
- [ ] Connexion Neon testée
- [ ] Script exécuté sans erreur
- [ ] Résultats affichés (5 burgers, 4 menus, etc.)
- [ ] Application lancée (`dotnet run`)
- [ ] Images affichées dans le catalogue
- [ ] Tous les produits visibles

---

## 🎉 Succès!

Si vous avez ✅ toutes les étapes:

```
✓ Données insérées en BD
✓ Images disponibles (Unsplash)
✓ Burgers/Menus/Zones configurés
✓ Application prête à l'emploi

→ Vous pouvez maintenant explorer:
  - Catalogue complet
  - Ajouter au panier
  - Tester les filtres
  - Vérifier le responsive
```

---

*Guide complet d'exécution du script SQL - Aucune erreur avec la version corrigée! 🚀*