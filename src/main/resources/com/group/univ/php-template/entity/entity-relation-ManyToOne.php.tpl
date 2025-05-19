
    #[ORM\ManyToOne(targetEntity: {{RELATION_TO}}::class, inversedBy: '{{ENTITY_NAME}}s')]
    #[ORM\JoinColumn(name: '{{TWO_LETTER_ENTITY}}_{{TWO_FIRST_LETTER}}_id', referencedColumnName: '{{TWO_FIRST_LETTER}}_id', nullable: true)]
    private ?{{RELATION_TO}} ${{RELATION_to}} = null;
