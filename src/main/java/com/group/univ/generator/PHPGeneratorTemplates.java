package com.group.univ.generator;

import com.group.univ.model.Entity;
import com.group.univ.model.Field;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

public class PHPGeneratorTemplates {

    public static final String TWIG_TEMPLATE_PATH = "src/main/resources/com/group/univ/php-template/forms/";
    public static final String TEMPLATE_CREATE_FORM = TWIG_TEMPLATE_PATH + "form-create.twig.tpl";
    public static final String TEMPLATE_INDEX = TWIG_TEMPLATE_PATH + "form-index.twig.tpl";

    public void generateTwigTemplates(Map<String, Entity> entities) throws IOException {
        File templatesDir = new File("symfony/templates/");
        if (!templatesDir.exists()) {
            templatesDir.mkdirs();
        }

        for (Entity entity : entities.values()) {
            String entityDirName = entity.getName().toLowerCase();
            File entityDir = new File(templatesDir, entityDirName);
            if (!entityDir.exists()) {
                entityDir.mkdirs();
            }

            // Page de création
            writeTemplateToFile(entityDir, generateCreateTemplate(entity), "create.html.twig");

            // Page d'index
            writeTemplateToFile(entityDir, generateIndexTemplate(entity), "index.html.twig");
        }
    }

    private void writeTemplateToFile(File dir, String content, String filename) throws IOException {
        try (PrintWriter out = new PrintWriter(new File(dir, filename))) {
            out.print(content);
        }
        System.out.println("Fichier Twig généré : " + new File(dir, filename).getPath());
    }

    public String generateCreateTemplate(Entity entity) {
        String template = readTemplateFile(TEMPLATE_CREATE_FORM);
        return template
                .replace("{{ENTITY_NAME}}", entity.getName())
                .replace("{{ENTITY_NAME_LC}}", entity.getName().toLowerCase());
    }

    public String generateIndexTemplate(Entity entity) {
        String template = readTemplateFile(TEMPLATE_INDEX);
        StringBuilder headers = new StringBuilder();
        StringBuilder rows = new StringBuilder();

        for (Field field : entity.getFields()) {
            if (!field.isId()) {
                String fieldName = field.getName();
                headers.append("<th>").append(fieldName).append("</th>\n");
                rows.append("<td>{{ item.").append(fieldName).append(" }}</td>\n");
            }
        }

        return template
                .replace("{{ENTITY_NAME}}", entity.getName())
                .replace("{{ENTITY_NAME_LC}}", entity.getName().toLowerCase())
                .replace("{{TABLE_HEADERS}}", headers.toString().trim())
                .replace("{{TABLE_ROWS}}", rows.toString().trim());
    }

    private String readTemplateFile(String path) {
        try {
            return new String(Files.readAllBytes(Paths.get(path)));
        } catch (IOException e) {
            throw new RuntimeException("Erreur lecture template : " + path + " - " + e.getMessage());
        }
    }
}
