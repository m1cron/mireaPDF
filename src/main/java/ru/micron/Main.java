package ru.micron;

public class Main {
    static private final String gihubApiUrl = "https://api.github.com/repos/m1cron/java_rtu/contents/src/ru/micron/task3";

    public static void disableWarning() {
        System.err.close();
        System.setErr(System.out);
    }

    public static void main(String ... args) {
        disableWarning();

        MakeHtml.makeMap(gihubApiUrl, 1);
        MakeHtml.makeHtml("titul1.ftl", "index.html");
        MakePdf.makePdf("index.html", "pdf.pdf");
        System.out.println("Work done!");
    }
}