<?php

namespace App\Entity;

use Doctrine\ORM\Mapping as ORM;

/**
 * @ORM\Entity
 */
class Command
{
    /**
    * @ORM\Id
    * @ORM\GeneratedValue
    * @ORM\Column(type="integer")
    */
    private $co_id;

    /**
    * @ORM\Column(type="\DateTimeInterface", length=255)
    * 
    */
    private $co_date;

    /**
    * @ORM\Column(type="float", length=255)
    * 
    */
    private $co_prix;

    /**
    * @ORM\OneToOne(targetEntity="Client")
    * @ORM\JoinColumn(name="co_cu_id", referencedColumnName="id")
    */
    private $client;

    /**
    * @ORM\OneToMany(targetEntity="Produit", mappedBy="command")
    */
    private $produits;

    public function getco_id(): ?int
    {
        return $this->co_id;
    }

    public function setco_id(int $co_id): self
    {
        $this->co_id = $co_id;
        return $this;
    }
    public function getco_date(): ?\DateTimeInterface
    {
        return $this->co_date;
    }

    public function setco_date(\DateTimeInterface $co_date): self
    {
        $this->co_date = $co_date;
        return $this;
    }
    public function getco_prix(): ?float
    {
        return $this->co_prix;
    }

    public function setco_prix(float $co_prix): self
    {
        $this->co_prix = $co_prix;
        return $this;
    }
}
