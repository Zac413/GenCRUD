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

    private static final String PHP_TEMPLATE_PATH             = "src/main/resources/com/group/univ/php-template/controller/";
    private static final String CONTROLLER_TEMPLATE_PHP_TPL   = PHP_TEMPLATE_PATH + "controller.php.tpl";
    private static final String OUTPUT_DIR                     = "symfony/src/Controller";

    public void generatePhpControllers(Map<String, Entity> entities) throws IOException {
        File generatedDir = new File(OUTPUT_DIR);
        if (!generatedDir.exists()) generatedDir.mkdirs();

        for (Entity entity : entities.values()) {
            String phpCode = generatePhpControllerCode(entity);
            File out = new File(generatedDir, entity.getName() + "Controller.php");
            try (PrintWriter outWriter = new PrintWriter(out)) {
                outWriter.print(phpCode);
            }
            System.out.println("Controller généré : " + out.getPath());
        }
    }

    private String generatePhpControllerCode(Entity entity) throws IOException {
        String template = new String(Files.readAllBytes(Paths.get(CONTROLLER_TEMPLATE_PHP_TPL)));

        String name       = entity.getName();                // ex. "Client"
        String lower      = Utils.lcfirst(name);             // ex. "client"
        String listVar    = lower + "s";                     // ex. "clients"

        return template
                .replace("{{ENTITY_NAME}}", name)
                .replace("{{ENTITY_NAME_LOWER}}", lower)
                .replace("{{LIST_VAR}}", listVar);
    }
}
