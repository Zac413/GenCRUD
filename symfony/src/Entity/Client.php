<?php

namespace App\Entity;

use Doctrine\ORM\Mapping as ORM;


#[ORM\Entity]
class Client
{

        #[ORM\Id]
        #[ORM\GeneratedValue]
        #[ORM\Column(type: 'integer')]
        private ?int $cl_id = null;

        #[ORM\Column(type: 'string', length: 30)]
        private ?string $cl_nom = null;


        #[ORM\Column(type: 'string', length: 30)]
        private ?string $cl_prenom = null;


    public function __construct()
    {

    }

    public function getClId(): ?int
    {
        return $this->cl_id;
    }

    public function getClNom(): ?string
    {
        return $this->cl_nom;
    }

    public function setClNom(string $cl_nom): self
    {
        $this->cl_nom = $cl_nom;
        return $this;
    }

    public function getClPrenom(): ?string
    {
        return $this->cl_prenom;
    }

    public function setClPrenom(string $cl_prenom): self
    {
        $this->cl_prenom = $cl_prenom;
        return $this;
    }

    /**
     * Retourne une représentation chaîne de cet objet.
     */
    public function __toString(): string
    {
        return $this->cl_id.' '.$this->cl_nom.' '.$this->cl_prenom.' '.' ';
    }

}
