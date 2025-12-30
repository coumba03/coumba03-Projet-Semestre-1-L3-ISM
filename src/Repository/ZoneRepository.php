<?php

namespace App\Repository;

use App\Entity\Zone;
use Doctrine\Bundle\DoctrineBundle\Repository\ServiceEntityRepository;
use Doctrine\Persistence\ManagerRegistry;

class ZoneRepository extends ServiceEntityRepository
{
    public function __construct(ManagerRegistry $registry)
    {
        parent::__construct($registry, Zone::class);
    }

    /**
     * Get active zones sorted by name
     */
    public function findActive(): array
    {
        return $this->findBy(['archived' => false], ['nom' => 'ASC']);
    }

    /**
     * Find zone by ID
     */
    public function findById(int $id): ?Zone
    {
        return $this->find($id);
    }
}
