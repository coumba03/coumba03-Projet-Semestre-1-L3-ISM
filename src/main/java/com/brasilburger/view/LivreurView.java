package com.brasilburger.view;

import com.brasilburger.model.Livreur;
import com.brasilburger.service.LivreurService;
import java.util.List;
import java.util.Scanner;

public class LivreurView {
    private final LivreurService service;
    private final Scanner scanner = new Scanner(System.in);

    public LivreurView(LivreurService service) {
        this.service = service;
    }

    public void start() {
        int choix;
        do {
            System.out.println("\n╔════════════════════════════════════════╗");
            System.out.println("║        GESTION DES LIVREURS            ║");
            System.out.println("╠════════════════════════════════════════╣");
            System.out.println("║  1. Créer un livreur                   ║");
            System.out.println("║  2. Lister tous les livreurs           ║");
            System.out.println("║  3. Modifier un livreur                ║");
            System.out.println("║  4. Archiver un livreur                ║");
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
        Livreur l = new Livreur();
        System.out.println("\n CRÉATION D'UN LIVREUR");
        System.out.print("Nom : ");
        l.setNom(scanner.nextLine());
        System.out.print("Prénom : ");
        l.setPrenom(scanner.nextLine());
        System.out.print("Email : ");
        l.setEmail(scanner.nextLine());
        System.out.print("Téléphone : ");
        l.setTelephone(scanner.nextLine());
        System.out.print("Mot de passe : ");
        l.setPassword(scanner.nextLine());
        System.out.print("Zone ID : ");
        l.setZoneId(scanner.nextInt());
        scanner.nextLine();

        if (service.creer(l)) {
            System.out.println("✅ Livreur créé !");
        } else {
            System.out.println("❌ Erreur lors de la création.");
        }
    }

    private void lister() {
        List<Livreur> livreurs = service.listerTous();
        if (livreurs.isEmpty()) {
            System.out.println("Aucun livreur trouvé.");
            return;
        }
        System.out.println("\n LISTE DES LIVREURS");
        System.out.println("═══════════════════════════════════════════════════════");
        for (Livreur l : livreurs) {
            System.out.printf("ID: %d | %s %s | %s | Zone: %d%n", l.getId(), l.getNom(), l.getPrenom(), l.getTelephone(), l.getZoneId());
        }
    }

    private void modifier() {
        lister();
        System.out.print("\nID du livreur à modifier : ");
        int id = scanner.nextInt();
        scanner.nextLine();
        Livreur l = service.obtenirParId(id);
        if (l == null) {
            System.out.println("❌ Livreur non trouvé !");
            return;
        }
        System.out.print("Nouvel email (appuyer Entrée pour garder) : ");
        String email = scanner.nextLine();
        if (!email.isBlank()) l.setEmail(email);

        if (service.modifier(l)) {
            System.out.println("✅ Livreur modifié !");
        }
    }

    private void archiver() {
        lister();
        System.out.print("\nID du livreur à archiver : ");
        int id = scanner.nextInt();
        if (service.archiver(id)) {
            System.out.println("✅ Livreur archivé !");
        }
    }
}
