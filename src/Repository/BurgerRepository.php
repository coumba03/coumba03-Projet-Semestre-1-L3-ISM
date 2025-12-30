<?php

namespace App\Repository;

use App\Entity\Burger;
use Doctrine\Bundle\DoctrineBundle\Repository\ServiceEntityRepository;
use Doctrine\Persistence\ManagerRegistry;

class BurgerRepository extends ServiceEntityRepository
{
    public function __construct(ManagerRegistry $registry)
    {
        parent::__construct($registry, Burger::class);
    }

    /**
     * Get active burgers sorted by name
     */
    public function findActive(): array
    {
        return $this->findBy(['archived' => false], ['nom' => 'ASC']);
    }

    /**
     * Find burger by ID
     */
    public function findById(int $id): ?Burger
    {
        return $this->find($id);
    }
}
