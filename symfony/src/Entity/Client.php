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
        private ?string $cl_label = null;


        #[ORM\Column(type: 'string', length: 30)]
        private ?string $cl_nom = null;


    public function __construct()
    {

    }

    public function getClId(): ?int
    {
        return $this->cl_id;
    }

    public function getClLabel(): ?string
    {
        return $this->cl_label;
    }

    public function setClLabel(string $cl_label): self
    {
        $this->cl_label = $cl_label;
        return $this;
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

    /**
     * Retourne une représentation chaîne de cet objet.
     */
    public function __toString(): string
    {
        return $this->cl_id.' '.$this->cl_label.' '.$this->cl_nom.' '.' ';
    }

}
