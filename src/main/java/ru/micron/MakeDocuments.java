package ru.micron;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class MakeDocuments {
    private final WebDriver driver;

    public MakeDocuments() {
        String driverName = "webdriver.chrome.driver";

        String opSys = System.getProperty("os.name").toLowerCase();
        if (opSys.contains("win"))
            System.setProperty(driverName, "./drivers/chromedriver.exe");
        else if (opSys.contains("nix") || opSys.contains("nux") || opSys.contains("aix"))
            System.setProperty(driverName, "/usr/bin/chromedriver");
        else if (opSys.contains("mac"))
            System.setProperty(driverName, "/usr/local/bin/chromedriver");
        driver = new ChromeDriver(new ChromeOptions().addArguments("window-size=800,600")
                                                     .addArguments("window-position=-1920,10"));
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    public void makePdf(String html, String pdfName) {
        driver.get("https://deftpdf.com/ru/html-to-pdf");
        driver.findElement(By.xpath("//input[@type='file']")).sendKeys(new File(html).getAbsolutePath());
        driver.findElement(By.xpath("//a[@id='HTMLFileToPDF']")).click();
        sleep(2);
        String downloadUrl = driver.findElement(By.xpath("//*[@id='apply-popup']/div[2]/div[2]/div/div[2]/div[1]/a"))
                .getAttribute("href");
        downloadFile(downloadUrl, pdfName);
    }

    public void makeWord(String pdfName, String wordName) {
        driver.get("https://www.pdf2go.com/ru/pdf-to-word");
        driver.findElement(By.cssSelector("input[type=file]")).sendKeys(new File(pdfName).getAbsolutePath());
        sleep(2);
        driver.findElement(By.xpath("//*[@id=\"qg-toast\"]/div[3]/div[2]/p[2]/button")).click();
        driver.findElement(By.xpath("//*[@id=\"page_function_container\"]/div[1]/button")).click();
        sleep(10);
        String downloadUrl = driver.findElement(By.xpath("//*[@id=\"page_function_container\"]/div/div[1]/div/div[1]/div[8]/div[2]/div/div/div[2]/div[3]/a"))
                .getAttribute("href");
        downloadFile(downloadUrl, wordName);
    }

    public void closeDriver() {
        driver.close();
        driver.quit();
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