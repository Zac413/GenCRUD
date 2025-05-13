<?php

namespace App\Form;

use App\Entity\Command;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;
use Symfony\Component\Form\Extension\Core\Type\DateType;
use Symfony\Component\Form\Extension\Core\Type\NumberType;
class CommandType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options): void
    {
$builder->add('coDate', DateType::class);
        $builder->add('coPrix', NumberType::class);
        ;

    }

    public function configureOptions(OptionsResolver $resolver): void
    {
        $resolver->setDefaults([
            'data_class' => Command::class,
        ]);
    }
}