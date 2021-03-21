package ru.micron.converting;

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
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.stereotype.Component;
import ru.micron.config.AppConfiguration;
import ru.micron.reporting.ReportConstants;

@Slf4j
@Component
@RequiredArgsConstructor
public class MakeDocuments {

  private final Locale locale;
  private final ChromeOptions chromeOptions;
  private WebDriver driver;

  @PostConstruct
  private void init() {
    String opSys = System.getProperty(ConvertingConstants.OS_NAME).toLowerCase();
    if (opSys.matches(ConvertingConstants.OS_WIN_REGEX)) {
      System.setProperty(ConvertingConstants.DRIVER_NAME, ConvertingConstants.DRIVER_WIN_PATH);
      log.info("OS set: Win {}", ConvertingConstants.DRIVER_WIN_PATH);
    } else if (opSys.matches(ConvertingConstants.OS_LINUX_REGEX)) {
      System.setProperty(ConvertingConstants.DRIVER_NAME, ConvertingConstants.DRIVER_LINUX_PATH);
      log.info("OS set: Nix {}", ConvertingConstants.DRIVER_LINUX_PATH);
    } else if (opSys.matches(ConvertingConstants.OS_MAC_REGEX)) {
      System.setProperty(ConvertingConstants.DRIVER_NAME, ConvertingConstants.DRIVER_MAC_PATH);
      log.info("OS set: Mac {}", ConvertingConstants.DRIVER_MAC_PATH);
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
      log.info("Creating HTML.");
      Configuration cfg = new Configuration(Configuration.VERSION_2_3_30);
      cfg.setClassForTemplateLoading(this.getClass(), ReportConstants.TEMPLATE_DIR_NAME);
      cfg.setEncoding(locale, AppConfiguration.APP_ENCODING);
      cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
      Template template = cfg.getTemplate(ReportConstants.TEMPLATE_FILE_NAME);
      File htmlOpen = new File(ReportConstants.HTML_FILE_NAME);
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
    log.info("Converting PDF.");
    driver = new ChromeDriver(chromeOptions);
    driver.get(ConvertingConstants.CONVERT_TO_PDF_URL);
    driver.findElement(By.cssSelector(ConvertingConstants.INPUT_ELEMENT))
        .sendKeys(new File(ReportConstants.HTML_FILE_NAME).getAbsolutePath());
    sleep(1);
    driver.findElement(By.xpath("//a[@id='HTMLFileToPDF']")).click();
    sleep(2);
    String downloadUrl = driver
        .findElement(By.xpath("//*[@id='apply-popup']/div[2]/div[2]/div/div[2]/div[1]/a"))
        .getAttribute(ConvertingConstants.HREF);
    downloadFile(downloadUrl, ReportConstants.PDF_FILE_NAME);
  }

  public void makeWord() {
    log.info("Converting DOCX.");
    if (driver == null) {
      driver = new ChromeDriver(chromeOptions);
    }
    driver.get(ConvertingConstants.CONVERT_TO_DOCX_URL);
    driver.findElement(By.cssSelector(ConvertingConstants.INPUT_ELEMENT))
        .sendKeys(new File(ReportConstants.PDF_FILE_NAME).getAbsolutePath());
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
            .getAttribute(ConvertingConstants.HREF);
    downloadFile(downloadUrl, ReportConstants.DOCX_FILE_NAME);
  }

  public static void downloadFile(String downloadUrl, String fileName) {
    try {
      FileUtils.copyURLToFile(
          new URL(downloadUrl),
          new File("./" + fileName),
          Integer.MAX_VALUE,
          Integer.MAX_VALUE);
      log.info("Download {} OK!", fileName);
    } catch (IOException e) {
      log.warn("Download {} FAIL!", fileName);
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