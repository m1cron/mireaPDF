package ru.micron;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class MakePdf {
    public void makePdf(String file) throws IOException, DocumentException {
        System.out.printf("Creating PDF!\n");
        Document document = new Document();

        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("pdf.pdf"));

        document.open();

        XMLWorkerHelper.getInstance().parseXHtml(writer, document, new FileInputStream("titul.html"));
        document.close();
        System.out.printf("Done!\n");
    }
}
