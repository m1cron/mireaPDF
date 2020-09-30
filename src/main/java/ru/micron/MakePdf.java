package ru.micron;

import com.itextpdf.html2pdf.HtmlConverter;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;


public class MakePdf {
    public static void makePdf(String titulName, String pdfName) {
        try {
            System.out.print("Creating PDF!\n");
            HtmlConverter.convertToPdf(new FileInputStream(titulName),
                                       new FileOutputStream(pdfName));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
