package ru.micron;

public class Main {
    static private final String gihubApiUrl = "https://api.github.com/repos/m1cron/java_rtu/contents/src/ru/micron/task2";

    public static void disableWarning() {
        System.err.close();
        System.setErr(System.out);
    }

    public static void main(String ... args) {
        //disableWarning();

        MakeHtml.makeMap(gihubApiUrl, 1);
        MakeHtml.makeHtml("titul");

        MakePdf.makePdf("titul.html", "pdf.pdf");
        System.out.println("Work done!");
    }
}