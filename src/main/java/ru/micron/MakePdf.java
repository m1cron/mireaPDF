package ru.micron;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.http.HttpResponse;
import java.util.concurrent.TimeUnit;

public class MakePdf {
    private final WebDriver driver;

    private void sleep(int sec) {
        try {
            TimeUnit.SECONDS.sleep(sec);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public MakePdf() {
        String opSys = System.getProperty("os.name").toLowerCase();
        if (opSys.contains("win"))
            System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver.exe");
        else if (opSys.contains("nix") || opSys.contains("nux") || opSys.contains("aix"))
            System.setProperty("webdriver.chrome.driver", "./drivers/chromedriverUnix");
        else if (opSys.contains("mac"))
            System.setProperty("webdriver.chrome.driver", "./drivers/chromedriverMac");
        driver = new ChromeDriver();
    }

    public void makePdf(String html, String pdfName) {
        File file = new File(html);

        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().timeouts().setScriptTimeout(3, TimeUnit.SECONDS);
        driver.get("https://deftpdf.com/ru/html-to-pdf");
        driver.findElement(By.xpath("//input[@type='file']")).sendKeys(file.getAbsolutePath());
        driver.findElement(By.xpath("//a[@id='HTMLFileToPDF']")).click();
        sleep(3);
        String download = driver.findElement(By.xpath("//*[@id='apply-popup']/div[2]/div[2]/div/div[2]/div[1]/a"))
                                .getAttribute("href");
        try {
            FileUtils.copyURLToFile(
                    new URL(download),
                    new File("./" + pdfName),
                    10000,
                    10000);
        } catch (IOException e) {
            e.printStackTrace();
        }
        driver.close();
        driver.quit();
    }
}