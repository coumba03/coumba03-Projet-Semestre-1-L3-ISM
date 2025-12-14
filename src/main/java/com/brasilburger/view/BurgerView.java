package com.brasilburger.view;

import com.brasilburger.service.BurgerService;
import com.brasilburger.model.Burger;
import com.brasilburger.service.ImageService;
import java.util.List;
import java.util.Scanner;

public class BurgerView {
    private Scanner scanner;
    private BurgerService burgerDAO;
    private ImageService imageService;

    public BurgerView(Scanner scanner, BurgerService burgerDAO, ImageService imageService) {
        this.scanner = scanner;
        this.burgerDAO = burgerDAO;
        this.imageService = imageService;
    }

    public void start() {
        boolean retour = false;

        while (!retour) {
            System.out.println("\n╔════════════════════════════════════════╗");
            System.out.println("║        GESTION DES BURGERS             ║");
            System.out.println("╠════════════════════════════════════════╣");
            System.out.println("║  1. Créer un burger                    ║");
            System.out.println("║  2. Lister tous les burgers            ║");
            System.out.println("║  3. Modifier un burger                 ║");
            System.out.println("║  4. Archiver un burger                 ║");
            System.out.println("║  0. Retour                             ║");
            System.out.println("╚════════════════════════════════════════╝");
            System.out.print("Votre choix : ");

            int choix = lireEntier();

            switch (choix) {
                case 1:
                    creerBurger();
                    break;
                case 2:
                    listerBurgers();
                    break;
                case 3:
                    modifierBurger();
                    break;
                case 4:
                    archiverBurger();
                    break;
                case 0:
                    retour = true;
                    break;
                default:
                    System.out.println(" Choix invalide !");
            }
        }
    }

    private void creerBurger() {
        System.out.println("\n CRÉATION D'UN BURGER");

        System.out.print("Nom du burger : ");
        String nom = scanner.nextLine();

        System.out.print("Prix (FCFA) : ");
        double prix = lireDouble();

        System.out.print("Chemin de l'image (ou ENTER pour ignorer) : ");
        String cheminImage = scanner.nextLine();

        Burger burger = new Burger(nom, prix);

        if (!cheminImage.trim().isEmpty()) {
            String imageUrl = imageService.uploadImage(cheminImage, "burgers");
            burger.setImageUrl(imageUrl);
        }

        if (burgerDAO.creer(burger)) {
            System.out.println("Burger créé avec succès !");
            System.out.println("   ID: " + burger.getId());
            System.out.println("   Nom: " + burger.getNom());
            System.out.println("   Prix: " + burger.getPrix() + " FCFA");
        }
    }

    private void listerBurgers() {
        System.out.println("\n LISTE DES BURGERS");

        List<Burger> burgers = burgerDAO.listerTous();

        if (burgers.isEmpty()) {
            System.out.println("Aucun burger trouvé.");
            return;
        }

        System.out.println("╔════╦═══════════════════════╦═══════════╦══════════════╗");
        System.out.println("║ ID ║        NOM            ║   PRIX    ║  DISPONIBLE  ║");
        System.out.println("╠════╬═══════════════════════╬═══════════╬══════════════╣");

        for (Burger burger : burgers) {
            String disponible = burger.isDisponible() ? " Oui" : " Non";
            System.out.printf("║ %2d ║ %-21s ║ %7.0f F ║ %12s ║%n", 
                burger.getId(), 
                burger.getNom(), 
                burger.getPrix(), 
                disponible);
        }

        System.out.println("╚════╩═══════════════════════╩═══════════╩══════════════╝");
    }

    private void modifierBurger() {
        System.out.println("\n MODIFICATION D'UN BURGER");

        listerBurgers();

        System.out.print("\nID du burger à modifier : ");
        int id = lireEntier();

        Burger burger = burgerDAO.obtenirParId(id);
        if (burger == null) {
            System.out.println(" Burger non trouvé !");
            return;
        }

        System.out.println("\nBurger actuel: " + burger.getNom() + " - " + burger.getPrix() + " FCFA");

        System.out.print("Nouveau nom (ENTER pour garder) : ");
        String nom = scanner.nextLine();
        if (!nom.trim().isEmpty()) {
            burger.setNom(nom);
        }

        System.out.print("Nouveau prix (0 pour garder) : ");
        double prix = lireDouble();
        if (prix > 0) {
            burger.setPrix(prix);
        }

        System.out.print("Nouvelle image (ENTER pour garder) : ");
        String cheminImage = scanner.nextLine();
        if (!cheminImage.trim().isEmpty()) {
            String imageUrl = imageService.uploadImage(cheminImage, "burgers");
            burger.setImageUrl(imageUrl);
        }

        System.out.print("Disponible ? (o/n) : ");
        String dispo = scanner.nextLine();
        if (dispo.equalsIgnoreCase("o")) {
            burger.setDisponible(true);
        } else if (dispo.equalsIgnoreCase("n")) {
            burger.setDisponible(false);
        }

        if (burgerDAO.modifier(burger)) {
            System.out.println(" Burger modifié avec succès !");
        }
    }

    private void archiverBurger() {
        System.out.println("\n ARCHIVAGE D'UN BURGER");

        listerBurgers();

        System.out.print("\nID du burger à archiver : ");
        int id = lireEntier();

        System.out.print("Êtes-vous sûr ? (o/n) : ");
        String confirmation = scanner.nextLine();

        if (confirmation.equalsIgnoreCase("o")) {
            if (burgerDAO.archiver(id)) {
                System.out.println(" Burger archivé !");
            }
        } else {
            System.out.println(" Opération annulée");
        }
    }

    private int lireEntier() {
        while (true) {
            try {
                int valeur = Integer.parseInt(scanner.nextLine());
                return valeur;
            } catch (NumberFormatException e) {
                System.out.print(" Entrez un nombre valide : ");
            }
        }
    }

    private double lireDouble() {
        while (true) {
            try {
                double valeur = Double.parseDouble(scanner.nextLine());
                return valeur;
            } catch (NumberFormatException e) {
                System.out.print(" Entrez un nombre valide : ");
            }
        }
    }
}
