package com.brasilburger.view;

import com.brasilburger.model.Gestionnaire;
import com.brasilburger.service.GestionnaireService;
import java.util.List;
import java.util.Scanner;

public class GestionnaireView {
    private final GestionnaireService service;
    private final Scanner scanner = new Scanner(System.in);

    public GestionnaireView(GestionnaireService service) {
        this.service = service;
    }

    public void start() {
        int choix;
        do {
            System.out.println("\n╔════════════════════════════════════════╗");
            System.out.println("║      GESTION DES GESTIONNAIRES         ║");
            System.out.println("╠════════════════════════════════════════╣");
            System.out.println("║  1. Créer un gestionnaire              ║");
            System.out.println("║  2. Lister tous les gestionnaires      ║");
            System.out.println("║  3. Modifier un gestionnaire           ║");
            System.out.println("║  4. Archiver un gestionnaire           ║");
            System.out.println("║  0. Retour                             ║");
            System.out.println("╚════════════════════════════════════════╝");
            System.out.print("Choix : ");
            choix = scanner.nextInt();
            scanner.nextLine();

            if (choix == 1) creer();
            else if (choix == 2) lister();
            else if (choix == 3) modifier();
            else if (choix == 4) archiver();
        } while (choix != 0);
    }

    private void creer() {
        Gestionnaire g = new Gestionnaire();
        System.out.println("\n CRÉATION D'UN GESTIONNAIRE");
        System.out.print("Nom : ");
        g.setNom(scanner.nextLine());
        System.out.print("Prénom : ");
        g.setPrenom(scanner.nextLine());
        System.out.print("Email : ");
        g.setEmail(scanner.nextLine());
        System.out.print("Téléphone : ");
        g.setTelephone(scanner.nextLine());
        System.out.print("Mot de passe : ");
        g.setPassword(scanner.nextLine());

        if (service.creer(g)) {
            System.out.println("✅ Gestionnaire créé !");
        } else {
            System.out.println("❌ Erreur lors de la création.");
        }
    }

    private void lister() {
        List<Gestionnaire> gestionnaires = service.listerTous();
        if (gestionnaires.isEmpty()) {
            System.out.println("Aucun gestionnaire trouvé.");
            return;
        }
        System.out.println("\n LISTE DES GESTIONNAIRES");
        System.out.println("═══════════════════════════════════════════════════════");
        for (Gestionnaire g : gestionnaires) {
            System.out.printf("ID: %d | %s %s | %s%n", g.getId(), g.getNom(), g.getPrenom(), g.getEmail());
        }
    }

    private void modifier() {
        lister();
        System.out.print("\nID du gestionnaire à modifier : ");
        int id = scanner.nextInt();
        scanner.nextLine();
        Gestionnaire g = service.obtenirParId(id);
        if (g == null) {
            System.out.println("❌ Gestionnaire non trouvé !");
            return;
        }
        System.out.print("Nouveau nom (appuyer Entrée pour garder) : ");
        String nom = scanner.nextLine();
        if (!nom.isBlank()) g.setNom(nom);

        if (service.modifier(g)) {
            System.out.println("✅ Gestionnaire modifié !");
        }
    }

    private void archiver() {
        lister();
        System.out.print("\nID du gestionnaire à archiver : ");
        int id = scanner.nextInt();
        if (service.archiver(id)) {
            System.out.println("✅ Gestionnaire archivé !");
        }
    }
}
