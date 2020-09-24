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

    //public void makeMap()

    public void makeTitul(String gitUrl, String group, String student, String teacher) {
        try {
            System.out.println("Creating HTML!");
            Configuration cfg = new Configuration(Configuration.VERSION_2_3_30);
            cfg.setDirectoryForTemplateLoading(new File("src/main/resources/static/templates/"));
            cfg.setDefaultEncoding("UTF-8");
            cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);

            Map<String, Object> map = new HashMap<>();

            // вызвать метод филл стадент и передать туда хеш мапу (заполняем хешмапу)
            map.put("year", "2020");
            map.put("group", group);
            map.put("student", student);
            map.put("teacher", teacher);


            map.put("prac_number", "prac_number");
            map.put("target_content", "target_content");
            map.put("teor_content", "target_content");
            map.put("conclusion_content", "conclusion_content");
            map.put("literature_content", "literature_content");
            //GetJsonFromFile.parseJson(map, 2); // <---------

            map.put("step_by_step", "test"); // ?


            GetGithubFiles gh = new GetGithubFiles();
            gh.recursSearchGit(gitUrl);
            map.put("all_code", gh.getCode());

            Template template = cfg.getTemplate( "titul.ftl");
            Writer file = new FileWriter(new File("titul.html"));
            template.process(map, file);
            file.flush();
            file.close();
            System.out.println("Done!");
        } catch (IOException | TemplateException e) {
            e.printStackTrace();
        }
    }
}
