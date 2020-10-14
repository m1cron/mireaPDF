import ru.micron.MakeDocuments;

public class MakePdfTest {

    public static void main(String[] argv) {
        MakeDocuments make = new MakeDocuments();
        make.makePdf("index.html", "pdf.pdf");
        make.closeDriver();
    }
}