package com.group.univ.generator;

import com.group.univ.model.Entity;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

public class PHPGeneratorTemplates {

    public static final String TWIG_TEMPLATE_PATH = "src/main/resources/com/group/univ/php-template/forms/";
    public static final String TEMPLATE_CREATE_FORM = TWIG_TEMPLATE_PATH + "form-create.twig.tpl";

    public void generateTwigTemplates(Map<String, Entity> entities) throws IOException {
        File templatesDir = new File("symfony/templates/");
        if (!templatesDir.exists()) {
            templatesDir.mkdirs();
        }

        for (Entity entity : entities.values()) {
            String templateContent = generateCreateTemplate(entity);
            String filename = "create_" + entity.getName().toLowerCase() + ".html.twig";
            try (PrintWriter out = new PrintWriter(new File(templatesDir, filename))) {
                out.print(templateContent);
            }
            System.out.println("Fichier Twig généré : " + filename);
        }
    }

    public String generateCreateTemplate(Entity entity) {
        String template;
        try {
            template = new String(Files.readAllBytes(Paths.get(TEMPLATE_CREATE_FORM)));
        } catch (IOException e) {
            throw new RuntimeException("Erreur lors de la lecture du fichier template Twig : " + e.getMessage());
        }

        return template
            .replace("{{ENTITY_NAME}}", entity.getName())
            .replace("{{ENTITY_NAME_LC}}", entity.getName().toLowerCase());
    }
}
