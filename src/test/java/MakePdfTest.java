import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.File;
import java.util.concurrent.TimeUnit;


public class MakePdfTest {

    public static void main(String[] argv) {
        String fileInputPath = "./templates/index.html";
        File file = new File(fileInputPath);

        System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver.exe");

        WebDriver driver = new ChromeDriver();


        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

        driver.get("https://deftpdf.com/ru/html-to-pdf");

        driver.findElement(By.xpath("//input[@type='file']")).sendKeys(file.getAbsolutePath());
        driver.findElement(By.xpath("//a[@id='HTMLFileToPDF']")).click();

        driver.findElement(By.className("download_file_name")).click();
    }
}