package ru.micron;

public class Main {
    public static void disableWarning() {
        System.err.close();
        System.setErr(System.out);
    }

    public static void main(String... args) {
        disableWarning();

        CreateHtml html = new CreateHtml();
        html.makeTitul("ИКБО-13-19", "Голубков Максим Владимирович", "Владимирович Владимирович Максим");

        MakePdf.makePdf("", "pdf.pdf", "titul.html");
        System.out.println("Work done!");
    }
}