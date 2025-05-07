;
}

public function configureOptions(OptionsResolver $resolver): void
{
$resolver->setDefaults([
'data_class' => {{CLASS_NAME}}::class,
]);
}
}
