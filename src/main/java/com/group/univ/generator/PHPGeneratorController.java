package com.group.univ.generator;

import com.group.univ.model.Entity;
import com.group.univ.model.Relation;
import com.group.univ.utils.Utils;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

public class PHPGeneratorController {

    public static final String PHP_TEMPLATE_PATH = "src/main/resources/com/group/univ/php-template/";
    public static final String TEMPLATE_CONTROLLER = PHP_TEMPLATE_PATH + "controller/";
    private static final String CONTROLLER_TEMPLATE_PHP_TPL = TEMPLATE_CONTROLLER + "controller.php.tpl";
    private static final String CONTROLLER_FOREACH_OTM_PHP_TPL = TEMPLATE_CONTROLLER + "controller-foreach-onetomany.php.tpl";
    private static final String CONTROLLER_EDIT_OTM_PHP_TPL = TEMPLATE_CONTROLLER + "controller-edit-onetomany.php.tpl";
    private static final String CONTROLLER_IMPORTS_OTM_PHP_TPL = TEMPLATE_CONTROLLER + "controller-imports-onetomany.php.tpl";

    private static final String INDEX_TEMPLATE_PHP_TPL      =
            TEMPLATE_CONTROLLER + "index.php.tpl";

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

    public void generateIndexController() throws IOException {
        File generatedDir = new File(OUTPUT_DIR);
        if (!generatedDir.exists()) generatedDir.mkdir();

        String tpl = Files.readString(Paths.get(INDEX_TEMPLATE_PHP_TPL));

        File out = new File(generatedDir, "IndexController.php");
        try (PrintWriter writer = new PrintWriter(out)) {
            writer.print(tpl);
        }
        System.out.println("Fichier généré : IndexController.php");
    }

    private String generatePhpControllerCode(Entity entity) {
        String tpl, tpl_edit, tpl_foreach, tpl_imports ;
        try {
            tpl = Files.readString(Paths.get(CONTROLLER_TEMPLATE_PHP_TPL));
            tpl_edit = Files.readString(Paths.get(CONTROLLER_EDIT_OTM_PHP_TPL));
            tpl_foreach = Files.readString(Paths.get(CONTROLLER_FOREACH_OTM_PHP_TPL));
            tpl_imports = Files.readString(Paths.get(CONTROLLER_IMPORTS_OTM_PHP_TPL));
        } catch (IOException e) {
            throw new RuntimeException("Impossible de lire le template : " + e.getMessage(), e);
        }


        for(Relation relation: entity.getRelations()) {
            if(relation.getType().equalsIgnoreCase("one-to-many")) {
                tpl_foreach = tpl_foreach.replace("{{ONETOMANY_TO}}", relation.getTo());
                tpl_foreach = tpl_foreach.replace("{{ONETOMANY_to}}", Utils.lcfirst(relation.getTo()));
                tpl_foreach = tpl_foreach.replace("{{ONETOMANY_FROM}}", relation.getFrom());
                tpl_foreach = tpl_foreach.replace("{{ONETOMANY_from}}", Utils.lcfirst(relation.getFrom()));

                tpl_edit = tpl_edit.replace("{{ONETOMANY_TO}}", relation.getTo());
                tpl_edit = tpl_edit.replace("{{ONETOMANY_to}}", Utils.lcfirst(relation.getTo()));
                tpl_edit = tpl_edit.replace("{{ONETOMANY_FROM}}", relation.getFrom());
                tpl_edit = tpl_edit.replace("{{ONETOMANY_from}}", Utils.lcfirst(relation.getFrom()));


                tpl_imports = tpl_imports.replace("{{ONETOMANY_TO}}", relation.getTo());

                tpl = tpl.replace("{{EDIT_ONETOMANY}}", tpl_edit);
                tpl = tpl.replace("{{IMPORT_ONETOMANY}}", tpl_imports);

                tpl = tpl.replace("{{FOREACH_ONETOMANY}}", tpl_foreach);

            }
        }

        return tpl
                .replace("{{ENTITY_NAME}}", entity.getName())
                .replace("{{ENTITY_NAME_LOWER}}", Utils.lcfirst(entity.getName()))
                .replace("{{IMPORT_ONETOMANY}}", "")
                .replace("{{FOREACH_ONETOMANY}}", "")
                .replace("{{EDIT_ONETOMANY}}", "");

    }
}
