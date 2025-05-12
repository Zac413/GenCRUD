package com.group.univ.generator;

import com.group.univ.model.Entity;
import com.group.univ.model.Field;
import com.group.univ.utils.Utils;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class PHPGeneratorForms {

    private static final String FORM_TEMPLATE_PATH   = "src/main/resources/com/group/univ/php-template/forms/";
    private static final String HEADER_TPL           = FORM_TEMPLATE_PATH + "form-header.php.tpl";
    private static final String IMPORT_TPL           = FORM_TEMPLATE_PATH + "form-type-import.php.tpl";
    private static final String FIELD_TPL            = FORM_TEMPLATE_PATH + "form-field.php.tpl";
    private static final String FOOTER_TPL           = FORM_TEMPLATE_PATH + "form-footer.php.tpl";

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
        // 1) Lecture du header
        String headerTpl = Files.readString(Paths.get(HEADER_TPL));

        // 2) Collecte des FormType à importer
        Set<String> formTypes = new LinkedHashSet<>();
        for (Field f : entity.getFields()) {
            if (!f.isId()) {
                formTypes.add(mapToFormType(f.getType()));
            }
        }

        // 3) Lecture du template d'un import et génération du bloc {{IMPORTS}}
        String singleImportTpl = Files.readString(Paths.get(IMPORT_TPL));
        StringBuilder imports = new StringBuilder();
        for (String type : formTypes) {
            imports
                    .append(singleImportTpl.replace("{{TYPE}}", type))
                    .append("\n");
        }

        // 4) Injection dans le header
        String header = headerTpl
                .replace("{{ENTITY_NAME}}", entity.getName())
                .replace("{{IMPORTS}}", imports.toString().trim());

        // 5) Génération du code des champs
        StringBuilder fieldsCode = new StringBuilder();
        for (Field f : entity.getFields()) {
            if (!f.isId()) {
                String line = Files.readString(Paths.get(FIELD_TPL))
                        .replace("{{FIELD_NAME}}", Utils.lcfirst(Utils.toCamelCase(f.getName())))
                        .replace("{{FIELD_TYPE}}", mapToFormType(f.getType()));
                fieldsCode.append(line).append("\n");
            }
        }

        // 6) Lecture et complétion du footer
        String footer = Files.readString(Paths.get(FOOTER_TPL))
                .replace("{{ENTITY_NAME}}", entity.getName());

        // 7) Concaténation finale
        return header + "\n"
                + fieldsCode.toString().trim() + "\n"
                + footer;
    }

    private String mapToFormType(String type) {
        return switch (type.toLowerCase()) {
            case "int","integer","long","float","double","decimal" -> "NumberType";
            case "date","localdate"                               -> "DateType";
            case "datetime","timestamp"                           -> "DateTimeType";
            case "boolean","bool"                                 -> "CheckboxType";
            default                                                -> "TextType";
        };
    }
}
