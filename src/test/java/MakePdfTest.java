import com.itextpdf.html2pdf.HtmlConverter;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;


public class MakePdfTest {
    public static void main(String[] argv) {
        try {
            System.out.print("Creating PDF!\n");
            HtmlConverter.convertToPdf(new FileInputStream("index.html"),
                                        new FileOutputStream("pdf.pdf"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}