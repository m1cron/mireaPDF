import ru.micron.MakeDocuments;

public class MakeWordTest {
    public static void main(String[] args) {
        MakeDocuments make = new MakeDocuments();
        make.makeWord("pdf.pdf", "word.docx");
        make.closeDriver();
    }
}
