package com.norriszhang.customerletters;

import org.apache.pdfbox.contentstream.operator.Operator;
import org.apache.pdfbox.cos.COSArray;
import org.apache.pdfbox.cos.COSBase;
import org.apache.pdfbox.cos.COSDictionary;
import org.apache.pdfbox.cos.COSFloat;
import org.apache.pdfbox.cos.COSInteger;
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.cos.COSObject;
import org.apache.pdfbox.cos.COSString;
import org.apache.pdfbox.pdfparser.PDFStreamParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageTree;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.util.Matrix;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws IOException {
        System.out.println( "Hello World!" );
        PDDocument doc = null;
        try {
            doc = PDDocument.load(App.class.getResourceAsStream("/template1.pdf"));
            int numberOfPages = doc.getNumberOfPages();
            System.out.println("Number of Pages: " + numberOfPages);
//            PDFTextStripper textStripper = new PDFTextStripper();
//            String text = textStripper.getText(doc);
//            System.out.println(text);

            List<Object> unknownType = new ArrayList<>();
            for (PDPage page : doc.getPages()) {
                PDFStreamParser parser = new PDFStreamParser(page);
                parser.parse();
                List<Object> tokens = parser.getTokens();
                System.out.println("what are the tokens?");
                for (Object next : tokens) {
                    printToken(next, unknownType);
                }
            }

            for (Object unknown : unknownType) {
                System.out.println((unknown == null ? "<java:null>" : unknown.getClass().getName()));
            }

        } finally {
            if (doc != null) {
                doc.close();
            }
        }
    }

    private static void printToken(Object next, List<Object> unknownType) {
        System.out.println(next.getClass().getName());
        if (next instanceof Operator) {
            System.out.println("Operator : " + ((Operator) next).getName());
        } else if (next instanceof COSName) {
            System.out.println("COSName : " + ((COSName) next).getName());
        } else if (next instanceof COSInteger) {
            System.out.println("COSInteger : " + ((COSInteger) next).intValue());
        } else if (next instanceof COSDictionary) {
            COSDictionary cosDict = (COSDictionary) next;
            System.out.println("COSDictionary: = start ==");
            for (COSName cosName : cosDict.keySet()) {
                COSObject cosObject = cosDict.getCOSObject(cosName);
                System.out.println(cosName.getName() + " : " + (cosObject == null ? "<java:null>" : cosObject.getClass().getName()));
                if (cosObject != null) {
                    System.out.println("nested ===");
                    printToken(cosObject, unknownType);
                    System.out.println("nested end ===");
                }
            }
            System.out.println("COSDictionary: = end ==");
        } else if (next instanceof COSFloat) {
            System.out.println("COSFloat : " + ((COSFloat) next).floatValue());
        } else if (next instanceof COSArray) {
            System.out.println("COSArray start ===");
            for (COSBase cosBase : ((COSArray) next)) {
                String cosBaseStr = cosBase == null ? "<java:null>" : cosBase.getClass().getName();
                System.out.println(" - " + cosBaseStr);
                if (cosBase != null) {
                    System.out.println("nested ===");
                    printToken(cosBase, unknownType);
                    System.out.println("nested end ===");
                }
            }
            System.out.println("COSArray end ===");
        } else if (next instanceof COSString) {
            System.out.println("COSString : " + ((COSString) next).getString());
        } else {
            unknownType.add(next);
        }
    }
}
