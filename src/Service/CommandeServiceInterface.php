<?php

namespace App\Service;

use App\Entity\Commande;
use App\Pagination\Paginator;

interface CommandeServiceInterface
{
    public function findById(int $id): ?Commande;

    public function search(array $filters, Paginator $paginator): array;

    public function countByFilters(array $filters): int;

    public function updateEtat(int $commandeId, string $etat): void;

    public function annuler(int $commandeId): void;
}
