<?php

namespace App\Service\impl;

use App\Entity\Livraison;
use App\Pagination\Paginator;
use App\Repository\CommandeRepository;
use App\Repository\LivraisonRepository;
use App\Repository\LivreurRepository;
use App\Repository\ZoneRepository;
use App\Service\LivraisonServiceInterface;
use Doctrine\ORM\EntityManagerInterface;

class LivraisonService implements LivraisonServiceInterface
{
    public function __construct(
        private readonly LivraisonRepository $livraisonRepository,
        private readonly CommandeRepository $commandeRepository,
        private readonly ZoneRepository $zoneRepository,
        private readonly LivreurRepository $livreurRepository,
        private readonly EntityManagerInterface $em
    ) {
    }

    public function getCommandesGroupeesParZone(): array
    {
        return $this->livraisonRepository->getCommandesGroupeesParZone();
    }

    public function affecterEnLot(array $commandeIds, int $livreurId): int
    {
        $livreur = $this->livreurRepository->findById($livreurId);
        if (!$livreur) {
            throw new \InvalidArgumentException('Livreur introuvable');
        }

        $affected = 0;
        foreach ($commandeIds as $commandeId) {
            $commande = $this->commandeRepository->findById($commandeId);
            if (!$commande || $this->livraisonRepository->existsForCommande($commande)) {
                continue;
            }

            $livraison = new Livraison();
            $livraison->setCommande($commande);
            $livraison->setZone($commande->getZone());
            $livraison->setLivreur($livreur);
            $livraison->setStatut(Livraison::STATUT_AFFECTEE);

            $this->em->persist($livraison);
            $affected++;
        }

        $this->em->flush();
        return $affected;
    }

    public function getCommandesAffecter(Paginator $paginator): array
    {
        $total = $this->livraisonRepository->countCommandesAffecter();
        $paginator->setTotal($total);

        return $this->livraisonRepository->getCommandesAffecter(
            $paginator->getOffset(),
            $paginator->getLimit()
        );
    }

    public function countCommandesAffecter(): int
    {
        return $this->livraisonRepository->countCommandesAffecter();
    }

    public function affecter(int $commandeId, int $zoneId, int $livreurId): void
    {
        $commande = $this->commandeRepository->findById($commandeId);
        if (!$commande) {
            throw new \InvalidArgumentException('Commande introuvable');
        }

        $zone = $this->zoneRepository->findById($zoneId);
        if (!$zone) {
            throw new \InvalidArgumentException('Zone introuvable');
        }

        $livreur = $this->livreurRepository->findById($livreurId);
        if (!$livreur) {
            throw new \InvalidArgumentException('Livreur introuvable');
        }

        if ($this->livraisonRepository->existsForCommande($commande)) {
            return;
        }

        $livraison = new Livraison();
        $livraison->setCommande($commande);
        $livraison->setZone($zone);
        $livraison->setLivreur($livreur);
        $livraison->setStatut(Livraison::STATUT_AFFECTEE);

        $this->em->persist($livraison);
        $this->em->flush();
    }

    public function listLivraisons(array $filters, Paginator $paginator): array
    {
        $total = $this->livraisonRepository->countByFilters($filters);
        $paginator->setTotal($total);

        return $this->livraisonRepository->listByFilters(
            $filters,
            $paginator->getOffset(),
            $paginator->getLimit()
        );
    }

    public function countLivraisons(array $filters): int
    {
        return $this->livraisonRepository->countByFilters($filters);
    }

    public function updateStatut(int $livraisonId, string $statut): void
    {
        $allowed = [
            Livraison::STATUT_A_AFFECTER,
            Livraison::STATUT_AFFECTEE,
            Livraison::STATUT_EN_COURS,
            Livraison::STATUT_LIVREE,
        ];

        if (!in_array($statut, $allowed, true)) {
            throw new \InvalidArgumentException('Statut invalide');
        }

        $livraison = $this->livraisonRepository->findById($livraisonId);
        if (!$livraison) {
            throw new \InvalidArgumentException('Livraison introuvable');
        }

        $livraison->setStatut($statut);
        $this->em->flush();
    }
}
