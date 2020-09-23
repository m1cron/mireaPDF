package ru.micron;

import com.itextpdf.text.DocumentException;
import freemarker.template.TemplateException;
import ru.micron.MakePdf;

import java.io.IOException;

public class Main {
    public static void main(String... args) {
        try {
            CreateHtml html = new CreateHtml();
            html.makeTitul("2020", "ИКБО-13-19", "Голубков Максим Владимирович", "Владимирович Владимирович Максим");


            MakePdf pdf = new MakePdf();
            pdf.makePdf("");
            System.out.println("Work done!");
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        }
    }
}