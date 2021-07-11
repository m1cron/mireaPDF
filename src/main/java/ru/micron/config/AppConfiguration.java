package ru.micron.config;

import java.util.Locale;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfiguration {

  private static final String APP_LANGUAGE = "ru";

  @Bean
  public Locale locale() {
    return new Locale(APP_LANGUAGE);
  }
}
