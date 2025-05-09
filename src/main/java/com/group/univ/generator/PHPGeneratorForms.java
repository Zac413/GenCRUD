package com.group.univ.generator;

import com.group.univ.model.Entity;
import com.group.univ.model.Field;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class PHPGeneratorForms {

    private static final String FORM_TEMPLATE_PATH = "src/main/resources/com/group/univ/php-template/forms/";
    private static final String HEADER_TPL         = FORM_TEMPLATE_PATH + "form-header.tpl";
    private static final String FIELD_TPL          = FORM_TEMPLATE_PATH + "form-field.tpl";
    private static final String FOOTER_TPL         = FORM_TEMPLATE_PATH + "form-footer.tpl";

    private static final String OUTPUT_DIR = "symfony/src/Form";

    public void generateFormFiles(Map<String, Entity> entities) throws IOException {
        File dir = new File(OUTPUT_DIR);
        if (!dir.exists()) dir.mkdirs();

        for (Entity entity : entities.values()) {
            String code = generateFormCode(entity);
            File out = new File(dir, entity.getName() + "Type.php");
            try (PrintWriter writer = new PrintWriter(out)) {
                writer.print(code);
            }
            System.out.println("Formulaire généré : " + out.getPath());
        }
    }

    private String generateFormCode(Entity entity) throws IOException {
        // Collect unique form types
        Set<String> formTypes = new LinkedHashSet<>();
        for (Field field : entity.getFields()) {
            if (!field.isId()) {
                formTypes.add(mapToFormType(field.getType()));
            }
        }

        // Build TYPE_IMPORTS for header
        StringBuilder typeImports = new StringBuilder();
        for (String type : formTypes) {
            typeImports
                    .append("use Symfony\\Component\\Form\\Extension\\Core\\Type\\")
                    .append(type)
                    .append(";\n");
        }

        // Read header template and replace placeholders
        String header = readTpl(HEADER_TPL)
                .replace("{{ENTITY_NAME}}", entity.getName())
                .replace("{{TYPE_IMPORTS}}", typeImports.toString().trim());

        // Read footer template
        String footer = readTpl(FOOTER_TPL)
                .replace("{{ENTITY_NAME}}", entity.getName());

        // Generate fields
        StringBuilder fieldsCode = new StringBuilder();
        for (Field field : entity.getFields()) {
            if (!field.isId()) {
                String line = readTpl(FIELD_TPL)
                        .replace("{{FIELD_NAME}}", field.getName())
                        .replace("{{FIELD_TYPE}}", mapToFormType(field.getType()));
                fieldsCode.append(line).append("\n");
            }
        }

        return header + "\n" + fieldsCode.toString().trim() + "\n" + footer;
    }

    private String mapToFormType(String type) {
        return switch (type.toLowerCase()) {
            case "int", "integer", "long", "float", "double", "decimal" -> "NumberType";
            case "date", "localdate" -> "DateType";
            case "datetime", "timestamp" -> "DateTimeType";
            case "boolean", "bool" -> "CheckboxType";
            default -> "TextType";
        };
    }

    private String readTpl(String path) {
        try {
            return new String(Files.readAllBytes(Paths.get(path)));
        } catch (IOException e) {
            throw new RuntimeException("Impossible de lire le template : " + path, e);
        }
    }
}
