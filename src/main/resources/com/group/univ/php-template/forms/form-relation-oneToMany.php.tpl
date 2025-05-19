        $builder->add('{{RELATION_NAME_LOWER}}s', EntityType::class, [
        'class' => {{RELATION_CLASS}}::class,
        'choice_label' => '{{TWO_FIRST_LETTER}}Label',
        'multiple' => true,
        'expanded' => false,
        'required'     => false,
        'placeholder' => 'Select {{RELATION_CLASS}}',
        ]);

