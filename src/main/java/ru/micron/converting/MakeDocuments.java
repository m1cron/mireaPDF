package ru.micron.converting;

import static ru.micron.web.RestHolder.CONNECT_TIMEOUT_MS;
import static ru.micron.web.RestHolder.READ_TIMEOUT_MS;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Slf4j
@Component
@RequiredArgsConstructor
public class MakeDocuments {

  private static final String DOCX_FILE_NAME = "word.docx";
  private static final String PDF_FILE_NAME = "pdf.pdf";
  private static final String HTML_FILE_NAME = "index.html";
  private static final String TEMPLATE_FILE_NAME = "template.html";

  private static final String CONVERT_TO_PDF_URL = "https://deftpdf.com/ru/html-to-pdf";
  private static final String CONVERT_TO_DOCX_URL = "https://www.pdf2go.com/ru/pdf-to-word";
  private static final String INPUT_ELEMENT = "input[type=file]";
  private static final String HREF = "href";

  private final Locale locale;
  private final ResultMapHolder mapHolder;
  private final ChromeWebDriverHolder driverHolder;
  private final TemplateEngine templateEngine;

  public void makeHtml() {
    try {
      log.info("Creating HTML");
      var context = new Context(locale, mapHolder.getMap());
      var htmlFile = new File(HTML_FILE_NAME);
      var html = new FileWriter(htmlFile);
      var htmlStr = templateEngine.process(TEMPLATE_FILE_NAME, context);
      html.write(htmlStr);
      html.flush();
      html.close();
      htmlFile.deleteOnExit();
      log.info("Creating HTML - Done!");
    } catch (IOException e) {
      log.error(e.getMessage());
    }
  }

  public void makePdf() {
    log.info("Converting PDF");
    var driver = driverHolder.getDriver();
    driver.get(CONVERT_TO_PDF_URL);
    driver.findElement(By.cssSelector(INPUT_ELEMENT))
        .sendKeys(new File(HTML_FILE_NAME).getAbsolutePath());
    sleep(1);
    driver.findElement(By.xpath("//a[@id='HTMLFileToPDF']")).click();
    sleep(2);
    String downloadUrl = driver
        .findElement(By.xpath("//*[@id='apply-popup']/div[2]/div[2]/div/div[2]/div[1]/a"))
        .getAttribute(HREF);
    downloadFile(downloadUrl, PDF_FILE_NAME);
  }

  public void makeWord() {
    log.info("Converting DOCX");
    var driver = driverHolder.getDriver();
    driver.get(CONVERT_TO_DOCX_URL);
    driver.findElement(By.cssSelector(INPUT_ELEMENT))
        .sendKeys(new File(PDF_FILE_NAME).getAbsolutePath());
    sleep(1);
    driver.findElement(By.xpath("//*[@id=\"qg-toast\"]/div[3]/div[2]/p[4]/button")).click();
    driver.findElement(By.xpath("//*[@id=\"locale_btn_no\"]")).click();
    sleep(1);
    driver.findElement(By.xpath("//*[@id=\"page_function_container\"]/div[1]/button/strong"))
        .click();
    sleep(10);
    driver.findElement(By.xpath("//*[@id=\"qg-toast\"]/div[2]/div/div/p[3]/button")).click();
    String downloadUrl =
        driver.findElement(By.xpath(
            "//*[@id=\"page_function_container\"]/div/div[1]/div/div[1]/div[6]/div[1]/div/div/div[2]/div[3]/a"))
            .getAttribute(HREF);
    downloadFile(downloadUrl, DOCX_FILE_NAME);
  }

  private void downloadFile(String downloadUrl, String fileName) {
    try {
      FileUtils.copyURLToFile(
          new URL(downloadUrl),
          new File("./" + fileName),
          CONNECT_TIMEOUT_MS,
          READ_TIMEOUT_MS);
      log.info("Download {} OK!", fileName);
    } catch (IOException e) {
      log.error("Download {} FAIL!", fileName);
    }
  }

  private void sleep(int sec) {
    try {
      TimeUnit.SECONDS.sleep(sec);
    } catch (InterruptedException e) {
      log.error(e.getMessage());
    }
  }
}