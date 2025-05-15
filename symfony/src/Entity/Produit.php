<?php

namespace App\Entity;

use Doctrine\ORM\Mapping as ORM;


#[ORM\Entity]
class Produit
{

        #[ORM\ManyToOne(targetEntity: Command::class, inversedBy: 'produits')]
        #[ORM\JoinColumn(name: 'pr_co_id', referencedColumnName: 'co_id', nullable: true)]
        private ?Command $command = null;


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

    public function getCommand(): ?Command
    {
        return $this->command;
    }

    public function setCommand(?Command $command): self
    {
        $this->command = $command;
        return $this;
    }


    /**
     * Retourne une représentation chaîne de cet objet.
     */
    public function __toString(): string
    {
        return $this->pr_id.' '.$this->pr_label.' '.$this->pr_prix_unitaire.' '.' ';
    }

}
