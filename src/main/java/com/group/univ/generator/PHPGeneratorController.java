package com.group.univ.generator;

import com.group.univ.model.Entity;
import com.group.univ.utils.Utils;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

public class PHPGeneratorController {

    public static String PHP_TEMPLATE_PATH = "src/main/resources/com/group/univ/php-template/";
    public static String CONTROLLER_TEMPLATE_PHP_TPL = PHP_TEMPLATE_PATH + "controller.php.tpl";

    // Génère les fichiers PHP pour chaque contrôleur
    public void generatePhpControllers(Map<String, Entity> entities) throws IOException {
        File generatedDir = new File("symfony/src/Controller");
        if (!generatedDir.exists()) {
            generatedDir.mkdir();
        }
        for (Entity entity : entities.values()) {
            String phpCode = generatePhpControllerCode(entity);
            try (PrintWriter out = new PrintWriter(new File(generatedDir, entity.getName() + "Controller.php"))) {
                out.print(phpCode);
            }
            System.out.println("Fichier généré : " + entity.getName() + "Controller.php");
        }
    }

    // Génère le code PHP pour un contrôleur
    public String generatePhpControllerCode(Entity entity) {
        String template;
        try {
            template = new String(java.nio.file.Files.readAllBytes(java.nio.file.Paths.get(CONTROLLER_TEMPLATE_PHP_TPL)));
        } catch (IOException e) {
            throw new RuntimeException("Erreur lors de la lecture du fichier template : " + e.getMessage());
        }

        template = template.replace("{{ENTITY_NAME}}", entity.getName());
        template = template.replace("{{ENTITY_NAME_LOWER}}", Utils.lcfirst(entity.getName()));

        return template;
    }
}