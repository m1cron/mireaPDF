package ru.micron.converting;

import java.nio.file.Path;
import javax.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.SystemUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ChromeWebDriverHolder {

  private static final String DRIVER_NAME = "chromedriver";
  private static final String WIN_DRIVER_SUFFIX = ".exe";
  private static final String DRIVER_NAME_PROPERTY = "webdriver.chrome.driver";
  private static final ChromeOptions CHROME_OPTIONS = new ChromeOptions()
      .addArguments("window-size=800,600")
      .addArguments("window-position=-1200,10");

  static {
    var driverPath = new StringBuilder(DRIVER_NAME);
    if (SystemUtils.IS_OS_WINDOWS) {
      driverPath.append(WIN_DRIVER_SUFFIX);
    }
    System.setProperty(DRIVER_NAME_PROPERTY, Path.of(driverPath.toString()).toString());
  }

  private WebDriver driver;

  public WebDriver getDriver() {
    if (driver == null) {
      driver = new ChromeDriver(CHROME_OPTIONS);
    }
    return driver;
  }

  @PreDestroy
  public void destroy() {
    if (driver != null) {
      driver.close();
      driver.quit();
    }
    driver = null;
  }
}
