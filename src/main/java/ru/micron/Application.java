package ru.micron;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Locale;
import java.util.Map;
import javax.swing.SwingUtilities;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import ru.micron.config.AppConfiguration;
import ru.micron.reporting.ReportConstants;
import ru.micron.ui.MainFrame;

@SpringBootApplication
public class Application {

  public static void main(String[] args) throws IOException {
    System.setProperty(AppConfiguration.FILE_ENCODING, AppConfiguration.APP_ENCODING);

    ConfigurableApplicationContext context =
        new SpringApplicationBuilder(AppConfiguration.class)
            .headless(false)
            .run(args);

    SwingUtilities.invokeLater(() -> {
      MainFrame mainFrame = context.getBean(MainFrame.class);
      mainFrame.run();
    });


/*    var templateResolver = new ClassLoaderTemplateResolver();
    //templateResolver.setPrefix("classpath:/");
    templateResolver.setSuffix(".html");
    templateResolver.setTemplateMode("HTML");
    templateResolver.setCacheable(true);

    TemplateEngine templateEngine = new TemplateEngine();
    templateEngine.setTemplateResolver(templateResolver);

    var tmp = new File("GAVNO.html");
    Writer html = new FileWriter(tmp);
    StringWriter temp = new StringWriter();
    Context context = new Context(new Locale("ru"), Map.of("literature_content", "World"));
    templateEngine.process("template.html", context, temp);
    System.out.println(temp);*/
  }
}