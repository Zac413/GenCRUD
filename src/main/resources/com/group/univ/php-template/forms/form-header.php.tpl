<?php

namespace App\Form;

use App\Entity\{{ENTITY_NAME}};
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;
use Symfony\Bridge\Doctrine\Form\Type\EntityType;
{{IMPORTS}}
class {{ENTITY_NAME}}Type extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options): void
    {