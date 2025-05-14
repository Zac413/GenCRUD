package com.group.univ.generator;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.PrintWriter;

public class StyleGenerator {

    // Chemins des templates source dans le projet générateur
    private static final String BASE_TEMPLATE_PATH = "src/main/resources/com/group/univ/twig-template/templates/base.html.twig.tpl";
    private static final String STYLE_CSS_PATH = "src/main/resources/com/group/univ/twig-template/templates/style.css.tpl";

    /**
     * Génère le fichier base.html.twig dans le projet Symfony.
     */
    public void generateBaseTemplate() throws IOException {
        copyTemplateToSymfonyProject(BASE_TEMPLATE_PATH, "base.html.twig");
    }

    /**
     * Génère le fichier style.css dans le projet Symfony.
     */
    public void generateStyleCSS() throws IOException {
        copyTemplateToSymfonyProject(STYLE_CSS_PATH, "style.css");
    }

    /**
     * Copie un fichier template .tpl dans le répertoire de sortie du projet Symfony.
     *
     * @param templatePath Le chemin du fichier template source (.tpl)
     * @param outputFileName Le nom du fichier de sortie dans le projet Symfony
     */
    private void copyTemplateToSymfonyProject(String templatePath, String outputFileName) throws IOException {

        // Création du répertoire de sortie si nécessaire
        File outDir = outputFileName.endsWith(".css") ? new File("symfony/public/css/") : new File("symfony/templates/");

        if (!outDir.exists()) {
            outDir.mkdirs();
        }

        // Lire le fichier template source (.tpl)
        String templateContent = Files.readString(Paths.get(templatePath));

        // Écriture du fichier dans le répertoire de sortie Symfony
        File outFile = new File(outDir, outputFileName);
        try (PrintWriter writer = new PrintWriter(outFile)) {
            writer.print(templateContent);
        }

        System.out.println(outputFileName + " généré dans " + outFile.getPath());
    }

}
