package com.norriszhang.customerletters;

import fr.opensagres.poi.xwpf.converter.pdf.PdfConverter;
import fr.opensagres.poi.xwpf.converter.pdf.PdfOptions;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import java.io.FileOutputStream;
import java.io.IOException;

public class AppWithPoi {
    public static void main(String[] args) throws IOException {
        try (XWPFDocument doc = new XWPFDocument(AppWithPoi.class.getResourceAsStream("/template1.docx"))) {
//            XWPFWordExtractor extractor = new XWPFWordExtractor(doc);
//            String text = extractor.getText();
//            System.out.println(text);
            int paraIndex = 0;
            int runIndex = 0;
            for (XWPFParagraph paragraph : doc.getParagraphs()) {
                System.out.println("Paragraph " + (++paraIndex));
                for (XWPFRun run : paragraph.getRuns()) {
                    System.out.println("run " + (++runIndex));
                    String runText = run.getText(0);
                    System.out.println(runText);
                    if (runText.equalsIgnoreCase("Norris")) {
                        run.setText("Norris Zhang", 0);
                    }
                }

            }

            try (FileOutputStream fos = new FileOutputStream("/Users/norris/Documents/temp/test.pdf")) {
                PdfOptions options = PdfOptions.getDefault();
                PdfConverter.getInstance().convert(doc, fos, options);
            }


        }
    }
}
