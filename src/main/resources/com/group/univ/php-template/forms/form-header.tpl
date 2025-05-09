<?php

namespace App\Form;

use App\Entity\{{ENTITY_NAME}};
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;
{{TYPE_IMPORTS}}
class {{ENTITY_NAME}}Type extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options): void
    {
