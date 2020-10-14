package ru.micron;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import ru.micron.utils.UtilsForIO;

import java.io.File;
import java.util.concurrent.TimeUnit;

public class MakeDocuments {
    private final WebDriver driver;

    public MakeDocuments() {
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
    }

    public void makeWord(String pdfName, String wordName) {
        driver.get("https://www.pdf2go.com/ru/pdf-to-word");
        driver.findElement(By.cssSelector("input[type=file]")).sendKeys(new File(pdfName).getAbsolutePath());
        UtilsForIO.sleep(2);
        driver.findElement(By.xpath("//*[@id=\"qg-toast\"]/div[3]/div[2]/p[2]/button")).click();
        driver.findElement(By.xpath("//*[@id=\"page_function_container\"]/div[1]/button")).click();
        UtilsForIO.sleep(10);
        String downloadUrl = driver.findElement(By.xpath("//*[@id=\"page_function_container\"]/div/div[1]/div/div[1]/div[8]/div[2]/div/div/div[2]/div[3]/a"))
                                    .getAttribute("href");
        UtilsForIO.downloadFile(downloadUrl, wordName);
    }

    public void closeDriver() {
        driver.close();
        driver.quit();
    }
}