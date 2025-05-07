package com.group.univ.generator;

import com.group.univ.model.Entity;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

public class PHPGeneratorRepository {

    public static String PHP_TEMPLATE_PATH = "src/main/resources/com/group/univ/php-template/";
    public static String REPOSITORY_TEMPLATE_PHP_TPL = PHP_TEMPLATE_PATH + "repository.php.tpl";

    // Génère les fichiers PHP pour chaque repository
    public void generatePhpRepositories(Map<String, Entity> entities) throws IOException {
        File generatedDir = new File("symfony/src/Repository");
        if (!generatedDir.exists()) {
            generatedDir.mkdir();
        }
        for (Entity entity : entities.values()) {
            String phpCode = generatePhpRepositoryCode(entity);
            String fileName = entity.getName() + "Repository.php";
            try (PrintWriter out = new PrintWriter(new File(generatedDir, fileName))) {
                out.print(phpCode);
            }
            System.out.println("Fichier généré : " + fileName);
        }
    }

    // Génère le code PHP pour un repository
    public String generatePhpRepositoryCode(Entity entity) {
        String template;
        try {
            template = new String(java.nio.file.Files.readAllBytes(java.nio.file.Paths.get(REPOSITORY_TEMPLATE_PHP_TPL)));
        } catch (IOException e) {
            throw new RuntimeException("Erreur lors de la lecture du fichier template : " + e.getMessage());
        }
        template = template.replace("{{ENTITY_NAME}}", entity.getName());
        return template;
    }
}