import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import ru.micron.MakeHtml;
import ru.micron.json.GetJsonFromFile;
import ru.micron.json.Github;
import ru.micron.json.StudentJson;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

public class MakeHtmlTest {
    private static Map<String, String> map;

    public static void makeMap(int pracNum) {
        map = new HashMap<>();
        map.put("year", "2020");
        map.put("step_by_step", "test"); // ?

        StudentJson.getStudentJson(map);
        GetJsonFromFile.parseJson(map, pracNum);

        map.put("all_code", "2734253457");
    }

    public static void makeHtml(String templatesDir, String ftlFile, String htmlFile) {
        try {
            System.out.print("Creating HTML!\n");
            Configuration cfg = new Configuration(Configuration.VERSION_2_3_30);
            cfg.setDirectoryForTemplateLoading(new File(templatesDir));
            cfg.setDefaultEncoding("UTF-8");
            cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
            Template template = cfg.getTemplate(ftlFile);
            File htmlOpen = new File(htmlFile);
            Writer html = new FileWriter(htmlOpen);
            template.process(map, html);
            html.flush();
            html.close();
            // htmlOpen.deleteOnExit();
        } catch (IOException | TemplateException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        makeMap(3);
        makeHtml("./templates", "index.ftl", "./index.html");
        System.out.print("Work done!");
    }
}