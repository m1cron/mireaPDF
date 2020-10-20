package ru.micron;

import ru.micron.json.StudentJson;

public class Main {

    public static void printHelp() {
        System.out.print("Options:\n");
        System.out.printf("\t%s %60s\n", "--help", "Display this information.");
        System.out.printf("\t%s %24s\n", "-json <Stud name> <Grp> <Tchr name>", "Make Student Json.");
        System.out.printf("\t%s %31s\n", "-make <Work number> <GitHub Task URL>", "Make PDF from Student Json.");
        System.exit(-1);
    }

    public static void main(String[] args) {
        if (args.length == 4) {
            if (args[0].compareTo("-json") != 0)
                printHelp();
            else
                new StudentJsonIO().saveJson(new StudentJson(args[1], args[2], args[3]));
        } else if (args.length == 3) {
            if (args[0].compareTo("-make") != 0)
                printHelp();
            else {
                MakeMap map = new MakeMap();
                map.makeMap(Integer.parseInt(args[1]), args[2]);
                new MakeHtml("./templates", "index.ftl")
                        .makeHtml(map.getMap(), "./index.html");
                MakeDocuments docs = new MakeDocuments();
                docs.makePdf("./index.html", "pdf.pdf");
                docs.closeDriver();
                System.out.print("Work done!");
            }
        } else if (args.length == 0)
            new GUI().run();
        else
            printHelp();
    }
}