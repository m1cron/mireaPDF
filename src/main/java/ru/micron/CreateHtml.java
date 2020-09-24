package ru.micron;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.*;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import ru.micron.Json.GetGithubFiles;
import ru.micron.Json.GetJsonFromFile;

public class CreateHtml {
    Map<String, String> map;

    public CreateHtml() {
        map = new HashMap<>();
    }

    public void makeMap(String gitUrl, int pracNum, String group, String student, String teacher){
        map.put("year", "2020");
        map.put("group", group);
        map.put("student", student);
        map.put("teacher", teacher);


        GetJsonFromFile.parseJson(map, pracNum);

        map.put("step_by_step", "test"); // ?

        GetGithubFiles gh = new GetGithubFiles();
        gh.recursSearchGit(gitUrl);
        map.put("all_code", gh.getCode());
    }

    public void makeTitul() {
        try {
            System.out.println("Creating HTML!");
            Configuration cfg = new Configuration(Configuration.VERSION_2_3_30);
            cfg.setDirectoryForTemplateLoading(new File("src/main/resources/static/templates/"));
            cfg.setDefaultEncoding("UTF-8");
            cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
            Template template = cfg.getTemplate( "titul.ftl");
            File htmlFile = new File("titul.html");
            Writer html = new FileWriter(htmlFile);
            template.process(map, html);
            html.flush();
            html.close();
            htmlFile.deleteOnExit();
        } catch (IOException | TemplateException e) {
            e.printStackTrace();
        }
    }
}
