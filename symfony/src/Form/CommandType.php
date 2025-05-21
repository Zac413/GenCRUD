<?php

namespace App\Form;

use App\Entity\Command;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;
use Symfony\Bridge\Doctrine\Form\Type\EntityType;
use Symfony\Component\Form\Extension\Core\Type\TextType;

use Symfony\Component\Form\Extension\Core\Type\DateType;

use Symfony\Component\Form\Extension\Core\Type\NumberType;

use App\Entity\Client;
use App\Entity\Produit;
class CommandType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options): void
    {
$builder->add('coLabel', TextType::class);
        $builder->add('coDate', DateType::class);
        $builder->add('coPrix', NumberType::class);
        $builder->add('client', EntityType::class, [
        'class' => Client::class,
        'choice_label' => 'clLabel',
        'placeholder' => 'Select Client',

        ]);

        $builder->add('produits', EntityType::class, [
        'class' => Produit::class,
        'choice_label' => 'prLabel',
        'multiple' => true,
        'expanded' => false,
        'required'     => false,
        'placeholder' => 'Select Produit',
        ]);
        ;

    }

    public function configureOptions(OptionsResolver $resolver): void
    {
        $resolver->setDefaults([
            'data_class' => Command::class,
        ]);
    }
}