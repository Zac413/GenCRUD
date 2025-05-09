        ;

    }

    public function configureOptions(OptionsResolver $resolver): void
    {
        $resolver->setDefaults([
            'data_class' => {{ENTITY_NAME}}::class,
        ]);
    }
}
