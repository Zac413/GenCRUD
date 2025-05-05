<?php

namespace App\Entity;

use Doctrine\ORM\Mapping as ORM;

/**
 * @ORM\Entity
 */
class Produit
{
    /**
    * @ORM\Id
    * @ORM\GeneratedValue
    * @ORM\Column(type="integer")
    */
    private $pr_id;

    /**
    * @ORM\Column(type="string", length=50)
    * 
    */
    private $pr_label;

    /**
    * @ORM\Column(type="float", length=255)
    * 
    */
    private $pr_prix_unitaire;

    public function getpr_id(): ?int
    {
        return $this->pr_id;
    }

    public function setpr_id(int $pr_id): self
    {
        $this->pr_id = $pr_id;
        return $this;
    }
    public function getpr_label(): ?string
    {
        return $this->pr_label;
    }

    public function setpr_label(string $pr_label): self
    {
        $this->pr_label = $pr_label;
        return $this;
    }
    public function getpr_prix_unitaire(): ?float
    {
        return $this->pr_prix_unitaire;
    }

    public function setpr_prix_unitaire(float $pr_prix_unitaire): self
    {
        $this->pr_prix_unitaire = $pr_prix_unitaire;
        return $this;
    }
}
