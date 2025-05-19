package com.group.univ.generator;

import com.group.univ.model.Entity;
import com.group.univ.model.Field;
import com.group.univ.model.Relation;
import com.group.univ.utils.Utils;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

public class TWIGGeneratorEdit {

    public static final String TWIG_OUT_PATH = "symfony/templates/";

    public static final String TWIG_TEMPLATE_PATH = "src/main/resources/com/group/univ/twig-template/templates/";
    public static final String TEMPLATE_CREATE_FORM = TWIG_TEMPLATE_PATH + "create/";
    public static final String TEMPLATE_EDIT_FORM = TWIG_TEMPLATE_PATH + "edit/";

    public static final String TEMPLATE_CREATE_HEADER_FORM = TEMPLATE_CREATE_FORM + "templates-create-header.twig.tpl";
    public static final String TEMPLATE_CREATE_FOOTER_FORM = TEMPLATE_EDIT_FORM + "templates-edit-footer.twig.tpl";
    public static final String TEMPLATE_CREATE_FIELD_FORM = TEMPLATE_CREATE_FORM + "templates-create-field.twig.tpl";


    public void generateTwigFiles(Map<String, Entity> entities) throws IOException {

        for (Entity entity : entities.values()) {
            File generatedDir = new File(TWIG_OUT_PATH+""+entity.getName().toLowerCase());
            if (!generatedDir.exists()) {
                generatedDir.mkdir();
            }
            String twigCreateCode = generateTwigCreateCode(entity);
            try (PrintWriter out = new PrintWriter(new File(generatedDir, "edit"+".html.twig"))) {
                out.print(twigCreateCode);
            }
            System.out.println("Génération du fichier edit-"+entity.getName() + ".html.twig");
        }

    }

    // Génère le code TWIG pour une entité
    public String generateTwigCreateCode(Entity entity) {
        StringBuilder twig = new StringBuilder();

        // Header
        twig.append(generateHeaderCreateTwigCode(entity));


        // Create Fields
        for (Field f : entity.getFields()) {
            if(!f.isId()){
                twig.append(generateCreateFieldTwigCode(f.getName()));
            }
        }

        // Create Relations
        for (Relation r : entity.getRelations()) {
            String name = "";
            if(r.getType().equalsIgnoreCase("one-to-one")) {
                name = r.getTo();
                twig.append(generateCreateFieldTwigCode(name));

            } else if (r.getType().equalsIgnoreCase("one-to-many")) {
                name = r.getTo()+"s";
                twig.append(generateCreateFieldTwigCode(name));

            } else if (r.getType().equalsIgnoreCase("many-to-many")) {

            } else if (r.getType().equalsIgnoreCase("many-to-one")) {

            }
        }


        // Footer
        twig.append(generateFooterCreateTwigCode(entity));
        return twig.toString();
    }


    public String generateHeaderCreateTwigCode(Entity entity){
        String template;
        try {
            template = new String(java.nio.file.Files.readAllBytes(java.nio.file.Paths.get(TEMPLATE_CREATE_HEADER_FORM)));
        } catch (IOException e) {
            throw new RuntimeException("Erreur lors de la lecture du fichier template : " + e.getMessage());
        }

        template = template.replace("{{ENTITY_NAME}}", entity.getName());

        return template;
    }

    public String generateCreateFieldTwigCode(String name){
        String template;
        try {
            template = new String(java.nio.file.Files.readAllBytes(java.nio.file.Paths.get(TEMPLATE_CREATE_FIELD_FORM)));
        } catch (IOException e) {
            throw new RuntimeException("Erreur lors de la lecture du fichier template : " + e.getMessage());
        }

        template = template.replace("{{FIELD_NAME}}", Utils.lcfirst(Utils.toCamelCase(name)));

        return template;
    }

    public String generateFooterCreateTwigCode(Entity entity) {
        String template;
        try {
            template = new String(java.nio.file.Files.readAllBytes(java.nio.file.Paths.get(TEMPLATE_CREATE_FOOTER_FORM)));
        } catch (IOException e) {
            throw new RuntimeException("Erreur lors de la lecture du fichier template : " + e.getMessage());
        }

        template = template.replace("{{PATH}}", entity.getName().toLowerCase());

        return template;
    }




}
