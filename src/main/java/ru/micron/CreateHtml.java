package ru.micron;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

public class CreateHtml {

    public void makeTitul(String year, String group, String student_snp, String teacher_snp) throws IOException, TemplateException {
        try {
            Configuration cfg = new Configuration(Configuration.VERSION_2_3_30);
            cfg.setDirectoryForTemplateLoading(new File("src/main/resources/static/templates/"));
            cfg.setDefaultEncoding("UTF-8");
            cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);

            Map<String, Object> map = new HashMap<>();
            map.put("year", year);
            map.put("group", group);
            map.put("student_snp", student_snp);
            map.put("teacher_snp", teacher_snp);
            map.put("prac_number", "test");
            map.put("target_content", "test");
            map.put("teor_content", "test");
            map.put("step_by_step", "test");
            String out = new Scanner(new URL("https://raw.githubusercontent.com/m1cron/java_rtu/master/src/ru/micron/task2/Dog.java").openStream(), "UTF-8").useDelimiter("\\A").next();
            map.put("all_code", out);

            map.put("conclusion_content", "test");
            map.put("literature_used_content", "test");

            Template template = cfg.getTemplate( "titul.ftl");

            //Writer console = new OutputStreamWriter(System.out);
            //template.process(map, console);
            //console.flush();

            Writer file = new FileWriter(new File("titul.html"));
            template.process(map, file);
            file.flush();
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        }
    }
}
