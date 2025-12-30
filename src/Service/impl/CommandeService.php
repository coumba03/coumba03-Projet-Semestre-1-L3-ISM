<?php

namespace App\Service\impl;

use App\Entity\Commande;
use App\Pagination\Paginator;
use App\Repository\CommandeRepository;
use App\Service\CommandeServiceInterface;
use Doctrine\ORM\EntityManagerInterface;

class CommandeService implements CommandeServiceInterface
{
    public function __construct(
        private readonly CommandeRepository $commandeRepository,
        private readonly EntityManagerInterface $em
    ) {
    }

    public function findById(int $id): ?Commande
    {
        return $this->commandeRepository->findById($id);
    }

    public function search(array $filters, Paginator $paginator): array
    {
        $total = $this->commandeRepository->countByFilters($filters);
        $paginator->setTotal($total);

        return $this->commandeRepository->search(
            $filters,
            $paginator->getOffset(),
            $paginator->getLimit()
        );
    }

    public function countByFilters(array $filters): int
    {
        return $this->commandeRepository->countByFilters($filters);
    }

    public function updateEtat(int $commandeId, string $etat): void
    {
        $allowed = [
            Commande::ETAT_NEW,
            Commande::ETAT_VALIDEE,
            Commande::ETAT_EN_PREPARATION,
            Commande::ETAT_TERMINER,
            Commande::ETAT_LIVREE,
            Commande::ETAT_ANNULEE,
        ];

        if (!in_array($etat, $allowed, true)) {
            throw new \InvalidArgumentException('Etat invalide');
        }

        $commande = $this->commandeRepository->findById($commandeId);
        if (!$commande) {
            throw new \InvalidArgumentException('Commande introuvable');
        }

        $commande->setEtat($etat);
        $this->em->flush();
    }

    public function annuler(int $commandeId): void
    {
        $commande = $this->commandeRepository->findById($commandeId);
        if (!$commande) {
            throw new \InvalidArgumentException('Commande introuvable');
        }

        $commande->setEtat(Commande::ETAT_ANNULEE);
        $this->em->flush();
    }
}
