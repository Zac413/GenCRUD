<?php

namespace App\Entity;

use Doctrine\Common\Collections\ArrayCollection;
use Doctrine\Common\Collections\Collection;
use Doctrine\ORM\Mapping as ORM;

use App\Entity\Command;


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

    #[ORM\ManyToMany(targetEntity: Command::class, mappedBy: 'produits')]
    private Collection $commands;


    public function __construct()
    {
        $this->commands = new ArrayCollection();
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

    public function getCommands(): ?Collection
    {
        return $this->commands;
    }

    public function setCommands(Collection $commands): self
    {
        $this->$commands = $commands;
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
