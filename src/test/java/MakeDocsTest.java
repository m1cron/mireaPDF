import ru.micron.MakeDocuments;

public class MakeDocsTest {
    public static void main(String[] args) {
        MakeDocuments docs = new MakeDocuments();
        docs.makePdf("index.html", "pdf.pdf");
        docs.makeWord("pdf.pdf", "word.docx");
        docs.closeDriver();
    }
}
