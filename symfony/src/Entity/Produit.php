<?php

namespace App\Entity;

use Doctrine\ORM\Mapping as ORM;


#[ORM\Entity]
class Produit
{

        #[ORM\Id]
        #[ORM\GeneratedValue]
        #[ORM\Column(type: 'integer')]
        private ?int $pr_id = null;

        #[ORM\Column(type: 'string', length: 50)]
        private ?string $pr_label = null;


        #[ORM\Column(type: 'float', length: 255)]
        private ?float $pr_prix_unitaire = null;


    public function __construct()
    {

    }

    public function getPrId(): ?int
    {
        return $this->pr_id;
    }

    public function getPrLabel(): ?string
    {
        return $this->pr_label;
    }

    public function setPrLabel(string $pr_label): self
    {
        $this->pr_label = $pr_label;
        return $this;
    }

    public function getPrPrixUnitaire(): ?float
    {
        return $this->pr_prix_unitaire;
    }

    public function setPrPrixUnitaire(float $pr_prix_unitaire): self
    {
        $this->pr_prix_unitaire = $pr_prix_unitaire;
        return $this;
    }

}
