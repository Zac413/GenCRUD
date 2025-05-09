package com.group.univ.generator;

import com.group.univ.model.Entity;
import com.group.univ.model.Field;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class PHPGeneratorForms {

    private static final String FORM_OUTPUT_DIR = "symfony/src/Form";

    // Génère les fichiers de formulaire Symfony pour chaque entité
    public void generateFormFiles(Map<String, Entity> entities) throws IOException {
        File dir = new File(FORM_OUTPUT_DIR);
        if (!dir.exists()) {
            dir.mkdir();
        }
        for (Entity entity : entities.values()) {
            File file = new File(dir, entity.getName() + "Type.php");
            try (FileWriter writer = new FileWriter(file)) {
                writer.write(generateFormCode(entity));
            }
            System.out.println("Fichier généré : " + entity.getName() + "Type.php");
        }
    }

    // Génère le code PHP du formulaire pour une entité donnée
    private String generateFormCode(Entity entity) {
        StringBuilder sb = new StringBuilder();
        sb.append("<?php\n");
        sb.append("namespace App\\Form;\n\n");
        sb.append("use App\\Entity\\").append(entity.getName()).append(";\n");
        sb.append("use Symfony\\Component\\Form\\AbstractType;\n");
        sb.append("use Symfony\\Component\\Form\\FormBuilderInterface;\n");
        sb.append("use Symfony\\Component\\OptionsResolver\\OptionsResolver;\n");

        // 1) On collecte les types de champs uniques
        Set<String> formTypes = new LinkedHashSet<>();
        for (Field field : entity.getFields()) {
            if (!field.isId()) {
                formTypes.add(mapToFormType(field.getType()));
            }
        }

        // 2) On écrit chaque use une seule fois
        for (String type : formTypes) {
            sb.append("use Symfony\\Component\\Form\\Extension\\Core\\Type\\")
                    .append(type)
                    .append(";\n");
        }

        sb.append("\nclass ").append(entity.getName()).append("Type extends AbstractType\n");
        sb.append("{\n");
        sb.append("    public function buildForm(FormBuilderInterface $builder, array $options): void\n");
        sb.append("    {\n");
        sb.append("        $builder\n");

        for (Field field : entity.getFields()) {
            if (!field.isId()) {
                String phpFormType = mapToFormType(field.getType());
                sb.append("            ->add('")
                        .append(field.getName())
                        .append("', ")
                        .append(phpFormType)
                        .append("::class)\n");
            }
        }

        sb.append("        ;\n");
        sb.append("    }\n\n");

        sb.append("    public function configureOptions(OptionsResolver $resolver): void\n");
        sb.append("    {\n");
        sb.append("        $resolver->setDefaults([\n");
        sb.append("            'data_class' => ")
                .append(entity.getName())
                .append("::class,\n");
        sb.append("        ]);\n");
        sb.append("    }\n");
        sb.append("}\n");

        return sb.toString();
    }


    // Mappe les types métier vers les types de champ Symfony
    private String mapToFormType(String type) {
        return switch (type.toLowerCase()) {
            case "int", "integer", "long", "float", "double", "decimal" -> "NumberType";
            case "date", "localdate" -> "DateType";
            case "datetime", "timestamp" -> "DateTimeType";
            case "boolean", "bool" -> "CheckboxType";
            default -> "TextType";
        };
    }
}
