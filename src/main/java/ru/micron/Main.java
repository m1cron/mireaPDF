package ru.micron;

public class Main {
    public static void disableWarning() {
        System.err.close();
        System.setErr(System.out);
    }

    public static void main(String... args) {
        disableWarning();

        CreateHtml html = new CreateHtml();
        html.makeMap("https://api.github.com/repos/m1cron/java_rtu/contents/src/ru/micron/task5", 2,"ИКБО-13-19", "Голубков Максим Владимирович", "Владимирович Владимирович Максим");
        html.makeTitul();

        MakePdf.makePdf("", "pdf.pdf", "titul.html");
        System.out.println("Work done!");
    }
}