package ru.micron.utils;

import org.apache.commons.io.FileUtils;
import ru.micron.MyProxy;

import java.io.*;
import java.net.Proxy;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.TimeUnit;

abstract public class UtilsForIO {

    public static String readFile(String fileDir) throws IOException {
        Path fileName = Path.of(fileDir);
        return Files.readString(fileName);
    }

    public static String scanInStream(InputStream stream) {
        try (Scanner scanner = new Scanner(stream, Charset.defaultCharset()).useDelimiter("\\A")) {
            return scanner.hasNext() ? scanner.next() : "";
        } finally {
            try {
                stream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static String readStringFromURL(String url, MyProxy myProxyproxy, Proxy recursProxy) {
        InputStream urlCon;
        try {
            urlCon = new URL(url).openConnection(myProxyproxy.getProxy()).getInputStream();
        } catch (IOException e) {
            return readStringFromURL(url, myProxyproxy, myProxyproxy.getNewProxy());
        }
        return scanInStream(urlCon);
    }

    public static String readStringFromURL(String url) {
        InputStream urlCon = null;
        try {
            urlCon = new URL(url).openConnection().getInputStream();
        } catch (IOException e) {
            System.out.println("Turn on proxy!");
        }
        return scanInStream(urlCon);
    }

    public static void sleep(int sec) {
        try {
            TimeUnit.SECONDS.sleep(sec);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void downloadFile(String downloadUrl, String fileName) {
        try {
            FileUtils.copyURLToFile(
                    new URL(downloadUrl),
                    new File("./" + fileName),
                                Integer.MAX_VALUE,
                                Integer.MAX_VALUE);
            System.out.println("Download " + fileName + " OK!");
        } catch (IOException e) {
            System.out.println("Download " + fileName + " FAIL!");
        }
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