package ru.micron;

import static ru.micron.ui.UIUtils.FRAME_HEIGHT;
import static ru.micron.ui.UIUtils.FRAME_WIDTH;
import static ru.micron.ui.UIUtils.ICON_PNG;
import static ru.micron.ui.UIUtils.TITLE;

import java.awt.EventQueue;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import ru.micron.ui.MainPanel;

@SpringBootApplication(scanBasePackages = "ru.micron")
public class Application extends JFrame {

  private static final String APP_ENCODING = "windows-1251";
  private static final String FILE_ENCODING = "file.encoding";

  public static void main(String[] args) {
    System.setProperty(FILE_ENCODING, APP_ENCODING);

    var context =
        new SpringApplicationBuilder(Application.class)
            .headless(false)
            .run(args);

    EventQueue.invokeLater(() -> {
      var app = context.getBean(Application.class);

      app.setTitle(TITLE);
      app.setIconImage(new ImageIcon(Application.class.getResource(ICON_PNG)).getImage());
      app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      app.setResizable(false);
      app.setLocationRelativeTo(null);
      app.add(context.getBean(MainPanel.class));
      app.pack();
      app.setSize(FRAME_WIDTH, FRAME_HEIGHT);
      app.setVisible(true);
    });
  }
}