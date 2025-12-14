package com.brasilburger.view;

import com.brasilburger.service.MenuService;
import com.brasilburger.service.ComplementService;
import com.brasilburger.service.BurgerService;
import com.brasilburger.model.Menu;
import com.brasilburger.model.Complement;
import com.brasilburger.service.ImageService;
import java.util.List;
import java.util.Scanner;

public class MenuView {
    private Scanner scanner;
    private MenuService menuDAO;
    private BurgerService burgerDAO;
    private ComplementService complementDAO;
    private ImageService imageService;

    public MenuView(Scanner scanner, MenuService menuDAO, BurgerService burgerDAO, ComplementService complementDAO, ImageService imageService) {
        this.scanner = scanner;
        this.menuDAO = menuDAO;
        this.burgerDAO = burgerDAO;
        this.complementDAO = complementDAO;
        this.imageService = imageService;
    }

    public void start() {
        boolean retour = false;

        while (!retour) {
            System.out.println("\n╔════════════════════════════════════════╗");
            System.out.println("║         GESTION DES MENUS              ║");
            System.out.println("╠════════════════════════════════════════╣");
            System.out.println("║  1. Créer un menu                      ║");
            System.out.println("║  2. Lister tous les menus              ║");
            System.out.println("║  3. Modifier un menu                   ║");
            System.out.println("║  4. Archiver un menu                   ║");
            System.out.println("║  0. Retour                             ║");
            System.out.println("╚════════════════════════════════════════╝");
            System.out.print("Votre choix : ");

            int choix = lireEntier();

            switch (choix) {
                case 1:
                    creerMenu();
                    break;
                case 2:
                    listerMenus();
                    break;
                case 3:
                    modifierMenu();
                    break;
                case 4:
                    archiverMenu();
                    break;
                case 0:
                    retour = true;
                    break;
                default:
                    System.out.println(" Choix invalide !");
            }
        }
    }

    private void creerMenu() {
        System.out.println("\n CRÉATION D'UN MENU");

        System.out.print("Nom du menu : ");
        String nom = scanner.nextLine();

        // Choisir le burger
        listerBurgers();
        System.out.print("\nID du burger : ");
        int burgerId = lireEntier();

        // Choisir la boisson
        List<Complement> boissons = complementDAO.listerParType("BOISSON");
        System.out.println("\n BOISSONS DISPONIBLES:");
        for (Complement b : boissons) {
            System.out.println(b.getId() + ". " + b.getNom() + " - " + b.getPrix() + " FCFA");
        }
        System.out.print("ID de la boisson : ");
        int boissonId = lireEntier();

        // Choisir les frites
        List<Complement> frites = complementDAO.listerParType("FRITE");
        System.out.println("\n FRITES DISPONIBLES:");
        for (Complement f : frites) {
            System.out.println(f.getId() + ". " + f.getNom() + " - " + f.getPrix() + " FCFA");
        }
        System.out.print("ID des frites : ");
        int friteId = lireEntier();

        System.out.print("Chemin de l'image (ou ENTER) : ");
        String cheminImage = scanner.nextLine();

        Menu menu = new Menu(nom);

        if (!cheminImage.trim().isEmpty()) {
            String imageUrl = imageService.uploadImage(cheminImage, "menus");
            menu.setImageUrl(imageUrl);
        }

        if (menuDAO.creer(menu, burgerId, boissonId, friteId)) {
            System.out.println(" Menu créé avec succès !");
        }
    }

    private void listerMenus() {
        System.out.println("\n LISTE DES MENUS");

        java.util.List<Menu> menus = menuDAO.listerTous();

        if (menus.isEmpty()) {
            System.out.println("Aucun menu trouvé.");
            return;
        }

        for (Menu menu : menus) {
            System.out.println("\n╔════════════════════════════════════════════════════╗");
            System.out.println("   " + menu.getNom() + " (ID: " + menu.getId() + ")");
            System.out.println("  ╠════════════════════════════════════════════════════╣");

            if (menu.getBurger() != null) {
                System.out.printf("   Burger : %s (%,.0f FCFA)%n", 
                    menu.getBurger().getNom(), menu.getBurger().getPrix());
            }
            if (menu.getBoisson() != null) {
                System.out.printf("   Boisson: %s (%,.0f FCFA)%n", 
                    menu.getBoisson().getNom(), menu.getBoisson().getPrix());
            }
            if (menu.getFrite() != null) {
                System.out.printf("   Frites : %s (%,.0f FCFA)%n", 
                    menu.getFrite().getNom(), menu.getFrite().getPrix());
            }

            System.out.println("╠════════════════════════════════════════════════════╣");
            System.out.printf("    PRIX TOTAL: %,.0f FCFA%n", menu.getPrixTotal());
            System.out.println("╚════════════════════════════════════════════════════╝");
        }
    }

    private void modifierMenu() {
        System.out.println("\n MODIFICATION D'UN MENU");
        listerMenus();

        System.out.print("\nID du menu : ");
        int id = lireEntier();

        Menu menu = menuDAO.obtenirParId(id);
        if (menu == null) {
            System.out.println(" Menu non trouvé !");
            return;
        }

        System.out.print("Nouveau nom (ENTER pour garder) : ");
        String nom = scanner.nextLine();
        if (!nom.trim().isEmpty()) {
            menu.setNom(nom);
        }

        int burgerId = menu.getBurger() != null ? menu.getBurger().getId() : 0;
        int boissonId = menu.getBoisson() != null ? menu.getBoisson().getId() : 0;
        int friteId = menu.getFrite() != null ? menu.getFrite().getId() : 0;

        System.out.print("Changer le burger ? (o/n) : ");
        if (scanner.nextLine().equalsIgnoreCase("o")) {
            listerBurgers();
            System.out.print("Nouvel ID burger : ");
            burgerId = lireEntier();
        }

        if (menuDAO.modifier(menu, burgerId, boissonId, friteId)) {
            System.out.println(" Menu modifié !");
        }
    }

    private void archiverMenu() {
        System.out.println("\n ARCHIVAGE D'UN MENU");
        listerMenus();

        System.out.print("\nID à archiver : ");
        int id = lireEntier();

        if (menuDAO.archiver(id)) {
            System.out.println(" Menu archivé !");
        }
    }

    private void listerBurgers() {
        java.util.List<com.brasilburger.model.Burger> burgers = burgerDAO.listerTous();
        if (burgers.isEmpty()) {
            System.out.println("Aucun burger trouvé.");
            return;
        }
        for (com.brasilburger.model.Burger b : burgers) {
            System.out.println(b.getId() + ". " + b.getNom() + " - " + b.getPrix() + " FCFA");
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

}
