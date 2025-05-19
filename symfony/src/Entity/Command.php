<?php

namespace App\Entity;

use Doctrine\ORM\Mapping as ORM;

use App\Entity\Client;

use Doctrine\Common\Collections\ArrayCollection;
use Doctrine\Common\Collections\Collection;


use App\Entity\Produit;


#[ORM\Entity]
class Command
{

        #[ORM\Id]
        #[ORM\GeneratedValue]
        #[ORM\Column(type: 'integer')]
        private ?int $co_id = null;

        #[ORM\Column(type: 'datetime', length: 255)]
        private ?\DateTime $co_date = null;


        #[ORM\Column(type: 'float', length: 255)]
        private ?float $co_prix = null;


        #[ORM\OneToOne(targetEntity: Client::class)]
        #[ORM\JoinColumn(name: 'co_cl_id', referencedColumnName: 'cl_id')]
        private ?Client $client = null;

    #[ORM\OneToMany(targetEntity: Produit::class, mappedBy: 'command', cascade: ['persist'], orphanRemoval: true)]
    private ?Collection $produits;

    public function __construct()
    {

        $this->produits = new ArrayCollection();

    }

    public function getCoId(): ?int
    {
        return $this->co_id;
    }

    public function getCoDate(): ?\DateTime
    {
        return $this->co_date;
    }

    public function setCoDate(\DateTime $co_date): self
    {
        $this->co_date = $co_date;
        return $this;
    }

    public function getCoPrix(): ?float
    {
        return $this->co_prix;
    }

    public function setCoPrix(float $co_prix): self
    {
        $this->co_prix = $co_prix;
        return $this;
    }

    public function getClient(): ?Client
    {
        return $this->client;
    }

    public function setClient(Client $client): self
    {
        $this->client = $client;
        return $this;
    }

    public function getProduits(): ?Collection
    {
        return $this->produits;
    }

    public function setProduits(Collection $produits): self
    {
        $this->produits = $produits;
        return $this;
    }

    public function addProduit(Produit $produit): self
    {
        if (!$this->produits->contains($produit))
        {
            $this->produits[] = $produit;
            $produit->setCommand($this);


        }
        return $this;
    }

    public function removeProduit(Produit $produit): self
    {
        $this->produits->removeElement($produit);
        $produit->setCommand(null);
        return $this;
    }

    /**
     * Retourne une représentation chaîne de cet objet.
     */
    public function __toString(): string
    {
        return $this->co_id.' '.$this->co_date->format('Y-m-d H:i:s').' '.$this->co_prix.' '.$this->client->__toString().' '.' ';
    }

}
