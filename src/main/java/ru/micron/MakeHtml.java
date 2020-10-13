package ru.micron;

import java.io.*;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

public class MakeHtml {
    private final String templatesDir;
    private final String ftlFile;

    public MakeHtml(String templatesDir, String ftlFile) {
        this.templatesDir = templatesDir;
        this.ftlFile = ftlFile;
    }

    public void makeHtml(Map<String, String> map, String htmlFile) {
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
            //htmlOpen.deleteOnExit();
        } catch (IOException | TemplateException e) {
            e.printStackTrace();
        }
    }
}