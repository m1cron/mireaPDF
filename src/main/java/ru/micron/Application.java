package ru.micron;

import javax.swing.SwingUtilities;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import ru.micron.config.AppConfiguration;
import ru.micron.ui.MainFrame;

@SpringBootApplication
public class Application {

  public static void main(String[] args) {
    System.setProperty(AppConfiguration.FILE_ENCODING, AppConfiguration.APP_ENCODING);

    ConfigurableApplicationContext context =
        new SpringApplicationBuilder(AppConfiguration.class)
            .headless(false)
            .run(args);

    SwingUtilities.invokeLater(() -> {
      MainFrame mainFrame = context.getBean(MainFrame.class);
      mainFrame.run();
    });
  }
}