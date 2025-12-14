package com.brasilburger.view;

import com.brasilburger.service.ComplementService;
import com.brasilburger.model.Complement;
import com.brasilburger.service.ImageService;
import java.util.List;
import java.util.Scanner;

public class ComplementView {
    private Scanner scanner;
    private ComplementService complementDAO;
    private ImageService imageService;

    public ComplementView(Scanner scanner, ComplementService complementDAO, ImageService imageService) {
        this.scanner = scanner;
        this.complementDAO = complementDAO;
        this.imageService = imageService;
    }

    public void start() {
        boolean retour = false;

        while (!retour) {
            System.out.println("\n╔════════════════════════════════════════╗");
            System.out.println("║       GESTION DES COMPLÉMENTS          ║");
            System.out.println("╠════════════════════════════════════════╣");
            System.out.println("║  1. Créer un complément                ║");
            System.out.println("║  2. Lister tous les compléments        ║");
            System.out.println("║  3. Modifier un complément             ║");
            System.out.println("║  4. Archiver un complément             ║");
            System.out.println("║  0. Retour                             ║");
            System.out.println("╚════════════════════════════════════════╝");
            System.out.print("Votre choix : ");

            int choix = lireEntier();

            switch (choix) {
                case 1:
                    creerComplement();
                    break;
                case 2:
                    listerComplements();
                    break;
                case 3:
                    modifierComplement();
                    break;
                case 4:
                    archiverComplement();
                    break;
                case 0:
                    retour = true;
                    break;
                default:
                    System.out.println(" Choix invalide !");
            }
        }
    }

    private void creerComplement() {
        System.out.println("\n CRÉATION D'UN COMPLÉMENT");

        System.out.print("Nom : ");
        String nom = scanner.nextLine();

        System.out.println("Type : 1=BOISSON, 2=FRITE");
        System.out.print("Votre choix : ");
        int typeChoix = lireEntier();
        String type = (typeChoix == 1) ? "BOISSON" : "FRITE";

        System.out.print("Prix (FCFA) : ");
        double prix = lireDouble();

        System.out.print("Chemin de l'image (ou ENTER) : ");
        String cheminImage = scanner.nextLine();

        Complement complement = new Complement(nom, type, prix);

        if (!cheminImage.trim().isEmpty()) {
            String imageUrl = imageService.uploadImage(cheminImage, "complements");
            complement.setImageUrl(imageUrl);
        }

        if (complementDAO.creer(complement)) {
            System.out.println(" Complément créé !");
        }
    }

    private void listerComplements() {
        System.out.println("\n LISTE DES COMPLÉMENTS");

        List<Complement> complements = complementDAO.listerTous();

        if (complements.isEmpty()) {
            System.out.println("Aucun complément trouvé.");
            return;
        }

        System.out.println("╔════╦═══════════════════════╦══════════╦═══════════╗");
        System.out.println("║ ID ║        NOM            ║   TYPE   ║   PRIX    ║");
        System.out.println("╠════╬═══════════════════════╬══════════╬═══════════╣");

        for (Complement c : complements) {
            System.out.printf("║ %2d ║ %-21s ║ %-8s ║ %7.0f F ║%n", 
                c.getId(), c.getNom(), c.getType(), c.getPrix());
        }

        System.out.println("╚════╩═══════════════════════╩══════════╩═══════════╝");
    }

    private void modifierComplement() {
        System.out.println("\n MODIFICATION D'UN COMPLÉMENT");
        listerComplements();

        System.out.print("\nID du complément : ");
        int id = lireEntier();

        Complement complement = complementDAO.obtenirParId(id);
        if (complement == null) {
            System.out.println(" Complément non trouvé !");
            return;
        }

        System.out.print("Nouveau nom (ENTER pour garder) : ");
        String nom = scanner.nextLine();
        if (!nom.trim().isEmpty()) {
            complement.setNom(nom);
        }

        System.out.print("Nouveau prix (0 pour garder) : ");
        double prix = lireDouble();
        if (prix > 0) {
            complement.setPrix(prix);
        }

        if (complementDAO.modifier(complement)) {
            System.out.println(" Complément modifié !");
        }
    }

    private void archiverComplement() {
        System.out.println("\n ARCHIVAGE D'UN COMPLÉMENT");
        listerComplements();

        System.out.print("\nID à archiver : ");
        int id = lireEntier();

        if (complementDAO.archiver(id)) {
            System.out.println(" Complément archivé !");
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
