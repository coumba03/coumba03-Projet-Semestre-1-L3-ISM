<?php

namespace App\Entity;

use Doctrine\Common\Collections\ArrayCollection;
use Doctrine\Common\Collections\Collection;
use Doctrine\ORM\Mapping as ORM;

#[ORM\Entity]
#[ORM\Table(name: 'commandes')]
class Commande
{
    public const ETAT_NEW = 'NEW';
    public const ETAT_VALIDEE = 'VALIDEE';
    public const ETAT_EN_PREPARATION = 'EN_PREPARATION';
    public const ETAT_TERMINER = 'TERMINER';
    public const ETAT_LIVREE = 'LIVREE';
    public const ETAT_ANNULEE = 'ANNULEE';

    public const TYPE_SUR_PLACE = 'SUR_PLACE';
    public const TYPE_A_EMPORTER = 'A_EMPORTER';
    public const TYPE_LIVRAISON = 'DELIVERY';

    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column(type: 'integer')]
    private ?int $id = null;

    #[ORM\ManyToOne(targetEntity: Client::class)]
    #[ORM\JoinColumn(name: 'client_id', referencedColumnName: 'id', nullable: true)]
    private ?Client $client = null;

    #[ORM\ManyToOne(targetEntity: Zone::class)]
    #[ORM\JoinColumn(name: 'zone_id', referencedColumnName: 'id', nullable: true)]
    private ?Zone $zone = null;

    #[ORM\Column(length: 50, nullable: true)]
    private ?string $typeCommande = null;

    #[ORM\Column(length: 50, options: ['default' => 'NEW'])]
    private string $etat = self::ETAT_NEW;

    #[ORM\Column(name: 'date_commande', type: 'datetime_immutable', options: ['default' => 'CURRENT_TIMESTAMP'])]
    private \DateTimeImmutable $dateCommande;

    #[ORM\Column(name: 'montant_total', type: 'decimal', precision: 10, scale: 2, nullable: true)]
    private ?string $montantTotal = null;

    #[ORM\Column(type: 'boolean', options: ['default' => false])]
    private bool $archived = false;

    #[ORM\OneToMany(mappedBy: 'commande', targetEntity: CommandeItem::class, cascade: ['persist', 'remove'])]
    private Collection $items;

    #[ORM\OneToOne(mappedBy: 'commande', targetEntity: Paiement::class)]
    private ?Paiement $paiement = null;

    public function __construct()
    {
        $this->dateCommande = new \DateTimeImmutable();
        $this->items = new ArrayCollection();
    }

    public function getId(): ?int
    {
        return $this->id;
    }

    public function getClient(): ?Client
    {
        return $this->client;
    }

    public function setClient(?Client $client): self
    {
        $this->client = $client;

        return $this;
    }

    public function getZone(): ?Zone
    {
        return $this->zone;
    }

    public function setZone(?Zone $zone): self
    {
        $this->zone = $zone;

        return $this;
    }

    public function getTypeCommande(): ?string
    {
        return $this->typeCommande;
    }

    public function setTypeCommande(?string $typeCommande): self
    {
        $this->typeCommande = $typeCommande;

        return $this;
    }

    public function getEtat(): string
    {
        return $this->etat;
    }

    public function setEtat(string $etat): self
    {
        $this->etat = $etat;

        return $this;
    }

    public function getDateCommande(): \DateTimeImmutable
    {
        return $this->dateCommande;
    }

    public function setDateCommande(\DateTimeImmutable $dateCommande): self
    {
        $this->dateCommande = $dateCommande;

        return $this;
    }

    public function getMontantTotal(): ?string
    {
        return $this->montantTotal;
    }

    public function setMontantTotal(?string $montantTotal): self
    {
        $this->montantTotal = $montantTotal;

        return $this;
    }

    public function isArchived(): bool
    {
        return $this->archived;
    }

    public function setArchived(bool $archived): self
    {
        $this->archived = $archived;

        return $this;
    }
    public function getItems(): Collection
    {
        return $this->items;
    }

    public function addItem(CommandeItem $item): self
    {
        if (!$this->items->contains($item)) {
            $this->items->add($item);
            $item->setCommande($this);
        }

        return $this;
    }

    public function removeItem(CommandeItem $item): self
    {
        if ($this->items->removeElement($item)) {
            if ($item->getCommande() === $this) {
                $item->setCommande(null);
            }
        }

        return $this;
    }

    public function getPaiement(): ?Paiement
    {
        return $this->paiement;
    }

    public function setPaiement(?Paiement $paiement): self
    {
        $this->paiement = $paiement;

        return $this;
    }

    public function isPayee(): bool
    {
        return $this->paiement !== null && $this->paiement->isValide();
    }
}
