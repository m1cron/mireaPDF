package ru.micron;

import org.junit.Test;

public class MakeDocumentsTest {

    @Test
    public void makePdf() {
        MakeDocuments docs = new MakeDocuments();
        docs.makePdf();
    }

    @Test
    public void makeWord() {
        MakeDocuments docs = new MakeDocuments();
        docs.makePdf();
        docs.makeWord();
    }

    @Test
    public void downloadFile() {
        String[] urls = {"https://file-examples-com.github.io/uploads/2017/10/file-sample_150kB.pdf",
                "https://file-examples-com.github.io/uploads/2017/10/file-example_PDF_500_kB.pdf",
                "https://file-examples-com.github.io/uploads/2017/10/file-example_PDF_1MB.pdf"};
        int i = 0;
        for (String url : urls) {
            MakeDocuments.downloadFile(url, String.format("TMP/file_%d.pdf", i++));
        }
    }
}