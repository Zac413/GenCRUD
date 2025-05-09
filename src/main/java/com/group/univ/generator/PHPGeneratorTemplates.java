package com.group.univ.generator;

import com.group.univ.model.Entity;
import com.group.univ.model.Field;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

public class PHPGeneratorTemplates {

    private static final String TWIG_TPL_PATH = "src/main/resources/com/group/univ/php-template/templates/";
    private static final String CREATE_TPL    = TWIG_TPL_PATH + "create.tpl";
    private static final String INDEX_TPL     = TWIG_TPL_PATH + "index.tpl";

    private static final String OUTPUT_DIR    = "symfony/templates";

    public void generateTwigTemplates(Map<String, Entity> entities) throws IOException {
        File baseDir = new File(OUTPUT_DIR);
        if (!baseDir.exists()) baseDir.mkdirs();

        for (Entity e : entities.values()) {
            String name    = e.getName();
            String lc      = name.toLowerCase();
            // Prepare the target folder, e.g. templates/client
            File dir = new File(baseDir, lc);
            if (!dir.exists()) dir.mkdirs();

            // Build table headers and rows from XML-defined fields
            String idField = null;
            StringBuilder headers = new StringBuilder();
            StringBuilder rows    = new StringBuilder();
            for (Field f : e.getFields()) {
                if (f.isId()) {
                    idField = f.getName();
                } else {
                    headers.append("      <th>")
                            .append(f.getName())
                            .append("</th>\n");
                    rows.append("      <td>{{ item.")
                            .append(f.getName())
                            .append(" }}</td>\n");
                }
            }

            // Generate index.html.twig
            String indexTpl = read(INDEX_TPL)
                    .replace("%%ENTITY_NAME%%", name)
                    .replace("%%ENTITY_NAME_LC%%", lc)
                    .replace("%%TABLE_HEADERS%%", headers.toString().trim())
                    .replace("%%TABLE_ROWS%%", rows.toString().trim())
                    .replace("%%ID_FIELD%%", idField);
            write(dir, "index.html.twig", indexTpl);

            // Generate create.html.twig
            String createTpl = read(CREATE_TPL)
                    .replace("%%ENTITY_NAME%%", name)
                    .replace("%%ENTITY_NAME_LC%%", lc);
            write(dir, "create.html.twig", createTpl);
        }
    }

    private String read(String path) throws IOException {
        return new String(Files.readAllBytes(Paths.get(path)));
    }

    private void write(File dir, String filename, String content) throws IOException {
        try (PrintWriter out = new PrintWriter(new File(dir, filename))) {
            out.print(content);
        }
        System.out.println("Généré : " + new File(dir, filename).getPath());
    }
}
