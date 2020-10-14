package ru.micron;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import ru.micron.utils.UtilsForIO;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class MakePdf {
    private final WebDriver driver;

    public MakePdf() {
        String opSys = System.getProperty("os.name").toLowerCase();
        if (opSys.contains("win"))
            System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver.exe");
        else if (opSys.contains("nix") || opSys.contains("nux") || opSys.contains("aix"))
            System.setProperty("webdriver.chrome.driver", "./drivers/chromedriverUnix");
        else if (opSys.contains("mac"))
            System.setProperty("webdriver.chrome.driver", "./drivers/chromedriverMac");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    public void makePdf(String html, String pdfName) {
        driver.get("https://deftpdf.com/ru/html-to-pdf");
        driver.findElement(By.xpath("//input[@type='file']")).sendKeys(new File(html).getAbsolutePath());
        driver.findElement(By.xpath("//a[@id='HTMLFileToPDF']")).click();
        UtilsForIO.sleep(2);
        String downloadUrl = driver.findElement(By.xpath("//*[@id='apply-popup']/div[2]/div[2]/div/div[2]/div[1]/a"))
                                .getAttribute("href");
        UtilsForIO.downloadFile(downloadUrl, pdfName);
        driver.close();
        driver.quit();
    }
}