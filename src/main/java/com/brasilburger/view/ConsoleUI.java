package com.brasilburger.view;

import com.brasilburger.service.BurgerService;
import com.brasilburger.service.ComplementService;
import com.brasilburger.service.MenuService;
import com.brasilburger.service.ImageService;
import com.brasilburger.service.ClientService;
import com.brasilburger.service.GestionnaireService;
import com.brasilburger.service.LivreurService;
import com.brasilburger.service.ZoneService;
import com.brasilburger.service.CommandeService;
import com.brasilburger.service.PaiementService;

import java.util.Scanner;

public class ConsoleUI {

    private Scanner scanner = new Scanner(System.in);
    private BurgerService burgerDAO;
    private ComplementService complementDAO;
    private MenuService menuDAO;
    private ImageService imageService;

    private BurgerView burgerView;
    private ComplementView complementView;
    private MenuView menuView;
    private ClientView clientView;
    private GestionnaireView gestionnaireView;
    private LivreurView livreurView;
    private ZoneView zoneView;
    private CommandeView commandeView;
    private PaiementView paiementView;

    public ConsoleUI(BurgerService burgerDAO, ComplementService complementDAO, MenuService menuDAO, ImageService imageService,
                     ClientService clientService, GestionnaireService gestionnaireService, LivreurService livreurService,
                     ZoneService zoneService, CommandeService commandeService, PaiementService paiementService) {
        this.burgerDAO = burgerDAO;
        this.complementDAO = complementDAO;
        this.menuDAO = menuDAO;
        this.imageService = imageService;

        this.burgerView = new BurgerView(scanner, burgerDAO, imageService);
        this.complementView = new ComplementView(scanner, complementDAO, imageService);
        this.menuView = new MenuView(scanner, menuDAO, burgerDAO, complementDAO, imageService);
        this.clientView = new ClientView(clientService);
        this.gestionnaireView = new GestionnaireView(gestionnaireService);
        this.livreurView = new LivreurView(livreurService);
        this.zoneView = new ZoneView(zoneService);
        this.commandeView = new CommandeView(commandeService);
        this.paiementView = new PaiementView(paiementService);
    }

    public void start() {
        System.out.println("╔════════════════════════════════════════╗");
        System.out.println("║    BRASIL BURGER - GESTION             ║");
        System.out.println("║   Gestion des Ressources               ║");
        System.out.println("╚════════════════════════════════════════╝");

        boolean continuer = true;

        while (continuer) {
            afficherMenuPrincipal();
            int choix = lireEntier();

            switch (choix) {
                case 1:
                    burgerView.start();
                    break;
                case 2:
                    complementView.start();
                    break;
                case 3:
                    menuView.start();
                    break;
                case 4:
                    clientView.start();
                    break;
                case 5:
                    gestionnaireView.start();
                    break;
                case 6:
                    livreurView.start();
                    break;
                case 7:
                    zoneView.start();
                    break;
                case 8:
                    commandeView.start();
                    break;
                case 9:
                    paiementView.start();
                    break;
                case 0:
                    continuer = false;
                    System.out.println(" Au revoir !");
                    break;
                default:
                    System.out.println(" Choix invalide !");
            }
        }

        // Ne pas fermer le Scanner sur System.in (ferme System.in globalement)
    }

    private void afficherMenuPrincipal() {
        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║          MENU PRINCIPAL                ║");
        System.out.println("╠════════════════════════════════════════╣");
        System.out.println("║  1. Gestion des Burgers                ║");
        System.out.println("║  2. Gestion des Compléments            ║");
        System.out.println("║  3. Gestion des Menus                  ║");
        System.out.println("║  4. Gestion des Clients                ║");
        System.out.println("║  5. Gestion des Gestionnaires          ║");
        System.out.println("║  6. Gestion des Livreurs               ║");
        System.out.println("║  7. Gestion des Zones                  ║");
        System.out.println("║  8. Gestion des Commandes              ║");
        System.out.println("║  9. Gestion des Paiements              ║");
        System.out.println("║  0. Quitter                            ║");
        System.out.println("╚════════════════════════════════════════╝");
        System.out.print("Votre choix : ");
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
