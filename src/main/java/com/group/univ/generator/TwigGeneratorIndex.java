package com.group.univ.generator;

            import com.group.univ.model.Entity;
            import java.io.File;
            import java.io.IOException;
            import java.io.PrintWriter;
            import java.nio.file.Files;
            import java.nio.file.Paths;
            import java.util.Map;

            public class TwigGeneratorIndex {

                public static String PHP_TEMPLATE_PATH = "src/main/resources/com/group/univ/php-template/index/";
                public static String INDEX_TEMPLATE_PHP_TPL = PHP_TEMPLATE_PATH + "index.html.twig.tpl";

                public void generateIndexTemplates(Map<String, Entity> entities) throws IOException {
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

                        String template = new String(Files.readAllBytes(Paths.get(INDEX_TEMPLATE_PHP_TPL)));
                        StringBuilder fieldsList = new StringBuilder();
                        for (var field : entity.getFields()) {
                            fieldsList.append(field.getName()).append(",");
                        }
                        String fields = fieldsList.length() > 0 ? fieldsList.substring(0, fieldsList.length() - 1) : "";

                        String twig = template
                            .replace("{{ENTITY_NAME}}", entity.getName())
                            .replace("{{ENTITY_NAME_LC}}", entity.getName().toLowerCase())
                            .replace("FIELDS", fields);

                        String filename = entityDirName + "_index.html.twig";
                        try (PrintWriter out = new PrintWriter(new File(entityDir, filename))) {
                            out.print(twig);
                        }
                        System.out.println("Fichier Twig généré : " + entityDirName + "/" + filename);
                    }
                }
            }