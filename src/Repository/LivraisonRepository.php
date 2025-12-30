<?php

namespace App\Repository;

use App\Entity\Commande;
use App\Entity\Livraison;
use Doctrine\Bundle\DoctrineBundle\Repository\ServiceEntityRepository;
use Doctrine\Persistence\ManagerRegistry;

class LivraisonRepository extends ServiceEntityRepository
{
    public function __construct(ManagerRegistry $registry)
    {
        parent::__construct($registry, Livraison::class);
    }

    /**
     * Get commandes ready for delivery (no livraison assigned yet)
     */
    public function getCommandesAffecter(int $offset = 0, int $limit = 15): array
    {
        $qb = $this->getEntityManager()->createQueryBuilder()
            ->select('c, cl')
            ->from(Commande::class, 'c')
            ->leftJoin('c.client', 'cl')
            ->where('c.archived = false')
            ->andWhere('c.etat = :etat')
            ->andWhere('c.typeCommande = :type')
            ->andWhere('NOT EXISTS (
                SELECT l.id FROM ' . Livraison::class . ' l WHERE l.commande = c
            )')
            ->orderBy('c.dateCommande', 'DESC')
            ->setParameter('etat', Commande::ETAT_TERMINER)
            ->setParameter('type', Commande::TYPE_LIVRAISON)
            ->setFirstResult($offset)
            ->setMaxResults($limit);

        return $qb->getQuery()->getResult();
    }

    /**
     * Count commandes ready for delivery
     */
    public function countCommandesAffecter(): int
    {
        $qb = $this->getEntityManager()->createQueryBuilder()
            ->select('COUNT(DISTINCT c)')
            ->from(Commande::class, 'c')
            ->where('c.archived = false')
            ->andWhere('c.etat = :etat')
            ->andWhere('c.typeCommande = :type')
            ->andWhere('NOT EXISTS (
                SELECT l.id FROM ' . Livraison::class . ' l WHERE l.commande = c
            )')
            ->setParameter('etat', Commande::ETAT_TERMINER)
            ->setParameter('type', Commande::TYPE_LIVRAISON);

        return (int) $qb->getQuery()->getSingleScalarResult();
    }

    /**
     * List livraisons with filters
     */
    public function listByFilters(array $filters, int $offset = 0, int $limit = 15): array
    {
        $qb = $this->createQueryBuilder('l')
            ->select('l, c, cl, z, li')
            ->leftJoin('l.commande', 'c')
            ->leftJoin('c.client', 'cl')
            ->leftJoin('l.zone', 'z')
            ->leftJoin('l.livreur', 'li')
            ->orderBy('l.createdAt', 'DESC');

        if (($filters['statut'] ?? '') !== '') {
            $qb->andWhere('l.statut = :statut')->setParameter('statut', $filters['statut']);
        }

        if (($filters['zone'] ?? '') !== '') {
            $qb->andWhere('z.id = :zone')->setParameter('zone', (int) $filters['zone']);
        }

        if (($filters['livreur'] ?? '') !== '') {
            $qb->andWhere('li.id = :livreur')->setParameter('livreur', (int) $filters['livreur']);
        }

        return $qb->setFirstResult($offset)
            ->setMaxResults($limit)
            ->getQuery()
            ->getResult();
    }

    /**
     * Count livraisons with filters
     */
    public function countByFilters(array $filters): int
    {
        $qb = $this->createQueryBuilder('l')
            ->select('COUNT(DISTINCT l)')
            ->leftJoin('l.zone', 'z')
            ->leftJoin('l.livreur', 'li');

        if (($filters['statut'] ?? '') !== '') {
            $qb->andWhere('l.statut = :statut')->setParameter('statut', $filters['statut']);
        }

        if (($filters['zone'] ?? '') !== '') {
            $qb->andWhere('z.id = :zone')->setParameter('zone', (int) $filters['zone']);
        }

        if (($filters['livreur'] ?? '') !== '') {
            $qb->andWhere('li.id = :livreur')->setParameter('livreur', (int) $filters['livreur']);
        }

        return (int) $qb->getQuery()->getSingleScalarResult();
    }

    /**
     * Find livraison by ID
     */
    public function findById(int $id): ?Livraison
    {
        return $this->find($id);
    }

    /**
     * Check if livraison exists for commande
     */
    public function existsForCommande(Commande $commande): bool
    {
        return $this->findOneBy(['commande' => $commande]) !== null;
    }
}
