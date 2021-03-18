package ru.micron;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.net.URL;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MakeDocuments {

  private WebDriver driver;

  @Autowired
  private ChromeOptions chromeOptions;

  @Value("${freemarker.templates.dir}")
  private String templatesDir;
  @Value("${freemarker.templates.file}")
  private String templateFile;
  @Value("${html.templates.file}")
  private String htmlName;
  @Value("${name.pdf.file}")
  private String pdfName;
  @Value("${name.word.file}")
  private String wordName;

  @PostConstruct
  private void init() {
    String driverName = "webdriver.chrome.driver";
    String opSys = System.getProperty("os.name").toLowerCase();
    if (opSys.contains("win")) {
      System.setProperty(driverName, "./chromedriver.exe");
      log.info("OS set: Win");
    } else if (opSys.contains("nix") || opSys.contains("nux") || opSys.contains("aix")) {
      System.setProperty(driverName, "/usr/bin/chromedriver");
      log.info("OS set: Nix");
    } else if (opSys.contains("mac")) {
      System.setProperty(driverName, "/usr/local/bin/chromedriver");
      log.info("OS set: Mac");
    }
  }

  @PreDestroy
  public void destroy() {
    if (driver != null) {
      driver.close();
      driver.quit();
    }
  }

  public void makeHtml(Map<String, String> map) {
    try {
      log.info("Creating HTML!");
      Configuration cfg = new Configuration(Configuration.VERSION_2_3_30);
      cfg.setClassForTemplateLoading(this.getClass(), templatesDir);
      cfg.setEncoding(new Locale("ru"), "windows-1251");
      cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
      Template template = cfg.getTemplate(templateFile);
      File htmlOpen = new File(htmlName);
      Writer html = new FileWriter(htmlOpen);
      template.process(map, html);
      html.flush();
      html.close();
      htmlOpen.deleteOnExit();
      log.info("Creating HTML - Done!");
    } catch (IOException | TemplateException e) {
      log.warn(e.getMessage());
    }
  }

  public void makePdf() {
    log.info("Creating PDF!");
    driver = new ChromeDriver(chromeOptions);
    driver.get("https://deftpdf.com/ru/html-to-pdf");
    driver.findElement(By.xpath("//input[@type='file']"))
        .sendKeys(new File(htmlName).getAbsolutePath());
    sleep(1);
    driver.findElement(By.xpath("//a[@id='HTMLFileToPDF']")).click();
    sleep(2);
    String downloadUrl = driver
        .findElement(By.xpath("//*[@id='apply-popup']/div[2]/div[2]/div/div[2]/div[1]/a"))
        .getAttribute("href");
    downloadFile(downloadUrl, pdfName);
  }

  public void makeWord() {
    log.info("Creating DOCX!");
    if (driver == null) {
      driver = new ChromeDriver(chromeOptions);
    }
    driver.get("https://www.pdf2go.com/ru/pdf-to-word");
    driver.findElement(By.cssSelector("input[type=file]"))
        .sendKeys(new File(pdfName).getAbsolutePath());
    sleep(1);
    driver.findElement(By.xpath("//*[@id=\"qg-toast\"]/div[3]/div[2]/p[4]/button")).click();
    driver.findElement(By.xpath("//*[@id=\"locale_btn_no\"]")).click();
    sleep(1);
    driver.findElement(By.xpath("//*[@id=\"page_function_container\"]/div[1]/button/strong"))
        .click();
    sleep(10);
    driver.findElement(By.xpath("//*[@id=\"qg-toast\"]/div[2]/div/div/p[3]/button")).click();
    String downloadUrl = driver
        .findElement(By.xpath(
            "//*[@id=\"page_function_container\"]/div/div[1]/div/div[1]/div[6]/div[2]/div/div/div[2]/div[3]/a"))
        .getAttribute("href");
    downloadFile(downloadUrl, wordName);
  }

  public static void downloadFile(String downloadUrl, String fileName) {
    try {
      FileUtils.copyURLToFile(
          new URL(downloadUrl),
          new File("./" + fileName),
          Integer.MAX_VALUE,
          Integer.MAX_VALUE);
      log.info(String.format("Download %s OK!", fileName));
    } catch (IOException e) {
      log.info(String.format("Download %s FAIL!", fileName));
    }
  }

  public static void sleep(int sec) {
    try {
      TimeUnit.SECONDS.sleep(sec);
    } catch (InterruptedException e) {
      log.warn(e.getMessage());
    }
  }

}