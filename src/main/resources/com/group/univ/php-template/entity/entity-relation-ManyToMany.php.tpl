
        #[ORM\ManyToMany(targetEntity: {{RELATION_TO}}::class)]
        #[ORM\JoinTable(
                name: '{{RELATION_to}}s_{{ENTITY_NAME}}s',
                joinColumns: [new ORM\JoinColumn(name: '{{TWO_LETTER_ENTITY}}_id', referencedColumnName: '{{TWO_LETTER_ENTITY}}_id')],
                inverseJoinColumns: [new ORM\JoinColumn(name: '{{TWO_FIRST_LETTER}}_id', referencedColumnName: '{{TWO_FIRST_LETTER}}_id')]
        )]
        private Collection ${{RELATION_to}}s;
