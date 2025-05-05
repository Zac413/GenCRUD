<?php

namespace App\Entity;

use Doctrine\ORM\Mapping as ORM;

/**
 * @ORM\Entity
 */
class Client
{
    /**
    * @ORM\Id
    * @ORM\GeneratedValue
    * @ORM\Column(type="integer")
    */
    private $cu_id;

    /**
    * @ORM\Column(type="string", length=30)
    * nom du client
    */
    private $cu_nom;

    /**
    * @ORM\Column(type="string", length=30)
    * prénom du client
    */
    private $cu_prenom;

    public function getcu_id(): ?int
    {
        return $this->cu_id;
    }

    public function setcu_id(int $cu_id): self
    {
        $this->cu_id = $cu_id;
        return $this;
    }
    public function getcu_nom(): ?string
    {
        return $this->cu_nom;
    }

    public function setcu_nom(string $cu_nom): self
    {
        $this->cu_nom = $cu_nom;
        return $this;
    }
    public function getcu_prenom(): ?string
    {
        return $this->cu_prenom;
    }

    public function setcu_prenom(string $cu_prenom): self
    {
        $this->cu_prenom = $cu_prenom;
        return $this;
    }
}
