package ru.micron.utils;

import com.google.common.io.Resources;
import org.apache.commons.io.FileUtils;
import ru.micron.json.MyProxy;

import java.io.*;
import java.net.Proxy;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

abstract public class UtilsForIO {

    public static String readFileFromRes(final String fileName, Charset charset) throws IOException {
        return Resources.toString(Resources.getResource(fileName), charset);
    }

    public static void writeFile(String fileDir, String str) throws IOException {
        Path fileName = Path.of(fileDir);
        Files.writeString(fileName, str);
    }

    public static String readFile(String fileDir) throws IOException {
        Path fileName = Path.of(fileDir);
        return Files.readString(fileName);
    }

    public static String readStringFromURL(String url, MyProxy myProxyproxy, Proxy recursProxy) {
        InputStream urlCon;
        try {
            urlCon = new URL(url).openConnection(myProxyproxy.getProxy()).getInputStream();
        } catch (IOException e) {
            return readStringFromURL(url, myProxyproxy, myProxyproxy.getNewProxy());
        }
        assert urlCon != null;
        try (Scanner scanner = new Scanner(urlCon, Charset.defaultCharset()).useDelimiter("\\A")) {
            return scanner.hasNext() ? scanner.next() : "";
        }
    }

    public static String readStringFromURL(String url) {
        InputStream urlCon = null;
        try {
            urlCon = new URL(url).openStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert urlCon != null;
        try (Scanner scanner = new Scanner(urlCon, Charset.defaultCharset()).useDelimiter("\\A")) {
            return scanner.hasNext() ? scanner.next() : "";
        }
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
                    10000,
                    10000);
            System.out.println("Download " + fileName + " OK!");
        } catch (IOException e) {
            System.out.println("Download " + fileName + " FAIL!");
        }
    }
}