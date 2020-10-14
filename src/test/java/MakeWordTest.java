import ru.micron.MakePdf;

public class MakeWordTest {
    public static void main(String[] args) {
        MakePdf make = new MakePdf();
        make.makeWord("pdf.pdf", "word.docx");
        make.closeDriver();
    }
}
