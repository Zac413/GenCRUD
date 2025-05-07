<?php

namespace App\Form;

use App\Entity\{{CLASS_NAME}};
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;
{{USE_FIELD_TYPES}}
class {{CLASS_NAME}}Type extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options): void
    {
        $builder
