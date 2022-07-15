package com.norriszhang.customerletters.services;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import static java.lang.String.format;

public class Docx2PdfService {
    public String docx2Pdf(String filePath) throws IOException, InterruptedException {
        ProcessBuilder builder = new ProcessBuilder();
        builder.command("/Applications/LibreOffice.app/Contents/MacOS/soffice",
            "--headless",
            "--nologo",
            "--convert-to",
            "pdf:writer_pdf_Export",
            filePath,
            "--outdir",
            filePath.substring(0, filePath.lastIndexOf(File.separator)));
        Process process = builder.start();
        int exitCode = process.waitFor();
        if (exitCode == 0) {
            return filePath.substring(0, filePath.length() - 4) + "pdf";
        } else {
            throw new IllegalStateException(format("Failed to convert docx %s to pdf.", filePath));
        }
    }
}
