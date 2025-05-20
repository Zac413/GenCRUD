<?php

namespace App\Form;

use App\Entity\Produit;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;
use Symfony\Bridge\Doctrine\Form\Type\EntityType;
use Symfony\Component\Form\Extension\Core\Type\TextType;

use Symfony\Component\Form\Extension\Core\Type\NumberType;

use App\Entity\Command;
class ProduitType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options): void
    {
$builder->add('prLabel', TextType::class);
        $builder->add('prPrixUnitaire', NumberType::class);
        $builder->add('commands', EntityType::class, [
        'class' => Command::class,
        'choice_label' => 'coLabel',
        'multiple' => true,
        'expanded' => false,
        'required'     => false,
        'placeholder' => 'Select Command',
        ]);
        ;

    }

    public function configureOptions(OptionsResolver $resolver): void
    {
        $resolver->setDefaults([
            'data_class' => Produit::class,
        ]);
    }
}