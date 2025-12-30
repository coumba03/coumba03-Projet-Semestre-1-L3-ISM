<?php

namespace App\Repository;

use App\Entity\Livreur;
use Doctrine\Bundle\DoctrineBundle\Repository\ServiceEntityRepository;
use Doctrine\Persistence\ManagerRegistry;

class LivreurRepository extends ServiceEntityRepository
{
    public function __construct(ManagerRegistry $registry)
    {
        parent::__construct($registry, Livreur::class);
    }

    /**
     * Get active livreurs sorted by name
     */
    public function findActive(): array
    {
        return $this->findBy(['archived' => false], ['nom' => 'ASC']);
    }

    /**
     * Find livreur by ID
     */
    public function findById(int $id): ?Livreur
    {
        return $this->find($id);
    }
}
