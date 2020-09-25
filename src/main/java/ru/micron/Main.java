package ru.micron;

import ru.micron.Json.StudentJson;

public class Main {

    public static void printHelp() {
        System.out.print("Options:\n");
        System.out.printf("\t%s %60s\n", "--help", "Display this information.");
        System.out.printf("\t%s %24s\n", "-json <Stud name> <Grp> <Tchr name>", "Make Student Json.");
        System.out.printf("\t%s %31s\n", "-make <Work number> <GitHub Task URL>", "Make PDF from Student Json.");
        System.exit(-1);
    }

    public static void disableWarning() {
        System.err.close();
        System.setErr(System.out);
    }

    public static void main(String[] args) {
        //disableWarning();

        if (args.length == 4) {
            if (args[0].compareTo("-json") != 0)
                printHelp();
            else
                StudentJson.saveStudentJson(args[1], args[2], args[3]);
        } else if (args.length == 3) {
            if (args[0].compareTo("-json") != 0)
                printHelp();
            else {
                MakeHtml.makeMap(Integer.parseInt(args[1]), MakeHtml.parseUrl(args[2]));
                MakeHtml.makeHtml("./", "titul.ftl", "./index.html");
                MakePdf.makePdf("index.html", "pdf.pdf");
                System.out.println("Work done!");
            }
        } else
            printHelp();
    }
}