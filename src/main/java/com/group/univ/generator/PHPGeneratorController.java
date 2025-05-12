package com.group.univ.generator;

import com.group.univ.model.Entity;
import com.group.univ.utils.Utils;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

public class PHPGeneratorController {

    private static final String PHP_TEMPLATE_PATH            =
            "src/main/resources/com/group/univ/php-template/controller/";
    private static final String CONTROLLER_TEMPLATE_PHP_TPL =
            PHP_TEMPLATE_PATH + "controller.php.tpl";

    private static final String OUTPUT_DIR = "symfony/src/Controller";

    public void generatePhpControllers(Map<String, Entity> entities) throws IOException {
        File generatedDir = new File(OUTPUT_DIR);
        if (!generatedDir.exists()) generatedDir.mkdir();

        for (Entity entity : entities.values()) {
            String code = generatePhpControllerCode(entity);
            File out = new File(generatedDir, entity.getName() + "Controller.php");
            try (PrintWriter writer = new PrintWriter(out)) {
                writer.print(code);
            }
            System.out.println("Fichier généré : " + out.getName());
        }
    }

    private String generatePhpControllerCode(Entity entity) {
        String tpl;
        try {
            tpl = Files.readString(Paths.get(CONTROLLER_TEMPLATE_PHP_TPL));
        } catch (IOException e) {
            throw new RuntimeException("Impossible de lire le template : " + e.getMessage(), e);
        }

        return tpl
                .replace("{{ENTITY_NAME}}", entity.getName())
                .replace("{{ENTITY_NAME_LOWER}}", Utils.lcfirst(entity.getName()));
    }
}
