import ru.micron.MakePdf;

public class MakePdfTest {

    public static void main(String[] argv) {
        MakePdf make = new MakePdf();
        make.makePdf("index.html", "pdf.pdf");
        make.closeDriver();
    }
}