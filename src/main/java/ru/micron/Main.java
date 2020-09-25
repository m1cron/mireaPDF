package ru.micron;

import ru.micron.Json.StudentJson;

public class Main {
    static private final String gihubApiUrl = "https://api.github.com/repos/m1cron/java_rtu/contents/src/ru/micron/task3";

    public static void disableWarning() {
        System.err.close();
        System.setErr(System.out);
    }

    public static void main(String ... args) {
        disableWarning();

        MakeHtml.makeMap(gihubApiUrl, 1);
        //StudentJson.saveStudentJson("maxim s","dgbhdghsdgh", "1234");
        MakeHtml.makeHtml("./", "titul.ftl", "./index.html");
        MakePdf.makePdf("index.html", "pdf.pdf");
        System.out.println("Work done!");
    }
}