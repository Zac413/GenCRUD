package com.group.univ.generator;

import com.group.univ.model.Entity;
import com.group.univ.utils.Utils;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

public class TWIGGeneratorIndexGlobal {

    private static final String TWIG_OUT_DIR        = "symfony/templates/index/";
    private static final String TWIG_TEMPLATE_PATH  = "src/main/resources/com/group/univ/twig-template/templates/index/global-index.twig.tpl";

    /**
     * Génère le template Twig global (page d'accueil) listant toutes les entités.
     */
    public void generateGlobalIndex(Map<String, Entity> entities) throws IOException {
        // Création du répertoire si nécessaire
        File outDir = new File(TWIG_OUT_DIR);
        if (!outDir.exists()) outDir.mkdirs();

        // Lecture du template global
        String tpl = Files.readString(Paths.get(TWIG_TEMPLATE_PATH));

        // Construire dynamiquement les liens vers chaque entité
        StringBuilder links = new StringBuilder();
        for (Entity e : entities.values()) {
            String lower = Utils.lcfirst(e.getName());
            links.append("    <li><a href=\"/")
                    .append(lower)
                    .append("\">")
                    .append(e.getName())
                    .append("</a></li>\n");
        }

        // Remplacement du placeholder {{ENTITY_LINKS}}
        String rendered = tpl
                .replace("{{ ENTITY_LINKS|raw }}", links.toString().trim())
                .replace("{{ENTITY_LINKS|raw}}", links.toString().trim());

        // Écriture du fichier final
        File outFile = new File(outDir, "index.html.twig");
        try (PrintWriter writer = new PrintWriter(outFile)) {
            writer.print(rendered);
        }

        System.out.println("Index global généré dans " + outFile.getPath());
    }
}
