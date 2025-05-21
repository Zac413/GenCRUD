<?php

namespace App\DataFixtures;

use App\Entity\Client;
use App\Entity\Command;
use App\Entity\Produit;
use Doctrine\Bundle\FixturesBundle\Fixture;
use Doctrine\Persistence\ObjectManager;
use Faker\Factory;

class AppFixtures extends Fixture
{
    public function load(ObjectManager $manager): void
    {
        $faker = Factory::create('fr_FR');

        // Générer des produits
        $produits = [];
        for ($i = 0; $i < 10; $i++) {
            $produit = new Produit();
            $produit->setPrLabel($faker->word());
            $produit->setPrPrixUnitaire($faker->randomFloat(2, 5, 100));
            $manager->persist($produit);
            $produits[] = $produit;
        }

        // Générer des clients avec une seule commande chacun
        for ($i = 0; $i < 5; $i++) {
            $client = new Client();
            $client->setClLabel($faker->company());
            $client->setClNom($faker->lastName());
            $manager->persist($client);

            $commande = new Command();
            $commande->setCoLabel($faker->sentence(3));
            $commande->setCoDate($faker->dateTimeBetween('-1 year', 'now'));
            $commande->setClient($client);

            // Ajouter 1 à 3 produits à la commande
            $selectedProduits = $faker->randomElements($produits, rand(1, 3));
            $total = 0;
            foreach ($selectedProduits as $produit) {
                $commande->addProduit($produit);
                $total += $produit->getPrPrixUnitaire();
            }
            $commande->setCoPrix($total);

            $manager->persist($commande);
        }

        $manager->flush();
    }
}
