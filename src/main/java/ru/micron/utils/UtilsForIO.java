package ru.micron.utils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;

abstract public class UtilsForIO {

    public static String readFile(String fileDir) throws IOException {
        Path fileName = Path.of(fileDir);
        return Files.readString(fileName);
    }

    public static void showThreadsTrace() {
        Set<Thread> threads = Thread.getAllStackTraces().keySet();
        System.out.println("====================================");
        for (Thread t : threads) {
            String name = t.getName();
            Thread.State state = t.getState();
            int priority = t.getPriority();
            String type = t.isDaemon() ? "Daemon" : "Normal";
            System.out.printf("%-20s \t %s \t %d \t %s\n", name, state, priority, type);
        }
        System.out.println("====================================");
    }
}