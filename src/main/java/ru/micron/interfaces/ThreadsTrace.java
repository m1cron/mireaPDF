package ru.micron.interfaces;

import java.util.Set;

interface ThreadsTrace {

    static void showThreadsTrace() {
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
