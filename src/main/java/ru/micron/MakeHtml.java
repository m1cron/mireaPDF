package ru.micron;

import java.io.*;
import java.util.*;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import ru.micron.json.GetGithubFiles;
import ru.micron.json.GetJsonFromFile;
import ru.micron.json.StudentJson;

public class MakeHtml {
    private static Map<String, String> map;

    public static String parseUrl(String url) {
        return url.replace("github.com", "api.github.com/repos")
                    .replace("/tree/master/", "/contents/")
                    .replace("/blob/master/", "/contents/");
    }

    public static void makeMap(int pracNum, String gitUrl) {
        map = new HashMap<>();
        map.put("year", "2020");
        map.put("step_by_step", "test"); // ?

        StudentJson.getStudentJson(map);
        GetJsonFromFile.parseJson(map, pracNum);

        GetGithubFiles gh = new GetGithubFiles("1.20.97.181", 34102, true);
        gh.recursSearchGit(gitUrl);
        map.put("all_code", gh.getCode());
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
            htmlOpen.deleteOnExit();
        } catch (IOException | TemplateException e) {
            e.printStackTrace();
        }
    }
}
