package com.norriszhang.customerletters;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import java.io.FileOutputStream;
import java.io.IOException;

public class AppWithPoi {
    public static void main(String[] args) throws IOException {
        try (XWPFDocument doc = new XWPFDocument(AppWithPoi.class.getResourceAsStream("/SettlementInstruction.docx"))) {
            int paraIndex = 0;
            int runIndex = 0;
            for (XWPFParagraph paragraph : doc.getParagraphs()) {
                System.out.println("Paragraph " + (++paraIndex));
                for (XWPFRun run : paragraph.getRuns()) {
                    System.out.println("run " + (++runIndex));
                    String runText = run.getText(0);
                    System.out.println(runText);
                    if (runText != null && runText.contains("«name»")) {
                        run.setText(runText.replace("«name»", "Norris Zhang"), 0);
                    }
                }

            }

            doc.write(new FileOutputStream("/Users/norris/Documents/temp/test.docx"));

        }
    }
}
