package ru.micron.config;

import java.util.Locale;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.micron.converting.MakeDocuments;
import ru.micron.formatting.ReportDate;
import ru.micron.formatting.ReportFormatting;
import ru.micron.model.ReportHandler;
import ru.micron.ui.MainFrame;
import ru.micron.ui.MainPanel;

@Configuration
public class AppConfiguration {

  public static final String APP_LANGUAGE = "ru";
  public static final String APP_ENCODING = "windows-1251";
  public static final String FILE_ENCODING = "file.encoding";

  @Bean
  public Locale getProjectLocale() {
    return new Locale(APP_LANGUAGE);
  }

  @Bean
  public MakeDocuments makeDocuments() {
    return new MakeDocuments(getProjectLocale(), chromeOptions());
  }

  @Bean
  public ReportDate getReportDate() {
    return new ReportDate(getProjectLocale());
  }

  @Bean
  public ReportFormatting reportFormatting() {
    return new ReportFormatting();
  }

  @Bean
  public ReportHandler reportHandler() {
    return new ReportHandler();
  }

  @Bean
  public ChromeOptions chromeOptions() {
    return new ChromeOptions()
        .addArguments("window-size=800,600")
        .addArguments("window-position=-1200,10");
  }

  @Bean
  public MainPanel mainPanel() {
    return new MainPanel(reportHandler(), makeDocuments(), getReportDate(), reportFormatting());
  }

  @Bean
  public MainFrame mainFrame() {
    return new MainFrame(mainPanel());
  }

}
