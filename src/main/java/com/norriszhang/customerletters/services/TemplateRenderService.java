package com.norriszhang.customerletters.services;

import com.norriszhang.customerletters.AppWithPoi;
import com.norriszhang.customerletters.models.DataForm;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

public class TemplateRenderService {
    private final String tempFolder;

    public TemplateRenderService(String tempFolder) {this.tempFolder = tempFolder;}

    /**
     * Replace the template with the data.
     * Then output the result to the configured temp folder with a sub-folder named as UUID
     *
     * @param templatePath
     * @param data
     * @return generated .docx file path
     */
    public String render(String templatePath, DataForm data) throws IOException {
        try (XWPFDocument doc = new XWPFDocument(AppWithPoi.class.getResourceAsStream(templatePath))) {
            for (XWPFParagraph paragraph : doc.getParagraphs()) {
                for (XWPFRun run : paragraph.getRuns()) {
                    tryReplace(run, data);
                }
            }

            Path folder = Files.createDirectories(Paths.get(tempFolder, UUID.randomUUID().toString()));
            String templateFileName = Paths.get(templatePath).getFileName().toString();

            Path tempPath = Paths.get(folder.toString(), templateFileName);
            doc.write(new FileOutputStream(tempPath.toFile()));
            return tempPath.toString();
        }
    }

    private void tryReplace(XWPFRun run, DataForm data) {
        if (run == null || run.getText(0) == null) {
            return;
        }
        String runText = run.getText(0);
        if (runText.contains("«name»")) {
            run.setText(runText.replace("«name»", data.getName()), 0);
        }
        if (runText.contains("«address»")) {
            run.setText(runText.replace("«address»", data.getAddress()), 0);
        }
    }
}
