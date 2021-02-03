package ru.micron;

public class Main {

    public static void printHelp() {
        System.out.print("Options:\n");
        System.out.printf("\t%s %60s\n", "--help", "Display this information.");
        System.out.printf("\t%s %24s\n", "-json <Stud name> <Grp> <Tchr name>", "Make Student Json.");
        System.out.printf("\t%s %31s\n", "-make <Make DOCX? (0 or 1)> <GitHub Task URL>", "Make documents.");
        System.exit(-1);
    }

    public static void main(String[] args) {
        new GUI().run();
    }
}