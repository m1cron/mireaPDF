package ru.micron;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.micron.config.AppConfiguration;
import ru.micron.converting.MakeDocuments;
import ru.micron.formatting.ReportConstants;
import ru.micron.formatting.ReportDate;
import ru.micron.reporting.ReportHandler;

import java.util.HashMap;
import java.util.Map;

public class MakeDocumentsTest {

  @Test
  public void testMakeHtml() {
    ApplicationContext context = new AnnotationConfigApplicationContext(AppConfiguration.class);
    MakeDocuments docs = context.getBean(MakeDocuments.class);
    Map<String, String> map = new HashMap<>();
    context.getBean(ReportDate.class).fillMap(map);
    context.getBean(ReportHandler.class).fillMap(map);
    map.put(ReportConstants.CODE, ReportConstants.CODE);
    docs.makeHtml(map);
  }

  @Test
  public void makePdf() {
    ApplicationContext context = new AnnotationConfigApplicationContext(AppConfiguration.class);
    MakeDocuments docs = context.getBean(MakeDocuments.class);
    Map<String, String> map = new HashMap<>();
    context.getBean(ReportDate.class).fillMap(map);
    context.getBean(ReportHandler.class).fillMap(map);
    map.put(ReportConstants.CODE, ReportConstants.CODE);
    docs.makeHtml(map);
    docs.makePdf();
    docs.destroy();
  }

  @Test
  public void makeWord() {
    ApplicationContext context = new AnnotationConfigApplicationContext(AppConfiguration.class);
    MakeDocuments docs = context.getBean(MakeDocuments.class);
    Map<String, String> map = new HashMap<>();
    context.getBean(ReportDate.class).fillMap(map);
    context.getBean(ReportHandler.class).fillMap(map);
    map.put(ReportConstants.CODE, ReportConstants.CODE);
    docs.makeHtml(map);
    docs.makeWord();
    docs.destroy();
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