package ru.micron.config;

import java.util.Locale;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfiguration {

  private static final String APP_LANGUAGE = "ru";

  @Bean
  public Locale locale() {
    return new Locale(APP_LANGUAGE);
  }

  @Bean
  public ChromeOptions chromeOptions() {
    return new ChromeOptions()
        .addArguments("window-size=800,600")
        .addArguments("window-position=-1200,10");
  }
}
