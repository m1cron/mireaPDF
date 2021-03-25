package ru.micron;

import org.junit.Test;
import ru.micron.converting.MakeDocuments;

public class MakeDocumentsTest {

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