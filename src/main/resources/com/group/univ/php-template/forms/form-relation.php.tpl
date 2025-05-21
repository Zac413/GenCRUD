        $builder->add('{{RELATION_NAME_LOWER}}', EntityType::class, [
        'class' => {{RELATION_CLASS}}::class,
        'choice_label' => '{{TWO_FIRST_LETTER}}Label',
        'placeholder' => 'Select {{RELATION_CLASS}}',

        ]);
