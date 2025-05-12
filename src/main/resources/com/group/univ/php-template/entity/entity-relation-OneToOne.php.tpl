
        #[ORM\OneToOne(targetEntity: {{RELATION_TO}}::class)]
        #[ORM\JoinColumn(name: '{{RELATION_NAME}}', referencedColumnName: '{{TWO_FIRST_LETTER}}_id')]
        private ?{{FIELD_TYPE}} ${{RELATION_to}} = null;
