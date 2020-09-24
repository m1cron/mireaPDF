package ru.micron;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class MakePdf {
    public static void makePdf(String dir, String pdfName, String titulName) {
        try {
        System.out.print("Creating PDF!\n");
        Document document = new Document();
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(dir + pdfName));

        document.open();
        XMLWorkerHelper.getInstance().parseXHtml(writer, document, new FileInputStream(dir + titulName));
        document.close();

        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }
    }
}
