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
import ru.micron.Json.StudentJson;

public class MakeHtml {
    private static Map<String, String> map;

    public static void makeMap(String gitUrl, int pracNum) {
        map = new HashMap<>();
        map.put("year", "2020");
        map.put("step_by_step", "test"); // ?

        StudentJson.getStudentJson(map);
        GetJsonFromFile.parseJson(map, pracNum);

        GetGithubFiles gh = new GetGithubFiles();
        gh.recursSearchGit(gitUrl);
        map.put("all_code", gh.getCode());
    }

    public static void makeHtml(String ftlFile, String htmlFile) {
        try {
            System.out.println("Creating HTML!");
            Configuration cfg = new Configuration(Configuration.VERSION_2_3_30);
            cfg.setDirectoryForTemplateLoading(new File("src/main/resources/static/templates/"));
            cfg.setDefaultEncoding("UTF-8");
            cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
            Template template = cfg.getTemplate(ftlFile);
            File htmlOpen = new File(htmlFile);
            Writer html = new FileWriter(htmlOpen);
            template.process(map, html);
            html.flush();
            html.close();
            //htmlOpen.deleteOnExit();
        } catch (IOException | TemplateException e) {
            e.printStackTrace();
        }
    }
}
