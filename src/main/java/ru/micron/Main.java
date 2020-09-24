package ru.micron;

public class Main {
    static private final String gihubApiUrl = "https://api.github.com/repos/m1cron/java_rtu/contents/src/ru/micron/task5";

    public static void disableWarning() {
        System.err.close();
        System.setErr(System.out);
    }

    public static void main(String ... args) {
        disableWarning();

        CreateHtml.makeMap(gihubApiUrl, 2);
        CreateHtml.makeHtml();

        MakePdf.makePdf("", "pdf.pdf", "titul.html");
        System.out.println("Work done!");
    }
}