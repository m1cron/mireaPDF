package ru.micron;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.net.URL;
import java.util.Map;
import java.util.concurrent.TimeUnit;

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
    void init() {
        String driverName = "webdriver.chrome.driver";
        String opSys = System.getProperty("os.name").toLowerCase();
        if (opSys.contains("win")) {
            System.setProperty(driverName, "./drivers/chromedriver.exe");
        } else if (opSys.contains("nix") || opSys.contains("nux") || opSys.contains("aix")) {
            System.setProperty(driverName, "/usr/bin/chromedriver");
        } else if (opSys.contains("mac")) {
            System.setProperty(driverName, "/usr/local/bin/chromedriver");
        }
    }

    @PreDestroy
    void destroy() {
        if (driver != null) {
            driver.close();
            driver.quit();
        }
    }

    public void makeHtml(Map<String, String> map) {
        try {
            System.out.print("Creating HTML!\n");
            Configuration cfg = new Configuration(Configuration.VERSION_2_3_30);
            cfg.setDirectoryForTemplateLoading(new File(templatesDir));
            cfg.setDefaultEncoding("UTF-8");
            cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
            Template template = cfg.getTemplate(templateFile);
            File htmlOpen = new File(htmlName);
            Writer html = new FileWriter(htmlOpen);
            template.process(map, html);
            html.flush();
            html.close();
            htmlOpen.deleteOnExit();
        } catch (IOException | TemplateException e) {
            e.printStackTrace();
        }
    }

    public void makePdf() {
        driver = new ChromeDriver(chromeOptions);
        driver.get("https://deftpdf.com/ru/html-to-pdf");
        driver.findElement(By.xpath("//input[@type='file']")).sendKeys(new File(htmlName).getAbsolutePath());
        sleep(1);
        driver.findElement(By.xpath("//a[@id='HTMLFileToPDF']")).click();
        sleep(2);
        String downloadUrl = driver.findElement(By.xpath("//*[@id='apply-popup']/div[2]/div[2]/div/div[2]/div[1]/a"))
                .getAttribute("href");
        downloadFile(downloadUrl, pdfName);
    }

    public void makeWord() {
        if (driver == null) {
            driver = new ChromeDriver(chromeOptions);
        }
        driver.get("https://www.pdf2go.com/ru/pdf-to-word");
        driver.findElement(By.cssSelector("input[type=file]")).sendKeys(new File(pdfName).getAbsolutePath());
        sleep(1);
        driver.findElement(By.xpath("//*[@id=\"qg-toast\"]/div[3]/div[2]/p[4]/button")).click();
        driver.findElement(By.xpath("//*[@id=\"locale_btn_no\"]")).click();
        sleep(1);
        driver.findElement(By.xpath("//*[@id=\"page_function_container\"]/div[1]/button/strong")).click();
        sleep(10);
        driver.findElement(By.xpath("//*[@id=\"qg-toast\"]/div[2]/div/div/p[3]/button")).click();
        String downloadUrl = driver.findElement(By.xpath("//*[@id=\"page_function_container\"]/div/div[1]/div/div[1]/div[6]/div[2]/div/div/div[2]/div[3]/a")).getAttribute("href");
        downloadFile(downloadUrl, wordName);
    }

    public static void downloadFile(String downloadUrl, String fileName) {
        try {
            FileUtils.copyURLToFile(
                    new URL(downloadUrl),
                    new File("./" + fileName),
                    Integer.MAX_VALUE,
                    Integer.MAX_VALUE);
            System.out.printf("Download %s OK!\n", fileName);
        } catch (IOException e) {
            System.out.printf("Download %s FAIL!\n", fileName);
        }
    }

    public static void sleep(int sec) {
        try {
            TimeUnit.SECONDS.sleep(sec);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}