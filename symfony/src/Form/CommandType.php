<?php

namespace App\Form;

use App\Entity\Command;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;
use Symfony\Bridge\Doctrine\Form\Type\EntityType;
use Symfony\Component\Form\Extension\Core\Type\DateType;

use Symfony\Component\Form\Extension\Core\Type\NumberType;
use Symfony\Component\Form\Extension\Core\Type\CollectionType;

use App\Entity\Client;
use App\Entity\Produit;
class CommandType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options): void
    {
        $builder->add('coDate', DateType::class)
        ->add('coPrix', NumberType::class)
        ->add('client', EntityType::class, [
        'class' => Client::class,
        'choice_label' => 'clId',
        'placeholder' => 'Select Client',])

        ->add('produits', EntityType::class, [
        'class'        => Produit::class,
        'choice_label' => 'prLabel',
        'multiple'     => true,
        'expanded'     => false,
        'required'     => false,
        'placeholder'  => 'Sélectionnez des produits',
    ]);

    }

    public function configureOptions(OptionsResolver $resolver): void
    {
        $resolver->setDefaults([
            'data_class' => Command::class,
        ]);
    }
}