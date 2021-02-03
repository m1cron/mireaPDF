package ru.micron.config;

import com.google.gson.Gson;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import ru.micron.ReportDate;
import ru.micron.MakeDocuments;

@Configuration
@PropertySource("classpath:application.properties")
@ComponentScan("ru.micron")
public class AppConfiguration {

    @Bean
    public MakeDocuments makeDocuments() {
        return new MakeDocuments();
    }

    @Bean
    public ReportDate getDate() {
        return new ReportDate();
    }

    @Bean
    public ChromeOptions chromeOptions() {
        return new ChromeOptions()
                .addArguments("window-size=800,600")
                .addArguments("window-position=-1200,10");
    }

}
