package com.group.univ.generator;

import com.group.univ.model.Entity;
import com.group.univ.model.Field;
import com.group.univ.model.Relation;
import com.group.univ.utils.Utils;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

public class TWIGGeneratorIndex {

    public static final String TWIG_OUT_PATH = "symfony/templates/";

    public static final String TWIG_TEMPLATE_PATH = "src/main/resources/com/group/univ/twig-template/templates/";
    public static final String TEMPLATE_INDEX = TWIG_TEMPLATE_PATH + "index/";

    public static final String TEMPLATE_INDEX_HEADER = TEMPLATE_INDEX + "templates-index-header.twig.tpl";
    public static final String TEMPLATE_INDEX_FOOTER = TEMPLATE_INDEX + "templates-index-footer.twig.tpl";
    public static final String TEMPLATE_INDEX_FIELD_TD = TEMPLATE_INDEX + "templates-index-field-td.twig.tpl";
    public static final String TEMPLATE_INDEX_FIELD_TH = TEMPLATE_INDEX + "templates-index-field-th.twig.tpl";
    public static final String TEMPLATE_INDEX_TBODY_FOOTER = TEMPLATE_INDEX + "templates-index-tbody-footer.twig.tpl";
    public static final String TEMPLATE_INDEX_TBODY_HEADER = TEMPLATE_INDEX + "templates-index-tbody-header.twig.tpl";
    public static final String TEMPLATE_INDEX_THEAD_FOOTER = TEMPLATE_INDEX + "templates-index-thead-footer.twig.tpl";
    public static final String TEMPLATE_INDEX_THEAD_HEADER = TEMPLATE_INDEX + "templates-index-thead-header.twig.tpl";

    public void generateTwigFiles(Map<String, Entity> entities) throws IOException {
        for (Entity entity : entities.values()) {
            File generatedDir = new File(TWIG_OUT_PATH+""+entity.getName().toLowerCase());
            if (!generatedDir.exists()) {
                generatedDir.mkdir();
            }
            String twigIndexCode = generateTwigIndexCode(entity);
            try (PrintWriter out = new PrintWriter(new File(generatedDir, "index"+".html.twig"))) {
                out.print(twigIndexCode);
            }
            System.out.println("Génération du fichier index"+entity.getName() + ".html.twig");
        }

    }

    // Génère le code TWIG pour une entité
    public String generateTwigIndexCode(Entity entity) {
        StringBuilder twig = new StringBuilder();

        // Header
        twig.append(generateHeaderIndexTwigCode(entity));

        // Header Table
        twig.append(generateIndexTHeadHeaderTwigCode(entity));

        // Create Fields
        for (Field f : entity.getFields()) {
            twig.append(generateIndexFieldTHTwigCode(entity.getName(),f.getName()));
        }

        // Create Relations
        for (Relation r : entity.getRelations()) {
            String name = "";
            if(r.getType().equalsIgnoreCase("one-to-one")) {
                name = r.getTo();
            } else if (r.getType().equalsIgnoreCase("one-to-many")) {
                name = r.getTo()+"s";
            }
            twig.append(generateIndexFieldTHTwigCode(entity.getName(),name));
        }
        twig.append(generateIndexTHeadFooterTwigCode(entity));

        twig.append(generateIndexTBodyHeaderTwigCode(entity));
        for (Field f : entity.getFields()) {
            if(f.getType().equalsIgnoreCase("date")) {
                twig.append(generateIndexFieldTDDateTwigCode(entity.getName(),f.getName()));
            } else {
                twig.append(generateIndexFieldTDTwigCode(entity.getName(),f.getName()));
            }

        }

        // Create Relations
        for (Relation r : entity.getRelations()) {
            String name = "";
            if(r.getType().equalsIgnoreCase("one-to-one")) {
                name = r.getTo();
            } else if (r.getType().equalsIgnoreCase("one-to-many")) {
                name = r.getTo()+"s"+"| map(p => p.toString) | join(', ')\n";
            }
            twig.append(generateIndexFieldTDTwigCode(entity.getName(),name));
        }
        twig.append(generateIndexTBodyFooterTwigCode(entity));


        // Footer
        twig.append(generateFooterIndexTwigCode(entity));
        return twig.toString();
    }


    public String generateHeaderIndexTwigCode(Entity entity){
        String template;
        try {
            template = new String(java.nio.file.Files.readAllBytes(java.nio.file.Paths.get(TEMPLATE_INDEX_HEADER)));
        } catch (IOException e) {
            throw new RuntimeException("Erreur lors de la lecture du fichier template : " + e.getMessage());
        }

        template = template.replace("{{ENTITY_NAME}}", entity.getName());

        return template;
    }

    public String generateIndexTHeadHeaderTwigCode(Entity entity){
        String template;
        try {
            template = new String(java.nio.file.Files.readAllBytes(java.nio.file.Paths.get(TEMPLATE_INDEX_THEAD_HEADER)));
        } catch (IOException e) {
            throw new RuntimeException("Erreur lors de la lecture du fichier template : " + e.getMessage());
        }

        template = template.replace("{{ENTITY_NAME}}", entity.getName());

        return template;
    }

    public String generateIndexTHeadFooterTwigCode(Entity entity){
        String template;
        try {
            template = new String(java.nio.file.Files.readAllBytes(java.nio.file.Paths.get(TEMPLATE_INDEX_THEAD_FOOTER)));
        } catch (IOException e) {
            throw new RuntimeException("Erreur lors de la lecture du fichier template : " + e.getMessage());
        }

        template = template.replace("{{ENTITY_NAME}}", entity.getName());

        return template;
    }

    public String generateIndexTBodyHeaderTwigCode(Entity entity){
        String template;
        try {
            template = new String(java.nio.file.Files.readAllBytes(java.nio.file.Paths.get(TEMPLATE_INDEX_TBODY_HEADER)));
        } catch (IOException e) {
            throw new RuntimeException("Erreur lors de la lecture du fichier template : " + e.getMessage());
        }

        template = template.replace("{{LC_ENTITY_NAME}}", Utils.lcfirst(entity.getName()));

        return template;
    }

    public String generateIndexTBodyFooterTwigCode(Entity entity){
        String template;
        try {
            template = new String(java.nio.file.Files.readAllBytes(java.nio.file.Paths.get(TEMPLATE_INDEX_TBODY_FOOTER)));
        } catch (IOException e) {
            throw new RuntimeException("Erreur lors de la lecture du fichier template : " + e.getMessage());
        }

        template = template.replace("{{ENTITY_NAME_LOWER}}", Utils.lcfirst(entity.getName()));
        template = template.replace("{{TWO_FIRST_LETTER}}", Utils.lcfirst(Utils.toCamelCase(entity.getName()).substring(0, 2)));

        return template;
    }

    public String generateIndexFieldTHTwigCode(String entityName, String name){
        String template;
        try {
            template = new String(java.nio.file.Files.readAllBytes(java.nio.file.Paths.get(TEMPLATE_INDEX_FIELD_TH)));
        } catch (IOException e) {
            throw new RuntimeException("Erreur lors de la lecture du fichier template : " + e.getMessage());
        }

        template = template.replace("{{LC_ENTITY_NAME}}", Utils.lcfirst(entityName));

        template = template.replace("{{TH}}", Utils.lcfirst(Utils.toCamelCase(name)));

        return template;
    }

    public String generateIndexFieldTDTwigCode(String entityName, String name){
        String template;
        try {
            template = new String(java.nio.file.Files.readAllBytes(java.nio.file.Paths.get(TEMPLATE_INDEX_FIELD_TD)));
        } catch (IOException e) {
            throw new RuntimeException("Erreur lors de la lecture du fichier template : " + e.getMessage());
        }

        template = template.replace("{{LC_ENTITY_NAME}}", Utils.lcfirst(entityName));

        template = template.replace("{{TD}}", Utils.lcfirst(Utils.toCamelCase(name)));

        return template;
    }

    public String generateIndexFieldTDDateTwigCode(String entityName, String name){
        String template;
        try {
            template = new String(java.nio.file.Files.readAllBytes(java.nio.file.Paths.get(TEMPLATE_INDEX_FIELD_TD)));
        } catch (IOException e) {
            throw new RuntimeException("Erreur lors de la lecture du fichier template : " + e.getMessage());
        }

        template = template.replace("{{LC_ENTITY_NAME}}", Utils.lcfirst(entityName));

        template = template.replace("{{TD}}", Utils.lcfirst(Utils.toCamelCase(name))+"|date('d/m/Y H:i')");

        return template;
    }



    public String generateFooterIndexTwigCode(Entity entity) {
        String template;
        try {
            template = new String(java.nio.file.Files.readAllBytes(java.nio.file.Paths.get(TEMPLATE_INDEX_FOOTER)));
        } catch (IOException e) {
            throw new RuntimeException("Erreur lors de la lecture du fichier template : " + e.getMessage());
        }

        template = template.replace("{{PATH}}", entity.getName().toLowerCase());

        return template;
    }




}
