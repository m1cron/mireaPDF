package ru.micron;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.File;
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
    }

    public void makePdf(String html) {
        File file = new File(html);

        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get("https://deftpdf.com/ru/html-to-pdf");
        driver.findElement(By.xpath("//input[@type='file']")).sendKeys(file.getAbsolutePath());
        driver.findElement(By.xpath("//a[@id='HTMLFileToPDF']")).click();
        driver.findElement(By.className("download_file_name")).click();
        try {
            TimeUnit.SECONDS.sleep(15);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        driver.close();
        driver.quit();
    }
}