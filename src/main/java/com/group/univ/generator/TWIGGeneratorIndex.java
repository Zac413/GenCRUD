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
    public static final String TEMPLATE_INDEX_FORM = TWIG_TEMPLATE_PATH + "index/";

    public static final String TEMPLATE_INDEX_HEADER_FORM = TEMPLATE_INDEX_FORM + "templates-index-header.twig.tpl";
    public static final String TEMPLATE_INDEX_FOOTER_FORM = TEMPLATE_INDEX_FORM + "templates-index-footer.twig.tpl";
    public static final String TEMPLATE_INDEX_FIELD_FORM = TEMPLATE_INDEX_FORM + "templates-index-field.twig.tpl";
    public static final String TEMPLATE_INDEX_FIELD_TD_FORM = TEMPLATE_INDEX_FORM + "templates-index-field-td.twig.tpl";
    public static final String TEMPLATE_INDEX_FIELD_TH_FORM = TEMPLATE_INDEX_FORM + "templates-index-field-th.twig.tpl";
    public static final String TEMPLATE_INDEX_TBODY_FOOTER_FORM = TEMPLATE_INDEX_FORM + "templates-index-tbody-footer.twig.tpl";
    public static final String TEMPLATE_INDEX_TBODY_HEADER_FORM = TEMPLATE_INDEX_FORM + "templates-index-tbody-header.twig.tpl";
    public static final String TEMPLATE_INDEX_THEAD_FOOTER_FORM = TEMPLATE_INDEX_FORM + "templates-index-thead-footer.twig.tpl";
    public static final String TEMPLATE_INDEX_THEAD_HEADER_FORM = TEMPLATE_INDEX_FORM + "templates-index-thead-header.twig.tpl";

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


        // Create Fields
        for (Field f : entity.getFields()) {
            twig.append(generateIndexFieldTwigCode(f.getName()));
        }

        // Create Relations
        for (Relation r : entity.getRelations()) {
            String name = "";
            if(r.getType().equalsIgnoreCase("one-to-one")) {
                name = r.getTo();
            } else if (r.getType().equalsIgnoreCase("one-to-many")) {
                name = r.getTo()+"s";
            }
            twig.append(generateIndexFieldTwigCode(name));
        }


        // Footer
        twig.append(generateFooterIndexTwigCode(entity));
        return twig.toString();
    }


    public String generateHeaderIndexTwigCode(Entity entity){
        String template;
        try {
            template = new String(java.nio.file.Files.readAllBytes(java.nio.file.Paths.get(TEMPLATE_INDEX_HEADER_FORM)));
        } catch (IOException e) {
            throw new RuntimeException("Erreur lors de la lecture du fichier template : " + e.getMessage());
        }

        template = template.replace("{{ENTITY_NAME}}", entity.getName());

        return template;
    }

    public String generateIndexFieldTwigCode(String name){
        String template;
        try {
            template = new String(java.nio.file.Files.readAllBytes(java.nio.file.Paths.get(TEMPLATE_INDEX_FIELD_FORM)));
        } catch (IOException e) {
            throw new RuntimeException("Erreur lors de la lecture du fichier template : " + e.getMessage());
        }

        template = template.replace("{{FIELD_NAME}}", Utils.lcfirst(Utils.toCamelCase(name)));

        return template;
    }

    public String generateFooterIndexTwigCode(Entity entity) {
        String template;
        try {
            template = new String(java.nio.file.Files.readAllBytes(java.nio.file.Paths.get(TEMPLATE_INDEX_FOOTER_FORM)));
        } catch (IOException e) {
            throw new RuntimeException("Erreur lors de la lecture du fichier template : " + e.getMessage());
        }

        template = template.replace("{{PATH}}", "index_"+entity.getName().toLowerCase());

        return template;
    }




}
